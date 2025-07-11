package com.tpcindia.professionalcouriersapp.viewModel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tpcindia.professionalcouriersapp.data.db.dao.PdfDao
import com.tpcindia.professionalcouriersapp.data.db.database.DatabaseProvider
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.entity.PdfEntity
import com.tpcindia.professionalcouriersapp.data.repository.PdfRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class PdfViewModel(application: Application) : AndroidViewModel(application) {

    private val _pdfListState = MutableStateFlow<List<PdfEntity>>(emptyList())
    val pdfListState: StateFlow<List<PdfEntity>> = _pdfListState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val pdfDao: PdfDao = DatabaseProvider.getDatabase(application).pdfDao()
    private val repository: PdfRepository = PdfRepository(NetworkService())

    fun getAllPdfDocuments(uniqueUser: String, context: Context) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true
            try {
                val pdfList = repository.getPDFs(uniqueUser = uniqueUser, pdfDao)
                _pdfListState.value = (_pdfListState.value + pdfList).convertPdfDataToUniqueData()
            } catch (e: Exception) {
                Toast.makeText(context, "Error fetching PDF documents: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getTopPdfs(uniqueUser: String, branch: String, userCode: String, context: Context) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true
            try {
                val result = repository.getTopPdfs(branch, userCode)
                if (result.isSuccess) {
                    val pdfDetails = result.getOrNull()
                    val topPdfData = pdfDetails?.mapNotNull { detail ->
                        if (detail.pdfAddress != null) {
                            PdfEntity(
                                fileName = detail.consignmentNumber + ".pdf",
                                pdfData = detail.pdfAddress!!,
                                uniqueUser = uniqueUser
                            )
                        } else {
                            null // Skip this item if pdfAddress is null
                        }
                    }?.toMutableList() ?: mutableListOf()
                    _pdfListState.value = (_pdfListState.value + topPdfData).convertPdfDataToUniqueData()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error refreshing PDF documents: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearAllPDFs(context: Context) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true
            try {
                repository.clearPDFs(pdfDao)
                _pdfListState.value = emptyList()
            } catch (e: Exception) {
                Toast.makeText(context, "Error clearing all the PDF documents: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun viewPdf(context: Context, pdf: PdfEntity) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true
            try {
                val file = savePdfToTempFile(context, pdf)
                if (file != null) {
                    openPdfFile(context, file.absolutePath)
                } else {
                    Toast.makeText(context, "Failed to open PDF file.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error opening PDF: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun openPdfFile(context: Context, filePath: String) {
        try {
            val file = File(filePath)
            val uri: Uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(Intent.createChooser(intent, "Open PDF with"))
            } else {
                Toast.makeText(context, "No PDF viewer found. Please install a PDF viewer to open this file.", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error opening PDF: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun savePdfToDownloads(context: Context, pdf: PdfEntity) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true
            try {
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file = File(downloadsDir, pdf.fileName)
                withContext(Dispatchers.IO) {
                    FileOutputStream(file).use { fos ->
                        fos.write(pdf.pdfData)
                        fos.flush()
                    }
                }
                Toast.makeText(context, "PDF saved to Downloads", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Error saving PDF to Downloads: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun savePdfToTempFile(context: Context, pdf: PdfEntity): File? {
        return try {
            val tempFile = File(context.cacheDir, pdf.fileName)
            withContext(Dispatchers.IO) {
                FileOutputStream(tempFile).use { fos ->
                    fos.write(pdf.pdfData)
                    fos.flush()
                }
            }
            tempFile
        } catch (e: Exception) {
            null
        }
    }

    private fun List<PdfEntity>.convertPdfDataToUniqueData(): List<PdfEntity> {
        return this.associateBy { it.fileName }.values.toList()
    }
}

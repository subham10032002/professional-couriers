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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class PdfViewModel(application: Application) : AndroidViewModel(application) {

    private val _pdfListState = MutableStateFlow<List<PdfEntity>>(emptyList())
    val pdfListState: StateFlow<List<PdfEntity>> = _pdfListState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val pdfDao: PdfDao = DatabaseProvider.getDatabase(application).pdfDao()
    private val repository: PdfRepository = PdfRepository(NetworkService())

    fun getAllPdfDocuments(branch: String, context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
                try {
                    val pdfList = repository.getPDFs(branch, pdfDao)
                    _pdfListState.value = pdfList
                } catch (e: Exception) {
                   Toast.makeText(context, "Error fetching PDF documents: ${e.message}", Toast.LENGTH_SHORT).show()
                } finally {
                    _isLoading.value = false
                }
        }
    }

    fun viewPdf(context: Context, pdf: PdfEntity) {
        _isLoading.value = true
        try {
            val file = savePdfToTempFile(context, pdf)
            if (file != null) {
                openPdfFile(context, file)
            } else {
                Toast.makeText(context, "Failed to open PDF file.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error opening PDF: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            _isLoading.value = false
        }
    }

    private fun openPdfFile(context: Context, file: File) {
        try {
            val uri: Uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }

            // Check if there is an app to handle this intent
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
        _isLoading.value = true
        try {
            viewModelScope.launch {
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file = File(downloadsDir, pdf.fileName)
                FileOutputStream(file).use { fos ->
                    fos.write(pdf.pdfData)
                    fos.flush()
                }
                Toast.makeText(context, "PDF saved to Downloads", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error saving PDF to Downloads: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            _isLoading.value = false
        }
    }

    private fun savePdfToTempFile(context: Context, pdf: PdfEntity): File? {
        return try {
            val tempFile = File(context.cacheDir, pdf.fileName)
            tempFile.writeBytes(pdf.pdfData)
            tempFile
        } catch (e: Exception) {
            null
        }
    }
}

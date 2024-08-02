package com.tpcindia.professionalcouriersapp.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpcindia.professionalcouriersapp.R
import com.tpcindia.professionalcouriersapp.data.model.MenuItem
import com.tpcindia.professionalcouriersapp.data.model.entity.PdfEntity
import com.tpcindia.professionalcouriersapp.ui.components.TopBanner
import com.tpcindia.professionalcouriersapp.viewModel.PdfViewModel

@Composable
fun PdfScreen(branch: String, viewModel: PdfViewModel) {
    val context = LocalContext.current
    val pdfList by viewModel.pdfListState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllPdfDocuments(branch, context)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            TopBanner(showMenuIcon = true,
                menuItem = listOf(
                    MenuItem(
                        text = "Clear PDFs",
                        onClick = {
                            viewModel.clearAllPDFs(context)
                            Toast.makeText(context, "PDFs cleared", Toast.LENGTH_SHORT).show()
                        }
                    )
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "PDFs",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {
                items(pdfList) { pdf ->
                    PdfItem(pdf = pdf, viewModel = viewModel, context = context)
                }
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun PdfItem(
    pdf: PdfEntity,
    viewModel: PdfViewModel,
    context: Context
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.tpc_pdf_24),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = pdf.fileName,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(onClick = {
            viewModel.viewPdf(context, pdf)
        }) {
            Icon(painter = painterResource(id = R.drawable.tpc_preview), contentDescription = "View PDF")
        }
        IconButton(onClick = {
            viewModel.savePdfToDownloads(context, pdf)
        }) {
            Icon(painter = painterResource(id = R.drawable.tpc_file_download), contentDescription = "Download PDF")
        }
    }
    HorizontalDivider()
}

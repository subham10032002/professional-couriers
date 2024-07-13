package com.tpcindia.professionalcouriersapp.data.utils

import android.content.Context
import com.itextpdf.kernel.colors.DeviceGray
import com.itextpdf.barcodes.Barcode128
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.UnitValue

class PdfGenerator {

    fun createPdf(context: Context): ByteArray {
        val stream = ByteArrayOutputStream()
        val pdfWriter = PdfWriter(stream)
        val pdfDocument = PdfDocument(pdfWriter)
        val document = Document(pdfDocument)

        document.setMargins(20f, 20f, 20f, 20f)

        val table = Table(UnitValue.createPercentArray(floatArrayOf(1f, 1f, 1f, 1f)))
        table.setWidth(UnitValue.createPercentValue(100f))

        // Add header row
        table.addCell(createCell("Shipper", true))
        table.addCell(createCell("Date: 01-07-2024 17:52:13", true, 3))

        // Add second row
        table.addCell(createCell("From :", false))
        table.addCell(createCell("ASTRAPIA", false, 3))

        // Add third row
        table.addCell(createCell("BLR", false))
        table.addCell(createCell("", false, 3))

        // Add fourth row with contact
        table.addCell(createCell("Contact: 9880599208", false, 4))

        // Add more rows as necessary
        table.addCell(createCell("Content :", false))
        table.addCell(createCell("Value: 0.0", false, 3))

        // Add barcode row
//        val barcode = Barcode128(pdfDocument)
//        barcode.code = "barcodeCode"
//        val barcodeImage = Image(barcode.createFormXObject(pdfDocument))
//        barcodeImage.scaleToFit(150f, 50f)

//        val barcodeCell = Cell(1, 4).add(barcodeImage)
//        barcodeCell.setBorder(Border.NO_BORDER)
//        barcodeCell.setTextAlignment(TextAlignment.CENTER)
//        table.addCell(barcodeCell)

        // Add more rows for remaining data...

        // Add table to document
        document.add(table)

        document.close()
        return stream.toByteArray()
    }

    private fun createCell(content: String, isHeader: Boolean, colSpan: Int = 1): Cell {
        val cell = Cell(1, colSpan).add(Paragraph(content))
        cell.setPadding(5f)
        if (isHeader) {
            cell.setBackgroundColor(DeviceGray(0.8f))
        }
        cell.setBorder(Border.NO_BORDER)
        return cell
    }
}
package com.tpcindia.professionalcouriersapp.data.utils

import android.annotation.SuppressLint
import android.content.Context
import com.itextpdf.kernel.colors.DeviceGray
import com.itextpdf.barcodes.Barcode128
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.borders.SolidBorder
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.UnitValue
import com.tpcindia.professionalcouriersapp.R
import com.tpcindia.professionalcouriersapp.configs.UIConfig
import java.io.InputStream

class PdfGenerator {

    @SuppressLint("ResourceType")
    fun createPdf(context: Context): ByteArray {
        val stream = ByteArrayOutputStream()
        val pdfWriter = PdfWriter(stream)
        val pdfDocument = PdfDocument(pdfWriter)
        val document = Document(pdfDocument)

        document.setMargins(20f, 20f, 20f, 20f)

        val logoStream = context.resources.openRawResource(R.drawable.tpc_logo)
        val logoByteArray = logoStream.readBytes()

        val logoImage = Image(ImageDataFactory.create(logoByteArray))
        logoImage.setWidth(UnitValue.createPercentValue(30f))
        document.add(logoImage)


        // Add company address
        val companyAddress = UIConfig.COMPANY_ADDRESS
        document.add(Paragraph(companyAddress))

        // Create table for main content
        val table = Table(UnitValue.createPercentArray(floatArrayOf(1f, 1f, 1f, 1f)))
        table.setWidth(UnitValue.createPercentValue(100f))

        // Add header row
        table.addCell(createCell("Credit Payment", true, 4))

        // Add divider after header row
        table.addCell(createDividerCell(4))

        // Add consignment number row
        table.addCell(createCell("Consignment Number: XXXXXX", false, 4))

        // Add divider after consignment number row
        table.addCell(createDividerCell(4))

        // Add date row
        table.addCell(createCell("Date: 01-07-2024 17:52:13", false, 4))

        // Add divider after date row
        table.addCell(createDividerCell(4))

        // Add from section
        table.addCell(createCell("From:", false))
        table.addCell(createCell("Name: ABC", false))
        table.addCell(createCell("Contact: 1234567890", false))
        table.addCell(createCell("Address: XYZ", false))

        // Add divider after from section
        table.addCell(createDividerCell(4))

        // Add to section
        table.addCell(createCell("To:", false))
        table.addCell(createCell("Name: DEF", false))
        table.addCell(createCell("Contact: 9876543210", false))
        table.addCell(createCell("Address: UVW", false))

        table.addCell(createDividerCell(4))

//        val barcode = Barcode128(pdfDocument)
//        barcode.setCode("barcodeCode") // Replace with your actual barcode data
//        val barcodeImage = Image(barcode.createFormXObject(pdfDocument))
//        barcodeImage.scaleToFit(150f, 50f)
//        val barcodeCell = Cell(1, 4).add(barcodeImage)
//        barcodeCell.setBorder(com.itextpdf.layout.borders.Border.NO_BORDER)
//        barcodeCell.setTextAlignment(TextAlignment.CENTER)
//        table.addCell(barcodeCell)

        // Add divider after barcode row
        table.addCell(createDividerCell(4))

        // Add row for name, signature, phone, and date
        table.addCell(createCell("Name:", false))
        table.addCell(createCell("Signature:", false))
        table.addCell(createCell("Phone:", false))
        table.addCell(createCell("Date:", false))

        table.addCell(createDividerCell(4))

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

    private fun createDividerCell(colSpan: Int): Cell {
        val cell = Cell(1, colSpan)
        cell.setBorderBottom(SolidBorder(1f))
        return cell
    }

    private fun InputStream.readBytes(): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var length: Int
        while (read(buffer).also { length = it } != -1) {
            outputStream.write(buffer, 0, length)
        }
        return outputStream.toByteArray()
    }
}

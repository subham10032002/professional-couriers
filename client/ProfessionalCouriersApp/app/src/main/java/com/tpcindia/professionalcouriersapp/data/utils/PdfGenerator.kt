package com.tpcindia.professionalcouriersapp.data.utils

import android.annotation.SuppressLint
import android.content.Context
import com.itextpdf.kernel.colors.DeviceGray
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
import com.itextpdf.layout.property.UnitValue
import com.tpcindia.professionalcouriersapp.R
import com.tpcindia.professionalcouriersapp.configs.UIConfig
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CBInfoData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import java.io.InputStream

class PdfGenerator {

    @SuppressLint("ResourceType")
    fun createPdf(
        context: Context,
        creditBookingData: CreditBookingData,
        cbDimensionData: CBDimensionData,
        cbInfoData: CBInfoData
    ): ByteArray {
        val stream = ByteArrayOutputStream()
        val pdfWriter = PdfWriter(stream)
        val pdfDocument = PdfDocument(pdfWriter)
        val document = Document(pdfDocument)

        document.setMargins(20f, 20f, 20f, 20f)

        val logoStream = context.resources.openRawResource(R.drawable.tpc_logo)
        val logoByteArray = logoStream.readBytes()

        val logoImage = Image(ImageDataFactory.create(logoByteArray))
        logoImage.setWidth(UnitValue.createPercentValue(30f))

        // Add logo at the top of the document (optional)
         document.add(logoImage)

        // Add company address
        val companyAddress = UIConfig.COMPANY_ADDRESS
        document.add(Paragraph(companyAddress))

        // Create table for main content
        val table = Table(UnitValue.createPercentArray(floatArrayOf(3f, 5f, 3f, 6f, 4f, 4f, 10f, 6f)))
        table.setWidth(UnitValue.createPercentValue(100f))

        // Add header row
        table.addCell(createCell("Shipper", true, 1))
        table.addCell(createCell(creditBookingData.branch, false, 1))
        table.addCell(createCell("Date: ${creditBookingData.bookingDate}", true, 2))
        table.addCell(createCell("Consignee ", true, 1))
        table.addCell(createCell("Destination ", false, 1))
        table.addCell(createCell(creditBookingData.destination, false, 1))
        table.addCell(createCell(creditBookingData.mode, false, 1))

        // Add divider after header row
        table.addCell(createDividerCell(8))

        // Add second row
        table.addCell(createCell("From :\n ${creditBookingData.clientName}", false, 4))
        table.addCell(createCell("To :\n ${creditBookingData.consigneeName}", false, 3))
        table.addCell(createCell("Weight : ${creditBookingData.weight}", false, 1))
        table.addCell(createDividerCell(8))

        table.addCell(createCell("Add Shipper addr: ", true, 4))
        table.addCell(createCell("Add Consignee addr: ${creditBookingData.pincode}", false, 3))
        table.addCell(createCell("Pcs : ${creditBookingData.noOfPsc}", false, 1))
        table.addCell(createDividerCell(8))

        table.addCell(createCell("Add Shipper contact: ", false, 4))
        table.addCell(createCell("Add Consignee contact", false, 3))
        table.addCell(createCell(" ", false, 1))
        table.addCell(createDividerCell(8))

        val details = """
            Invoice No: ${cbInfoData.invoiceNumber}
            Product: ${cbInfoData.product}
            Value: ${cbInfoData.declaredValue}
            Eway Bill: ${cbInfoData.ewayBill}
        """.trimIndent()
        table.addCell(createCell(details, false, 4))
        table.addCell(createCell("Add Barcode ", true, 3))
        table.addCell(createCell("Mode: ${creditBookingData.mode}", false, 1))
        table.addCell(createDividerCell(8))

        val details2 = """
            This is a Non-negotiable consignment note subject to standard conditions of carriage.
            Carrier's liability limited to Rs.100 Only.
        """.trimIndent()
        table.addCell(createCell("Awb No: ${creditBookingData.consignmentNumber}", false, 4))
        table.addCell(createCell(details2, false, 3))
        table.addCell(createCell("Credit Booking ", true, 1))
        table.addCell(createDividerCell(8))

        val details3 = """
            Received by consignee in good order/condition.
            No Claims/Complaints will be accepted after 30 days of booking.
        """.trimIndent()

        // Add the logo in the "Add logo:" section
        val logoCell = Cell(1, 4)
        logoImage.setWidth(UnitValue.createPercentValue(30f))
        logoCell.add(logoImage)
        logoCell.setPadding(5f)
        logoCell.setBorder(Border.NO_BORDER)
        table.addCell(logoCell)

        table.addCell(createCell(details3, true, 4))
        table.addCell(createDividerCell(8))

        val details4 = "Name:                                   Phone:"
        table.addCell(createCell(details4, false, 8))

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

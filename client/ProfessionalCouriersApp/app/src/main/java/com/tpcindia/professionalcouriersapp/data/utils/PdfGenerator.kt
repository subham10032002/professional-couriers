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
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CBInfoData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import java.io.InputStream

class PdfGenerator {

    @SuppressLint("ResourceType")
    fun createPdf(context: Context,
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

        // Add consignment number and date rows
        table.addCell(createCell("Consignment Number: ${creditBookingData.consignmentNumber}", false, 2))
        table.addCell(createCell("Date: ${creditBookingData.currentDate}", false, 2))

        // Add divider after consignment number and date rows
        table.addCell(createDividerCell(4))

        // Add booking date and branch row
        table.addCell(createCell("Booking Date: ${creditBookingData.bookingDate}", false, 2))
        table.addCell(createCell("Branch: ${creditBookingData.branch}", false, 2))

        // Add divider after booking date and branch row
        table.addCell(createDividerCell(4))

        // Add from section
        table.addCell(createCell("From: ${creditBookingData.clientName}", false, 4))

        // Add divider after from section
        table.addCell(createDividerCell(4))

        // Add to section
        table.addCell(createCell("To: ${creditBookingData.consigneeName}", false, 2))
        table.addCell(createCell("Pincode: ${creditBookingData.pincode}", false, 2))
        table.addCell(createCell("Destination: ${creditBookingData.destination}", false, 4))

        // Add divider after to section
        table.addCell(createDividerCell(4))

        // Add consignee type and mode
        table.addCell(createCell("Consignee Type: ${creditBookingData.consigneeType}", false, 2))
        table.addCell(createCell("Mode: ${creditBookingData.mode}", false, 2))

        // Add divider after consignee type and mode
        table.addCell(createDividerCell(4))

        // Add content details
        table.addCell(createCell("Content:", false, 1))
        table.addCell(createCell("Pcs: ${creditBookingData.noOfPsc}", false, 1))
        table.addCell(createCell("Weight: ${creditBookingData.weight}", false, 2))

        // Add divider after content details
        table.addCell(createDividerCell(4))

        // Add dimensions details
        table.addCell(createCell("Dimensions:", false, 1))
        table.addCell(createCell("Unit: ${cbDimensionData.unit}", false, 2))
        table.addCell(createCell("Length: ${cbDimensionData.length}", false, 2))
        table.addCell(createCell("Width: ${cbDimensionData.width}", false, 2))
        table.addCell(createCell("Height: ${cbDimensionData.height}", false, 2))

        // Add divider after dimensions details
        table.addCell(createDividerCell(4))

        // Add info details
        table.addCell(createCell("Info:", false, 1))
        table.addCell(createCell("Invoice Number: ${cbInfoData.invoiceNumber}", false, 2))
        table.addCell(createCell("Product: ${cbInfoData.product}", false, 2))
        table.addCell(createCell("Declared Value: ${cbInfoData.declaredValue}", false, 2))
        table.addCell(createCell("Eway Bill: ${cbInfoData.ewayBill}", false, 2))

        table.addCell(createDividerCell(4))

        // Add row for name, signature, phone, and date
        table.addCell(createCell("Name:", false, 2))
        table.addCell(createCell("Signature:", false, 2))
        table.addCell(createCell("Phone:", false, 2))
        table.addCell(createCell("Date:", false, 2))

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

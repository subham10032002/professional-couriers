package com.tpcindia.professionalcouriersapp.data.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.geom.Rectangle
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.canvas.PdfCanvas
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.SolidBorder
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import com.tpcindia.professionalcouriersapp.R
import com.tpcindia.professionalcouriersapp.configs.UIConfig
import com.tpcindia.professionalcouriersapp.data.model.CBDimensionData
import com.tpcindia.professionalcouriersapp.data.model.CBInfoData
import com.tpcindia.professionalcouriersapp.data.model.CreditBookingData
import java.io.ByteArrayOutputStream

class PdfGenerator {

    @SuppressLint("ResourceType")
    fun createPdf(
        context: Context,
        creditBookingData: CreditBookingData,
        cbDimensionData: CBDimensionData,
        cbInfoData: CBInfoData
    ): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val writer = PdfWriter(outputStream)
        val pdfDoc = PdfDocument(writer)
        val pageSize = PageSize.A4
        pdfDoc.defaultPageSize = pageSize
        val font = PdfFontFactory.createFont(StandardFonts.HELVETICA)
        val document = Document(pdfDoc)
        val a4Width = pageSize.width
        val a4Height = pageSize.height
        val padding = 20f

        val boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)

        val contentRectangle = Rectangle(
            padding,
            a4Height - a4Height / 3 - padding - 50,
            a4Width - 2 * padding,
            a4Height / 3 - padding + 70
        )
        val canvas = PdfCanvas(pdfDoc.addNewPage())
        canvas.rectangle(contentRectangle)
        canvas.setStrokeColor(ColorConstants.BLACK)
        canvas.setLineWidth(1f)
        canvas.stroke()

        val table = Table(UnitValue.createPercentArray(floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f)))
        table.setWidth(UnitValue.createPointValue(contentRectangle.width))

        table.addCell(Cell().add(Paragraph("Shipper").setTextAlignment(TextAlignment.CENTER)).setBorderRight(SolidBorder(1f))).setBorderBottom(SolidBorder(1f))
        table.addCell(Cell().add(Paragraph("BLR/${creditBookingData.branch}").setTextAlignment(TextAlignment.CENTER)).setBorderRight(SolidBorder(1f))).setBorderBottom(SolidBorder(1f))
        table.addCell(Cell().add(Paragraph("Date: ${creditBookingData.bookingDate}").setTextAlignment(TextAlignment.CENTER)).setBorderRight(SolidBorder(1f))).setBorderBottom(SolidBorder(1f))
        table.addCell(Cell().add(Paragraph("Consignee").setTextAlignment(TextAlignment.CENTER)).setBorderRight(SolidBorder(1f))).setBorderBottom(SolidBorder(1f))
        table.addCell(Cell().add(Paragraph("Destination").setTextAlignment(TextAlignment.CENTER)).setBorderRight(SolidBorder(1f))).setBorderBottom(SolidBorder(1f))
        table.addCell(Cell().add(Paragraph("BLR/RRRR").setTextAlignment(TextAlignment.CENTER)).setBorderRight(SolidBorder(1f))).setBorderBottom(SolidBorder(1f))
        table.addCell(Cell().add(Paragraph("POD").setTextAlignment(TextAlignment.CENTER)).setBorderRight(SolidBorder(1f))).setBorderBottom(SolidBorder(1f))

        val rowHeight = 40f
        val tableTop = contentRectangle.top - rowHeight
        table.setFixedPosition(contentRectangle.left, tableTop, contentRectangle.width)

        document.add(table)

        val verticalDividerX = contentRectangle.left + (contentRectangle.width / 7) * 3
        canvas.moveTo(verticalDividerX.toDouble(), contentRectangle.bottom.toDouble())
        canvas.lineTo(verticalDividerX.toDouble(), contentRectangle.top.toDouble())
        canvas.setStrokeColor(ColorConstants.BLACK)
        canvas.setLineWidth(1f)
        canvas.stroke()

        val addressSectionHeight = 100f
        val verticalSpacing = 5f

        val fromAddressX = contentRectangle.left + 5f
        val toAddressX = verticalDividerX + 5f

        val fromAddressParagraph = Paragraph("From Address: \n${creditBookingData.clientName} \n\n ffeeffe\n efeff\n eefef")
            .setFont(font)
            .setFontSize(10f)
            .setTextAlignment(TextAlignment.LEFT)
            .setWidth(verticalDividerX - fromAddressX - 10f)

        val toAddressParagraph = Paragraph("To Address: \n${creditBookingData.consigneeName} \n\n${creditBookingData.destination}")
            .setFont(font)
            .setFontSize(10f)
            .setTextAlignment(TextAlignment.LEFT)
            .setWidth(contentRectangle.right - toAddressX - 10f)

        val fromAddressPositionY = tableTop - verticalSpacing - addressSectionHeight
        document.add(fromAddressParagraph.setFixedPosition(fromAddressX, fromAddressPositionY, verticalDividerX - fromAddressX - 10f))
        document.add(toAddressParagraph.setFixedPosition(toAddressX, fromAddressPositionY, contentRectangle.right - toAddressX - 10f))

        var horizontalDividerY = fromAddressPositionY - 10f
        canvas.moveTo(contentRectangle.left.toDouble(), horizontalDividerY.toDouble())
        canvas.lineTo(contentRectangle.right.toDouble(), horizontalDividerY.toDouble())
        canvas.setStrokeColor(ColorConstants.BLACK)
        canvas.setLineWidth(1f)
        canvas.stroke()

        // Barcode Image
        val barcodeBitmap = generateBarcode(creditBookingData.consignmentNumber, 300, 100)
        val barcodeStream = ByteArrayOutputStream()
        barcodeBitmap.compress(Bitmap.CompressFormat.PNG, 100, barcodeStream)
        val barcodeByteArray = barcodeStream.toByteArray()

        val barcodeImage = Image(ImageDataFactory.create(barcodeByteArray))
        barcodeImage.scaleToFit(120f, 40f) // Adjusted to fit inside the rectangle

        // Centering the barcode image
        val barcodeX =  (contentRectangle.width - barcodeImage.imageWidth) / 2 - 45f
        var currentY = horizontalDividerY - 50f

        barcodeImage.setFixedPosition(barcodeX, currentY)
        document.add(barcodeImage)

        // XYZ Data
        val xyzDataParagraph = Paragraph(creditBookingData.consignmentNumber)
            .setFont(font)
            .setFontSize(10f)
            .setWidth(barcodeImage.imageWidth)

        currentY -= 20f // Manually subtracting to adjust position
        document.add(xyzDataParagraph.setFixedPosition(barcodeX + 35f, currentY, barcodeImage.imageWidth))

        // Logo Image
        val logoStream = context.resources.openRawResource(R.drawable.tpc_logo)
        val logoByteArray = logoStream.readBytes()
        val logoImage = Image(ImageDataFactory.create(logoByteArray))
            .scaleToFit(65f, 30f) // Adjusted to fit inside the rectangle

        // Centering the logo image
        val logoX = (contentRectangle.width - logoImage.imageWidth) / 2 - 35f
        currentY -= 20f
        document.add(logoImage.setFixedPosition(logoX, currentY, logoImage.imageWidth))

        // Weight and Reference Texts
        val weightParagraph = Paragraph("Weight: ${creditBookingData.weight}")
            .setFont(font)
            .setFontSize(10f)
            .setTextAlignment(TextAlignment.LEFT)

        val volWeightParagraph = Paragraph("Vol weight: 0")
            .setFont(font)
            .setFontSize(10f)
            .setTextAlignment(TextAlignment.LEFT)

        val pcs = Paragraph("Pcs: ${creditBookingData.noOfPsc}")
            .setFont(font)
            .setFontSize(10f)
            .setTextAlignment(TextAlignment.LEFT)

        val awbNo = Paragraph("AWB No: ${creditBookingData.consignmentNumber}")
            .setFont(font)
            .setFontSize(10f)
            .setTextAlignment(TextAlignment.LEFT)

        var weightPositionY = horizontalDividerY - 30f
        val weightX = verticalDividerX + 5f
        document.add(weightParagraph.setFixedPosition(weightX, weightPositionY, contentRectangle.width / 2))

        weightPositionY -= 20f
        document.add(volWeightParagraph.setFixedPosition(weightX, weightPositionY, contentRectangle.width / 2))

        weightPositionY -= 20f
        document.add(pcs.setFixedPosition(weightX, weightPositionY, contentRectangle.width / 2))

        weightPositionY -= 20f
        document.add(awbNo.setFixedPosition(weightX, weightPositionY, contentRectangle.width / 2))

        val invoiceNo = Paragraph("Invoice Value: ${cbInfoData.invoiceValue}")
            .setFont(font)
            .setFontSize(10f)
            .setTextAlignment(TextAlignment.LEFT)

        val content = Paragraph("Invoice No: ${cbInfoData.invoiceNumber}")
            .setFont(font)
            .setFontSize(10f)
            .setTextAlignment(TextAlignment.LEFT)

        val value = Paragraph("E-waybill: ${cbInfoData.ewayBill}")
            .setFont(font)
            .setFontSize(10f)
            .setTextAlignment(TextAlignment.LEFT)

        val reference = Paragraph("Product: ${cbInfoData.product}")
            .setFont(font)
            .setFontSize(10f)
            .setTextAlignment(TextAlignment.LEFT)

        val rightColumnX = contentRectangle.right - 175f
        weightPositionY = horizontalDividerY - 30f

        document.add(invoiceNo.setFixedPosition(rightColumnX, weightPositionY, contentRectangle.width / 2))

        weightPositionY -= 20f
        document.add(content.setFixedPosition(rightColumnX, weightPositionY, contentRectangle.width / 2))

        weightPositionY -= 20f
        document.add(value.setFixedPosition(rightColumnX, weightPositionY, contentRectangle.width / 2))

        weightPositionY -= 20f
        document.add(reference.setFixedPosition(rightColumnX, weightPositionY, contentRectangle.width / 2))

        horizontalDividerY = weightPositionY - 10f
        canvas.moveTo(contentRectangle.left.toDouble(), horizontalDividerY.toDouble())
        canvas.lineTo(contentRectangle.right.toDouble(), horizontalDividerY.toDouble())
        canvas.setStrokeColor(ColorConstants.BLACK)
        canvas.setLineWidth(1f)
        canvas.stroke()

        var finalPositionY = horizontalDividerY - 55f
        var finalPositionX = contentRectangle.left - 18f

        val address = Paragraph(UIConfig.COMPANY_ADDRESS)
            .setFont(font)
            .setFontSize(9f)
            .setTextAlignment(TextAlignment.CENTER)

        document.add(address.setFixedPosition(finalPositionX, finalPositionY, contentRectangle.width / 2))

        finalPositionY = horizontalDividerY - 30f
        finalPositionX = verticalDividerX + 5f

        val receivedMessage = Paragraph(UIConfig.RECEIVED_MESSAGE)
            .setFont(font)
            .setFontSize(9f)
            .setTextAlignment(TextAlignment.LEFT)

        document.add(receivedMessage.setFixedPosition(finalPositionX, finalPositionY, contentRectangle.width / 2))

        val name = Paragraph("Name: ")
            .setFont(font)
            .setFontSize(10f)
            .setFont(boldFont)
            .setTextAlignment(TextAlignment.LEFT)

        finalPositionY -= 20f

        document.add(name.setFixedPosition(finalPositionX, finalPositionY, contentRectangle.width / 2))

        val sign = Paragraph("Sign/Stamp: ")
            .setFont(font)
            .setFontSize(10f)
            .setFont(boldFont)
            .setTextAlignment(TextAlignment.LEFT)

        finalPositionY -= 20f

        document.add(sign.setFixedPosition(finalPositionX, finalPositionY, contentRectangle.width / 2))

        finalPositionY = horizontalDividerY - 55f
        finalPositionX = contentRectangle.right - 135f

        val phone = Paragraph("Phone: ")
            .setFont(font)
            .setFontSize(10f)
            .setFont(boldFont)
            .setTextAlignment(TextAlignment.LEFT)

        document.add(phone.setFixedPosition(finalPositionX, finalPositionY, contentRectangle.width / 2))

        val date = Paragraph("Date: ")
            .setFont(font)
            .setFontSize(10f)
            .setFont(boldFont)
            .setTextAlignment(TextAlignment.LEFT)

        finalPositionY -= 20f

        document.add(date.setFixedPosition(finalPositionX, finalPositionY, contentRectangle.width / 2))

        document.close()
        return outputStream.toByteArray()
    }

    private fun generateBarcode(data: String, width: Int, height: Int): Bitmap {
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(data, BarcodeFormat.CODE_128, width, height)
        val bmpWidth = bitMatrix.width
        val bmpHeight = bitMatrix.height
        val bmp = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.RGB_565)

        for (x in 0 until bmpWidth) {
            for (y in 0 until bmpHeight) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        return bmp
    }
}

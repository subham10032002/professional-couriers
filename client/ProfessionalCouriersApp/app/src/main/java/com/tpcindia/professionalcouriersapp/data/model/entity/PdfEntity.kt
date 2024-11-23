package com.tpcindia.professionalcouriersapp.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pdf_table")
data class PdfEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fileName: String,
    val pdfData: ByteArray,
    val uniqueUser: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PdfEntity

        if (id != other.id) return false
        if (fileName != other.fileName) return false
        if (!pdfData.contentEquals(other.pdfData)) return false
        if (uniqueUser != other.uniqueUser) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + fileName.hashCode()
        result = 31 * result + pdfData.contentHashCode()
        result = 31 * result + uniqueUser.hashCode()
        return result
    }
}

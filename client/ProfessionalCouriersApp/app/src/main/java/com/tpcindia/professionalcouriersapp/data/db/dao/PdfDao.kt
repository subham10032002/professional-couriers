package com.tpcindia.professionalcouriersapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tpcindia.professionalcouriersapp.data.model.entity.PdfEntity

@Dao
interface PdfDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPdf(pdfEntity: PdfEntity)

    @Query("SELECT * FROM pdf_table WHERE uniqueUser = :uniqueUser")
    suspend fun getAllPdfs(uniqueUser: String): List<PdfEntity>

    @Query("DELETE FROM pdf_table")
    suspend fun deleteAllPdfs()
}
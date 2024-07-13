package com.tpcindia.professionalcouriersapp.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tpcindia.professionalcouriersapp.data.db.dao.PdfDao
import com.tpcindia.professionalcouriersapp.data.model.entity.PdfEntity

@Database(entities = [PdfEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun pdfDao(): PdfDao
}
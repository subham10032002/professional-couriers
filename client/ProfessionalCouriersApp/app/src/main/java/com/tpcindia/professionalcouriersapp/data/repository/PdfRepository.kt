package com.tpcindia.professionalcouriersapp.data.repository

import com.tpcindia.professionalcouriersapp.data.db.dao.PdfDao
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.entity.PdfEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PdfRepository() {
    suspend fun getPDFs(uniqueUser: String, pdfDao: PdfDao) : List<PdfEntity> {
        return withContext(Dispatchers.IO) {
            pdfDao.getAllPdfs(uniqueUser)
        }
    }

    suspend fun clearPDFs( pdfDao: PdfDao) {
        withContext(Dispatchers.IO) {
            pdfDao.deleteAllPdfs()
        }
    }
}
package com.tpcindia.professionalcouriersapp.data.repository

import com.tpcindia.professionalcouriersapp.data.db.dao.PdfDao
import com.tpcindia.professionalcouriersapp.data.io.NetworkService
import com.tpcindia.professionalcouriersapp.data.model.entity.PdfEntity

class PdfRepository() {
    suspend fun getPDFs(branch: String, pdfDao: PdfDao) : List<PdfEntity> {
        return pdfDao.getAllPdfs(branch)
    }

    suspend fun clearPDFs( pdfDao: PdfDao) {
        pdfDao.deleteAllPdfs()
    }
}
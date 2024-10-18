package com.tpcindia.professional_couriers.repository;

import com.tpcindia.professional_couriers.model.BookTrans;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookTransRepository extends JpaRepository<BookTrans, Long> {
    @Query("SELECT MAX(tr.accNo) FROM BookTrans tr, BookMaster ma WHERE ma.bookNo = tr.bookNo AND tr.counter = 'Yes' AND ma.custCode = :custCode AND ma.type = 'Auto Docket Mobile'")
    Long findMaxAccNoCredit(@Param("custCode") String custCode);
    
    @Query("SELECT COUNT(tr.accNo) FROM BookTrans tr, BookMaster ma WHERE ma.bookNo = tr.bookNo AND tr.counter = 'No' AND ma.custCode = :custCode AND ma.type = 'Auto Docket Mobile'")
    Integer countUnusedAccNosCredit(@Param("custCode") String custCode);

    @Query("SELECT MIN(tr.accNo) FROM BookTrans tr, BookMaster ma WHERE ma.bookNo = tr.bookNo AND tr.counter = 'No' AND ma.custCode = :custCode AND ma.type = 'Auto Docket Mobile'")
    Long findMinUnusedAccNoCredit(@Param("custCode") String custCode);

    @Modifying
    @Transactional
    @Query("UPDATE BookTrans tr SET tr.counter = :counter WHERE tr.accNo = :accNo AND tr.accCode = :accCode")
    int updateBookTransCounter(@Param("counter") String counter, @Param("accNo") Long accNo, @Param("accCode") String accCode);
}

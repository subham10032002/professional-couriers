package com.tpcindia.professional_couriers.repository;

import com.tpcindia.professional_couriers.model.BookTrans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookTransRepository extends JpaRepository<BookTrans, Long> {
    @Query("SELECT MAX(tr.accNo) FROM BookTrans tr, BookMaster ma, BookAllot bk WHERE bk.startNo = :startNo AND ma.bookType = 'Credit' AND ma.bookNo = tr.bookNo AND tr.counter = 'Yes' AND tr.accNo >= bk.startNo AND tr.accNo <= bk.endNo AND bk.type = 'Auto Docket Mobile'")
    Long findMaxAccNoCredit(@Param("startNo") Long startNo);

    @Query("SELECT COUNT(tr.accNo) FROM BookTrans tr, BookMaster ma, BookAllot bk WHERE bk.startNo = :startNo AND ma.bookType = 'Credit' AND ma.bookNo = tr.bookNo AND tr.counter = 'No' AND tr.accNo >= bk.startNo AND tr.accNo <= bk.endNo AND bk.type = 'Auto Docket Mobile'")
    Integer countUnusedAccNosCredit(@Param("startNo") Long startNo);

    @Query("SELECT MIN(tr.accNo) FROM BookTrans tr, BookMaster ma, BookAllot bk WHERE bk.startNo = :startNo AND ma.bookType = 'Credit' AND ma.bookNo = tr.bookNo AND tr.counter = 'No' AND tr.accNo >= bk.startNo AND tr.accNo <= bk.endNo AND bk.type = 'Auto Docket Mobile'")
    Long findMinUnusedAccNoCredit(@Param("startNo") Long startNo);
}

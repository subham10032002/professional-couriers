package com.tpcindia.professional_couriers.repository;

import com.tpcindia.professional_couriers.model.BookTrans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookTransRepository extends JpaRepository<BookTrans, Long> {
    // TODO:- Change all the Cast to Credit before release
    @Query("SELECT MAX(tr.accNo) FROM BookTrans tr, BookMaster ma, BookAllot bk WHERE bk.startNo = :startNo AND ma.bookType = 'Cash' AND ma.bookNo = tr.bookNo AND tr.counter = 'Yes' AND tr.accNo >= bk.startNo AND tr.accNo <= bk.endNo AND bk.type = 'Auto Docket Cash'")
    Long findMaxAccNoCredit(@Param("startNo") Long startNo);

    @Query("SELECT COUNT(tr.accNo) FROM BookTrans tr, BookMaster ma, BookAllot bk WHERE bk.startNo = :startNo AND ma.bookType = 'Cash' AND ma.bookNo = tr.bookNo AND tr.counter = 'No' AND tr.accNo >= bk.startNo AND tr.accNo <= bk.endNo AND bk.type = 'Auto Docket Cash'")
    Integer countUnusedAccNosCredit(@Param("startNo") Long startNo);

    @Query("SELECT MIN(tr.accNo) FROM BookTrans tr, BookMaster ma, BookAllot bk WHERE bk.startNo = :startNo AND ma.bookType = 'Cash' AND ma.bookNo = tr.bookNo AND tr.counter = 'No' AND tr.accNo >= bk.startNo AND tr.accNo <= bk.endNo AND bk.type = 'Auto Docket Cash'")
    Long findMinUnusedAccNoCredit(@Param("startNo") Long startNo);
}

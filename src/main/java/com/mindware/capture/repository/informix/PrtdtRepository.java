package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Prtdt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PrtdtRepository extends JpaRepository<Prtdt, Integer> {

    List<Prtdt> findByPrtdtnpre(Integer prtdtnpre);

    @Query(value = "select sum(p.prtdtimpp) from Prtdt p  " +
            " where p.prtdtpref = 20 and p.prtdtccon = 1 " +
            " and p.prtdtnpre = :prtdtnpre and p.prtdtntra <= :prtdtntra and p.prtdtftra <= :prtdtftra")
    Double getSaldo(@Param("prtdtnpre") Integer prtdtnpre,
                    @Param("prtdtntra") Integer prtdtntra,
                    @Param("prtdtftra") LocalDate prtdtftra);
}

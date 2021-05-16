package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Gbtga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GbtgaRepository extends JpaRepository<Gbtga, Integer> {

    @Query(value = "select g from Gbtga g " +
            "inner join Prgar p on (p.prgartgar = g.gbtgactga) " +
            "where p.id.prgarnpre = :prgarnpre")
    List<Gbtga> findByPrgarnpre(@Param("prgarnpre")  Integer prgarnpre);
}

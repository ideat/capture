package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Lcdlc;
import com.mindware.capture.model.informix.LcdlcId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LcdlcRepository extends JpaRepository<Lcdlc, LcdlcId> {

    @Query(value = "select l from Lcdlc l inner join Gbage g on (l.id.lcdlccage = g.gbagecage) " +
            " where l.id.lcdlcnrlc = :lcdlcnrlc")
    List<Lcdlc> findByIdLcdlcnrlc(@Param("lcdlcnrlc") Integer lcdlcnrlc);
}

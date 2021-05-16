package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Catrn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CatrnRepository extends JpaRepository<Catrn, Integer> {

    List<Catrn> findByCatrnncta(String catrnncta);

    @Query(value="select sum(c.catrnimpo) from Catrn c " +
            " where c.catrnntra <= :catrnntra and c.catrnftra <= :catrnftra " +
            " and c.catrnncta = :catrnncta ")
    Double getCalcularSaldo(@Param("catrnncta") String catrnncta,
                            @Param("catrnftra") LocalDate catrnftra,
                            @Param("catrnntra") Integer catrnntra);
}

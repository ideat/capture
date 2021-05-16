package com.mindware.capture.repository.informix;

import com.mindware.capture.dto.informix.GbagePrdeu;
import com.mindware.capture.model.informix.Prdeu;
import com.mindware.capture.model.informix.PrdeuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PrdeuRepository extends JpaRepository<Prdeu, PrdeuId> {

    List<Prdeu> findByIdPrdeunpre(Integer prdeunpre);

    Optional<Prdeu> findByIdPrdeunpreAndIdPrdeucage(Integer prdeunpre, Integer prdeucage);

    @Query(value = "select g.gbagecage, p.prdeutres, g.gbagenomb, g.gbagendid from Gbage g " +
            " inner join Prdeu p on (p.id.prdeucage = g.gbagecage) " +
            " where p.id.prdeunpre = :prdeunpre ")
    List<GbagePrdeu> findGbagePrdeuByPrdeunpre(@Param("prdeunpre") Integer prdeunpre);
}

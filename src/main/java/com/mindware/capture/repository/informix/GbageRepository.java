package com.mindware.capture.repository.informix;

import com.mindware.capture.dto.informix.GbagePrdeu;
import com.mindware.capture.model.informix.Gbage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GbageRepository extends JpaRepository<Gbage,Integer> {

    @Query(value = "select g" +
            " from Gbage g  " +
            " inner join Gbcon a on (g.gbagetper = a.id.gbconcorr and a.id.gbconpfij = 1) " +
            " where TRIM(g.gbagendid) in :lista ")
    List<Gbage> findByListGbagendid(@Param("lista") List<String> gbagendid);


//    @Query(value = " select g.gbagecage, g.gbagenomb, pr.prcondesc  " +
//            " from Gbage g " +
//            " inner join Prdeu p on (p.id.prdeucage = g.gbagecage) " +
//            " inner join Prcon pr on (pr.id.prconpref = 7 and pr.id.prdeucorr = p.prdeutres) " +
//            " where p.id.prdeunpre = :prdeunpre "
//    )
    List<Gbage> findByGbagecage(@Param("prdeunpre") Integer prdeunpre);

}

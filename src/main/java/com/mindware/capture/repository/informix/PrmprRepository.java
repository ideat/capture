package com.mindware.capture.repository.informix;

import com.mindware.capture.dto.IPersona;
import com.mindware.capture.model.informix.Prmpr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrmprRepository extends JpaRepository<Prmpr,Integer> {

    @Query(value = "select p from Prmpr p inner join Gbage g on (g.gbagecage = p.prmprcage)" +
            " where trim(g.gbagendid) in :lista")

    List<Prmpr> findByGbagendid(@Param("lista") List<String> carnets);

    @Query(value = " select d.gbdaccage, g.gbagetper, g.gbagendid, " +
            " g.gbagenomb, d.gbdacnom1, " +
            "d.gbdacnom2, d.gbdacape1, d.gbdacape2, " +
            "g.gbageeciv, g.gbagetdid, g.gbagenruc " +
            "from Gbage g " +
            "inner join Gbdac d on (g.gbagecage = d.gbdaccage) " +
            "inner join Prdeu p on (p.prdeucage = d.gbdaccage) " +
            "where p.prdeunpre = :prdeunpre ", nativeQuery = true )
    List<IPersona> findClientesByNumeroCredito(@Param("prdeunpre") String prdeunpre);


}

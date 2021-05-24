package com.mindware.capture.repository.informix;

import com.mindware.capture.dto.IPersona;
import com.mindware.capture.model.informix.Lcmlc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LcmlcRepository extends JpaRepository<Lcmlc, Integer> {

    List<Lcmlc> findByLcmlcnpre(Integer lcmlcnpre);

    Optional<Lcmlc> findById(Integer lcmlcnrlc);

    @Query(value = "select p from Lcmlc p inner join Gbage g on (g.gbagecage = p.lcmlccage)" +
            " where p.lcmlcstat <> 9 and  trim(g.gbagendid) in :lista")
    List<Lcmlc> findByGbagendid(@Param("lista") List<String> carnets);

    @Query(value = "select distinct d.gbdaccage, g.gbagetper, g.gbagendid, " +
            "g.gbagenomb, d.gbdacnom1, " +
            "d.gbdacnom2, d.gbdacape1, d.gbdacape2, " +
            "g.gbageeciv, g.gbagetdid, g.gbagenruc " +
            "from Gbage g " +
            "inner join Gbdac d on (g.gbagecage = d.gbdaccage) " +
            "inner join prdeu pd on(pd.prdeucage = d.gbdaccage) " +
            "inner join prmpr p on (p.prmprnpre = pd.prdeunpre) " +
            "where p.prmprlncr = :numerolinea ", nativeQuery = true)
    List<IPersona> findByNumeroLinea(@Param("numerolinea") Integer numerolinea);
}

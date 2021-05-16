package com.mindware.capture.repository.informix;

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
}

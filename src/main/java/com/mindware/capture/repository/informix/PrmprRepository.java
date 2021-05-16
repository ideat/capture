package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Prmpr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrmprRepository extends JpaRepository<Prmpr,Integer> {

    @Query(value = "select p from Prmpr p inner join Gbage g on (g.gbagecage = p.prmprcage)" +
            " where trim(g.gbagendid) in :lista")

    List<Prmpr> findByGbagendid(@Param("lista") List<String> carnets);


}

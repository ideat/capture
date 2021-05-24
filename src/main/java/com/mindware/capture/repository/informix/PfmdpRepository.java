package com.mindware.capture.repository.informix;

import com.mindware.capture.dto.IPersona;
import com.mindware.capture.model.informix.Pfmdp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PfmdpRepository extends JpaRepository<Pfmdp, Long> {

    List<Pfmdp> findByPfmdpcage(Integer pfmdpcage);

    @Query(value = " select d.gbdaccage, g.gbagetper, g.gbagendid, " +
            " g.gbagenomb, d.gbdacnom1, " +
            "d.gbdacnom2, d.gbdacape1, d.gbdacape2, " +
            "g.gbageeciv, g.gbagetdid, g.gbagenruc " +
            "from Gbage g " +
            "inner join Gbdac d on (g.gbagecage = d.gbdaccage) " +
            "where g.gbagecage in :gbagecage " )
    List<IPersona> findClientesInDPF(@Param("gbagecage") List<Integer> gbagecage);
}

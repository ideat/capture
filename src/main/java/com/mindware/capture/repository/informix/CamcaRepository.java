package com.mindware.capture.repository.informix;

import com.mindware.capture.dto.IPersona;

import com.mindware.capture.model.informix.Camca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CamcaRepository extends JpaRepository<Camca,Integer> {

    List<Camca> findByCamcacage(@Param("camcacage") Integer camcacage);

    @Query(value = " select d.gbdaccage, g.gbagetper, g.gbagendid, " +
            " g.gbagenomb, d.gbdacnom1, " +
            "d.gbdacnom2, d.gbdacape1, d.gbdacape2, " +
            "g.gbageeciv, g.gbagetdid, g.gbagenruc " +
            "from Gbage g " +
            "inner join Gbdac d on (g.gbagecage = d.gbdaccage) " +
            "inner join Cafir c on (c.cafircage = d.gbdaccage) " +
            "where c.cafirncta = :cafirncta ", nativeQuery = true )
    List<IPersona> findClientesByCajaAhorro(@Param("cafirncta") String cafirncta);


}

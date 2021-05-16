package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Cafir;
import com.mindware.capture.model.informix.CafirId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CafirRepository extends JpaRepository<Cafir, CafirId> {

    Optional<Cafir> findByIdCafirnctaAndIdCafircage(String cafirncta, Integer cafircage );

    List<Cafir> findByIdCafirncta(String cafirncta);
}

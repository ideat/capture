package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Pfcon;
import com.mindware.capture.model.informix.PfconId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PfconRepository extends JpaRepository<Pfcon, PfconId> {

    Optional<Pfcon> findByIdPfconpfijAndIdPfconcorr(Integer pfconpfij, Integer pfconcorr);

}

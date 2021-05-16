package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Gbcon;
import com.mindware.capture.model.informix.GbconId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GbconRepository extends JpaRepository<Gbcon, GbconId> {

    Optional<Gbcon> findByIdGbconpfijAndIdGbconcorr(Integer gbconpfij, Integer gbconcorr);

}

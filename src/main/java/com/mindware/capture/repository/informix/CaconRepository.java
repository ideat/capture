package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Cacon;
import com.mindware.capture.model.informix.CaconId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
@Repository
public interface CaconRepository extends JpaRepository<Cacon, CaconId> {

    Optional<Cacon> findByIdCaconprefAndIdCaconcorr( Integer caconpref, Integer caconcorr);
}

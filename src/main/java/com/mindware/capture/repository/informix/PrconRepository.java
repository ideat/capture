package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Prcon;
import com.mindware.capture.model.informix.PrconId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrconRepository extends JpaRepository<Prcon, PrconId> {

    Optional<Prcon> findByIdPrconprefAndIdPrconcorr(Integer prconprer, Integer prconcorr);
}

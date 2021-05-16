package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Lccon;
import com.mindware.capture.model.informix.LcconId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LcconRepository extends JpaRepository<Lccon, LcconId> {

    Optional<Lccon> findByIdLcconpfijAndIdLcconcorr(Integer lcconpfij, Integer lcconpcorr);
}

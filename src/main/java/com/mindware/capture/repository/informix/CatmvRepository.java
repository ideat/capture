package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Catmv;
import com.mindware.capture.model.informix.CatmvId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CatmvRepository extends JpaRepository<Catmv, CatmvId> {

    Optional<Catmv> findByIdCatmvprefAndIdCatmvcorr(Integer catmvpref, Integer catmvcorr);
}

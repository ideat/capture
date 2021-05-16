package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Lctrn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LctrnRepository extends JpaRepository<Lctrn, Integer> {

    List<Lctrn> findByLctrnnrlc(Integer lctrnoper);
}

package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Pftdt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PftdtRepository extends JpaRepository<Pftdt,Integer> {

    List<Pftdt> findByPftdtndep(Long pftdtndep);
}

package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Pfmdp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PfmdpRepository extends JpaRepository<Pfmdp, Long> {

    List<Pfmdp> findByPfmdpcage(Integer pfmdpcage);
}

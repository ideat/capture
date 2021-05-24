package com.mindware.capture.repository.informix;

import com.mindware.capture.model.informix.Gbcii;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GbciiRepository extends JpaRepository<Gbcii,Integer> {

    @Query(value = " select g from Gbcii g where g.gbciiciiu = :gbciiciiu")
    Optional<Gbcii> findByCiiu(@Param("gbciiciiu") Integer gbciiciiu);
}

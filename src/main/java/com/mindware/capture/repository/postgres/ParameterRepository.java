package com.mindware.capture.repository.postgres;

import com.mindware.capture.model.postgres.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface ParameterRepository extends JpaRepository<Parameter, UUID> {
    List<Parameter> findByCategoria(@Param("categoria") String categoria);

}

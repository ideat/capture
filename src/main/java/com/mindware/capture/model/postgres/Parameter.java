package com.mindware.capture.model.postgres;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Entity
@Table(name = "parameter")
@Data
public class Parameter {
    @Id
    private UUID id;

    private String codigo;

    private String description;

    private String orden;

    private String categoria;

    @Column(name = "codigo_externo")
    private String codigoExterno;

}

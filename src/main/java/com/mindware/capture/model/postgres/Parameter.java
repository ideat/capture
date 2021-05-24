package com.mindware.capture.model.postgres;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parameter")
public class Parameter {
    @Id
    private UUID id;

    private String codigo;

    private String descripcion;

    private Integer orden;

    private String categoria;

    @Column(name = "codigo_externo")
    private String codigoExterno;

}

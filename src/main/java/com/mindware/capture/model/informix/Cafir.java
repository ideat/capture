package com.mindware.capture.model.informix;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "cafir")
public class Cafir {

    @EmbeddedId
    private CafirId id;

    @Column(name = "cafirclas")
    private String cafirclas;

    @Column(name = "cafirstat")
    private Integer cafirstat;

}

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
@Table(name = "pfcon")
public class Pfcon {

    @EmbeddedId
    private PfconId id;

    @Column(name = "pfcondesc")
    private String pfcondesc;
}

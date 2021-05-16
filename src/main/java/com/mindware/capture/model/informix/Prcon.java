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
@Table(name = "prcon")
public class Prcon {

    @EmbeddedId
    private PrconId id;

    @Column(name = "prcondesc")
    private String prcondesc;
}

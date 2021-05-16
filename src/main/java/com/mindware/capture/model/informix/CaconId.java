package com.mindware.capture.model.informix;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class CaconId implements Serializable {

    @Column(name = "caconpref")
    private Integer caconpref;

    @Column(name = "caconcorr")
    private Integer caconcorr;
}

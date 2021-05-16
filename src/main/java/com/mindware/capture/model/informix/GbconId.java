package com.mindware.capture.model.informix;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class GbconId implements Serializable {

    private Integer gbconpfij;

    private Integer gbconcorr;
}

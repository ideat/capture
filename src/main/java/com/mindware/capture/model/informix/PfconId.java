package com.mindware.capture.model.informix;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class PfconId implements Serializable {

    private Integer pfconpfij;

    private Integer pfconcorr;
}

package com.mindware.capture.model.informix;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class PrconId implements Serializable {

    private Integer prconpref;
    private Integer prconcorr;
}

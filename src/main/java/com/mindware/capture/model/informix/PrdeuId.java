package com.mindware.capture.model.informix;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class PrdeuId implements Serializable {

    private Integer prdeunpre;

    private Integer prdeucage;
}

package com.mindware.capture.model.informix;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class LcconId implements Serializable {

    private Integer lcconpfij;

    private Integer lcconcorr;
}

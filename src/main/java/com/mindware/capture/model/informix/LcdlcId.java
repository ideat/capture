package com.mindware.capture.model.informix;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class LcdlcId implements Serializable {

    private Integer lcdlcnrlc;

    private Integer lcdlccage;
}

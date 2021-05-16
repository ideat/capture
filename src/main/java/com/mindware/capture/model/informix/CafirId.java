package com.mindware.capture.model.informix;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class CafirId implements Serializable {

    private String cafirncta;

    private Integer cafircage;
}


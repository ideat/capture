package com.mindware.capture.model.informix;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class PrgarId implements Serializable {

    private Integer prgarnpre;

    private Integer prgarcorr;
}

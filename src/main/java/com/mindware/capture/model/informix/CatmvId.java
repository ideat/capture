package com.mindware.capture.model.informix;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class CatmvId implements Serializable {

    private Integer catmvpref;

    private Integer catmvcorr;
}

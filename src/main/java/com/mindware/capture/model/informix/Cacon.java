package com.mindware.capture.model.informix;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "cacon")
public class Cacon {

    @EmbeddedId
    private CaconId id;

    @Column(name = "cacondesc")
    private String cacondesc;
}

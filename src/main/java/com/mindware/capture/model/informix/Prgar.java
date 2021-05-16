package com.mindware.capture.model.informix;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "prgar")
public class Prgar {

    @EmbeddedId
    private PrgarId id;

    private Integer prgartgar;

    private LocalDateTime prgarfreg;
}

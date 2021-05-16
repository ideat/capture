package com.mindware.capture.model.informix;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "pftdt")
public class Pftdt {

    @Id
    private Integer pftdtntra;

    private LocalDate pftdtftra;

    private Long pftdtndep;

    private Double pftdtimpp;

    private String pftdtdesc;

}

package com.mindware.capture.model.informix;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "prtdt")
public class Prtdt {

    @Id
    private Integer prtdtntra;

    @Column(name = "prtdtnpre")
    private Integer prtdtnpre;

    @Column(name = "prtdtftra")
    private LocalDate prtdtftra;

    @Column(name = "prtdtdesc")
    private String prtdtdesc;

    @Column(name = "prtdtcmon")
    private Integer prtdtcmon;

    @Column(name = "prtdtccon")
    private Integer prtdtccon;

    @Column(name = "prtdtpref")
    private Integer prtdtpref;

    @Column(name = "prtdtttrn")
    private Integer prtdtttrn;

    @Column(name = "prtdtimpp")
    private Double prtdtimpp;
}

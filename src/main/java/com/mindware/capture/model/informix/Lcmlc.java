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
@Table(name = "lcmlc")
public class Lcmlc {

    @Id
    private Integer lcmlcnrlc;

    @Column(name = "lcmlccage")
    private Integer lcmlccage;

    @Column(name = "lcmlctlcr")
    private Integer lcmlctlcr;

    @Column(name = "lcmlcnpre")
    private Integer lcmlcnpre;

    @Column(name = "lcmlcplzo")
    private Integer lcmlcplzo;

    @Column(name = "lcmlcncuo")
    private Integer lcmlcncuo;

    @Column(name = "lcmlccmon")
    private Integer lcmlccmon;

    @Column(name = "lcmlcfreg")
    private LocalDate lcmlcfreg;

    @Column(name = "lcmlcfini")
    private LocalDate lcmlcfini;

    @Column(name = "lcmlcfvco")
    private LocalDate lcmlcfvco;

    @Column(name = "lcmlcfven")
    private LocalDate lcmlcfven;

    @Column(name = "lcmlcciiu")
    private Integer lcmlcciiu;

    @Column(name = "lcmlcmapr")
    private Double lcmlcmapr;

    @Column(name = "lcmlcrubr")
    private Integer lcmlcrubr;

    @Column(name = "lcmlctcuo")
    private Integer lcmlctcuo;

    @Column(name = "lcmlcstat")
    private Integer lcmlcstat;

    @Column(name = "lcmlcdest")
    private Integer lcmlcdest;

}

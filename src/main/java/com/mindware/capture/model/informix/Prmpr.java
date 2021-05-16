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
@Table(name = "prmpr")
public class Prmpr {
    
    @Id
    @Column(name = "prmprnpre")
    private Integer prmprnpre;
    
    @Column(name = "prmprcage")
    private Integer prmprcage;

    @Column(name = "prmprcmon")
    private Integer prmprcmon;

    @Column(name = "prmprsald")
    private Double prmprsald;

    @Column(name = "prmprmdes")
    private Double prmprmdes;

    @Column(name = "prmprfreg")
    private LocalDate prmprfreg;

    @Column(name = "prmprstat")
    private Integer prmprstat;

    @Column(name = "prmprfdes")
    private LocalDate prmprfdes;

    @Column(name = "prmprfulp")
    private LocalDate prmprfulp;

    @Column(name = "prmprdest")
    private Integer prmprdest;

    @Column(name = "prmprddes")
    private String prmprddes;

    @Column(name = "prmprfpag")
    private Integer prmprfpag;

    @Column(name = "prmprplzo")
    private Integer prmprplzo;

    @Column(name = "prmpruplz")
    private Integer prmpruplz;

    @Column(name = "prmprfvac")
    private LocalDate prmprfvac;

    @Column(name= "prmprmapt")
    private Double prmprmapt;

    @Column(name = "prmprorgr")
    private Integer prmprorgr;

    @Column(name = "prmprciiu")
    private Integer prmprciiu;

    @Column(name = "prmprctad")
    private String prmprctad;
}

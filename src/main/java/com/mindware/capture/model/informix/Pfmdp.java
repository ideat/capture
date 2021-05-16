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
@Table(name = "pfmdp")
public class Pfmdp {

    @Id
    private Long pfmdpndep;

    @Column(name ="pfmdpcage")
    private Integer pfmdpcage;

    @Column(name = "pfmdpcag2")
    private Integer pfmdpcag2;

    @Column(name = "pfmdpcag3")
    private Integer pfmdpcag3;

    @Column(name = "pfmdpcag4")
    private Integer pfmdpcag4;

    @Column(name = "pfmdpcmon")
    private Integer pfmdpcmon;

    @Column(name = "pfmdpcapi")
    private Double pfmdpcapi;

    @Column(name = "pfmdpfreg")
    private LocalDate pfmdpfreg;

    @Column(name = "pfmdpfvto")
    private LocalDate pfmdpfvto;

    @Column(name = "pfmdpmane")
    private Integer pfmdpmane;

    @Column(name = "pfmdpncrt")
    private Integer pfmdpncrt;

    @Column(name = "pfmdpplzo")
    private Integer pfmdpplzo;

    @Column(name = "pfmdpstat")
    private Integer pfmdpstat;

    @Column(name = "pfmdpndpr")
    private Long pfmdpndpr;
}

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
@Table(name = "camca")
public class Camca {

    @Id
    @Column(name = "camcancta")
    private String camcancta;

    @Column(name = "camcacage")
    private Integer camcacage;

    @Column(name = "camcacmon")
    private Integer camcacmon;

    @Column(name = "camcafapt")
    private LocalDate camcafapt;

    @Column(name = "camcasact")
    private Double camcasact;

    @Column(name = "camcastat")
    private Integer camcastat;

    @Column(name = "camcamane")
    private Integer camcamane;

    @Column(name = "camcafcbl")
    private LocalDate camcafcbl;

    @Column(name = "camcainst")
    private String camcainst;

}

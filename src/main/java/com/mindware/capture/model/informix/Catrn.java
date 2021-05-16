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
@Table(name = "catrn")
public class Catrn {

    @Id
    private Integer catrnntra;

    @Column(name = "catrnftra")
    private LocalDate catrnftra;

    @Column(name = "catrnncta")
    private String catrnncta;

    @Column(name = "catrncmon")
    private Integer catrncmon;

    @Column(name = "catrnimpo")
    private Double catrnimpo;

    @Column(name = "catrnglos")
    private String catrnglos;

    @Column(name = "catrnagen")
    private Integer catrnagen;

    @Column(name = "catrnpref")
    private Integer catrnpref;

    @Column(name = "catrncorr")
    private Integer catrncorr;
}

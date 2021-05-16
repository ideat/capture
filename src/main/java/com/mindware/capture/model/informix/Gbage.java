package com.mindware.capture.model.informix;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "gbage")
public class Gbage {

    @Id
    @Column(name = "gbagecage")
    private Integer gbagecage;

    @Column(name = "gbagenomb")
    private String gbagenomb;

    @Column(name = "gbagendid")
    private String gbagendid;

    @Column(name = "gbagetper")
    private Integer gbagetper;

    @Column(name = "gbageciiu")
    private Integer gbageciiu;

    @Column(name = "gbageeciv")
    private Integer gbageeciv;

    @Column(name = "gbagetdid")
    private Integer gbagetdid;

    @Column(name = "gbagenruc")
    private String gbagenruc;

    @OneToMany
    @JoinColumn(name = "prdeucage", referencedColumnName = "gbagecage")
    private List<Prdeu> prdeuList = new ArrayList<>();

}

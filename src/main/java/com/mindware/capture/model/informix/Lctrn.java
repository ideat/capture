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
@Table(name = "lctrn")
public class Lctrn {

    @Id
    private Integer lctrnntra;

    @Column(name = "lctrncmon")
    private Integer lctrncmon;

    @Column(name = "lctrnoper")
    private Integer lctrnoper;

    @Column(name = "lctrntorg")
    private Integer lctrntorg;

    @Column(name = "lctrnimpt")
    private Double lctrnimpt;

    @Column(name = "lctrnftra")
    private LocalDate lctrnftra;

    @Column(name = "lctrnnrlc")
    private Integer lctrnnrlc;
}

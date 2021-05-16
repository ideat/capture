package com.mindware.capture.model.informix;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "gbtga")
public class Gbtga {

    @Id
    private Integer gbtgactga;

    @Column(name = "gbtgacsup")
    private String gbtgacsup;

    @Column(name = "gbtgasigl")
    private String gbtgasigl;

    @Column(name = "gbtgadesc")
    private String gbtgadesc;

    @Column(name = "gbtgamrcb")
    private Integer gbtgamrcb;
}

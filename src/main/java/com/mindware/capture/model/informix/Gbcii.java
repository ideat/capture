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
@Table(name = "gbcii")
public class Gbcii {

    @Id
    @Column(name = "gbciiciuu")
    private Integer gbciiciuu;

    @Column(name = "gbciidesc")
    private String gbciidesc;
}

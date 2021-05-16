package com.mindware.capture.model.informix;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "gbdac")
public class Gbdac {

    @Id
    private Integer gbdaccage;

    private String gbdacnom1;

    private String gbdacnom2;

    private String gbdacape1;

    private String gbdacape2;

    private String gbdacape3;


}

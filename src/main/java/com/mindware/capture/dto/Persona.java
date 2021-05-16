package com.mindware.capture.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//@SqlResultSetMapping(
//        name = "findClientesByCajaAhorro",
//        classes = {
//                @ConstructorResult(
//                        targetClass = com.mindware.capture.dto.Persona.class,
//                        columns = {
//                                @ColumnResult( name = "gbdaccage", type = Integer.class),
//                                @ColumnResult( name = "gbagetper", type = Integer.class),
//                                @ColumnResult( name = "gbagetdid", type = Integer.class),
//                                @ColumnResult( name = "gbagenomb", type = String.class),
//                                @ColumnResult( name = "gbdacnom1", type = String.class),
//                                @ColumnResult( name = "gbdacnom2", type = String.class),
//                                @ColumnResult( name = "gbdacape1", type = String.class),
//                                @ColumnResult( name = "gbdacape2", type = String.class),
//                                @ColumnResult( name = "gbageeciv", type = Integer.class),
//                                @ColumnResult( name = "gbagendid", type = String.class)
//
//                        }
//                )
//        }
//)

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Value
public class Persona {

    private Integer gbdaccage; // codigoCliente;//

    private String  tipoPersona;//

    private String tipoDocumento;//

    private String numeroDocumento;

    private String complemento;

    private String tipoExtension;

    private String nombreCompleto;//

    private String primerNombre;//

    private String segundoNombre;//

    private String primerApellido;//

    private String segundoApellido;//

    private String estadoCivil;//

    private String gbagendid; // carnetCompleto;//

    private String razonSocial;

}

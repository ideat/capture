package com.mindware.capture.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Transacciones {

    private String documentoPersonaSolicitud;

    private String numeroOperacion;

    private String tipoOperacion;

    private String numeroTransaccion;

    private LocalDate fechaTransaccion;

    private String tipoTransaccion;

    private String tipoFormaPagoTransaccion;

    private String tipoModalidad;

    private Double montoTransaccion;

    private Double saldo;

    private String glosaEntidad;

    private String agencia;

    private String nombrePersonaTransaccion;

    private String numeroCuentaTransferencia;

    private String nombreTitularCuentaTransferencia;

    private String bancoTransferencia;

    private String paisTransferencia;

    private String ciudadTransferencia;

    private String numeroCheque;

    private String fechaProgramada;

    private String importeProgramado;

    private String nombreGiroCheque;

    private String nombreCobradorCheque;

    private String numeroDpfRenovacion;



}

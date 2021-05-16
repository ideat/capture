package com.mindware.capture.dto;

import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.List;

@Data
public class Operaciones {

    private String documentoPersona;

    private String numeroOperacion;

    private String tipoOperacion;

    private String tipoManejoCuenta;

    private LocalDate fechaAperturaCuenta;

    private String moneda;

    private Double saldoCuenta;

    private String estadoCuenta;

    private String tipoPrestamoBono;

    private String formaPago;

    private String nombreGarante;

    private String descripcionGarantia;

    private String garantia;

    private String objetivoPrestamo;

    private LocalDate fechaSolicitudPrestamo;

    private LocalDate fechaAprobacionPrestamo;

    private LocalDate fechaCierre;

    private String fuentePago;

    private LocalDate fechaDesembolso;

    private String plazoVigencia;

    private Double montoLimiteCompra;

    private LocalDate fechaConstitucion;

    private LocalDate fechaPagoEfectivo;

    private LocalDate fechaCancelacion;

    private LocalDate fechaVencimiento;

    private Double monto;

    private Double montoIndividual;

    private String nombreAsociacion;

    private String numeroContrato;

    private String objetoFideicomisoLineaCredito;

    private LocalDate fechaContrato;

    private Double importeBonosBs;

    private Double cantidadBonos;

    private LocalDate fechaCompraBonos;

    private LocalDate fechaEmision;

    private String observaciones;

    private String origenRecursos;

    private String destinoRecursos;

    private String cuentaAportePropio;

    private LocalDate fechaFinalizacionContrato;

    private String fideicomisoAdministracion;

    private LocalDate fechaDeposito;

    private String numeroCuentaDestino;

    private String lugarDepositoEnvio;

    private LocalDate fechaCobro;

    private LocalDate fechaNegociacion;

    private String dpfNegociado;

    private LocalDate fechaEnvio;

    private String lugarCobro;

    private String objetoBoleta;

    private String boletaEjecucion;

    private String tipoGarantia;

    private String paisDestino;

    private LocalDate fechaCheque;

    private String departamentoDeposito;

    private String direccionAgenciaDeposito;

    private String lugarEnvio;

    private Integer numeroRegistrosPrimeraPersona;

    private Integer numeroRegistrosSegundaPersona;

    private List<Persona> personas;

}

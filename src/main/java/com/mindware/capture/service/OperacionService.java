package com.mindware.capture.service;

import com.mindware.capture.dto.IPersona;
import com.mindware.capture.dto.Operaciones;
import com.mindware.capture.dto.Persona;
import com.mindware.capture.dto.informix.IGbagePrdeu;
import com.mindware.capture.model.informix.*;
import com.mindware.capture.model.postgres.Parameter;
import com.mindware.capture.repository.informix.*;
import com.mindware.capture.repository.postgres.ParameterRepository;
import com.mindware.capture.util.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class OperacionService {

    @Autowired
    private GbageRepository gbageRepository;

    @Autowired
    private CamcaRepository camcaRepository;

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private CaconRepository caconRepository;

    @Autowired
    private CafirRepository cafirRepository;

    @Autowired
    private PrmprRepository prmprRepository;

    @Autowired
    private PrdeuRepository prdeuRepository;

    @Autowired
    private PrconRepository prconRepository;

    @Autowired
    private GbtgaService gbtgaService;

    @Autowired
    private GbciiService gbciiService;

    @Autowired
    private PfmdpRepository pfmdpRepository;

    @Autowired
    private PfconRepository pfconRepository;

    @Autowired
    private LcmlcRepository lcmlRepository;

    @Autowired
    private LcconRepository lcconRepository;

    @Autowired
    private LcdlcRepository lcdlcRepository;


    @Autowired
    Utiles utiles;

    List<String> listaDocumentos = new ArrayList<>();
    List<Gbage> listaPersonas = new ArrayList<>();



    public List<Operaciones> generarOperaciones(String documentos){

        String[] arrayCarnet = documentos.trim().split("\\s*,\\s*");

       listaDocumentos = Arrays.asList(arrayCarnet);
       listaPersonas = getListaPersonas(listaDocumentos);

        List<Operaciones> listaOperaciones = new ArrayList<>();

        listaOperaciones = getListaOperacionesCajaAhorro();

        listaOperaciones.addAll(getListaOperacionesCreditos());

        listaOperaciones.addAll(getListaOperacionesDpf());

        listaOperaciones.addAll(getListaOperacionesLineaCreditos());


        return listaOperaciones;
    }

    private List<Gbage> getListaPersonas(List<String> listaDocumentos){

        List<Gbage> gbageList = gbageRepository.findByListGbagendid(listaDocumentos);
        return gbageList;
    }

    private List<Camca> getListCajaAhorro(List<Gbage> listaPersonas){

        List<Camca> listaCajasAhorro = new ArrayList<>();
        for(Gbage persona: listaPersonas ){
            List<Camca> camcaList = camcaRepository.findByCamcacage(persona.getGbagecage());
            listaCajasAhorro.addAll(camcaList);
        }
        return listaCajasAhorro;
    }

    //OPERACIONES CAJAS DE AHORRO
    private List<Operaciones> getListaOperacionesCajaAhorro(){

        List<Camca> listaCajasAhorro = getListCajaAhorro(listaPersonas);
        List<Parameter> parameters = new ArrayList<>();

        List<Operaciones> operacionesList = new ArrayList<>();

        for (Camca camca : listaCajasAhorro) {
            Operaciones operaciones = new Operaciones();
            Gbage cliente = listaPersonas.stream()
                    .filter(persona -> camca.getCamcacage().equals(persona.getGbagecage()))
                    .findFirst().orElse(null);

            operaciones.setDocumentoPersona(cliente.getGbagendid());
            operaciones.setNumeroOperacion(camca.getCamcancta());
            operaciones.setTipoOperacion("CA");

            Parameter parameter = utiles.getCodigoUIF("TIPO_MANEJO_CUENTA","CH", camca.getCamcamane().toString());

            operaciones.setTipoManejoCuenta(parameter.getCodigo());
            operaciones.setFechaAperturaCuenta(camca.getCamcafapt());

            parameters = parameterRepository.findByCategoria("MONEDA");
            parameter = parameters.stream()
                    .filter(param -> param.getCodigoExterno().equals(camca.getCamcacmon().toString()))
                    .findFirst().orElse(new Parameter());

            operaciones.setMoneda(parameter.getCodigo());
            operaciones.setSaldoCuenta(Math.abs(camca.getCamcasact()));

            parameter = utiles.getCodigoUIF("ESTADO_CUENTA","CH", camca.getCamcastat().toString());
            operaciones.setEstadoCuenta(parameter.getCodigo());

            operaciones.setTipoPrestamoBono(null);

            operaciones.setFormaPago(null);

            operaciones.setNombreGarante(null);
            operaciones.setDescripcionGarantia(null);
            operaciones.setObjetivoPrestamo(null);
            operaciones.setFechaSolicitudPrestamo(null);
            operaciones.setFechaAprobacionPrestamo(null);

            operaciones.setFechaCierre(operaciones.getEstadoCuenta().equals("CER")?camca.getCamcafcbl():null);
            operaciones.setFuentePago(null);
            operaciones.setFechaDesembolso(null);
            operaciones.setPlazoVigencia(null);

            operaciones.setMontoLimiteCompra(null);
            operaciones.setFechaConstitucion(null);
            operaciones.setFechaPagoEfectivo(null);
            operaciones.setFechaVencimiento(null);
            operaciones.setMonto(null);
            operaciones.setMontoIndividual(null);

            operaciones.setNombreAsociacion(null);
            operaciones.setNumeroContrato(null);
            operaciones.setObjetoFideicomisoLineaCredito(null);
            operaciones.setFechaContrato(null);
            operaciones.setImporteBonosBs(null);
            operaciones.setCantidadBonos(null);
            operaciones.setFechaCompraBonos(null);

            operaciones.setFechaEmision(null);

            operaciones.setObservaciones(camca.getCamcainst());
            operaciones.setOrigenRecursos(null);
            operaciones.setDestinoRecursos(null);

            operaciones.setCuentaAportePropio(null);
            operaciones.setFechaFinalizacionContrato(null);
            operaciones.setFideicomisoAdministracion(null);
            operaciones.setFechaDeposito(null);

            operaciones.setDepartamentoDeposito(null);
            operaciones.setDireccionAgenciaDeposito(null);
            operaciones.setNumeroCuentaDestino(null);
            operaciones.setLugarEnvio(null);

            operaciones.setFechaCobro(null);
            operaciones.setFechaNegociacion(null);
            operaciones.setDpfNegociado(null);

            operaciones.setFechaEnvio(null);
            operaciones.setLugarCobro(null);

            operaciones.setObjetoBoleta(null);
            operaciones.setBoletaEjecucion(null);
            operaciones.setTipoGarantia(null);
            operaciones.setPaisDestino(null);
            operaciones.setFechaCheque(null);

            List<Cafir> cafirList = cafirRepository.findByIdCafirncta(camca.getCamcancta());

            operaciones.setNumeroRegistrosPrimeraPersona(1);
            operaciones.setNumeroRegistrosSegundaPersona(cafirList.size()-1);

            List<IPersona> listPersona = camcaRepository.findClientesByCajaAhorro(camca.getCamcancta());
            List<Persona> personaList = listaPersonasOperaciones(listPersona);

            operaciones.setPersonas(personaList);
            operacionesList.add(operaciones);

        }
        return operacionesList;
    }

    private List<Persona> listaPersonasOperaciones(List<IPersona> listPersona){
        List<Persona> personas = new ArrayList<>();
        Parameter parameter = new Parameter();

        for(IPersona persona: listPersona){
            Persona per = new Persona();
            String carnet = persona.getGbagendid().trim();
            String extension = carnet.substring(carnet.length()-2);
            String literal = carnet.substring(carnet.length()-3,carnet.length()-2);

            String complemento="";
            String numeroCarnet = "";

            parameter = utiles.getCodigoUIF("TIPO_PERSONA","TP", persona.getGbagetper().toString());
            per.setTipoPersona(parameter.getCodigo());
            if(parameter.getCodigo().equals("J")){
                numeroCarnet = persona.getGbagenruc()==null?persona.getGbagendid():persona.getGbagenruc();
            }else {
                if (literal.matches("^[a-zA-Z]*$")) {
                    complemento = carnet.substring(carnet.length() - 4, carnet.length() - 2);
                    numeroCarnet = carnet.substring(0, carnet.length() - 4);
                } else {
                    numeroCarnet = carnet.substring(0, carnet.length() - 2);
                }
            }


            parameter = utiles.getCodigoUIF("TIPO_DOCUMENTO","TID", persona.getGbagetdid().toString());
            per.setTipoDocumento(parameter.getCodigo());

            per.setNumeroDocumento(numeroCarnet);
            per.setComplemento(complemento);
            parameter = utiles.getCodigoUIF("DOC_EXTENSION","EX", extension);
            per.setTipoExtension(parameter.getCodigo());

            per.setPrimerNombre(persona.getGbdacnom1());
            per.setSegundoNombre(persona.getGbdacnom2());
            per.setPrimerApellido(persona.getGbdacape1());
            per.setSegundoApellido(persona.getGbdacape2());
            parameter = utiles.getCodigoUIF("ESTADO_CIVIL","EC", persona.getGbageeciv().toString());
            per.setEstadoCivil(parameter.getCodigo());
            per.setRazonSocial(per.getTipoPersona().equals("J")?persona.getGbagenomb():"");

            personas.add(per);
        }

        return personas;
    }


    //OPERACIONES CREDITOS

    public List<Operaciones> getListaOperacionesCreditos(){

        List<Prmpr> listaPrestamos = prmprRepository.findByGbagendid(listaDocumentos);
        List<Parameter> parameters = new ArrayList<>();
        List<Operaciones> operacionesList = new ArrayList<>();

        for(Prmpr prmpr: listaPrestamos){
            Operaciones operaciones = new Operaciones();
            Gbage cliente = listaPersonas.stream()
                    .filter(persona -> prmpr.getPrmprcage().equals(persona.getGbagecage()))
                    .findFirst().orElse(null);

            operaciones.setDocumentoPersona(cliente.getGbagendid());
            operaciones.setNumeroOperacion(prmpr.getPrmprnpre().toString());
            operaciones.setTipoOperacion("PRE");

//            List<Prdeu> prdeuList = prdeuRepository.findByIdPrdeunpre(prmpr.getPrmprnpre());
//
//            operaciones.setTipoManejoCuenta(prdeuList.size()==1?"UNI":"MAN"); //TODO: consultar si es nulo, individual o mancomunada segun #codeudores

            operaciones.setTipoManejoCuenta(null);
            operaciones.setFechaAperturaCuenta(null);
//            operaciones.setFechaAperturaCuenta(prmpr.getPrmprfreg()); //TODO: consultar si en la fecha de apertura va fecha desembolso o fecha de registro

            parameters = parameterRepository.findByCategoria("MONEDA");
            Parameter parameter = parameters.stream()
                    .filter(param -> param.getCodigoExterno().equals(prmpr.getPrmprcmon().toString()))
                    .findFirst().orElse(new Parameter());

            operaciones.setMoneda(parameter.getCodigo());
            operaciones.setSaldoCuenta(prmpr.getPrmprsald());

            Optional<Prcon> prcon = prconRepository.findByIdPrconprefAndIdPrconcorr(4,prmpr.getPrmprstat());
            if(prcon.isPresent()){
                parameter = utiles.getCodigoUIF("ESTADO_CUENTA","PRE",prmpr.getPrmprstat().toString());
                operaciones.setEstadoCuenta(parameter.getCodigo());
            }else operaciones.setEstadoCuenta("Desconocido");

            prcon = prconRepository.findByIdPrconprefAndIdPrconcorr(2,prmpr.getPrmprfpag());
            operaciones.setFormaPago(prcon.get().getPrcondesc());  //TODO: Catalogo forma pago no corresponde con los codigos del core


            String garantes = "";
            int i=1;
            List<IGbagePrdeu> gbagePrdeuList = prdeuRepository.findGbagePrdeuByPrdeunpre(prmpr.getPrmprnpre());
            for(IGbagePrdeu g: gbagePrdeuList){
                if (i == gbagePrdeuList.size())
                    garantes = g.getPrdeutres()==2?g.getGbagenomb() + " " + garantes:garantes;
                else
                    garantes = g.getPrdeutres()==2? g.getGbagenomb() + ", "  + garantes :garantes;
                i=+1;
            }
            operaciones.setNombreGarante(garantes);

            String garantias = gbtgaService.getGbtgadesc(prmpr.getPrmprnpre());
            operaciones.setDescripcionGarantia(garantias);

            prcon = prconRepository.findByIdPrconprefAndIdPrconcorr(50,prmpr.getPrmprdest());  //TODO: CAMPO A TOMAR PARA EL OBJETO DEL CREDITO
            if(prcon.isPresent()){
                operaciones.setObjetivoPrestamo(prcon.get().getPrcondesc()
                        + prmpr.getPrmprddes() != null?", " +prmpr.getPrmprddes():"");
            }else operaciones.setObjetivoPrestamo("");

            operaciones.setFechaSolicitudPrestamo(prmpr.getPrmprfreg()); //TODO: QUE TABLA WORKFLOW SE GUARDA LA FECHA SOLICITUD
            operaciones.setFechaAprobacionPrestamo(prmpr.getPrmprfreg()); //TODO: QUE TABLA WORKFLOW SE GUARDA LA FECHA APROBACION
            operaciones.setFechaCierre(prmpr.getPrmprstat()==7?prmpr.getPrmprfulp():null); //TODO: FECHA CIERRE == FECHA CANCELACION?

            operaciones.setFuentePago(gbciiService.getCiiudesc(cliente.getGbageciiu())); //TODO: PENDIENTE MIGRACION, consultar
            operaciones.setFechaDesembolso(prmpr.getPrmprfdes());

            prcon = prconRepository.findByIdPrconprefAndIdPrconcorr(3,prmpr.getPrmpruplz());
            if(prcon.isPresent()) {
                Integer plazo = prcon.get().getId().getPrconcorr().equals(1)?prmpr.getPrmprplzo()*360
                        :prcon.get().getId().getPrconcorr().equals(2)?prmpr.getPrmprplzo()*30
                        :prmpr.getPrmprplzo();
                operaciones.setPlazoVigencia(plazo.toString());
            }
            else operaciones.setPlazoVigencia(prmpr.getPrmprplzo().toString());

//            operaciones.setGarantia(gbtgaService.getGbtgadesc(prmpr.getPrmprnpre())); //TODO: PENDIENTE MIGRACION

            operaciones.setMontoLimiteCompra(null);
            operaciones.setFechaConstitucion(null);
            operaciones.setFechaPagoEfectivo(null);
            operaciones.setFechaVencimiento(null);

//            operaciones.setFechaVencimiento(prmpr.getPrmprfvac());
            operaciones.setMonto(prmpr.getPrmprmapt());

            operaciones.setMontoIndividual(null); //TODO: En prestamos bajo qu  e condicion se tiene un monto individual

            operaciones.setNombreAsociacion(null); //TODO: Campo a considerar para banca comunual, que campo considerar

            operaciones.setNumeroContrato(null);
            operaciones.setObjetoFideicomisoLineaCredito(null);

            operaciones.setFechaContrato(null);
            operaciones.setImporteBonosBs(null);
            operaciones.setCantidadBonos(null);
            operaciones.setFechaCompraBonos(null);
            operaciones.setObservaciones(null); //TODO: OBSERVACIONES PARA CREDITOS

            operaciones.setOrigenRecursos(null);
            operaciones.setDestinoRecursos(null);

//            prcon = prconRepository.findByIdPrconprefAndIdPrconcorr(51,prmpr.getPrmprorgr());
//            operaciones.setOrigenRecursos(prcon.isPresent()?prcon.get().getPrcondesc():"");
//            operaciones.setDestinoRecursos(gbciiService.getCiiudesc(prmpr.getPrmprciiu()));

            operaciones.setCuentaAportePropio(prmpr.getPrmprctad()); //TODO: verficicar, esta la cuenta de desembolso
            operaciones.setFechaFinalizacionContrato(null);
            operaciones.setFideicomisoAdministracion(null);
            operaciones.setFechaDeposito(null);
            operaciones.setDepartamentoDeposito(null);
            operaciones.setDireccionAgenciaDeposito(null);
            operaciones.setNumeroCuentaDestino(null);
//            operaciones.setFechaEmision(null);

            operaciones.setLugarEnvio(null);
            operaciones.setFechaCobro(null);
            operaciones.setFechaNegociacion(null);
            operaciones.setDpfNegociado(null);
            operaciones.setFechaEnvio(null);
            operaciones.setLugarCobro(null);
            operaciones.setObjetoBoleta(null);
            operaciones.setBoletaEjecucion(null);

            operaciones.setTipoGarantia(null); //TODO: PENDIENTE DE TENER ACCESO TABLA GAGAR PARA OBTENER GARANTIA PRINCIPAL
            operaciones.setPaisDestino(null);
            operaciones.setFechaCheque(null);

            Long primera = gbagePrdeuList.stream()
                    .filter(p -> p.getPrdeutres()==1)  //DIRECTA
                    .count();
            operaciones.setNumeroRegistrosPrimeraPersona(primera.intValue());
            operaciones.setNumeroRegistrosSegundaPersona(gbagePrdeuList.size()- primera.intValue());

            List<IPersona> listPersona = prmprRepository.findClientesByNumeroCredito(prmpr.getPrmprnpre().toString());
            List<Persona> personaList = listaPersonasOperaciones(listPersona);

            operaciones.setPersonas(personaList);
            operacionesList.add(operaciones);

        }

        return operacionesList;
    }

    private List<Pfmdp> getListaDpf(List<Gbage> listaPersonas){

        List<Pfmdp> listaDpf = new ArrayList<>();
        for(Gbage persona: listaPersonas){
            List<Pfmdp> pfmdpList = pfmdpRepository.findByPfmdpcage(persona.getGbagecage());
            listaDpf.addAll(pfmdpList);
        }
        return listaDpf;
    }

// LISTA OPERACIONES DPF
    public List<Operaciones> getListaOperacionesDpf(){

        List<Parameter> parameters = new ArrayList<>();

        List<Pfmdp> listaDpf = getListaDpf(listaPersonas);

        List<Operaciones> operacionesList = new ArrayList<>();

        for(Pfmdp pfmdp : listaDpf){
            Operaciones operaciones = new Operaciones();

            Gbage cliente = listaPersonas.stream()
                    .filter(persona -> pfmdp.getPfmdpcage().equals(persona.getGbagecage()))
                    .findFirst().orElse(null);

            operaciones.setDocumentoPersona(cliente.getGbagendid());
            operaciones.setNumeroOperacion(pfmdp.getPfmdpndep().toString());
            operaciones.setTipoOperacion("DPF");

            Parameter parameter = utiles.getCodigoUIF("TIPO_MANEJO","DPF", pfmdp.getPfmdpmane().toString());
            operaciones.setTipoManejoCuenta(parameter.getCodigo());
            operaciones.setFechaAperturaCuenta(pfmdp.getPfmdpfreg());

            parameters = parameterRepository.findByCategoria("MONEDA");
            parameter = parameters.stream()
                    .filter(param -> param.getCodigoExterno().equals(pfmdp.getPfmdpcmon().toString()))
                    .findFirst().orElse(new Parameter());

            operaciones.setMoneda(parameter.getCodigo());
            operaciones.setSaldoCuenta(pfmdp.getPfmdpcapi());  //TODO: CONSULTAR SI EL CAPITAL DE ES EL SALDO EN DPF

            parameter = utiles.getCodigoUIF("ESTADO_CUENTA","DPF",pfmdp.getPfmdpstat().toString());
            operaciones.setEstadoCuenta(parameter.getCodigo());
            operaciones.setTipoPrestamoBono(null);
            operaciones.setFormaPago(null);
            operaciones.setNombreGarante(null);

            operaciones.setDescripcionGarantia(null);
            operaciones.setObjetivoPrestamo(null);
            operaciones.setFechaSolicitudPrestamo(null);
            operaciones.setFechaAprobacionPrestamo(null);
            operaciones.setFechaCancelacion(null);
            operaciones.setFuentePago(null);
            operaciones.setFechaDesembolso(null);
            operaciones.setPlazoVigencia(pfmdp.getPfmdpplzo().toString());
            operaciones.setMontoLimiteCompra(null);
            operaciones.setFechaConstitucion(pfmdp.getPfmdpfreg());
            operaciones.setFechaPagoEfectivo(null);
            operaciones.setFechaVencimiento(pfmdp.getPfmdpfvto());
            operaciones.setMonto(null);
            operaciones.setMontoIndividual(null);
            operaciones.setNombreAsociacion(null);
            operaciones.setNumeroContrato(null);
            operaciones.setObjetoFideicomisoLineaCredito(null);
            operaciones.setFechaContrato(null);
            operaciones.setImporteBonosBs(null);
            operaciones.setCantidadBonos(null);
            operaciones.setFechaCompraBonos(null);
            operaciones.setObservaciones(null); //TODO: Para DPF, se registran observaciones?
            operaciones.setOrigenRecursos(null); //TODO: Para DPF, se registra el origen de recursos?
            operaciones.setDestinoRecursos(null);

            operaciones.setCuentaAportePropio(null);
            operaciones.setFechaFinalizacionContrato(null);
            operaciones.setFideicomisoAdministracion(null);
            operaciones.setFechaDeposito(null);

            operaciones.setDepartamentoDeposito(null);
            operaciones.setDireccionAgenciaDeposito(null);
            operaciones.setNumeroCuentaDestino(null);
            operaciones.setLugarEnvio(null);

            operaciones.setFechaCobro(null); //TODO: Fecha de cobro en DPF, que campo considerar
            operaciones.setFechaNegociacion(null); //TODO: Fecha de negociacion en DPF, que campo considerar
            operaciones.setDpfNegociado(null); //TODO: DPF negociado en DFP, que campo considerar

            operaciones.setFechaEnvio(null);
            operaciones.setLugarCobro(null);
            operaciones.setObjetoBoleta(null);
            operaciones.setBoletaEjecucion(null);
            operaciones.setTipoGarantia(null);
            operaciones.setPaisDestino(null);
            operaciones.setFechaCheque(null);


            operaciones.setNumeroRegistrosPrimeraPersona(1);
            int countSegundaPersona = 0;
            countSegundaPersona = (pfmdp.getPfmdpcag2()!=null) && (pfmdp.getPfmdpcag2()>0)?countSegundaPersona+1:countSegundaPersona;
            countSegundaPersona = (pfmdp.getPfmdpcag3()!=null) && (pfmdp.getPfmdpcag3()>0)?countSegundaPersona+1:countSegundaPersona;
            countSegundaPersona = (pfmdp.getPfmdpcag4()!=null) && (pfmdp.getPfmdpcag4()>0)?countSegundaPersona+1:countSegundaPersona;
            operaciones.setNumeroRegistrosSegundaPersona(countSegundaPersona);

            List<Integer> gbagecageList = new ArrayList<>();
            gbagecageList.add(pfmdp.getPfmdpcage());
            if(pfmdp.getPfmdpcag2()!=null)  gbagecageList.add(pfmdp.getPfmdpcag2());
            if(pfmdp.getPfmdpcag3()!=null)  gbagecageList.add(pfmdp.getPfmdpcag3());

            List<IPersona> listPersona = pfmdpRepository.findClientesInDPF(gbagecageList);
            List<Persona> personaList = listaPersonasOperaciones(listPersona);
            operaciones.setPersonas(personaList);

            operacionesList.add(operaciones);

        }

        return operacionesList;
    }

    //OPERACIONES LINEAS DE CREDITO
    public List<Operaciones> getListaOperacionesLineaCreditos(){
        List<Lcmlc> listaLineaCreditos = lcmlRepository.findByGbagendid(listaDocumentos);
        List<Parameter> parameters = new ArrayList<>();
        List<Operaciones> operacionesList = new ArrayList<>();

        for(Lcmlc lcmlc: listaLineaCreditos){
            Operaciones operaciones = new Operaciones();

            Gbage cliente = listaPersonas.stream()
                    .filter(persona -> lcmlc.getLcmlccage().equals(persona.getGbagecage()))
                    .findFirst().orElse(null);

            operaciones.setDocumentoPersona(cliente.getGbagendid());
            operaciones.setNumeroOperacion(lcmlc.getLcmlcnrlc().toString());
            operaciones.setTipoOperacion("LDC");
            operaciones.setTipoManejoCuenta(null);
            operaciones.setFechaAperturaCuenta(null);

            parameters = parameterRepository.findByCategoria("MONEDA");
            Parameter parameter = parameters.stream()
                    .filter(param -> param.getCodigoExterno().equals(lcmlc.getLcmlccmon().toString()))
                    .findFirst().orElse(new Parameter());

            operaciones.setMoneda(parameter.getCodigo());
            operaciones.setSaldoCuenta(lcmlc.getLcmlcmapr());

            Optional<Lccon> lccon = lcconRepository.findByIdLcconpfijAndIdLcconcorr(1,lcmlc.getLcmlcstat());
            if(lccon.isPresent()){
                parameter = utiles.getCodigoUIF("ESTADO_CUENTA","LC", lcmlc.getLcmlcstat().toString());
                operaciones.setEstadoCuenta(parameter.getCodigo());
            } else operaciones.setEstadoCuenta("Desconocido");

            operaciones.setTipoPrestamoBono(null);
            operaciones.setFormaPago(null);
            operaciones.setNombreGarante(null);
            operaciones.setDescripcionGarantia(null);
            operaciones.setObjetivoPrestamo(null);
            operaciones.setFechaSolicitudPrestamo(null);
            operaciones.setFechaAprobacionPrestamo(null);
            operaciones.setFechaCancelacion(null);
            operaciones.setFuentePago(null);
            operaciones.setFechaDesembolso(null);
            Long plzo = DAYS.between(lcmlc.getLcmlcfini(),lcmlc.getLcmlcfven());
            operaciones.setPlazoVigencia(plzo.toString()); //TODO: El plazo de la linea esta nulo

            operaciones.setMontoLimiteCompra(null);
            operaciones.setFechaConstitucion(null);
            operaciones.setFechaPagoEfectivo(null);
            operaciones.setFechaVencimiento(null);
            operaciones.setMonto(null);
            operaciones.setMontoIndividual(null);
            operaciones.setNombreAsociacion(null);
            operaciones.setNumeroContrato(null);

            operaciones.setObjetoFideicomisoLineaCredito(lcmlc.getLcmlcdest()!=null?lcmlc.getLcmlcdest().toString():""); //TODO: Campo a considerar para la linea de credito, configuracion

            operaciones.setFechaContrato(null);
            operaciones.setImporteBonosBs(null);
            operaciones.setCantidadBonos(null);
            operaciones.setFechaCompraBonos(null);
            operaciones.setObservaciones(null);
            operaciones.setOrigenRecursos(null);
            operaciones.setDestinoRecursos(null);
            operaciones.setCuentaAportePropio(null);
            operaciones.setFechaFinalizacionContrato(null);
            operaciones.setFideicomisoAdministracion(null);
            operaciones.setFechaDeposito(null);
            operaciones.setDepartamentoDeposito(null);
            operaciones.setDireccionAgenciaDeposito(null);
            operaciones.setNumeroCuentaDestino(null);
            operaciones.setLugarEnvio(null);
            operaciones.setFechaCobro(null);
            operaciones.setFechaNegociacion(null);
            operaciones.setDpfNegociado(null);
            operaciones.setFechaEnvio(null);
            operaciones.setLugarCobro(null);
            operaciones.setObjetoBoleta(null);
            operaciones.setBoletaEjecucion(null);
            operaciones.setTipoGarantia(null);
            operaciones.setPaisDestino(null);
            operaciones.setFechaCheque(null);

            List<Lcdlc> lcdlcList = lcdlcRepository.findByIdLcdlcnrlc(lcmlc.getLcmlcnrlc());
            operaciones.setNumeroRegistrosPrimeraPersona(lcdlcList.size()); //TODO: en linea de credito exite segundas personas?
            operaciones.setNumeroRegistrosSegundaPersona(0);// TODO: si se tiene segundas personas se coloca el valor

            List<IPersona> listPersona = lcmlRepository.findByNumeroLinea(lcmlc.getLcmlcnrlc());
            List<Persona> personaList = listaPersonasOperaciones(listPersona);
            operaciones.setPersonas(personaList);

            operacionesList.add(operaciones);

        }
        return operacionesList;
    }


}

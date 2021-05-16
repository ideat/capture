package com.mindware.capture.service;

import com.mindware.capture.dto.Transacciones;
import com.mindware.capture.model.informix.*;
import com.mindware.capture.model.postgres.Parameter;
import com.mindware.capture.repository.informix.*;
import com.mindware.capture.util.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TransaccionesService {


    @Autowired
    private GbageRepository gbageRepository;

    @Autowired
    private CamcaRepository camcaRepository;

    @Autowired
    private CatrnRepository catrnRepository;

    @Autowired
    private CatmvRepository catmvRepository;

    @Autowired
    private GbconRepository gbconRepository;

    @Autowired
    private PrmprRepository prmprRepository;

    @Autowired
    private PrtdtRepository prtdtRepository;

    @Autowired
    private PfmdpRepository pfmdpRepository;

    @Autowired
    private PftdtRepository pftdtRepository;

    @Autowired
    private LcmlcRepository lcmlcRepository;

    @Autowired
    private LctrnRepository lctrnRepository;

    @Autowired
    Utiles utiles;

    List<String> listaDocumentos = new ArrayList<>();
    List<Gbage> listaPersonas = new ArrayList<>();

    private List<Gbage> getListaPersonas(List<String> listaDocumentos){

        List<Gbage> gbageList = gbageRepository.findByListGbagendid(listaDocumentos);
        return gbageList;
    }

    public List<Transacciones> generarTransacciones(String documentos){

        String[] arrayCarnet = documentos.trim().split("\\s*,\\s*");

        List<Transacciones> listaTransacciones = new ArrayList<>();

        listaDocumentos = Arrays.asList(arrayCarnet);
        listaPersonas = getListaPersonas(listaDocumentos);


        return listaTransacciones;
    }

    //Tranacciones Cajas de Ahorro
    private List<Camca> getListCajaAhorro(List<Gbage> listaPersonas){

        List<Camca> listaCajasAhorro = new ArrayList<>();
        for(Gbage persona: listaPersonas ){
            List<Camca> camcaList = camcaRepository.findByCamcacage(persona.getGbagecage());
            listaCajasAhorro.addAll(camcaList);
        }
        return listaCajasAhorro;
    }


    private List<Transacciones> generarTransaccionesCajasAhorro(){
        List<Camca> listaCajasAhorro = getListCajaAhorro(listaPersonas);
        List<Parameter> parameters = new ArrayList<>();

        List<Transacciones> transaccionesList = new ArrayList<>();
        Optional<Gbcon> gbcon = Optional.empty();

        for(Camca camca : listaCajasAhorro){
            List<Catrn> catrnList = catrnRepository.findByCatrnncta(camca.getCamcancta());
            Optional<Gbage> gbage = gbageRepository.findById(camca.getCamcacage());

            Transacciones transacciones = new Transacciones();

            for(Catrn catrn: catrnList){
                transacciones.setDocumentoPersonaSolicitud(gbage.get().getGbagendid());
                transacciones.setNumeroOperacion(catrn.getCatrnncta());
                transacciones.setTipoOperacion("CA");
                transacciones.setNumeroTransaccion(catrn.getCatrnntra().toString());
                transacciones.setFechaTransaccion(catrn.getCatrnftra());
                transacciones.setTipoTransaccion(catrn.getCatrnimpo()>0?"CRE":"DEB"); //TODO: CON VALOR NEGATIVO EN TRANSACCION CAJA AHORRO ES DEBITO?

                Parameter parameter = utiles.getCodigoUIF("TIPO_MODALIDAD", catrn.getCatrnpref().toString(), catrn.getCatrncorr().toString());

                transacciones.setTipoFormaPagoTransaccion(parameter.getCodigo());
                transacciones.setMontoTransaccion(Math.abs(catrn.getCatrnimpo()));

                Double saldo = catrnRepository.getCalcularSaldo(catrn.getCatrnncta(), catrn.getCatrnftra(), catrn.getCatrnntra());
                transacciones.setSaldo(Math.abs(saldo));

                transacciones.setGlosaEntidad(catrn.getCatrnglos());
                gbcon = gbconRepository.findByIdGbconpfijAndIdGbconcorr(500,catrn.getCatrnagen());
                transacciones.setAgencia(gbcon.isPresent()? gbcon.get().getGbcondesc():"");
                transacciones.setNombrePersonaTransaccion(gbage.get().getGbagenomb()); //TODO: En cajas ahorro nombre de la persona que hizo la tranccion
                transacciones.setNumeroCuentaTransferencia(null); //TODO: Donde se ven las transaferencias en cajas de ahorro
                transacciones.setNombreTitularCuentaTransferencia(null); //TODO: Donde estan los datos del propietario cuenta de transferencia
                transacciones.setBancoTransferencia(null);
                transacciones.setPaisTransferencia(null);
                transacciones.setCiudadTransferencia(null);
                transacciones.setNumeroCheque(null);
                transacciones.setFechaProgramada(null);
                transacciones.setImporteProgramado(null);
                transacciones.setNombreGiroCheque(null);
                transacciones.setNombreCobradorCheque(null);
                transacciones.setNumeroDpfRenovacion(null);

                transaccionesList.add(transacciones);

            }

        }

        return transaccionesList;

    }

    //Transacciones de creditos
    private List<Transacciones> generarTransaccionesCredito(){
        List<Prmpr> listaPrestamos = prmprRepository.findByGbagendid(listaDocumentos);
        List<Parameter> parameters = new ArrayList<>();
        List<Transacciones> transaccionesList = new ArrayList<>();
        Optional<Gbcon> gbcon = Optional.empty();

        for(Prmpr prmpr: listaPrestamos){
            List<Prtdt> prtdtList = prtdtRepository.findByPrtdtnpre(prmpr.getPrmprnpre());
            Optional<Gbage> gbage = gbageRepository.findById(prmpr.getPrmprcage());

            Transacciones transacciones = new Transacciones();

            for(Prtdt prtdt: prtdtList){
                transacciones.setDocumentoPersonaSolicitud(gbage.get().getGbagendid());
                transacciones.setNumeroOperacion(prtdt.getPrtdtnpre().toString());
                transacciones.setTipoOperacion("PRE");
                transacciones.setNumeroTransaccion(prtdt.getPrtdtntra().toString());
                transacciones.setFechaTransaccion(prtdt.getPrtdtftra());
                transacciones.setTipoTransaccion(prtdt.getPrtdtimpp()>=0?"CRE":"DEB");

                transacciones.setTipoFormaPagoTransaccion(prtdt.getPrtdtpref()==1?"ABA":"EFE"); //TODO: Transacciones credito desembolso es abono automatico?

                transacciones.setMontoTransaccion(Math.abs(prtdt.getPrtdtimpp()));
                Double saldo = prtdtRepository.getSaldo(prtdt.getPrtdtnpre(), prtdt.getPrtdtntra(), prtdt.getPrtdtftra());
                transacciones.setSaldo(saldo);
                transacciones.setGlosaEntidad(prtdt.getPrtdtdesc());
                transacciones.setAgencia(null); //TODO : Transacciones de credito, la agencia se registra?
                transacciones.setNombrePersonaTransaccion(gbage.get().getGbagenomb()); //TODO: Se puede obtener el nombre de la persona que realizo un pago?

                transacciones.setNumeroCuentaTransferencia(null);
                transacciones.setNombreTitularCuentaTransferencia(null);
                transacciones.setBancoTransferencia(null);
                transacciones.setPaisTransferencia(null);
                transacciones.setCiudadTransferencia(null);
                transacciones.setNumeroCheque(null);
                transacciones.setFechaProgramada(null); //TODO: Fecha programada en creditos donde se registra, para transacciones
                transacciones.setImporteProgramado(null); //TODO: Importe programado para creditos donde se registra
                transacciones.setNombreGiroCheque(null);
                transacciones.setNombreCobradorCheque(null);
                transacciones.setNumeroDpfRenovacion(null);

                transaccionesList.add(transacciones);
            }
        }

        return transaccionesList;
    }

    //Transacciones de DPF

    private List<Pfmdp> getListaDpf(List<Gbage> listaPersonas){

        List<Pfmdp> listaDpf = new ArrayList<>();
        for(Gbage persona: listaPersonas){
            List<Pfmdp> pfmdpList = pfmdpRepository.findByPfmdpcage(persona.getGbagecage());
            listaDpf.addAll(pfmdpList);
        }
        return listaDpf;
    }

    private List<Transacciones> generarTransaccionesDpf(){
        List<Pfmdp> listaDpf = getListaDpf(listaPersonas);

        List<Transacciones> listaTransacciones = new ArrayList<>();

        for(Pfmdp pfmdp: listaDpf){
            List<Pftdt> pftdtList = pftdtRepository.findByPftdtndep(pfmdp.getPfmdpndep());

            Transacciones transacciones = new Transacciones();
            Optional<Gbage> gbage = gbageRepository.findById(pfmdp.getPfmdpcage());

            for(Pftdt pftdt: pftdtList){
                transacciones.setDocumentoPersonaSolicitud(gbage.get().getGbagendid());
                transacciones.setNumeroOperacion(pftdt.getPftdtndep().toString());
                transacciones.setTipoOperacion("DPF");
                transacciones.setNumeroTransaccion(null);

                transacciones.setFechaTransaccion(pftdt.getPftdtftra());
                transacciones.setTipoTransaccion(null);
                transacciones.setTipoFormaPagoTransaccion(null);
                transacciones.setMontoTransaccion(null);
                transacciones.setSaldo(pfmdp.getPfmdpcapi());
                transacciones.setGlosaEntidad(null);

                transacciones.setAgencia(null);
                transacciones.setNombrePersonaTransaccion(null);
                transacciones.setNumeroCuentaTransferencia(null);
                transacciones.setNombreTitularCuentaTransferencia(null);
                transacciones.setBancoTransferencia(null);
                transacciones.setPaisTransferencia(null);
                transacciones.setCiudadTransferencia(null);
                transacciones.setNumeroCheque(null);
                transacciones.setFechaProgramada(null);
                transacciones.setImporteProgramado(null);
                transacciones.setImporteProgramado(null);
                transacciones.setNombreGiroCheque(null);
                transacciones.setNombreCobradorCheque(null);
                transacciones.setNumeroDpfRenovacion(pfmdp.getPfmdpndpr().toString());

                listaTransacciones.add(transacciones);

            }

        }
        return listaTransacciones;
    }

    //Transacciones Lineas Creditos
    private List<Transacciones> generarTransaccionesLineaCredito(){
        List<Lcmlc> listaLineasCredito = lcmlcRepository.findByGbagendid(listaDocumentos);

        List<Transacciones> listaTransacciones = new ArrayList<>();

        for(Lcmlc lcmlc: listaLineasCredito){
            List<Lctrn> lctrnList = lctrnRepository.findByLctrnnrlc(lcmlc.getLcmlcnrlc());

            Transacciones transacciones = new Transacciones();
            Optional<Gbage> gbage = gbageRepository.findById(lcmlc.getLcmlccage());

            for(Lctrn lctrn:lctrnList){
                transacciones.setDocumentoPersonaSolicitud(gbage.get().getGbagendid());
                transacciones.setNumeroOperacion(lctrn.getLctrnoper().toString());
                transacciones.setTipoOperacion("LDC");
                transacciones.setNumeroTransaccion(null);
                transacciones.setFechaTransaccion(lctrn.getLctrnftra());
                transacciones.setTipoTransaccion(null);
                transacciones.setTipoFormaPagoTransaccion(null);
                transacciones.setMontoTransaccion(null);
                transacciones.setSaldo(lcmlc.getLcmlcmapr());

                transacciones.setGlosaEntidad(null);

                transacciones.setAgencia(null);
                transacciones.setNombrePersonaTransaccion(null);
                transacciones.setNumeroCuentaTransferencia(null);
                transacciones.setNombreTitularCuentaTransferencia(null);
                transacciones.setBancoTransferencia(null);
                transacciones.setPaisTransferencia(null);
                transacciones.setCiudadTransferencia(null);
                transacciones.setNumeroCheque(null);
                transacciones.setFechaProgramada(null);
                transacciones.setImporteProgramado(null);
                transacciones.setImporteProgramado(null);
                transacciones.setNombreGiroCheque(null);
                transacciones.setNombreCobradorCheque(null);
                transacciones.setNumeroDpfRenovacion(null);

                listaTransacciones.add(transacciones);
            }

        }

        return listaTransacciones;
    }



}

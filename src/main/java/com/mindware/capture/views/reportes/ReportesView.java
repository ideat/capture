package com.mindware.capture.views.reportes;

import com.helger.commons.csv.CSVWriter;
import com.mindware.capture.dto.Operaciones;
import com.mindware.capture.dto.Persona;
import com.mindware.capture.dto.Transacciones;
import com.mindware.capture.util.Utiles;
import com.mindware.capture.views.busqueda.BusquedaView;
import com.mindware.capture.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.wontlost.sweetalert2.Config;
import com.wontlost.sweetalert2.SweetAlert2Vaadin;
import org.apache.commons.io.IOUtils;
import org.vaadin.tabs.PagedTabs;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route(value = "report", layout = MainView.class)
@ParentLayout(MainView.class)
@PageTitle("Reportes")
@CssImport("./views/reportes/reportes-view.css")
public class ReportesView extends Div implements RouterLayout {

    private Grid<Operaciones> gridOperaciones;
    private Grid<Transacciones> gridTransacciones;
    private ListDataProvider<Operaciones> operacionesListDataProvider;
    private ListDataProvider<Transacciones> transaccionesListDataProvider;
    private List<Operaciones> operacionesList;
    private List<Transacciones> transaccionesList;

    private  Anchor anchorOperaciones;
    private  Anchor anchorTransacciones;
    private  Checkbox checkHeaders;

    private Utiles utiles;

    public ReportesView() {
        addClassName("reportes-view");
        utiles = new Utiles();

        if (VaadinSession.getCurrent().getAttribute("operaciones") != null) {
            operacionesList = (List<Operaciones>) VaadinSession.getCurrent().getAttribute("operaciones");
            transaccionesList = (List<Transacciones>) VaadinSession.getCurrent().getAttribute("transacciones");

            gridOperaciones = new Grid<>();
            operacionesListDataProvider = new ListDataProvider<>(operacionesList);
            gridOperaciones.setHeight("600px");

            gridTransacciones = new Grid<>();
            transaccionesListDataProvider = new ListDataProvider<>(transaccionesList);
            gridTransacciones.setHeight("600px");

            createGridOperaciones();
            createGridTransacciones();

            add(mainPage());
        }else{
            Config config = new Config();
            config.setTitle("Atencion");
            config.setText("Realice una busqueda y procesamiento de datos para ver los reportes");
            config.setIcon("warning");
            new SweetAlert2Vaadin(config).open();
            UI.getCurrent().navigate(BusquedaView.class);
        }
    }

    private VerticalLayout mainPage(){
        HorizontalLayout headerButtons = new HorizontalLayout();
        headerButtons.setSpacing(true);

        checkHeaders = new Checkbox();
        checkHeaders.setLabel("Incluir cabeceras");
        checkHeaders.setValue(false);

        if(operacionesList.size() > 0) {

            anchorOperaciones = new Anchor(new StreamResource("operaciones.csv", this::getInputStreamOperaciones), "");
            anchorOperaciones.getElement().setAttribute("download",true);
            Button b1 = new Button("Operaciones");
            b1.setIcon(VaadinIcon.DOWNLOAD_ALT.create());
            anchorOperaciones.add(b1);
            headerButtons.add(anchorOperaciones);
        }
        if(transaccionesList.size() > 0) {
            anchorTransacciones = new Anchor(new StreamResource("transacciones.csv", this::getInputStreamTransacciones),"");
            anchorTransacciones.getElement().setAttribute("download",true);
            Button b2 = new Button("Transacciones");
            b2.setIcon(VaadinIcon.DOWNLOAD_ALT.create());
            anchorTransacciones.add(b2);
            headerButtons.add(anchorTransacciones);
        }

        headerButtons.add(checkHeaders);

        VerticalLayout container = new VerticalLayout();

        PagedTabs tabs = new PagedTabs(container);
        tabs.getContent().setWidthFull();

        tabs.add("Operaciones", gridOperaciones);
        tabs.add("Transacciones", gridTransacciones);

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.add(headerButtons,tabs,container);
        layout.expand(tabs,container);
        return layout;
    }

    private void createGridOperaciones(){

        gridOperaciones.setDataProvider(operacionesListDataProvider);

        gridOperaciones.addColumn(Operaciones::getDocumentoPersona)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Documento Persona");
        gridOperaciones.addColumn(Operaciones::getNumeroOperacion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Número operación");
        gridOperaciones.addColumn(Operaciones::getTipoOperacion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Tipo operación");
        gridOperaciones.addColumn(Operaciones::getTipoManejoCuenta)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Tipo manejo cuenta");
        gridOperaciones.addColumn(Operaciones::getFechaAperturaCuenta)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Fecha apertura cuenta");
        gridOperaciones.addColumn(Operaciones::getMoneda)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Moneda");
        gridOperaciones.addColumn(Operaciones::getSaldoCuenta)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Saldo cuenta");
        gridOperaciones.addColumn(Operaciones::getEstadoCuenta)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Estado cuenta");
        gridOperaciones.addColumn(Operaciones::getTipoPrestamoBono)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Tipo operación");
        gridOperaciones.addColumn(Operaciones::getFormaPago)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Forma pago");
        gridOperaciones.addColumn(Operaciones::getNombreGarante)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Nombre garantes");
        gridOperaciones.addColumn(Operaciones::getDescripcionGarantia)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Descripcion garantia");
        gridOperaciones.addColumn(Operaciones::getGarantia)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Garantia");
        gridOperaciones.addColumn(Operaciones::getObjetivoPrestamo)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Objeto Prestamo");
        gridOperaciones.addColumn(Operaciones::getFechaSolicitudPrestamo)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Solicitud Prestamo");
        gridOperaciones.addColumn(Operaciones::getFechaAprobacionPrestamo)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Aprobacion Prestamo");
        gridOperaciones.addColumn(Operaciones::getFechaCancelacion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Fecha cancelacion");
        gridOperaciones.addColumn(Operaciones::getFuentePago)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Fuente pago");
        gridOperaciones.addColumn(Operaciones::getMonto)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Monto/Importe");
        gridOperaciones.addColumn(Operaciones::getMontoIndividual)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Monto individual");
        gridOperaciones.addColumn(Operaciones::getNombreAsociacion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Nombre asociacion");
        gridOperaciones.addColumn(Operaciones::getNumeroContrato)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Numero contrato");
        gridOperaciones.addColumn(Operaciones::getObjetoFideicomisoLineaCredito)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Objeto fideicomiso/Linea de credito");
        gridOperaciones.addColumn(Operaciones::getFechaContrato)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Fecha contrato");
        gridOperaciones.addColumn(Operaciones::getImporteBonosBs)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Importe bonos");
        gridOperaciones.addColumn(Operaciones::getCantidadBonos)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Cantidad bonos");
        gridOperaciones.addColumn(Operaciones::getFechaCompraBonos)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Fecha compra bonos");
        gridOperaciones.addColumn(Operaciones::getObservaciones)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Observaciones");
        gridOperaciones.addColumn(Operaciones::getOrigenRecursos)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Origen recursos");
        gridOperaciones.addColumn(Operaciones::getDestinoRecursos)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Destino recursos");
        gridOperaciones.addColumn(Operaciones::getCuentaAportePropio)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Cuenta aporte propio");
        gridOperaciones.addColumn(Operaciones::getFechaFinalizacionContrato)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Finalizacion contrato");
        gridOperaciones.addColumn(Operaciones::getFideicomisoAdministracion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Fideicomiso en Administracion");
        gridOperaciones.addColumn(Operaciones::getFechaDeposito)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Fecha deposito");
        gridOperaciones.addColumn(Operaciones::getDepartamentoDeposito)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Departamento deposito");
        gridOperaciones.addColumn(Operaciones::getDireccionAgenciaDeposito)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Direccion Agencia deposito");
        gridOperaciones.addColumn(Operaciones::getNumeroCuentaDestino)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Numero cuenta destino");
        gridOperaciones.addColumn(Operaciones::getLugarEnvio)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Lugar envio");
        gridOperaciones.addColumn(Operaciones::getFechaCobro)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Fecha cobro");
        gridOperaciones.addColumn(Operaciones::getFechaNegociacion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Fecha negociacion");
        gridOperaciones.addColumn(Operaciones::getDpfNegociado)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("DPF negociado");
        gridOperaciones.addColumn(Operaciones::getFechaEnvio)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Fecha envio");
        gridOperaciones.addColumn(Operaciones::getLugarCobro)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Lugar cobro");
        gridOperaciones.addColumn(Operaciones::getObjetoBoleta)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Objeto boleta");
        gridOperaciones.addColumn(Operaciones::getBoletaEjecucion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Boleta ejecucion");
        gridOperaciones.addColumn(Operaciones::getTipoGarantia)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Tipo garantia");
        gridOperaciones.addColumn(Operaciones::getPaisDestino)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Pais destino");
        gridOperaciones.addColumn(Operaciones::getFechaCheque)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Fecha cheque");
        gridOperaciones.addColumn(Operaciones::getNumeroRegistrosPrimeraPersona)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Registros primera persona");
        gridOperaciones.addColumn(Operaciones::getNumeroRegistrosSegundaPersona)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Registros segunda persona");

        gridOperaciones.addColumn(Operaciones::getPersonas)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Lista Personas");

    }

    private void createGridTransacciones(){
        gridTransacciones.setDataProvider(transaccionesListDataProvider);

        gridTransacciones.addColumn(Transacciones::getDocumentoPersonaSolicitud)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Documento Persona");
        gridTransacciones.addColumn(Transacciones::getNumeroOperacion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Numero operacion");
        gridTransacciones.addColumn(Transacciones::getTipoOperacion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Tipo operacion");
        gridTransacciones.addColumn(Transacciones::getNumeroTransaccion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Numero transaccion");
        gridTransacciones.addColumn(Transacciones::getFechaTransaccion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Fecha transaccion");
        gridTransacciones.addColumn(Transacciones::getTipoTransaccion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Tipo transaccion");
        gridTransacciones.addColumn(Transacciones::getTipoFormaPagoTransaccion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Forma pago transaccion");
        gridTransacciones.addColumn(Transacciones::getMontoTransaccion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Monto transaccion");
        gridTransacciones.addColumn(Transacciones::getSaldo)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Saldo/Importe");
        gridTransacciones.addColumn(Transacciones::getGlosaEntidad)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Glosa Entidad");
        gridTransacciones.addColumn(Transacciones::getAgencia)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Agencia");
        gridTransacciones.addColumn(Transacciones::getNombrePersonaTransaccion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Persona transaccion");
        gridTransacciones.addColumn(Transacciones::getNumeroCuentaTransferencia)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Cuenta transferencia");
        gridTransacciones.addColumn(Transacciones::getNombreTitularCuentaTransferencia)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Titular cuenta transferencia");
        gridTransacciones.addColumn(Transacciones::getBancoTransferencia)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Banco transferencia");
        gridTransacciones.addColumn(Transacciones::getPaisTransferencia)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Pais transferencia");
        gridTransacciones.addColumn(Transacciones::getCiudadTransferencia)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Ciudad transferencia");
        gridTransacciones.addColumn(Transacciones::getNumeroCheque)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Numero cheque");
        gridTransacciones.addColumn(Transacciones::getFechaProgramada)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Fecha programada");
        gridTransacciones.addColumn(Transacciones::getImporteProgramado)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Importe programado");
        gridTransacciones.addColumn(Transacciones::getNombreGiroCheque)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Nombre giro cheque");
        gridTransacciones.addColumn(Transacciones::getNombreCobradorCheque)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Nombre cobrador cheque");
        gridTransacciones.addColumn(Transacciones::getNumeroDpfRenovacion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("DPF renovacion");

    }

    private InputStream getInputStreamOperaciones() {
        try {
            StringWriter stringWriter = new StringWriter();

            CSVWriter csvWriter = new CSVWriter(stringWriter);
            csvWriter.setApplyQuotesToAll(false);
            if(checkHeaders.getValue()==true) {
                csvWriter.writeNext(
                        "Documento persona",
                        "Nro. Operacion",
                        "Tipo Operacion",
                        "Tipo manejo cuenta",
                        "Fecha apertura",
                        "Moneda",
                        "Saldo cuenta",
                        "Estado cuenta",
                        "Tipo prestamo/bono",
                        "Forma pago",
                        "Nombres garantes",
                        "Descripcion garantia",
                        "Objeto prestamo",
                        "Fecha solicitud prestamo",
                        "Fecha aprobacion",
                        "Fecha cancelacion/cierre",
                        "Fuente pago",
                        "Fecha desembolso",
                        "Plazo/Vigencia(Dias)",
                        "Monto limite compra",
                        "Fecha constitucion",
                        "Fecha pago efectivo",
                        "Fecha vencimiento",
                        "Importe deposito",
                        "Monto individual",
                        "Nombre asociacion",
                        "Numero contrato",
                        "Objeto fideicomiso/Linea credito",
                        "Fecha contrato",
                        "Importe bonos",
                        "Cantidad bonos",
                        "Fecha compra bono",
                        "Observaciones",
                        "Origen recursos",
                        "Destino recursos",
                        "Cuenta aporte propio",
                        "Fecha finalizacion contrato",
                        "Fideicomiso en administracion",
                        "Fecha deposito",
                        "Departamento deposito",
                        "Direccion agencia deposito",
                        "Numero cuenta destino/Cuenta cheque",
                        "Lugar envio",
                        "Fecha cobro",
                        "Fecha negociacion",
                        "DPF negociado",
                        "Fecha envio",
                        "Lugar cobro",
                        "Objeto boleta",
                        "Boleta ejecucion",
                        "Tipo garantia",
                        "Pais destino",
                        "Fecha cheque",
                        "Nro registros 1ra Persona",
                        "Nro registros 2da Persona",
                        "Tipo persona",
                        "Tipo documento",
                        "Nro documento",
                        "Complemento",
                        "Tipo extension",
                        "Primer nombre",
                        "Segundo nombre",
                        "Primer apellido",
                        "Segundo Apellido",
                        "Estado civil",
                        "Razon social",
                        "Tipo persona",
                        "Tipo documento",
                        "Nro documento",
                        "Complemento",
                        "Tipo extension",
                        "Primer nombre",
                        "Segundo nombre",
                        "Primer apellido",
                        "Segundo Apellido",
                        "Estado civil",
                        "Razon social",
                        "Tipo persona",
                        "Tipo documento",
                        "Nro documento",
                        "Complemento",
                        "Tipo extension",
                        "Primer nombre",
                        "Segundo nombre",
                        "Primer apellido",
                        "Segundo Apellido",
                        "Estado civil",
                        "Razon social",
                        "Tipo persona",
                        "Tipo documento",
                        "Nro documento",
                        "Complemento",
                        "Tipo extension",
                        "Primer nombre",
                        "Segundo nombre",
                        "Primer apellido",
                        "Segundo Apellido",
                        "Estado civil",
                        "Razon social");
            }
            operacionesList.forEach(c -> {
                csvWriter.writeNext(
                        c.getDocumentoPersona().trim(),
                        c.getNumeroOperacion(),
                        c.getTipoOperacion(),
                        c.getTipoManejoCuenta(),
                        c.getFechaAperturaCuenta()!=null?utiles.dateFormatted(c.getFechaAperturaCuenta(),"dd/MM/yyyy"):"",
                        c.getMoneda(),
                        c.getSaldoCuenta()!=null?c.getSaldoCuenta().toString():"",
                        c.getEstadoCuenta(),
                        c.getTipoPrestamoBono(),
                        c.getFormaPago(),
                        c.getNombreGarante(),
                        c.getDescripcionGarantia(),
                        c.getObjetivoPrestamo(),
                        c.getFechaSolicitudPrestamo()!=null?utiles.dateFormatted(c.getFechaSolicitudPrestamo(),"dd/MM/yyyy"):"",
                        c.getFechaAprobacionPrestamo()!=null?utiles.dateFormatted(c.getFechaAprobacionPrestamo(),"dd/MM/yyyy"):"",
                        c.getFechaCancelacion()!=null?utiles.dateFormatted(c.getFechaCancelacion(),"dd/MM/yyyy"):"",
                        c.getFuentePago(),
                        c.getFechaDesembolso()!=null?utiles.dateFormatted(c.getFechaDesembolso(),"dd/MM/yyyy"):"",
                        c.getPlazoVigencia(),
                        c.getMontoLimiteCompra()!=null?c.getMontoLimiteCompra().toString():"",
                        c.getFechaConstitucion()!=null?utiles.dateFormatted(c.getFechaConstitucion(),"dd/MM/yyyy"):"",
                        c.getFechaPagoEfectivo()!=null?utiles.dateFormatted(c.getFechaPagoEfectivo(),"dd/MM/yyyy"):"",
                        c.getFechaVencimiento()!=null?utiles.dateFormatted(c.getFechaVencimiento(),"dd/MM/yyyy"):"",
                        c.getMonto()!=null?c.getMonto().toString():"",
                        c.getMontoIndividual()!=null?c.getMontoIndividual().toString():"",
                        c.getNombreAsociacion(),
                        c.getNumeroContrato(),
                        c.getObjetoFideicomisoLineaCredito(),
                        c.getFechaContrato()!=null?utiles.dateFormatted(c.getFechaContrato(),"dd/MM/yyyy"):"",
                        c.getImporteBonosBs()!=null?c.getImporteBonosBs().toString():"",
                        c.getCantidadBonos()!=null?c.getCantidadBonos().toString():"",
                        c.getFechaCompraBonos()!=null?utiles.dateFormatted(c.getFechaCompraBonos(),"dd/MM/yyyy"):"",
                        c.getObservaciones(),
                        c.getOrigenRecursos(),
                        c.getDestinoRecursos(),
                        c.getCuentaAportePropio(),
                        c.getFechaFinalizacionContrato()!=null?utiles.dateFormatted(c.getFechaFinalizacionContrato(),"dd/MM/yyyy"):"",
                        c.getFideicomisoAdministracion(),
                        c.getFechaDeposito()!=null?utiles.dateFormatted(c.getFechaDeposito(),"dd/MM/yyyy"):"",
                        c.getDepartamentoDeposito(),
                        c.getDireccionAgenciaDeposito(),
                        c.getNumeroCuentaDestino(),
                        c.getLugarEnvio(),
                        c.getFechaCobro()!=null?utiles.dateFormatted(c.getFechaCobro(),"dd/MM/yyyy"):"",
                        c.getFechaNegociacion()!=null?utiles.dateFormatted(c.getFechaNegociacion(),"dd/MM/yyyy"):"",
                        c.getDpfNegociado(),
                        c.getFechaEnvio()!=null?utiles.dateFormatted(c.getFechaEnvio(),"dd/MM/yyyy"):"",
                        c.getLugarCobro(),
                        c.getObjetoBoleta(),
                        c.getBoletaEjecucion(),
                        c.getTipoGarantia(),
                        c.getPaisDestino(),
                        c.getFechaCheque()!=null?utiles.dateFormatted(c.getFechaCheque(),"dd/MM/yyyy"):"",
                        c.getNumeroRegistrosPrimeraPersona()!=null?c.getNumeroRegistrosPrimeraPersona().toString():"",
                        c.getNumeroRegistrosSegundaPersona()!=null?c.getNumeroRegistrosSegundaPersona().toString():"",
                        c.getPersonas().get(0)!=null?c.getPersonas().get(0).getTipoPersona():"",
                        c.getPersonas().get(0)!=null?c.getPersonas().get(0).getTipoDocumento():"",
                        c.getPersonas().get(0)!=null?c.getPersonas().get(0).getNumeroDocumento():"",
                        c.getPersonas().get(0)!=null?c.getPersonas().get(0).getComplemento():"",
                        c.getPersonas().get(0)!=null?c.getPersonas().get(0).getTipoExtension():"",
                        c.getPersonas().get(0)!=null?c.getPersonas().get(0).getPrimerNombre():"",
                        c.getPersonas().get(0)!=null?c.getPersonas().get(0).getSegundoNombre():"",
                        c.getPersonas().get(0)!=null?c.getPersonas().get(0).getPrimerApellido():"",
                        c.getPersonas().get(0)!=null?c.getPersonas().get(0).getSegundoApellido():"",
                        c.getPersonas().get(0)!=null?c.getPersonas().get(0).getEstadoCivil():"",
                        c.getPersonas().get(0)!=null?c.getPersonas().get(0).getRazonSocial():"",
                        c.getPersonas().size()>=2?c.getPersonas().get(1)!=null?c.getPersonas().get(1).getTipoPersona():"":"",
                        c.getPersonas().size()>=2?c.getPersonas().get(1)!=null?c.getPersonas().get(1).getTipoDocumento():"":"",
                        c.getPersonas().size()>=2?c.getPersonas().get(1)!=null?c.getPersonas().get(1).getNumeroDocumento():"":"",
                        c.getPersonas().size()>=2?c.getPersonas().get(1)!=null?c.getPersonas().get(1).getComplemento():"":"",
                        c.getPersonas().size()>=2?c.getPersonas().get(1)!=null?c.getPersonas().get(1).getTipoExtension():"":"",
                        c.getPersonas().size()>=2?c.getPersonas().get(1)!=null?c.getPersonas().get(1).getPrimerNombre():"":"",
                        c.getPersonas().size()>=2?c.getPersonas().get(1)!=null?c.getPersonas().get(1).getSegundoNombre():"":"",
                        c.getPersonas().size()>=2?c.getPersonas().get(1)!=null?c.getPersonas().get(1).getPrimerApellido():"":"",
                        c.getPersonas().size()>=2?c.getPersonas().get(1)!=null?c.getPersonas().get(1).getSegundoApellido():"":"",
                        c.getPersonas().size()>=2?c.getPersonas().get(1)!=null?c.getPersonas().get(1).getEstadoCivil():"":"",
                        c.getPersonas().size()>=2?c.getPersonas().get(1)!=null?c.getPersonas().get(1).getRazonSocial():"":"",
                        c.getPersonas().size()>=3?c.getPersonas().get(2)!=null?c.getPersonas().get(2).getTipoPersona():"":"",
                        c.getPersonas().size()>=3?c.getPersonas().get(2)!=null?c.getPersonas().get(2).getTipoDocumento():"":"",
                        c.getPersonas().size()>=3?c.getPersonas().get(2)!=null?c.getPersonas().get(2).getNumeroDocumento():"":"",
                        c.getPersonas().size()>=3?c.getPersonas().get(2)!=null?c.getPersonas().get(2).getComplemento():"":"",
                        c.getPersonas().size()>=3?c.getPersonas().get(2)!=null?c.getPersonas().get(2).getTipoExtension():"":"",
                        c.getPersonas().size()>=3?c.getPersonas().get(2)!=null?c.getPersonas().get(2).getPrimerNombre():"":"",
                        c.getPersonas().size()>=3?c.getPersonas().get(2)!=null?c.getPersonas().get(2).getSegundoNombre():"":"",
                        c.getPersonas().size()>=3?c.getPersonas().get(2)!=null?c.getPersonas().get(2).getPrimerApellido():"":"",
                        c.getPersonas().size()>=3?c.getPersonas().get(2)!=null?c.getPersonas().get(2).getSegundoApellido():"":"",
                        c.getPersonas().size()>=3?c.getPersonas().get(2)!=null?c.getPersonas().get(2).getEstadoCivil():"":"",
                        c.getPersonas().size()>=3?c.getPersonas().get(2)!=null?c.getPersonas().get(2).getRazonSocial():"":"",
                        c.getPersonas().size()>=4?c.getPersonas().get(3)!=null?c.getPersonas().get(3).getTipoPersona():"":"",
                        c.getPersonas().size()>=4?c.getPersonas().get(3)!=null?c.getPersonas().get(3).getTipoDocumento():"":"",
                        c.getPersonas().size()>=4?c.getPersonas().get(3)!=null?c.getPersonas().get(3).getNumeroDocumento():"":"",
                        c.getPersonas().size()>=4?c.getPersonas().get(3)!=null?c.getPersonas().get(3).getComplemento():"":"",
                        c.getPersonas().size()>=4?c.getPersonas().get(3)!=null?c.getPersonas().get(3).getTipoExtension():"":"",
                        c.getPersonas().size()>=4?c.getPersonas().get(3)!=null?c.getPersonas().get(3).getPrimerNombre():"":"",
                        c.getPersonas().size()>=4?c.getPersonas().get(3)!=null?c.getPersonas().get(3).getSegundoNombre():"":"",
                        c.getPersonas().size()>=4?c.getPersonas().get(3)!=null?c.getPersonas().get(3).getPrimerApellido():"":"",
                        c.getPersonas().size()>=4?c.getPersonas().get(3)!=null?c.getPersonas().get(3).getSegundoApellido():"":"",
                        c.getPersonas().size()>=4?c.getPersonas().get(3)!=null?c.getPersonas().get(3).getEstadoCivil():"":"",
                        c.getPersonas().size()>=4?c.getPersonas().get(3)!=null?c.getPersonas().get(3).getRazonSocial():"":""

                );
            });

            return IOUtils.toInputStream(stringWriter.toString(), "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private InputStream getInputStreamTransacciones() {
        try {
            StringWriter stringWriter = new StringWriter();

            CSVWriter csvWriter = new CSVWriter(stringWriter);
            csvWriter.setApplyQuotesToAll(false);
            if(checkHeaders.getValue()==true) {
                csvWriter.writeNext(
                        "Documento Persona",
                        "Numero Operacion",
                        "Tipo Operacion",
                        "Nro Transaccion",
                        "Fecha transaccion",
                        "Tipo transaccion",
                        "Forma pago transaccion",
                        "Monto transaccion",
                        "Saldo/Importe",
                        "Gloasa entidad",
                        "Agencia",
                        "Nombre Agencia",
                        "Nombre persona transaccion",
                        "Nro. cuenta transferencia",
                        "Nombre titular cuenta transferencia",
                        "Banco transferencia",
                        "Pais transferencia",
                        "Ciudad transferencia",
                        "Nro cheque",
                        "Fecha programada",
                        "Importe programado",
                        "Nombre giro cheque",
                        "Nombre cobrador cheque",
                        "Nro DPF renovacion");
            }
            transaccionesList.forEach(c -> {

                csvWriter.writeNext(
                        c.getDocumentoPersonaSolicitud().trim(),
                        c.getNumeroOperacion().trim(),
                        c.getTipoOperacion(),
                        c.getNumeroTransaccion(),
                        c.getFechaTransaccion()!=null?utiles.dateFormatted(c.getFechaTransaccion(),"dd/MM/yyyy"):"",
                        c.getTipoTransaccion(),
                        c.getTipoFormaPagoTransaccion(),
                        c.getMontoTransaccion()!=null?c.getMontoTransaccion().toString():"",
                        c.getSaldo()!=null?c.getSaldo().toString():"",
                        c.getGlosaEntidad(),
                        c.getAgencia(),
                        c.getNombrePersonaTransaccion(),
                        c.getNumeroCuentaTransferencia(),
                        c.getNombreTitularCuentaTransferencia(),
                        c.getBancoTransferencia(),
                        c.getPaisTransferencia(),
                        c.getCiudadTransferencia(),
                        c.getNumeroCheque(),
                        c.getFechaProgramada()!=null?utiles.dateFormatted(c.getFechaProgramada(),"dd/MM/yyyy"):"",
                        c.getImporteProgramado()!=null?c.getImporteProgramado().toString():"",
                        c.getNombreGiroCheque(),
                        c.getNombreCobradorCheque(),
                        c.getNumeroDpfRenovacion());

            });

            return IOUtils.toInputStream(stringWriter.toString(), "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

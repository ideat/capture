package com.mindware.capture.views.busqueda;

import com.mindware.capture.dto.Operaciones;
import com.mindware.capture.dto.Transacciones;
import com.mindware.capture.model.informix.Gbage;
import com.mindware.capture.repository.informix.GbageRepository;
import com.mindware.capture.service.CamcaService;
import com.mindware.capture.service.GbageService;
import com.mindware.capture.service.OperacionService;
import com.mindware.capture.service.TransaccionesService;
import com.mindware.capture.views.main.MainView;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.server.VaadinSession;
import com.wontlost.sweetalert2.Config;
import com.wontlost.sweetalert2.SweetAlert2Vaadin;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route(value = "search", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Busqueda")
@CssImport("./views/busqueda/busqueda-view.css")
public class BusquedaView extends Div {

    @Autowired
    private GbageService service;

    @Autowired
    private CamcaService cameraService;

    @Autowired
    private OperacionService operacionService;

    @Autowired
    private TransaccionesService transaccionesService;

    @Autowired
    private GbageRepository gbageRepository;

    private ListDataProvider<Gbage> gbageListDataProvider;
    private Button btnProcesar;
    private TextArea search;

    private List<Operaciones> operacionesList = new ArrayList<>();
    private List<Transacciones> transaccionesList = new ArrayList<>();
    private List<Gbage> gbageList = new ArrayList<>();

    private List<Gbage> seleccionados= new ArrayList<>();

    public BusquedaView() {
        addClassName("busqueda-view");

        add(searchBox());


    }

    private VerticalLayout  searchBox(){
        search = new TextArea();
        search.setWidth("90%");
        Label label = new Label();
        label.setText("Ingrese los números de carnet separados por comas:");

        Button btnSearch = new Button("Buscar");
        btnSearch.setIcon(VaadinIcon.SEARCH_PLUS.create());

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();
        verticalLayout.setSpacing(true);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("90%");
        horizontalLayout.add(search,btnSearch);
        verticalLayout.add(label,horizontalLayout);

        btnSearch.addClickListener(buttonClickEvent ->{
            if(!search.isEmpty()){
                String[] ciArray = search.getValue().trim().split("\\s*,\\s*");
                List<String> ciList = Arrays.asList(ciArray);
                gbageList = gbageRepository.findByListGbagendid(ciList);
                if(!gbageList.isEmpty()){
                    verticalLayout.add(layoutGbage());
                }else{
                    Config config = new Config();
                    config.setTitle("Busqueda");
                    config.setText("No se tienen registros con los carnets ingresados");
                    config.setIcon("info");

                    new SweetAlert2Vaadin(config).open();
                }

            }else{
                Config config = new Config();
                config.setTitle("Error");
                config.setText("Ingrese carnets separados por comas");
                config.setIcon("error");
                config.setFooter("<a href>Ingrese 1 o mas carnets</a>");
                new SweetAlert2Vaadin(config).open();
                search.focus();
            }

        });
        return verticalLayout;
    }

    private Grid createGridGbage(){
        gbageListDataProvider = new ListDataProvider<>(gbageList);
        Grid<Gbage> gridGbage = new Grid<>();
        gridGbage.setSelectionMode(Grid.SelectionMode.MULTI);
        gridGbage.setWidthFull();
        gridGbage.setDataProvider(gbageListDataProvider);

        gridGbage.addColumn(Gbage::getGbagecage)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Codigo cliente");
        gridGbage.addColumn(Gbage::getGbagenomb)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Nombre completo");
        gridGbage.addColumn(Gbage::getGbagendid)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("CI");
        gridGbage.addColumn(Gbage::getGbagenruc)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("NIT");

        gridGbage.asMultiSelect().addValueChangeListener(event -> {
           seleccionados = List.copyOf(event.getValue());
        });

        return gridGbage;

    }

    private VerticalLayout layoutGbage(){
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setWidthFull();
        btnProcesar = new Button("Procesar");
        ProgressBar progressBar = new ProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        layout.add(createGridGbage(), btnProcesar,progressBar);

        btnProcesar.addClickListener(click -> {

            if(!seleccionados.isEmpty()) {

                Thread t = new Thread(() -> {
                    progressBar.getUI().ifPresent(ui -> ui.access(() -> {
                        progressBar.setVisible(true);
                        ui.push();
                    }));

                    String forSearch = getSeleccionados();

                    operacionesList = operacionService.generarOperaciones(forSearch);
                    transaccionesList = transaccionesService.generarTransacciones(forSearch);
                    progressBar.getUI().ifPresent(ui -> ui.access(() -> {
                        progressBar.setVisible(false);
                        ui.push();
                        VaadinSession.getCurrent().setAttribute("operaciones",operacionesList);
                        VaadinSession.getCurrent().setAttribute("transacciones",transaccionesList);
                        UI.getCurrent().navigate("report");
                    }));

                });
                t.start();

            }else{

                Config config = new Config();
                config.setTitle("Error");
                config.setText("Seleccione una o mas registros para ser procesados");
                config.setIcon("error");
                config.setFooter("<a href>Seleccione registros de la lista</a>");
                new SweetAlert2Vaadin(config).open();
            }
        });

        return layout;
    }

    private String getSeleccionados(){
        List<String> sel = new ArrayList<>();
        for(Gbage g:seleccionados){
            sel.add(g.getGbagendid());
        }
        return StringUtils.join(sel,",");
    }

}

package com.mindware.capture.views.busqueda;

import com.mindware.capture.dto.Operaciones;
import com.mindware.capture.model.informix.Gbage;
import com.mindware.capture.service.CamcaService;
import com.mindware.capture.service.GbageService;
import com.mindware.capture.service.OperacionService;
import com.mindware.capture.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;
import org.springframework.beans.factory.annotation.Autowired;

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

    public BusquedaView() {
        addClassName("busqueda-view");
        Button btn = new Button("Ejecutar");
        add(btn);

        btn.addClickListener(buttonClickEvent -> {
            List<Gbage> gbageList = service.findByGbagendid("  1796832TJ  , 1629748TJ");

            List<Operaciones> operacionesList = operacionService.generarOperaciones("  9351746CB,  7757208SC ,   3072168OR,   51594471HCB");

        });
    }

}

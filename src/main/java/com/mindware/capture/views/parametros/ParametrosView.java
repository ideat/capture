package com.mindware.capture.views.parametros;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.mindware.capture.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "parameter", layout = MainView.class)
@PageTitle("Parametros")
@CssImport("./views/parametros/parametros-view.css")
public class ParametrosView extends Div {

    public ParametrosView() {
        addClassName("parametros-view");
        add(new Text("Content placeholder"));
    }

}

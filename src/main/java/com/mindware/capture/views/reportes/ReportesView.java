package com.mindware.capture.views.reportes;

import com.mindware.capture.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "report", layout = MainView.class)
@PageTitle("Reportes")
@CssImport("./views/reportes/reportes-view.css")
public class ReportesView extends Div {

    public ReportesView() {
        addClassName("reportes-view");
        add(new Text("Content placeholder"));
    }

}

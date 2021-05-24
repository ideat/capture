package com.mindware.capture.views.parametros;

import com.mindware.capture.model.postgres.Parameter;
import com.mindware.capture.repository.postgres.ParameterRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.theme.lumo.Lumo;
import com.wontlost.sweetalert2.Config;
import com.wontlost.sweetalert2.SweetAlert2Vaadin;
import org.springframework.beans.factory.annotation.Autowired;

@CssImport("./views/parametros/parametros-view.css")
public class ParameterEditView extends Dialog {

    private Header header;
    private Button min;
    private Button max;
    private VerticalLayout content;
    private Footer footer;


    public ParameterEditView(Parameter parameter, ParameterRepository parameterRepository){
        setModal(true);
        setResizable(true);
        setDraggable(true);

        // Dialog theming
        getElement().getThemeList().add("my-dialog");
        setWidth("600px");


        H2 title = new H2("Parametro - "+parameter.getCategoria());
        title.addClassName("dialog-title");

        Button close = new Button(VaadinIcon.CLOSE_SMALL.create());
        close.addClickListener(event -> close());

        //Header
        header = new Header(title,  close);
        header.getElement().getThemeList().add(Lumo.DARK);

        add(header);

        //Content

        TextField categoria = new TextField("Categoria");
        categoria.setValue(parameter.getCategoria());
        categoria.setReadOnly(true);

        TextField codigoUif = new TextField("Codigo UIF");
        codigoUif.setValue(parameter.getCodigo());
        codigoUif.setReadOnly(true);

        TextArea codigoEntidad = new TextArea("Codigo entidad");
        codigoEntidad.setValue(parameter.getCodigoExterno());
        codigoEntidad.setWidth("100%");

        content = new VerticalLayout(categoria,codigoUif,codigoEntidad);
        add(content);

        //Footer
        Button guardar = new Button("Guardar");
//        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        guardar.addClickListener(click -> {
            parameter.setCodigoExterno(codigoEntidad.getValue());
            parameterRepository.save(parameter) ;
            Config config = new Config();
            config.setTitle("Parametro");
            config.setText("Parametro guardado correctamente");
            config.setIcon("success");
            new SweetAlert2Vaadin(config).open();

            close();
        });

        Button cancelar = new Button("Cerrar");
//        cancelar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelar.addClickListener(click -> close());



        footer = new Footer(guardar,cancelar);

        for (Button button : new Button[] { cancelar, guardar, close}) {
            button.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_TERTIARY);
        }
        add(footer);
    }
}

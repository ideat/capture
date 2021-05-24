package com.mindware.capture.views.parametros;

import com.mindware.capture.model.postgres.Parameter;
import com.mindware.capture.repository.postgres.ParameterRepository;
import com.mindware.capture.service.ParameterService;
import com.mindware.capture.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@SpringComponent
@UIScope
@Route(value = "parameter", layout = MainView.class)
@PageTitle("Parametros")
//@CssImport("./views/parametros/parametros-view.css")
public class ParameterView extends Div implements RouterLayout {


    private final ParameterRepository parameterRepository;
    private ListDataProvider<Parameter> parameterListDataProvider;
    private Grid<Parameter> gridParameter;
    private List<Parameter> parameterList;
    private ComboBox<String> cmbCategoriaFilter;
    private String[] categoria = {"CODIGO_CIUDAD","DOC_EXTENSION","ESTADO_CIVIL", "ESTADO_CUENTA",
            "ESTADO_FIRMA", "FIDEICOMISO", "FORMA_PAGO", "MONEDA", "PAIS", "TIPO_DOCUMENTO",
            "TIPO_MANEJO_CUENTA", "TIPO_MODALIDAD", "TIPO_PERSONA", "TIPO_OPERACION", "TIPO_TRANSACCION"
    };

    @Autowired
    public ParameterView(ParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
//        parameterList = parameterService.findAll();
        parameterList = parameterRepository.findAll();
        parameterListDataProvider = new ListDataProvider<>(parameterList);
        createGridParameter();
        gridParameter.setHeight("750px");
        add(gridParameter);

    }

    private void createGridParameter(){
        gridParameter = new Grid<>();
        gridParameter.setDataProvider(parameterListDataProvider);


        gridParameter.addColumn(Parameter::getCategoria)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setKey("categoria")
                .setHeader("Categoria");
        gridParameter.addColumn(Parameter::getCodigo)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Codigo UIF");
        gridParameter.addColumn(Parameter::getDescripcion)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Descripcion");
        gridParameter.addColumn(Parameter::getOrden)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setSortable(true)
                .setResizable(true)
                .setHeader("Orden");
//        gridParameter.addColumn(Parameter::getCodigoExterno)
//                .setAutoWidth(true)
//                .setFlexGrow(1)
//                .setSortable(true)
//                .setResizable(true)
//                .setHeader("Codigo entidad");
        gridParameter.addColumn(new ComponentRenderer<>(this::createModalDialog));

        HeaderRow hr = gridParameter.appendHeaderRow();
        cmbCategoriaFilter = new ComboBox<>();
        cmbCategoriaFilter.setItems(categoria);
        cmbCategoriaFilter.setWidth("100%");
        cmbCategoriaFilter.addValueChangeListener(e ->{
            applicarFiltro(parameterListDataProvider);
        });
        hr.getCell(gridParameter.getColumnByKey("categoria")).setComponent(cmbCategoriaFilter);

    }

    private void applicarFiltro(ListDataProvider<Parameter> parameterListDataProvider) {
        parameterListDataProvider.clearFilters();
        if(cmbCategoriaFilter.getValue()!=null){
            parameterListDataProvider.addFilter(parameter -> Objects.equals(cmbCategoriaFilter.getValue(),parameter.getCategoria()));
        }
    }

    private Component createModalDialog(Parameter parameter){
        Button button = new Button("Editar");
        button.setIcon(VaadinIcon.ARCHIVE.create());
        button.addClickListener(click -> {
            ParameterEditView parameterEditView = new ParameterEditView(parameter,parameterRepository);
            parameterEditView.open();

        });

        return button;
    }

}

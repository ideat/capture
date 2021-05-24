package com.mindware.capture.util;

import com.mindware.capture.model.postgres.Parameter;
import com.mindware.capture.repository.postgres.ParameterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class Utiles {

    @Autowired
    private  ParameterRepository parameterRepository;

    public List<Parameter> getParametersByCategory(String category) {
        return parameterRepository.findByCategoria(category);
    }

    public Parameter getCodigoUIF(String categoria, String tipoCuenta, String codigoInterno){
        List<Parameter> parameterList = parameterRepository.findByCategoria(categoria);
        List<Parameter> parameters = parameterList.stream()
                .filter(param -> param.getCodigoExterno().contains(tipoCuenta))
                .collect(Collectors.toList());

        //Obtener todos los parametros con los codigo y categoria
        List<Parameter> listByCategoria = new ArrayList<>();
        for(Parameter p:parameters){
            String[] arr = p.getCodigoExterno().split(";");

            for(String s: arr){
                if (s.contains(tipoCuenta)){
                    Parameter parameter = p;
                    parameter.setCodigoExterno(s);
                    listByCategoria.add(parameter);
                }
            }
        }

        //Seleccionando el parametro por codigoInterno

        for(Parameter p: listByCategoria){
            String s = p.getCodigoExterno().split("=")[1];
            String[] c = s.split(",");
            if(Arrays.stream(c).anyMatch(x -> x.equals(codigoInterno))){
                return p; //encontro
            }
        }

        return new Parameter();
    }

    public String dateFormatted(LocalDate date, String format){
        String[] d = date.toString().split("-");
        LocalDate da = LocalDate.of(Integer.parseInt(d[0]),Integer.parseInt(d[1]),Integer.parseInt(d[2]));
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return dtf.format(da);
    }
}

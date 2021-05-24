package com.mindware.capture.service;

import com.mindware.capture.model.postgres.Parameter;
import com.mindware.capture.repository.postgres.ParameterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

//@Service
public class ParameterService {

//    @Autowired
    private  ParameterRepository repository;

//    public ParameterService(ParameterRepository parameterRepository){
//        this.repository = parameterRepository;
//    }

    public List<Parameter> findAll(){
        return repository.findAll();
    }
}

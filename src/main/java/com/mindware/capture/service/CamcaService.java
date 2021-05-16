package com.mindware.capture.service;

import com.mindware.capture.model.informix.Camca;
import com.mindware.capture.repository.informix.CamcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CamcaService {

    @Autowired
    private CamcaRepository repository;

    public List<Camca> findbyCamcacage(Integer camcacage){
        return repository.findByCamcacage(camcacage);
    }
}

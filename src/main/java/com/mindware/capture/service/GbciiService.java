package com.mindware.capture.service;

import com.mindware.capture.model.informix.Gbcii;
import com.mindware.capture.repository.informix.GbciiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GbciiService {

    @Autowired
    GbciiRepository repository;

    public String getCiiudesc(Integer ciiu){
        Optional<Gbcii> gbcii = repository.findByCiiu(ciiu);
        return gbcii.isPresent()?gbcii.get().getGbciidesc():"";
    }
}

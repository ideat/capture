package com.mindware.capture.service;

import com.mindware.capture.model.informix.Gbage;
import com.mindware.capture.repository.informix.GbageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GbageService {
    @Autowired
    private GbageRepository repository;

    public List<Gbage> findByGbagendid(String gbagendid){
        String[] param = gbagendid.trim().split("\\s*,\\s*");

        List<String> params = Arrays.asList(param);

        return repository.findByListGbagendid(params);
    }


}

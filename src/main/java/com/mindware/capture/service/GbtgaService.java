package com.mindware.capture.service;

import com.mindware.capture.model.informix.Gbtga;
import com.mindware.capture.repository.informix.GbtgaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GbtgaService {

    @Autowired
    GbtgaRepository gbtgaRepository;

    public String getGbtgadesc(Integer prgarnpre){
        List<Gbtga> gbtgaList = gbtgaRepository.findByPrgarnpre(prgarnpre);
        String guarantee = "";
        int i =1;
        for(Gbtga gbtga:gbtgaList){
            if(gbtgaList.size() == i)
                if (guarantee.equals(""))
                    guarantee = gbtga.getGbtgadesc() +" " + guarantee ;
                else guarantee = gbtga.getGbtgadesc() +" y " + guarantee;
            else
                guarantee = gbtga.getGbtgadesc() + ", " + guarantee;
        }
        return guarantee;
    }
}

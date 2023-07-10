package com.example.currency.service;

import com.example.currency.controller.CurrencyController;
import com.example.currency.exception.ResourceNotFoundException;
import com.example.currency.modal.Currencies;
import com.example.currency.repository.CurrencyRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.logging.Logger;


@Validated
@Service
@RequiredArgsConstructor

public class CurrencyService {
    private final CurrencyRepo repo;
    public void savecurrency(@Valid Currencies currencies) {
        repo.save(currencies);
    }
    public List<Currencies> getcurrency(){
        return repo.findAll();
    }
    public java.util.Optional<Currencies> getcurrency(String id){
        java.util.Optional<Currencies> currencyOptional = repo.findById(id);
        return currencyOptional;
    }
    public Currencies updatedata(String id, Currencies currencies) throws ResourceNotFoundException {
        try {
            return repo.save(currencies);
        }
        catch(Exception e) {
            throw new ResourceNotFoundException("Currency not found with ID: " + id);
        }

    }
    public void deleteById(String id) throws ResourceNotFoundException  {
     try {
        repo.deleteById(id);
        }
        catch(Exception e) {

            throw new ResourceNotFoundException("Currency not found with ID: " + id);
        }
    }
}


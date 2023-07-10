package com.example.currency.controller;




import com.example.currency.exception.ResourceNotFoundException;
import com.example.currency.modal.Currencies;
import com.example.currency.service.CurrencyService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;

@RequestMapping(value="api/v1/currencies")
@RestController
@Validated
public class CurrencyController {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    @Autowired
    private CurrencyService currencyservice;

    @PostMapping
    public ResponseEntity<String> savecurrency(@Valid @RequestBody Currencies currency){
        currencyservice.savecurrency(currency);
        return ResponseEntity.ok("Currency addded successfully");
    }

    @GetMapping
    @Cacheable(value = "cachedCurrency")
    public List<Currencies> getallcurrency(){
        logger.info("calling from db");
        return this.currencyservice.getcurrency();
    }


    @GetMapping("/{id}")
    @Cacheable(value = "cachedCurrency", key="#id")
    public Optional<Currencies>getcurrencybyid(@PathVariable String id){
        logger.info("calling from db");
        return currencyservice.getcurrency(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updatedata(@PathVariable String id, @Valid @RequestBody Currencies currencies) throws ResourceNotFoundException
    {
        currencies.setId(id);
        currencyservice.updatedata(id, currencies);
        return ResponseEntity.ok("Updated successfully");

    }
    @CacheEvict(value = "cachedCurrency")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCurrency(@PathVariable String id) throws ResourceNotFoundException {

        currencyservice.deleteById(id);
        return ResponseEntity.ok("Deleted successfully");

    }
}

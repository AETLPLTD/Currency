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
import org.springframework.http.HttpStatus;
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
    private CurrencyService currencyService;

    @PostMapping
    @CacheEvict(value = "cachedCurrency", allEntries = true)
    public ResponseEntity<String> savecurrency(@Valid @RequestBody Currencies currency){
        logger.info("Saving currency");
        currencyService.saveCurrency(currency);
        logger.info("Currency added successfully");
        return ResponseEntity.ok("Currency addded successfully");
    }

    @GetMapping
    @Cacheable(value = "cachedCurrency")
    public List<Currencies> getallcurrency(){
        logger.info("Retrieving all currencies");
        List<Currencies> currencies = currencyService.getcurrency();
        logger.info("Retrieved all currencies successfully");
        return currencies;
    }


    @GetMapping("/{id}")
    @Cacheable(value = "cachedCurrency", key="#id")
    public Currencies getCurrencyById(@PathVariable String id) throws ResourceNotFoundException {
        logger.info("Retrieving currency by id: {}", id);
        Currencies currency = currencyService.getcurrencyById(id);
        logger.info("Retrieved currency by id: {} successfully", id);
        return currency;
    }



    @PutMapping("/{id}")
    @CacheEvict(value = "cachedCurrency", allEntries = true)
    public ResponseEntity<String> updateData(@PathVariable String id, @Valid @RequestBody Currencies currencies)
            throws ResourceNotFoundException {
        logger.info("Updating currency with id: {}", id);
        currencies.setId(id);
        currencyService.updatedata(id, currencies);
        logger.info("Updated currency with id: {} successfully", id);
        return ResponseEntity.ok("Updated successfully");
    }
    @CacheEvict(value = "cachedCurrency", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCurrency(@PathVariable String id) throws ResourceNotFoundException {
        logger.info("Deleting currency with id: {}", id);
        currencyService.deleteById(id);
        logger.info("Deleted currency with id: {} successfully", id);
        return ResponseEntity.ok("Deleted successfully");
    }
}

package com.example.currency.service;

import com.example.currency.exception.ResourceNotFoundException;
import com.example.currency.modal.Currencies;
import com.example.currency.repository.CurrencyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CurrencyServiceTest {

    @InjectMocks
    private CurrencyService currencyService;

    @Mock
    private CurrencyRepo currencyRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void saveCurrency() {
        Currencies currency = new Currencies("1", "USD", "US Dollar");

        currencyService.saveCurrency(currency);

        verify(currencyRepo, times(1)).save(currency);
    }

    @Test
    void getcurrency() {
        List<Currencies> currencies = new ArrayList<>();
        currencies.add(new Currencies("1", "USD", "US Dollar"));
        currencies.add(new Currencies("2", "EUR", "Euro"));

        when(currencyRepo.findAll()).thenReturn(currencies);

        List<Currencies> result = currencyService.getcurrency();

        assertEquals(2, result.size());
        assertEquals("USD", result.get(0).getCode());
        assertEquals("EUR", result.get(1).getCode());
    }

    @Test
    void getcurrencyById() throws ResourceNotFoundException {
        Currencies currency = new Currencies("1", "USD", "US Dollar");

        when(currencyRepo.findById("1")).thenReturn(Optional.of(currency));

        Currencies result = currencyService.getcurrencyById("1");

        assertNotNull(result);
        assertEquals("USD", result.getCode());
    }

    @Test
    void testGetCurrencyByIdNotFound() {
        when(currencyRepo.findById("1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            currencyService.getcurrencyById("1");
        });
    }

    @Test
    void updatedata() throws ResourceNotFoundException {
        Currencies currency = new Currencies("1", "USD", "US Dollar");

        when(currencyRepo.save(any(Currencies.class))).thenReturn(currency);

        Currencies result = currencyService.updatedata("1", currency);

        assertNotNull(result);
        assertEquals("USD", result.getCode());
    }




    @Test
    void deleteById() throws ResourceNotFoundException {
        String id = "1";

        currencyService.deleteById(id);

        verify(currencyRepo, times(1)).deleteById(id);
    }


}
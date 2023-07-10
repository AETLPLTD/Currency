package com.example.currency.repository;

import com.example.currency.modal.Currencies;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface CurrencyRepo extends MongoRepository<Currencies,String> {

}

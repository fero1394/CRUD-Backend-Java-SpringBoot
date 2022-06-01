package com.example.SpringBootWebFluxApiRest.dao;

import com.example.SpringBootWebFluxApiRest.documents.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoDao extends ReactiveMongoRepository<Producto, String> {
}

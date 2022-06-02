package com.example.SpringBootWebFluxApiRest.dao;

import com.example.SpringBootWebFluxApiRest.documents.Producto;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductoDao extends ReactiveMongoRepository<Producto, String> {

    public Mono<Producto> findByNombre(String nombre);

    @Query("{ 'nombre':?0 }")
    public Mono<Producto> obtenerPorNombre(String nombre);
}

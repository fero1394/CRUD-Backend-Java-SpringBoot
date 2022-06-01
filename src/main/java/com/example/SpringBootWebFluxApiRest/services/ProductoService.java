package com.example.SpringBootWebFluxApiRest.services;

import com.example.SpringBootWebFluxApiRest.documents.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {

    public Flux<Producto> mostrarTodos();

    public Mono<Producto> buscarPorId(String id);

    public Mono<Producto> guardar(Producto producto);

    public Mono<Producto> editar(Producto producto);

    //Corregir si elimina por el id
    public Mono<Void> eliminar(Producto producto);

}

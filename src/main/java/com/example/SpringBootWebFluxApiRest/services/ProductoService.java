package com.example.SpringBootWebFluxApiRest.services;

import com.example.SpringBootWebFluxApiRest.documents.Producto;
import org.springframework.validation.BindingResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {

    public Flux<Producto> mostrarTodos();

    public Mono<Producto> buscarPorId(String id);

    public Mono<Producto> guardar(Producto producto);


    public Mono<Producto> editar(Producto producto, String id);

    public Mono<Void> eliminar(String id);

}

package com.example.SpringBootWebFluxApiRest.services;

import com.example.SpringBootWebFluxApiRest.dao.ProductoDao;
import com.example.SpringBootWebFluxApiRest.documents.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoDao dao;

    @Override
    public Flux<Producto> mostrarTodos() {
        return null;
    }

    @Override
    public Mono<Producto> buscarPorId(String id) {
        return null;
    }

    @Override
    public Mono<Producto> guardar(Producto producto) {
        if (producto.getCreatedAt() == null) {
            producto.setCreatedAt(LocalDate.from(LocalDateTime.now()));

        }
        return dao.save(producto);
    }

    @Override
    public Mono<Producto> editar(Producto producto) {
        return null;
    }

    @Override
    public Mono<Void> eliminar(Producto producto) {
        return null;
    }
}

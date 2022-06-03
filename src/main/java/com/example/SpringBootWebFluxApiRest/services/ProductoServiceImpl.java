package com.example.SpringBootWebFluxApiRest.services;

import com.example.SpringBootWebFluxApiRest.dao.ProductoDao;
import com.example.SpringBootWebFluxApiRest.documents.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    @Autowired
    private ProductoDao dao;

    @Override
    public Flux<Producto> mostrarTodos() {
        return dao.findAll();
    }

    @Override
    public Mono<Producto> buscarPorId(String id) {
        return dao.findById(id);
    }

    @Override
    public Mono<Producto> guardar(Producto producto) {
            return dao.save(producto);
    }

    @Override
    public Mono<Producto> editar(Producto producto, String id) {
        return dao.findById(id).flatMap(p -> {
            p.setNombre(producto.getNombre());
            p.setPrecio(producto.getPrecio());
            //p.setCreatedAt(producto.getCreatedAt());
            return dao.save(p);
        });
    };

    @Override
    public Mono<Void> eliminar(String id) {
        return dao.deleteById(id);
    }

    @Override
    public Mono<Void> delete(Producto producto) {
        return dao.delete(producto);
    }

    @Override
    public Mono<Producto> findByNombre(String nombre) {
        return dao.obtenerPorNombre(nombre);
    }
}

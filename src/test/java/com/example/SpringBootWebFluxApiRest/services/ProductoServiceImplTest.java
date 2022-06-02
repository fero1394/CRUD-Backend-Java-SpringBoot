package com.example.SpringBootWebFluxApiRest.services;

import com.example.SpringBootWebFluxApiRest.dao.ProductoDao;
import com.example.SpringBootWebFluxApiRest.documents.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductoServiceImplTest {

    @Mock
    private ProductoDao dao;

    @InjectMocks
    private ProductoServiceImpl service;

    private Producto producto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        producto = new Producto();
        producto.setId("123");
        producto.setNombre("balon");
        producto.setPrecio(200.0);
        producto.setCreatedAt(LocalDate.now());
    }

    @Test
    void mostrarTodos_is_correct() {
        when(dao.findAll()).thenReturn(Flux.just(producto));
        assertNotNull(dao.findAll());
    }

    @Test
    void buscarPorId_is_correct() {
        when(dao.findById("123")).thenReturn(Mono.just(producto));
        assertNotNull(dao.findById("123"));
    }

    @Test
    void guardar() {
        when(dao.save(producto)).thenReturn(Mono.just(producto));
        assertNotNull(dao.save(producto));
    }

    @Test
    void editar() {
        when(dao.save(producto)).thenReturn(Mono.just(producto));
        producto.setNombre("raqueta");
        producto.setPrecio(1111.0);
        producto.setCreatedAt(LocalDate.now());
        assertNotNull(dao.save(producto));
    }

    @Test
    void eliminar() {
        when(dao.deleteById("123")).thenReturn(Mono.empty());
        assertNotNull(dao.deleteById("123"));
    }
}
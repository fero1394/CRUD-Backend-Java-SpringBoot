package com.example.SpringBootWebFluxApiRest.controllers;

import com.example.SpringBootWebFluxApiRest.documents.Producto;
import com.example.SpringBootWebFluxApiRest.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tiendaDeportiva")
public class ProductoController {

    @Autowired
    private ProductoService service;


    @GetMapping
    public Mono<ResponseEntity<Flux<Producto>>> listaTodos(){
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.mostrarTodos())
        );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Producto>> verPorId(@PathVariable String id){
        return service.buscarPorId(id).map(p -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PostMapping("/crear")
    public Mono<ResponseEntity<Map<String, Object>>> crear(@Valid @RequestBody Mono<Producto> monoProducto ) {

        Map<String, Object> respuesta = new HashMap<>();

        return monoProducto.flatMap(producto -> {
            if (producto.getCreatedAt() == null) {
                producto.setCreatedAt(LocalDate.from(LocalDateTime.now()));
            }

            return service.guardar(producto).map(p -> {
                respuesta.put("producto", p);
                respuesta.put("mensaje", "Producto creado con éxito");
                respuesta.put("timestamp", LocalDate.from(LocalDateTime.now()));
                return ResponseEntity
                        .created(URI.create("/api/productos/crear/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(respuesta);
            });
        }).onErrorResume(t -> {
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldError()))
                    //.flatMapMany(errors -> Flux.from(errors))
                    .map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    //.collectList()
                    .flatMap(list -> {
                        respuesta.put("errors", list);
                        respuesta.put("timestamp", LocalDate.from(LocalDateTime.now()));
                        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
                        return Mono.just(ResponseEntity.badRequest().body(respuesta));
                    });
        });
    }


        @PutMapping("{id}")
        public Mono<ResponseEntity<Producto>> actualizar(@RequestBody Producto producto ,@PathVariable String id){
            return service.editar(producto,id)
                    .map(p ->ResponseEntity.accepted()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(p))
                    .defaultIfEmpty(ResponseEntity.notFound().build());
        }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id){
        return service.eliminar(id).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
        }


    }



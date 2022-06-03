package com.example.SpringBootWebFluxApiRest;

import com.example.SpringBootWebFluxApiRest.documents.Producto;
import com.example.SpringBootWebFluxApiRest.services.ProductoService;
import com.example.SpringBootWebFluxApiRest.services.ProductoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SpringBootWebFluxApiRestApplicationTests {

	@Autowired
	private WebTestClient client;

	@Autowired
	private ProductoServiceImpl service;

	@Test
	void contextLoads() {
	}

	@Test
	public void listarTest(){

		client.get()
				.uri("/api/tiendaDeportiva/")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	public void verTest() {

		Producto producto = service.findByNombre("Balon Futbol").block();

		client.get()
				.uri("/api/tiendaDeportiva/{id}", Collections.singletonMap("id", producto.getId()))
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.nombre").isEqualTo("Balon Futbol");
	}
/*
.expectBody(Producto.class)
.consumeWith(response -> {
	Producto p = response.getResponseBody();
	Assertions.assertThat(p.getId().isNotEmpty();
	Assertions.assertThat(p.getId().length()>0.isTrue();
	Assertions.assertThat(p.getNombre().isEqualTo("Balon Futbol");
 */

	@Test
	public void crearTest(){

		Producto producto = new Producto("raqueta", 10.0);

		client.post()
				.uri("/api/tiendaDeportiva/crear" )
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(producto), Producto.class)
				.exchange()
				.expectStatus().isCreated()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody();

		}
	@Test
	public void editarTest(){

		Producto producto = service.findByNombre("Guayos talla 38").block();

		Producto productoEditado = new Producto("Balon Futbol", 10.0);
			
		client.put().uri("/api/tiendaDeportiva/{id}", Collections.singletonMap("id", producto.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(productoEditado), Producto.class)
				.exchange()
				.expectStatus().isAccepted()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.nombre").isEqualTo("Balon Futbol");

			}

	@Test
	public void eliminarTest(){
		Producto producto = service.buscarPorId("6297cf33fa08063e7902dbf0").block();
		client.delete()
				.uri("/api/tiendaDeportiva/{id}", Collections.singletonMap("id", producto.getId()))
				.exchange()
				.expectStatus().isNoContent()
				.expectBody()
				.isEmpty();

		client.get()
				.uri("/api/tiendaDeportiva/{id}", Collections.singletonMap("id", producto.getId()))
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.isEmpty();

	}

}


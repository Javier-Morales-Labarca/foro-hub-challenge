
package com.forohub.foro_hub.controller;


import com.forohub.foro_hub.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional

    // En TopicController.java

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoTopico> retornaDatosTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        // Aquí puedes agregar un DTO más detallado si lo necesitas
        var datosTopico = new DatosListadoTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion());
        return ResponseEntity.ok(datosTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {
        Page<DatosListadoTopico> paginaDeTopicos = topicoRepository.findAll(paginacion).map(DatosListadoTopico::new);
        return ResponseEntity.ok(paginaDeTopicos);
    }

    // En TopicoController.java

    @PutMapping
    @Transactional
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizar) {
        Topico topico = topicoRepository.getReferenceById(datosActualizar.id());
        topico.actualizarDatos(datosActualizar);
        // No es necesario llamar a save(), @Transactional se encarga de persistir los cambios.
        return ResponseEntity.ok(new DatosListadoTopico(topico)); // Devuelve el tópico actualizado
    }

    // En TopicoController.java

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
        return ResponseEntity.noContent().build(); // Devuelve un código 204 No Content
    }

    public ResponseEntity<String> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistro) {
        // Regla de negocio: No permitir tópicos duplicados
        // (La base de datos ya tiene una restricción UNIQUE, pero es bueno validar aquí también)
        // if (topicoRepository.findByTituloAndMensaje(datosRegistro.titulo(), datosRegistro.mensaje()).isPresent()) {
        //     return ResponseEntity.badRequest().body("Ya existe un tópico con el mismo título y mensaje.");
        // }

        Topico topico = new Topico(null, datosRegistro.titulo(), datosRegistro.mensaje(), null, null, datosRegistro.autor(), datosRegistro.curso());
        topicoRepository.save(topico);

        return ResponseEntity.ok("Tópico registrado exitosamente.");
    }
}
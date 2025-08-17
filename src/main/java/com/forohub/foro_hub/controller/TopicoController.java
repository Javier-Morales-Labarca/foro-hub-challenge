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
import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosListadoTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistro, UriComponentsBuilder uriComponentsBuilder) {
        // En tu clase Topico, el constructor debe coincidir con los campos del DTO
        Topico topico = new Topico(null, datosRegistro.titulo(), datosRegistro.mensaje(), null, null, datosRegistro.autor(), datosRegistro.curso(), true);
        topicoRepository.save(topico);

        // Retorna un código 201 Created y la URI del nuevo recurso.
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosListadoTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion()));
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {
        // Usa una expresión lambda para mapear cada objeto Topico a un objeto DatosListadoTopico.
        Page<DatosListadoTopico> paginaDeTopicos = topicoRepository.findByActivoTrue(paginacion)
                .map(topico -> new DatosListadoTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion()));
        return ResponseEntity.ok(paginaDeTopicos);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoTopico> retornaDatosTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DatosListadoTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion());
        return ResponseEntity.ok(datosTopico);
    }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @PutMapping
    @Transactional
    public ResponseEntity<DatosListadoTopico> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizar) {
        Topico topico = topicoRepository.getReferenceById(datosActualizar.id());
        topico.actualizarDatos(datosActualizar);
        // Devuelve el tópico actualizado.
        return ResponseEntity.ok(new DatosListadoTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion()));
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
        return ResponseEntity.noContent().build();
    }
}
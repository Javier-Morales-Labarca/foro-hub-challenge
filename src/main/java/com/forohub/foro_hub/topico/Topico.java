package com.forohub.foro_hub.topico;

import com.forohub.foro_hub.topico.DatosActualizarTopico;
import com.forohub.foro_hub.topico.StatusTopico;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private StatusTopico status = StatusTopico.NO_RESPONDIDO;

    private String autor;
    private String curso;
    private Boolean activo = true;

    public Topico() {
    }

    public Topico(String titulo, String mensaje, String autor, String curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.autor = autor;
        this.curso = curso;
    }

    public Topico(Object o, @NotBlank String titulo, @NotBlank String mensaje, Object o1, Object o2, @NotBlank String autor, @NotBlank String curso) {
    }

    public void actualizarDatos(DatosActualizarTopico datos) {
        if (datos.titulo() != null) {
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }
    }

    public void desactivarTopico() {
        this.activo = false;
    }

    // Método getTitulo() corregido
    public String getTitulo() {
        return titulo;
    }

    public Long getId() {
        return 0L;
    }

    public String getMensaje() {
        return "";
    }

    public LocalDateTime getFechaCreacion() {
        return null;
    }
}
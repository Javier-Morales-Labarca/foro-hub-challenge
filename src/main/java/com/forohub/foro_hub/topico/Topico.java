package com.forohub.foro_hub.topico;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    @Enumerated(EnumType.STRING)
    private StatusTopico status;
    private String autor;
    private String curso;
    private Boolean activo;

    // Constructor para la creación de nuevos tópicos
    public Topico(DatosRegistroTopico datosRegistro) {
        this.activo = true;
        this.titulo = datosRegistro.titulo();
        this.mensaje = datosRegistro.mensaje();
        this.autor = datosRegistro.autor();
        this.curso = datosRegistro.curso();
        this.fechaCreacion = LocalDateTime.now();
        this.status = StatusTopico.NO_RESPONDIDO;
    }

    // Método de actualización
    public void actualizarDatos(DatosActualizarTopico datos) {
        if (datos.titulo() != null) {
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }
    }

    // Método para eliminación lógica
    public void desactivarTopico() {
        this.activo = false;
    }
}
// src/main/java/com/alura/forohub/topico/DatosActualizarTopico.java
package com.forohub.foro_hub.topico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull Long id,
        String titulo,
        String mensaje
) {}
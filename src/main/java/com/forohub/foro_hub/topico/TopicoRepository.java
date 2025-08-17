
package com.forohub.foro_hub.topico;

import org.springframework.data.jpa.repository.JpaRepository;
// En TopicoRepository.java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// ...
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Page<Topico> findByActivoTrue(Pageable paginacion);
}

package com.well.forumhubchallenge.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    @Query("""
            SELECT t FROM Topico t
            WHERE (:curso IS NULL OR t.curso.id = :curso)
            AND (:inicio IS NULL OR t.dataCriacao >= :inicio)
            AND (:fim IS NULL OR t.dataCriacao < :fim)
            """)
    Page<Topico> buscar(Long curso, LocalDateTime inicio, LocalDateTime fim, Pageable pageable);
}
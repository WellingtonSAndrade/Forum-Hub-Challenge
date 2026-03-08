package com.well.forumhubchallenge.domain.topico.dto;

import com.well.forumhubchallenge.domain.topico.Status;
import com.well.forumhubchallenge.domain.topico.Topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Status status,
        String autor,
        String curso
) {
    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getStatus(), topico.getAutor().getNome(), topico.getCurso().getNome());
    }
}

package com.well.forumhubchallenge.domain.topico.dto;

import com.well.forumhubchallenge.domain.topico.Topico;

public record DadosListarTopico(
        Long id,
        String titulo,
        String mensagem,
        Long autor,
        Long curso
) {
    public DadosListarTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getAutor().getId(), topico.getCurso().getId());
    }
}

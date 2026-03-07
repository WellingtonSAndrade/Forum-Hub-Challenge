package com.well.forumhubchallenge.domain.topico.dto;

import com.well.forumhubchallenge.domain.topico.Topico;
import jakarta.validation.constraints.NotBlank;

public record DadosTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        Long autor,
        Long curso
) {
        public DadosTopico(Topico topico) {
                this(topico.getTitulo(), topico.getMensagem(), topico.getAutor().getId(), topico.getCurso().getId());
        }
}

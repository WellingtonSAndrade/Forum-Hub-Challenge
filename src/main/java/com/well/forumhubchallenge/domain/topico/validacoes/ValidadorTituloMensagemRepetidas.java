package com.well.forumhubchallenge.domain.topico.validacoes;

import com.well.forumhubchallenge.domain.exception.ValidacaoException;
import com.well.forumhubchallenge.domain.topico.Topico;
import com.well.forumhubchallenge.domain.topico.TopicoRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTituloMensagemRepetidas implements ValidadorTopico {

    private final TopicoRepository topicoRepository;

    public ValidadorTituloMensagemRepetidas(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    @Override
    public void validar(Topico topico) {
        if (topicoRepository.existsByTituloAndMensagemAndIdNot(topico.getTitulo(), topico.getMensagem(), topico.getId())) {
            throw new ValidacaoException("Já existe um tópico com esse titulo e mensagens");
        }
    }
}

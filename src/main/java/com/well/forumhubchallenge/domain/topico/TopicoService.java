package com.well.forumhubchallenge.domain.topico;

import com.well.forumhubchallenge.domain.curso.CursoRepository;
import com.well.forumhubchallenge.domain.exception.RecursoNaoEncontradoException;
import com.well.forumhubchallenge.domain.exception.ValidacaoException;
import com.well.forumhubchallenge.domain.topico.dto.DadosTopico;
import com.well.forumhubchallenge.domain.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicoService {

    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final TopicoRepository topicoRepository;

    public TopicoService(UsuarioRepository usuarioRepository, CursoRepository cursoRepository,
                         TopicoRepository topicoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.topicoRepository = topicoRepository;
    }

    @Transactional
    public Topico criarTopico(DadosTopico dados) {
        var usuario = usuarioRepository.findById(dados.autor()).orElseThrow(() -> new RecursoNaoEncontradoException("Id do usuário informado não existe"));

        var curso = cursoRepository.findById(dados.curso()).orElseThrow(() -> new RecursoNaoEncontradoException("Id do curso informado não eciste"));

        var topico = new Topico(dados.titulo(), dados.mensagem(), usuario, curso);
        if (topicoRepository.existsByTituloAndMensagem(topico.getTitulo(), topico.getMensagem())) {
            throw new ValidacaoException("Já existe um tópico com esse titulo e mensagens");
        }
        return topicoRepository.save(topico);
    }
}

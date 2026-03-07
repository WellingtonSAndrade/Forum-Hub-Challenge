package com.well.forumhubchallenge.domain.topico;

import com.well.forumhubchallenge.domain.curso.CursoRepository;
import com.well.forumhubchallenge.domain.exception.RecursoNaoEncontradoException;
import com.well.forumhubchallenge.domain.exception.ValidacaoException;
import com.well.forumhubchallenge.domain.topico.dto.DadosTopico;
import com.well.forumhubchallenge.domain.usuario.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public Page<DadosTopico> listarTopicos(Long curso, Integer ano, Pageable pageable) {
        LocalDateTime inicio = null;
        LocalDateTime fim = null;

        if (ano != null) {
            inicio = LocalDate.of(ano, 1, 1).atStartOfDay();
            fim = LocalDate.of(ano + 1, 1, 1).atStartOfDay();
        }

        return topicoRepository.buscar(curso, inicio, fim, pageable).map(DadosTopico::new);
    }
}

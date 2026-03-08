package com.well.forumhubchallenge.domain.topico;

import com.well.forumhubchallenge.domain.curso.CursoRepository;
import com.well.forumhubchallenge.domain.exception.RecursoNaoEncontradoException;
import com.well.forumhubchallenge.domain.topico.dto.DadosTopico;
import com.well.forumhubchallenge.domain.topico.validacoes.ValidadorTopico;
import com.well.forumhubchallenge.domain.usuario.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopicoService {

    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final TopicoRepository topicoRepository;
    private final List<ValidadorTopico> validadoresTopico;

    public TopicoService(UsuarioRepository usuarioRepository, CursoRepository cursoRepository,
                         TopicoRepository topicoRepository, List<ValidadorTopico> validadoresTopico) {
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.topicoRepository = topicoRepository;
        this.validadoresTopico = validadoresTopico;
    }

    @Transactional
    public Topico criarTopico(DadosTopico dados) {
        var usuario = usuarioRepository.findById(dados.autor()).orElseThrow(() -> new RecursoNaoEncontradoException("Id do usuário informado não existe"));

        var curso = cursoRepository.findById(dados.curso()).orElseThrow(() -> new RecursoNaoEncontradoException("Id do curso informado não existe"));

        var topico = new Topico(dados.titulo(), dados.mensagem(), usuario, curso);
        validadoresTopico.forEach(validador -> validador.validar(topico));

        return topicoRepository.save(topico);
    }

    public Page<Topico> listarTopicos(Long curso, Integer ano, Pageable pageable) {
        LocalDateTime inicio = null;
        LocalDateTime fim = null;

        if (ano != null) {
            inicio = LocalDate.of(ano, 1, 1).atStartOfDay();
            fim = LocalDate.of(ano + 1, 1, 1).atStartOfDay();
        }

        return topicoRepository.buscar(curso, inicio, fim, pageable);
    }

    public Topico detalhar(Long id) {
        return topicoRepository.getReferenceById(id);
    }

    @Transactional
    public Topico atualizar(Long id, DadosAtualizacaoTopico dados) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Id do tópico informado não existe"));

        topico.atualizarInformacoes(dados);
        validadoresTopico.forEach(validador -> validador.validar(topico));

        return topico;
    }
}

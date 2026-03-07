package com.well.forumhubchallenge.controller;

import com.well.forumhubchallenge.domain.topico.TopicoService;
import com.well.forumhubchallenge.domain.topico.dto.DadosTopico;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private TopicoService servico;

    public TopicoController(TopicoService servico) {
        this.servico = servico;
    }

    @PostMapping
    public ResponseEntity<DadosTopico> criarTopico(@RequestBody @Valid DadosTopico dados, UriComponentsBuilder uriBuilder) {
        var topico = servico.criarTopico(dados);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosTopico(topico));
    }
}

package com.well.forumhubchallenge.controller;

import com.well.forumhubchallenge.domain.topico.TopicoService;
import com.well.forumhubchallenge.domain.topico.dto.DadosTopico;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public ResponseEntity<Page<DadosTopico>> listarTopicos(
            @RequestParam(required = false) Long curso,
            @RequestParam(required = false) Integer ano,
            @PageableDefault(sort = "dataCriacao", direction = Sort.Direction.ASC) Pageable pageable) {
        var page = servico.listarTopicos(curso, ano, pageable);
        return ResponseEntity.ok(page);
    }
}

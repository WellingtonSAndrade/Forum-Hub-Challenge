package com.well.forumhubchallenge.controller;

import com.well.forumhubchallenge.domain.topico.DadosAtualizacaoTopico;
import com.well.forumhubchallenge.domain.topico.TopicoService;
import com.well.forumhubchallenge.domain.topico.dto.DadosDetalhamentoTopico;
import com.well.forumhubchallenge.domain.topico.dto.DadosListarTopico;
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
    public ResponseEntity<DadosDetalhamentoTopico> criarTopico(@RequestBody @Valid DadosTopico dados, UriComponentsBuilder uriBuilder) {
        var topico = servico.criarTopico(dados);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListarTopico>> listarTopicos(
            @RequestParam(required = false) Long curso,
            @RequestParam(required = false) Integer ano,
            @PageableDefault(sort = "dataCriacao", direction = Sort.Direction.ASC) Pageable pageable) {
        var page = servico.listarTopicos(curso, ano, pageable).map(DadosListarTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalharTopico(@PathVariable Long id){
        var topico = servico.detalhar(id);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> atualizarTopico(@PathVariable Long id, @RequestBody DadosAtualizacaoTopico dados){
        var topico = servico.atualizar(id, dados);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }
}

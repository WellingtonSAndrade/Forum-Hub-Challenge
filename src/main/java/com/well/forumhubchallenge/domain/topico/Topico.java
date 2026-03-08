package com.well.forumhubchallenge.domain.topico;

import com.well.forumhubchallenge.domain.curso.Curso;
import com.well.forumhubchallenge.domain.resposta.Resposta;
import com.well.forumhubchallenge.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario autor;
    @ManyToOne(fetch = FetchType.LAZY)
    private Curso curso;
    @OneToMany
    private List<Resposta> respostas = new ArrayList<>();

    public Topico(String titulo, String mensagem, Usuario usuario, Curso curso) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataCriacao = LocalDateTime.now();
        this.status = Status.NAO_RESPONDIDO;
        this.autor = usuario;
        this.curso = curso;
    }

    public void atualizarInformacoes(DadosAtualizacaoTopico dados) {
        if(dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if(dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
    }
}

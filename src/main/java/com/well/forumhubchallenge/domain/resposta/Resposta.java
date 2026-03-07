package com.well.forumhubchallenge.domain.resposta;

import com.well.forumhubchallenge.domain.topico.Topico;
import com.well.forumhubchallenge.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;
    @ManyToOne(fetch = FetchType.LAZY)
    private Topico topico;
    private LocalDateTime dataCriacao;
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario autor;
    private Boolean solucao;
}

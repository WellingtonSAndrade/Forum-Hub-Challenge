create table perfis
(
    id   bigint      not null auto_increment,
    nome varchar(50) not null,

    primary key (id)
);

create table usuarios
(
    id    bigint       not null auto_increment,
    nome  varchar(100) not null,
    email varchar(100) not null,
    senha varchar(255) not null,

    primary key (id)
);

create table usuario_perfil
(
    id         bigint not null auto_increment,
    usuario_id bigint not null,
    perfil_id  bigint not null,

    primary key (id),
    constraint fk_usuario_perfil_id foreign key (usuario_id) references usuarios (id),
    constraint fk_perfil_usuario_id foreign key (perfil_id) references perfis (id)
);

create table cursos
(
    id        bigint       not null auto_increment,
    nome      varchar(100) not null,
    categoria varchar(20),

    primary key (id)
);

create table topicos
(
    id           bigint       not null auto_increment,
    titulo       varchar(200) not null,
    mensagem     text         not null,
    data_criacao datetime     not null,
    status       varchar(20)  not null,
    autor_id     bigint       not null,
    curso_id     bigint       not null,

    primary key (id),
    constraint fk_topico_usuario_id foreign key (autor_id) references usuarios (id),
    constraint fk_topico_curso_id foreign key (curso_id) references cursos (id)
);

create table respostas
(
    id           bigint   not null auto_increment,
    mensagem     text     not null,
    topico_id    bigint   not null,
    data_criacao datetime not null,
    autor_id     bigint   not null,
    solucao      tinyint,

    primary key (id),
    constraint fk_resposta_usuario_id foreign key (autor_id) references usuarios (id),
    constraint fk_resposta_topico_id foreign key (topico_id) references topicos (id)
);

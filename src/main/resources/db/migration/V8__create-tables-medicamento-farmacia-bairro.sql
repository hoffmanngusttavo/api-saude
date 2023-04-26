CREATE TABLE bairros_farmacia
(
    id   bigint       not null auto_increment,
    nome varchar(200) not null,

    primary key (id)
);


CREATE TABLE farmacias
(
    id        bigint       not null auto_increment,
    nome      varchar(100) not null unique,
    endereco  varchar(200) not null,
    telefone  varchar(15)  not null,
    bairro_id bigint       not null,
    horario_inicio_funcionamento   time not null,
    horario_termino_funcionamento   time not null,

    primary key (id),

    constraint fk_farmacias_bairro_id foreign key (bairro_id) references bairros_farmacia (id)
);


CREATE TABLE medicamentos
(
    id        bigint       not null auto_increment,
    nome      varchar(100) not null unique,
    laboratorio  varchar(200) not null,
    disponivel  tinyint  not null,
    miligramas  decimal not null,
    farmacia_id bigint   not null,

    primary key (id),

    constraint fk_medicamentos_farmacia_id foreign key (farmacia_id) references farmacias (id)
);

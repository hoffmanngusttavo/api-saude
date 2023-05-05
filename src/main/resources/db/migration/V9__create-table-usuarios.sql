CREATE TABLE usuarios
(
    id          bigint       not null auto_increment,
    login       varchar(100) not null unique,
    senha       varchar(50) not null ,


    primary key (id)
);
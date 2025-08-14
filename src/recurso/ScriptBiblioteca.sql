drop database if exists biblioteca;
create database biblioteca;

use biblioteca;

create table usuario(
id_usuario int primary key auto_increment,
nome varchar(100) not null,
telefone varchar(15) unique not null,
email varchar(100),
tipo_usuario varchar(20)
);

create table livro(
id_livro int primary key auto_increment,
id_doador int not null,
titulo varchar(100) not null,
autor varchar(100) not null,
genero varchar(100) not null,
disponivel boolean default true,
foreign key(id_doador) references usuario(id_usuario)
);

create table emprestimo(
id_emprestimo int primary key auto_increment,
id_livro int not null,
id_usuario int not null,
data_emprestimo date not null,
data_prevista date not null,
data_devolucao date,
foreign key(id_livro) references livro(id_livro),
foreign key(id_usuario) references usuario(id_usuario)
);

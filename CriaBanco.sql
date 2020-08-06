CREATE TABLE [IF NOT EXISTS] pessoa
(
	id serial,
	nome varchar(100) not null,
	email varchar(100) not null,
	senha varchar(15) not null	
);


CREATE TABLE [IF NOT EXISTS] empresa
(
	id PRIMARY KEY,
	nome varchar(100) not null,
	email varchar(100) not null,
	senha varchar(15) not null	
);
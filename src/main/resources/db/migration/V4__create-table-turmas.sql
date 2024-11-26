create table turmas (
    id int not null primary key auto_increment,
    curso_id int not null,
    ano int,
    semestre int,
    foreign key (curso_id) references cursos(id)
);
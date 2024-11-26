create table notas(
    id int not null primary key auto_increment,
    matricula_id  int not null,
    disciplina_id int not null,
    nota decimal(5,2),
    data_lancamento date,
    foreign key (matricula_id) references matriculas(id),
    foreign key (disciplina_id) references disciplinas(id)
)
create table testedefu(
                      id int not null primary key auto_increment,
                      matricula_id  int not null,
                      disciplina_id int not null,
                      status in not null,
                      percentual_nota int not null,
                      percentual_presença int not nuul,
                      foreign key (matricula_id) references matriculas(id),
                      foreign key (disciplina_id) references disciplinas(id)
)
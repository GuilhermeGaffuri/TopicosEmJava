package br.grupointegrado.projetoTDE.repository;

import br.grupointegrado.projetoTDE.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
}

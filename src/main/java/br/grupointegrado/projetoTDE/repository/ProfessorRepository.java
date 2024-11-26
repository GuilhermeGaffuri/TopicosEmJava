package br.grupointegrado.projetoTDE.repository;

import br.grupointegrado.projetoTDE.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
}

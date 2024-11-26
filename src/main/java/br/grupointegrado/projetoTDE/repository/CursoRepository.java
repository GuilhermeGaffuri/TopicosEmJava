package br.grupointegrado.projetoTDE.repository;

import br.grupointegrado.projetoTDE.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
}

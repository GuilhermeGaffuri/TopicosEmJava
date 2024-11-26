package br.grupointegrado.projetoTDE.repository;

import br.grupointegrado.projetoTDE.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
}

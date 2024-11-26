package br.grupointegrado.projetoTDE.repository;

import br.grupointegrado.projetoTDE.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NotaRepository extends JpaRepository<Nota, Integer> {

    List<Nota> findByMatriculaId(Integer id);

    List<Nota> findByDisciplinaId(Integer id);
}

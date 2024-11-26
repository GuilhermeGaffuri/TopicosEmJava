package br.grupointegrado.projetoTDE.controller;

import br.grupointegrado.projetoTDE.model.Disciplina;
import br.grupointegrado.projetoTDE.model.Matricula;
import br.grupointegrado.projetoTDE.repository.DisciplinaRepository;
import br.grupointegrado.projetoTDE.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaRepository matriculaRepository;

    // Função para listar todas as Matriculas
    @GetMapping
    public List<Matricula> findAll() {
        return matriculaRepository.findAll();
    }

    // Função para buscar uma Matricula pelo id
    @GetMapping("/{id}")
    public ResponseEntity<Matricula> findById(@PathVariable Integer id) {
        Optional<Matricula> matricula = matriculaRepository.findById(id);
        if (matricula.isPresent()) {
            return ResponseEntity.ok(matricula.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Função para cadastrar uma nova matricula
    @PostMapping
    public Matricula save(@RequestBody Matricula matricula) {
        return matriculaRepository.save(matricula);
    }

    // Função para atualizar uma matricula existente pelo id;
    @PutMapping("/{id}")
    public ResponseEntity<Matricula> update(@PathVariable Integer id, @RequestBody Matricula matriculaDetails) {
        Optional<Matricula> optionalMatricula = matriculaRepository.findById(id);

        if (optionalMatricula.isPresent()) {
            Matricula matricula = optionalMatricula.get();
            matricula.setAlunoId(optionalMatricula.get().getAlunoId());
            matricula.setTurmaId(optionalMatricula.get().getTurmaId());

            matriculaRepository.save(matricula);
            return ResponseEntity.ok(matricula);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Função para deletar uma matricula pelo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<Matricula> optionalMatricula = matriculaRepository.findById(id);

        if (optionalMatricula.isPresent()) {
            matriculaRepository.delete(optionalMatricula.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}

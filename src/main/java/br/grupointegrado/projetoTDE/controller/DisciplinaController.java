package br.grupointegrado.projetoTDE.controller;

import br.grupointegrado.projetoTDE.model.Disciplina;
import br.grupointegrado.projetoTDE.model.Nota;
import br.grupointegrado.projetoTDE.repository.DisciplinaRepository;
import br.grupointegrado.projetoTDE.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private NotaRepository notaRepository;

    // Função para listar todas as disciplinas
    @GetMapping
    public List<Disciplina> findAll() {
        return disciplinaRepository.findAll();
    }



    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> findById(@PathVariable Integer id) {
        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);
        if (disciplina.isPresent()) {
            return ResponseEntity.ok(disciplina.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Função para cadastrar uma nova disciplina
    @PostMapping
    public Disciplina save(@RequestBody Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    // Função para atualizar uma disciplina existente pelo id;
    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> update(@PathVariable Integer id, @RequestBody Disciplina disciplinaDetails) {
        Optional<Disciplina> optionalDisciplina = disciplinaRepository.findById(id);

        if (optionalDisciplina.isPresent()) {
            Disciplina disciplina = optionalDisciplina.get();
            disciplina.setNome(disciplinaDetails.getNome());
            disciplina.setCodigo(disciplinaDetails.getCodigo());

            disciplinaRepository.save(disciplina);
            return ResponseEntity.ok(disciplina);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Função para deletar uma disciplina pelo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<Disciplina> optionalDisciplina = disciplinaRepository.findById(id);

        if (optionalDisciplina.isPresent()) {
            disciplinaRepository.delete(optionalDisciplina.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}/notas")
    public ResponseEntity<List<Nota>> findNotasByDisciplinaId(@PathVariable Integer id) {
        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);

        if (disciplina.isPresent()) {
            // Busca todas as notas relacionadas à disciplina pelo id
            List<Nota> notas = notaRepository.findByDisciplinaId(id);

            if (notas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // Sem notas encontradas
            }

            return ResponseEntity.ok(notas); // Retorna as notas
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Disciplina não encontrada
        }
    }
}

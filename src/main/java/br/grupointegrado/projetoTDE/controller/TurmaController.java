package br.grupointegrado.projetoTDE.controller;

import br.grupointegrado.projetoTDE.model.Aluno;
import br.grupointegrado.projetoTDE.model.Matricula;
import br.grupointegrado.projetoTDE.model.Nota;
import br.grupointegrado.projetoTDE.model.Turma;
import br.grupointegrado.projetoTDE.repository.NotaRepository;
import br.grupointegrado.projetoTDE.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private NotaRepository notaRepository;

    @GetMapping
    public List<Turma> findAll() {
        return turmaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> findById(@PathVariable Integer id) {
        Optional<Turma> turma = turmaRepository.findById(id);
        if (turma.isPresent()) {
            return ResponseEntity.ok(turma.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public Turma save(@RequestBody Turma turma) {
        return turmaRepository.save(turma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> update(@PathVariable Integer id, @RequestBody Turma turma) {
        Optional<Turma> turmaExistente = turmaRepository.findById(id);
        if (turmaExistente.isPresent()) {
            Turma turmaAtualizado = turmaExistente.get();
            turmaAtualizado.setAno(turma.getAno());
            turmaAtualizado.setSemestre(turma.getSemestre());
            turmaAtualizado.setCurso(turma.getCurso());
            return ResponseEntity.ok(turmaAtualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<Turma> turma = turmaRepository.findById(id);
        if (turma.isPresent()) {
            turmaRepository.delete(turma.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Método para buscar notas de uma turma pelo ID
    @GetMapping("/{id}/relatorio")
    public ResponseEntity<Map<String, Object>> findNotasById(@PathVariable Integer id) {
        Optional<Turma> turma = turmaRepository.findById(id);

        if (turma.isPresent()) {





            Map<String, Object> response = new HashMap<>();

            // Adiciona o id da turma ao mapa
            response.put("idTurma", turma.get().getId());

            // Inicializa a lista para armazenar as notas
            List<Map<String, Object>> notasList = new ArrayList<>();

            // Percorre todas as matrículas da turma
            for (Matricula matricula : turma.get().getMatriculas()) {
                // Para cada matrícula, buscar as notas associadas
                List<Nota> notas = notaRepository.findByMatriculaId(matricula.getId());

                // Para cada nota associada à matrícula
                for (Nota nota : notas) {
                    // Cria um mapa com as informações da nota
                    Map<String, Object> notaData = new HashMap<>();
                    notaData.put("disciplina", nota.getDisciplina().getNome());  // Nome da disciplina
                    notaData.put("nota", nota.getNota());  // Nota obtida
                    notaData.put("data_lancamento", nota.getData_lancamento());  // Data de lançamento

                    // Adiciona o mapa da nota à lista de notas
                    notasList.add(notaData);
                }
            }

            // Adiciona a lista de notas ao mapa de resposta
            response.put("notas", notasList);

            // Retorna a resposta com as informações do aluno e suas notas
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}

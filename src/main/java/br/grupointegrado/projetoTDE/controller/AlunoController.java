package br.grupointegrado.projetoTDE.controller;


import br.grupointegrado.projetoTDE.model.Aluno;
import br.grupointegrado.projetoTDE.model.Matricula;
import br.grupointegrado.projetoTDE.model.Nota;
import br.grupointegrado.projetoTDE.repository.AlunoRepository;
import br.grupointegrado.projetoTDE.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {


    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private NotaRepository notaRepository;

    // Método para buscar todos os alunos
    @GetMapping
    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    // Método para buscar um aluno pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findById(@PathVariable Integer id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if (aluno.isPresent()) {
            return ResponseEntity.ok(aluno.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Método para salvar um novo aluno
    @PostMapping
    public Aluno save(@RequestBody Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    // Método para atualizar um aluno existente
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> update(@PathVariable Integer id, @RequestBody Aluno aluno) {
        Optional<Aluno> alunoExistente = alunoRepository.findById(id);
        if (alunoExistente.isPresent()) {
            Aluno alunoAtualizado = alunoExistente.get();
            alunoAtualizado.setNome(aluno.getNome());
            alunoAtualizado.setEmail(aluno.getEmail());
            alunoAtualizado.setMatricula(aluno.getMatricula());
            alunoAtualizado.setDataNascimento(aluno.getDataNascimento());
            alunoRepository.save(alunoAtualizado);
            return ResponseEntity.ok(alunoAtualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Método para deletar um aluno pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if (aluno.isPresent()) {
            alunoRepository.delete(aluno.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Método para buscar notas de um aluno pelo ID
    @GetMapping("/{id}/relatorio")
    public ResponseEntity<Map<String, Object>> findNotasById(@PathVariable Integer id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);

        if (aluno.isPresent()) {
            // Cria um mapa para armazenar o nome do aluno, as matrículas e as notas
            Map<String, Object> response = new HashMap<>();

            // Adiciona o nome do aluno ao mapa
            response.put("nome", aluno.get().getNome());

            // Inicializa a lista para armazenar as notas
            List<Map<String, Object>> notasList = new ArrayList<>();

            // Percorre todas as matrículas do aluno
            for (Matricula matricula : aluno.get().getMatriculas()) {
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

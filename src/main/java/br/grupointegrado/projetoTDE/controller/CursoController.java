package br.grupointegrado.projetoTDE.controller;

import br.grupointegrado.projetoTDE.model.Curso;
import br.grupointegrado.projetoTDE.model.Turma;
import br.grupointegrado.projetoTDE.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {
    @Autowired
    private CursoRepository cursoRepository;

    //Listar cursos;
    @GetMapping
    public ResponseEntity<List<Curso>> findAll() {
        return ResponseEntity.ok(this.cursoRepository.findAll());
    }

    //Função para buscar um curso pelo id;
    @GetMapping("/{id}")
    public ResponseEntity<Curso> findById(@PathVariable Integer id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        if (curso.isPresent()) {
            return ResponseEntity.ok(curso.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //Cadastrar novo curso;
    @PostMapping
    public Curso save(@RequestBody Curso curso) {
        return cursoRepository.save(curso);
    }


    //Função para atualizar um curso pelo id;
    @PutMapping("/{id}")
    public ResponseEntity<Curso> update(@PathVariable Integer id, @RequestBody Curso cursoDetails) {
        Optional<Curso> optionalCurso = cursoRepository.findById(id);

        if (optionalCurso.isPresent()) {
            Curso cruso = optionalCurso.get();
            cruso.setNome(cursoDetails.getNome());
            cruso.setCodigo(cursoDetails.getCodigo());
            cruso.setCarga_horaria(cursoDetails.getCarga_horaria());

            cursoRepository.save(cruso);
            return ResponseEntity.ok(cruso);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //Função para deletar um curso pelo id;
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<Curso> optionalCurso = cursoRepository.findById(id);

        if (optionalCurso.isPresent()) {
            cursoRepository.delete(optionalCurso.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
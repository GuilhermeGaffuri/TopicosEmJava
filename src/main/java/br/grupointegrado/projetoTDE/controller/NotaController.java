package br.grupointegrado.projetoTDE.controller;

import br.grupointegrado.projetoTDE.model.Nota;
import br.grupointegrado.projetoTDE.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    @Autowired
    private NotaRepository notaRepository;

    // Função para listar todas as Notas
    @GetMapping
    public List<Nota> findAll() {
        return notaRepository.findAll();
    }

    // Função para buscar uma Nota pelo id
    @GetMapping("/{id}")
    public ResponseEntity<Nota> findById(@PathVariable Integer id) {
        Optional<Nota> nota = notaRepository.findById(id);
        if (nota.isPresent()) {
            return ResponseEntity.ok(nota.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Função para cadastrar uma nova nota
    @PostMapping
    public ResponseEntity<Nota> save(@RequestBody Nota nota) {
        Nota savedNota = notaRepository.save(nota);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNota);
    }

    // Função para atualizar uma nota existente pelo id
    @PutMapping("/{id}")
    public ResponseEntity<Nota> update(@PathVariable Integer id, @RequestBody Nota notaDetails) {
        Optional<Nota> optionalNota = notaRepository.findById(id);

        if (optionalNota.isPresent()) {
            Nota nota = optionalNota.get();
            nota.setMatricula(notaDetails.getMatricula());
            nota.setDisciplina(notaDetails.getDisciplina());
            nota.setNota(notaDetails.getNota());
            nota.setData_lancamento(notaDetails.getData_lancamento());

            notaRepository.save(nota);
            return ResponseEntity.ok(nota);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Função para deletar uma nota pelo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<Nota> optionalNota = notaRepository.findById(id);

        if (optionalNota.isPresent()) {
            notaRepository.delete(optionalNota.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

package projetoescola.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetoescola.demo.modelo.Disciplina;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
public class ControllerDisciplina {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public List<Disciplina> getAllDisciplinas() {
        return entityManager.createQuery("from Disciplina", Disciplina.class).getResultList();
    }

    @GetMapping("/{idDisciplina}")
    public ResponseEntity<Disciplina> getDisciplinaById(@PathVariable Long idDisciplina) {
        Disciplina disciplina = entityManager.find(Disciplina.class, idDisciplina);
        if (disciplina == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(disciplina);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Disciplina> createDisciplina(@RequestBody Disciplina disciplina) {
        try {
            entityManager.persist(disciplina);
            return ResponseEntity.ok(disciplina);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{idDisciplina}")
    @Transactional
    public ResponseEntity<Disciplina> updateDisciplina(@PathVariable Long idDisciplina, @RequestBody Disciplina disciplinaDetails) {
        try {
            Disciplina disciplina = entityManager.find(Disciplina.class, idDisciplina);
            if (disciplina == null) {
                return ResponseEntity.notFound().build();
            }
            disciplina.setQtdTurmas(disciplinaDetails.getQtdTurmas());
            disciplina.setNome(disciplinaDetails.getNome());
            entityManager.merge(disciplina);
            return ResponseEntity.ok(disciplina);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{idDisciplina}")
    @Transactional
    public ResponseEntity<Void> deleteDisciplina(@PathVariable Long idDisciplina) {
        try {
            Disciplina disciplina = entityManager.find(Disciplina.class, idDisciplina);
            if (disciplina == null) {
                return ResponseEntity.notFound().build();
            }
            entityManager.remove(disciplina);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}

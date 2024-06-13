package projetoescola.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetoescola.demo.modelo.Professor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class controllerProfessor {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public List<Professor> getAllProfessores() {
        return entityManager.createQuery("from Professor", Professor.class).getResultList();
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable Long idUsuario) {
        Professor professor = entityManager.find(Professor.class, idUsuario);
        if (professor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(professor);
    }

    @PostMapping
    @Transactional
    public Professor createProfessor(@RequestBody Professor professor) {
        entityManager.persist(professor);
        return professor;
    }

    @PutMapping("/{idUsuario}")
    @Transactional
    public ResponseEntity<Professor> updateProfessor(@PathVariable Long idUsuario, @RequestBody Professor professorDetails) {
        Professor professor = entityManager.find(Professor.class, idUsuario);
        if (professor == null) {
            return ResponseEntity.notFound().build();
        }
        professor.setSalario(professorDetails.getSalario());
        professor.setCargo(professorDetails.getCargo());
        professor.setNome(professorDetails.getNome());
        professor.setNumero(professorDetails.getNumero());
        professor.setEmail(professorDetails.getEmail());
        professor.setIdade(professorDetails.getIdade());
        professor.setSexo(professorDetails.getSexo());
        professor.setCpf(professorDetails.getCpf());
        entityManager.merge(professor);
        return ResponseEntity.ok(professor);
    }

    @DeleteMapping("/{idUsuario}")
    @Transactional
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long idUsuario) {
        Professor professor = entityManager.find(Professor.class, idUsuario);
        if (professor == null) {
            return ResponseEntity.notFound().build();
        }
        entityManager.remove(professor);
        return ResponseEntity.ok().build();
    }
}

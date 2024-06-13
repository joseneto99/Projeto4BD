package projetoescola.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetoescola.demo.modelo.Semestre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RestController
@RequestMapping("/semestres")
public class ControllerSemestre {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public List<Semestre> getAllSemestres() {
        return entityManager.createQuery("from Semestre", Semestre.class).getResultList();
    }

    @GetMapping("/{idSemestre}")
    public ResponseEntity<Semestre> getSemestreById(@PathVariable Long idSemestre) {
        Semestre semestre = entityManager.find(Semestre.class, idSemestre);
        if (semestre == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(semestre);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Semestre> createSemestre(@RequestBody Semestre semestre) {
        try {
            entityManager.persist(semestre);
            return ResponseEntity.ok(semestre);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{idSemestre}")
    @Transactional
    public ResponseEntity<Semestre> updateSemestre(@PathVariable Long idSemestre, @RequestBody Semestre semestreDetails) {
        try {
            Semestre semestre = entityManager.find(Semestre.class, idSemestre);
            if (semestre == null) {
                return ResponseEntity.notFound().build();
            }
            semestre.setTempoSemestre(semestreDetails.getTempoSemestre());
            entityManager.merge(semestre);
            return ResponseEntity.ok(semestre);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{idSemestre}")
    @Transactional
    public ResponseEntity<Void> deleteSemestre(@PathVariable Long idSemestre) {
        try {
            Semestre semestre = entityManager.find(Semestre.class, idSemestre);
            if (semestre == null) {
                return ResponseEntity.notFound().build();
            }
            entityManager.remove(semestre);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}

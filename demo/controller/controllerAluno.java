package projetoescola.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetoescola.demo.modelo.Aluno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class controllerAluno {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public List<Aluno> getAllAlunos() {
        return entityManager.createQuery("from Aluno", Aluno.class).getResultList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getAlunoById(@PathVariable Long id) {
        Aluno aluno = entityManager.find(Aluno.class, id);
        if (aluno == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(aluno);
    }

    @PostMapping
    @Transactional
    public Aluno createAluno(@RequestBody Aluno aluno) {
        entityManager.persist(aluno);
        return aluno;
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Aluno> updateAluno(@PathVariable Long id, @RequestBody Aluno alunoDetails) {
        Aluno aluno = entityManager.find(Aluno.class, id);
        if (aluno == null) {
            return ResponseEntity.notFound().build();
        }
        aluno.setRa(alunoDetails.getRa());
        aluno.setTipoPlano(alunoDetails.getTipoPlano());
        aluno.setNome(alunoDetails.getNome());
        aluno.setNumero(alunoDetails.getNumero());
        aluno.setEmail(alunoDetails.getEmail());
        aluno.setIdade(alunoDetails.getIdade());
        aluno.setSexo(alunoDetails.getSexo());
        aluno.setCpf(alunoDetails.getCpf());
        entityManager.merge(aluno);
        return ResponseEntity.ok(aluno);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteAluno(@PathVariable Long id) {
        Aluno aluno = entityManager.find(Aluno.class, id);
        if (aluno == null) {
            return ResponseEntity.notFound().build();
        }
        entityManager.remove(aluno);
        return ResponseEntity.ok().build();
    }
}

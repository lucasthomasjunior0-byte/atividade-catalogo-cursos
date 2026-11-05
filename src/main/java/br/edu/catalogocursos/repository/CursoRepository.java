package br.edu.catalogocursos.repository;

import br.edu.catalogocursos.domain.Curso;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Override
    @EntityGraph(attributePaths = "instrutor")
    Optional<Curso> findById(Long id);

    List<Curso> findByInstrutorIdOrderByTituloAsc(Long instrutorId);
}

package br.edu.catalogocursos.application;

import br.edu.catalogocursos.application.exception.EmailDuplicadoException;
import br.edu.catalogocursos.application.exception.RecursoNaoEncontradoException;
import br.edu.catalogocursos.domain.Curso;
import br.edu.catalogocursos.domain.Instrutor;
import br.edu.catalogocursos.repository.CursoRepository;
import br.edu.catalogocursos.repository.InstrutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class InstrutorService {

    private final InstrutorRepository instrutorRepository;
    private final CursoRepository cursoRepository;

    public InstrutorService(InstrutorRepository instrutorRepository, CursoRepository cursoRepository) {
        this.instrutorRepository = instrutorRepository;
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public Instrutor cadastrar(Instrutor instrutor) {
        if (instrutorRepository.existsByEmail(instrutor.getEmail())) {
            throw new EmailDuplicadoException("Email já cadastrado");
        }
        return instrutorRepository.save(instrutor);
    }

    public Instrutor buscarPorId(Long id) {
        return instrutorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Instrutor não encontrado"));
    }

    public List<Curso> listarCursos(Long instrutorId) {
        if (!instrutorRepository.existsById(instrutorId)) {
            throw new RecursoNaoEncontradoException("Instrutor não encontrado");
        }
        return cursoRepository.findByInstrutorIdOrderByTituloAsc(instrutorId);
    }
}

package br.edu.catalogocursos.application;

import br.edu.catalogocursos.domain.Curso;
import br.edu.catalogocursos.domain.NivelCurso;
import br.edu.catalogocursos.repository.CursoRepository;
import br.edu.catalogocursos.repository.InstrutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstrutorServiceTest {

    @Mock
    private InstrutorRepository instrutorRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private InstrutorService instrutorService;

    @Test
    void deveListarSomenteCursosDoInstrutorOrdenadosPeloTitulo() {
        when(instrutorRepository.existsById(1L)).thenReturn(true);
        Curso apis = new Curso("APIs REST com Spring Boot", 40, NivelCurso.INTERMEDIARIO);
        Curso java = new Curso("Java orientado a objetos", 60, NivelCurso.INICIANTE);
        when(cursoRepository.findByInstrutorIdOrderByTituloAsc(1L)).thenReturn(List.of(apis, java));

        List<Curso> cursos = instrutorService.listarCursos(1L);

        assertEquals(
                List.of("APIs REST com Spring Boot", "Java orientado a objetos"),
                cursos.stream().map(Curso::getTitulo).toList());
        verify(cursoRepository).findByInstrutorIdOrderByTituloAsc(1L);
        verify(cursoRepository, never()).findByInstrutorIdOrderByTituloAsc(2L);
    }
}

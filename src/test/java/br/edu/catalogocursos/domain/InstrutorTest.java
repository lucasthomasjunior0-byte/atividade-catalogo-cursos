package br.edu.catalogocursos.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InstrutorTest {

    @Test
    void deveAssociarCursoMantendoOsDoisLadosConsistentes() throws Exception {
        Instrutor instrutor = new Instrutor("Marina Costa", "marina.costa@example.com");
        Curso curso = new Curso("APIs REST com Spring Boot", 40, NivelCurso.INTERMEDIARIO);

        instrutor.adicionarCurso(curso);

        assertSame(instrutor, curso.getInstrutor());
        assertEquals(1, instrutor.getCursos().size());
        assertSame(curso, instrutor.getCursos().get(0));

        instrutor.adicionarCurso(curso);
        assertEquals(1, instrutor.getCursos().size());
        assertThrows(NoSuchMethodException.class, () -> Instrutor.class.getMethod("setCursos", List.class));
    }

    @Test
    void deveRejeitarCursoNulo() {
        Instrutor instrutor = new Instrutor("Marina Costa", "marina.costa@example.com");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> instrutor.adicionarCurso(null));

        assertEquals("Curso não pode ser nulo", ex.getMessage());
        assertTrue(instrutor.getCursos().isEmpty());
    }
}

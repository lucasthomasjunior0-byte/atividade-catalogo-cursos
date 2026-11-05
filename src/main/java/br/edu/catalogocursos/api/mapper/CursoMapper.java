package br.edu.catalogocursos.api.mapper;

import br.edu.catalogocursos.api.dto.CursoRequest;
import br.edu.catalogocursos.api.dto.CursoResponse;
import br.edu.catalogocursos.api.dto.CursoResumoResponse;
import br.edu.catalogocursos.domain.Curso;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper {

    private final InstrutorMapper instrutorMapper;

    public CursoMapper(InstrutorMapper instrutorMapper) {
        this.instrutorMapper = instrutorMapper;
    }

    public Curso toEntity(CursoRequest request) {
        return new Curso(request.titulo().trim(), request.cargaHoraria(), request.nivel());
    }

    public CursoResponse toResponse(Curso curso) {
        return new CursoResponse(
                curso.getId(),
                curso.getTitulo(),
                curso.getCargaHoraria(),
                curso.getNivel(),
                instrutorMapper.toResumo(curso.getInstrutor())
        );
    }

    public CursoResumoResponse toResumo(Curso curso) {
        return new CursoResumoResponse(
                curso.getId(),
                curso.getTitulo(),
                curso.getCargaHoraria(),
                curso.getNivel()
        );
    }
}

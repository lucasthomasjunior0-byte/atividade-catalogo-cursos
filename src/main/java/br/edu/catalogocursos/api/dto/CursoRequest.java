package br.edu.catalogocursos.api.dto;

import br.edu.catalogocursos.domain.NivelCurso;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CursoRequest(
        @NotBlank(message = "é obrigatório")
        @Size(min = 3, max = 150, message = "deve ter entre 3 e 150 caracteres")
        String titulo,

        @NotNull(message = "é obrigatória")
        @Min(value = 1, message = "deve ser de no mínimo 1 hora")
        @Max(value = 400, message = "deve ser de no máximo 400 horas")
        Integer cargaHoraria,

        @NotNull(message = "é obrigatório")
        NivelCurso nivel,

        @NotNull(message = "é obrigatório")
        Long instrutorId
) {
}

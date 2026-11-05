package br.edu.catalogocursos.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InstrutorRequest(
        @NotBlank(message = "é obrigatório")
        @Size(min = 3, max = 120, message = "deve ter entre 3 e 120 caracteres")
        String nome,

        @NotBlank(message = "é obrigatório")
        @Email(message = "deve ser um e-mail válido")
        @Size(max = 160, message = "deve ter no máximo 160 caracteres")
        String email
) {
}

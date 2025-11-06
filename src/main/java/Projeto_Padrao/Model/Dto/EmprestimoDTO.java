package Projeto_Padrao.Model.Dto;

import java.time.LocalDate;

public record EmprestimoDTO(
        String celular,
        String nome,
        String livro,
        String autor,
        LocalDate retirada,
        LocalDate devolucao,
        boolean devolvido
) {}

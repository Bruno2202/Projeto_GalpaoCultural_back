package Projeto_Padrao.Model.Dto.Autenticacao;

public record CreateUserDto(
        String nome,
        String email,
        String password
) {
}

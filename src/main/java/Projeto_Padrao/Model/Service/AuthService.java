package Projeto_Padrao.Model.Service;


import Projeto_Padrao.Model.Dto.Autenticacao.CreateUserDto;
import Projeto_Padrao.Model.Dto.Autenticacao.LoginUserDto;
import Projeto_Padrao.Model.Dto.Autenticacao.RecoveryJwtTokenDto;
import Projeto_Padrao.Model.Entidade.Administrador;
import Projeto_Padrao.Model.Exception.DataNotFoundException;
import Projeto_Padrao.Model.Exception.Handle.GlobalExceptionHandler;
import Projeto_Padrao.Model.Repository.AdmRepository;
import Projeto_Padrao.Model.Repository.EmprestimoRepository;
import Projeto_Padrao.Model.Security.Config.SecurityConfiguration;
import Projeto_Padrao.Model.Security.JwtTokenService;
import Projeto_Padrao.Model.Security.UserDetailsImpl;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthService {

    final private AuthenticationManager authenticationManager;
    final private JwtTokenService jwtTokenService;
    final private AdmRepository admRepository;
    final private SecurityConfiguration securityConfiguration;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, AdmRepository admRepository, SecurityConfiguration securityConfiguration) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.admRepository = admRepository;
        this.securityConfiguration = securityConfiguration;
    }

    ///ENDPOINTS DE LOGIN
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    ///Método responsável por criar um usuário
    public void criarUsuario(CreateUserDto createUserDto) {

        Administrador adm = admRepository.findByEmail(createUserDto.email());
        if (adm != null) {
            throw new DataNotFoundException("EMAIL INVÁLIDO!");
        }

        // Cria um novo usuário com os dados fornecidos
        Administrador novoADM = Administrador.builder()
                .nome(createUserDto.nome())
                .email(createUserDto.email())
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password())) // Codifica a senha do usuário com o algoritmo bcrypt
                .dataCriacao(LocalDate.now())
                .build();
        admRepository.save(novoADM);
    }
}
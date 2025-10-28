package Projeto_Padrao.Model.Service;

import Projeto_Padrao.Model.Dto.Autenticacao.CreateUserDto;
import Projeto_Padrao.Model.Dto.Autenticacao.LoginUserDto;
import Projeto_Padrao.Model.Dto.Autenticacao.RecoveryJwtTokenDto;
import Projeto_Padrao.Model.Dto.EmprestimosAtrasadosDTO;
import Projeto_Padrao.Model.Dto.VisualizarEmpDTO;
import Projeto_Padrao.Model.Entidade.Administrador;
import Projeto_Padrao.Model.Entidade.Emprestimo;
import Projeto_Padrao.Model.Exception.DataNotFoundException;
import Projeto_Padrao.Model.Repository.AdmRepository;
import Projeto_Padrao.Model.Repository.EmprestimoRepository;
import Projeto_Padrao.Model.Security.Config.SecurityConfiguration;
import Projeto_Padrao.Model.Security.JwtTokenService;
import Projeto_Padrao.Model.Security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service("AdmServicePricipal")
public class AdmService {

    final private EmprestimoRepository emprestimoRepository;
    final private AuthenticationManager authenticationManager;
    final private JwtTokenService jwtTokenService;
    final private AdmRepository admRepository;
    final private SecurityConfiguration securityConfiguration;

    public AdmService(EmprestimoRepository emprestimoRepository, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, AdmRepository admRepository, SecurityConfiguration securityConfiguration) {
        this.emprestimoRepository = emprestimoRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.admRepository = admRepository;
        this.securityConfiguration = securityConfiguration;
    }

    //LISTAR TODOS OS REGISTROS
    public List<VisualizarEmpDTO> ListarEmprestimos() {
        List<Emprestimo> lista = emprestimoRepository.findAll();

        if (lista.isEmpty()) {
            throw new DataNotFoundException("EMPRESTIMOS NÃO ENCONTRADOS");
        }

        return lista.stream().map(e -> new VisualizarEmpDTO(
                e.getId(),
                e.getNome(),
                e.getLivro(),
                e.getAutor()
        )).toList();
    }

    public List<EmprestimosAtrasadosDTO> EmprestimosAtrasados() {
        List<EmprestimosAtrasadosDTO> listaAtrasados = emprestimoRepository.findAll().stream()
                .filter(e -> LocalDate.now().isAfter(e.getRetirada().plusMonths(1)))
                .filter(e -> !e.isDevolvido())
                .map(e -> new EmprestimosAtrasadosDTO(
                        e.getId(),
                        e.getCelular(),
                        e.getNome(),
                        e.getLivro(),
                        e.getAutor()
                ))
                .collect(Collectors.toList());

        if (listaAtrasados.isEmpty()) {
            throw new DataNotFoundException("NÃO EXISTEM EMPRESTIMOS ATRASADOS!");
        }
        return listaAtrasados;
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

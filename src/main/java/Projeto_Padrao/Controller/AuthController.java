package Projeto_Padrao.Controller;

import Projeto_Padrao.Model.Dto.Autenticacao.CreateUserDto;
import Projeto_Padrao.Model.Dto.Autenticacao.LoginUserDto;
import Projeto_Padrao.Model.Dto.Autenticacao.RecoveryJwtTokenDto;
import Projeto_Padrao.Model.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    ///ENDPOINTS DE LOGIN
    @PostMapping(path = "/criar")
    public ResponseEntity<Void> CriarUsuario (@RequestBody CreateUserDto createUserDto) {
        authService.criarUsuario(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto){
        RecoveryJwtTokenDto token = authService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    ///ENDPOINT DE TESTE DE TOKEN
    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }
}

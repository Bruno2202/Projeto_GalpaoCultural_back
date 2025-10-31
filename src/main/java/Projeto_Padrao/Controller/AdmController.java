package Projeto_Padrao.Controller;

import Projeto_Padrao.Model.Dto.Autenticacao.CreateUserDto;
import Projeto_Padrao.Model.Dto.Autenticacao.LoginUserDto;
import Projeto_Padrao.Model.Dto.Autenticacao.RecoveryJwtTokenDto;
import Projeto_Padrao.Model.Dto.EmprestimosAtrasadosDTO;
import Projeto_Padrao.Model.Dto.VisualizarEmpDTO;
import Projeto_Padrao.Model.Service.AdmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adm")
public class AdmController {

    final private AdmService admService;

    public AdmController(AdmService admService) {
        this.admService = admService;
    }

    @GetMapping(path = "/todosEmprestimos")
    public ResponseEntity<List<VisualizarEmpDTO>> ListarEmprestimos() {
        return ResponseEntity.status(HttpStatus.OK).body(admService.ListarEmprestimos());
    }

    @GetMapping(path = "/atrasados")
    public ResponseEntity<List<EmprestimosAtrasadosDTO>> EmprestimosAtrasados(){
        return ResponseEntity.status(HttpStatus.OK).body(admService.EmprestimosAtrasados());
    }

    ///ENDPOINTS DE LOGIN
    @PostMapping(path = "criar")
    public ResponseEntity<Void> CriarUsuario (@RequestBody CreateUserDto createUserDto) {
        admService.criarUsuario(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto){
        RecoveryJwtTokenDto token = admService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    ///ENDPOINT DE TESTE DE TOKEN
    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }
}

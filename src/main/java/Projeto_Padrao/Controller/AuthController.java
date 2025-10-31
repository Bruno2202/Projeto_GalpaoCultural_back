package Projeto_Padrao.Controller;

import Projeto_Padrao.Model.Dto.LoginRequestDTO;
import Projeto_Padrao.Model.Entidade.Adm;
import Projeto_Padrao.Model.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<Adm> Login(@RequestBody LoginRequestDTO loginRequest) {
        Adm adm = authService.Login(loginRequest.email(), loginRequest.password());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(adm);
    }
}
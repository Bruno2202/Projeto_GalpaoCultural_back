package Projeto_Padrao.Controller;

import Projeto_Padrao.Model.Dto.EmprestimoDTO;
import Projeto_Padrao.Model.Dto.EmprestimosAtrasadosDTO;
import Projeto_Padrao.Model.Service.AdmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/adm")
public class AdmController {

    final private AdmService admService;

    public AdmController(AdmService admService) {
        this.admService = admService;
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoDTO>> ListarEmprestimos() {
        return ResponseEntity.status(HttpStatus.OK).body(admService.ListarEmprestimos());
    }

    @GetMapping(path = "/atrasados")
    public ResponseEntity<List<EmprestimosAtrasadosDTO>> EmprestimosAtrasados(){
        return ResponseEntity.status(HttpStatus.OK).body(admService.EmprestimosAtrasados());
    }
}

package Projeto_Padrao.Controller;

import Projeto_Padrao.Model.Dto.EmprestimoDTO;
import Projeto_Padrao.Model.Dto.VisualizarEmpDTO;
import Projeto_Padrao.Model.Entidade.Emprestimo;
import Projeto_Padrao.Model.Service.EmprestimoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/emprestimo")
public class EmprestimoController {

    final private EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoDTO>> ListarEmprestimos() {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.ListarEmprestimos());
    }

    @GetMapping(path = "/devolver")
    public ResponseEntity<List<VisualizarEmpDTO>> ListaDeEmprestimo(@RequestParam String celular) {
        return ResponseEntity.status(HttpStatus.OK).body(emprestimoService.ListaDeEmprestimos(celular));
    }

    @PostMapping(path = "/registrar")
    public ResponseEntity<Void> NovoRegistro(@RequestBody EmprestimoDTO emprestimo) {
        emprestimoService.AdicionarEmprestimo(emprestimo);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping(path ="/devolver")
    public ResponseEntity<Void> DevolverLivro(@RequestParam Long id){
        emprestimoService.DevolverLivro(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}

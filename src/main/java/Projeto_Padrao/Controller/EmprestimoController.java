package Projeto_Padrao.Controller;

import Projeto_Padrao.Model.Dto.EmprestimoDTO;
import Projeto_Padrao.Model.Dto.VisualizarEmpDTO;
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

    @GetMapping(path = "/devolver")
    public ResponseEntity<?> ListaDeEmprestimo(@RequestParam String celular) {
        System.out.println("Celular recebido: " + celular);
        List<VisualizarEmpDTO> lista = emprestimoService.ListaDeEmprestimos(celular);
        return ResponseEntity.ok(lista);
    }

    @PostMapping(path = "/registrar")
    public ResponseEntity<Void> NovoRegistro(@RequestBody EmprestimoDTO emprestimo) {
        emprestimoService.AdicionarEmprestimo(emprestimo);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping(path ="/devolucao")
    public ResponseEntity<Void> DevolverLivro(@RequestParam Long id){
        emprestimoService.DevolverLivro(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}

package Projeto_Padrao.Controller;

import Projeto_Padrao.Model.Dto.EmprestimoDTO;
import Projeto_Padrao.Model.Dto.VisualizarEmpDTO;
import Projeto_Padrao.Model.Entidade.Emprestimo;
import Projeto_Padrao.Model.Service.EmprestimoService;
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
    public List<VisualizarEmpDTO> listaDeEmprestimos(@RequestParam String celular) {
        return emprestimoService.listaDeEmprestimos(celular);
    }

    @PostMapping(path = "/registrar")
    public void NovoRegistro(@RequestBody EmprestimoDTO emprestimo) {
        emprestimoService.AdicionarEmprestimo(emprestimo);
    }

}

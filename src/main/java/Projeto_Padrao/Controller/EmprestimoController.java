package Projeto_Padrao.Controller;

import Projeto_Padrao.Model.Entidade.Emprestimo;
import Projeto_Padrao.Service.EmprestimoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/emprestimo")
public class EmprestimoController {

    final private EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public List<Emprestimo> buscarEmprestimos() {
        return emprestimoService.buscarEmprestimos();
    }
}

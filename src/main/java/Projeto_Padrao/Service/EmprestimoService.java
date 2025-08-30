package Projeto_Padrao.Service;

import Projeto_Padrao.Model.Entidade.Emprestimo;
import Projeto_Padrao.Model.Repository.EmprestimoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmprestimoService {

    final private EmprestimoRepository emprestimoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Emprestimo> buscarEmprestimos() {
        List<Emprestimo> lista = emprestimoRepository.findAll();

        return lista;
    }
}

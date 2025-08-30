package Projeto_Padrao.Model.Service;

import Projeto_Padrao.Model.Dto.EmprestimoDTO;
import Projeto_Padrao.Model.Entidade.Emprestimo;
import Projeto_Padrao.Model.Exception.DataNotFoundException;
import Projeto_Padrao.Model.Repository.EmprestimoRepository;
import org.springframework.stereotype.Service;

@Service
public class EmprestimoService {

    final private EmprestimoRepository emprestimoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    //LISTAR TODOS OS REGISTROS POR CELULAR
    //ADCIONAR REGISTROS
    public void AdicionarEmprestimo (EmprestimoDTO emprestimoNovo){
        try {
            emprestimoRepository.save(new Emprestimo(emprestimoNovo));
        }catch (Exception e){
            throw new DataNotFoundException("NÃO FOI POSSIVEL REGISTRAR ESSE EMPRESTIMO!");
        }

    }

}

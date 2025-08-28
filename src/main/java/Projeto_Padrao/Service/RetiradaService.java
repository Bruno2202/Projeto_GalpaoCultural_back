package Projeto_Padrao.Service;

import Projeto_Padrao.Model.Repository.EmprestimoRepository;
import org.springframework.stereotype.Service;

@Service
public class RetiradaService {

    final private EmprestimoRepository emprestimoRepository;

    public RetiradaService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }


}

package Projeto_Padrao.Model.Service;

import Projeto_Padrao.Model.Dto.EmprestimoDTO;
import Projeto_Padrao.Model.Dto.EmprestimosAtrasadosDTO;
import Projeto_Padrao.Model.Entidade.Emprestimo;
import Projeto_Padrao.Model.Exception.DataNotFoundException;
import Projeto_Padrao.Model.Repository.EmprestimoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service("AdmServicePricipal")
public class AdmService {

    final private EmprestimoRepository emprestimoRepository;

    public AdmService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    //LISTAR TODOS OS REGISTROS
    public List<EmprestimoDTO> ListarEmprestimos() {
        List<Emprestimo> lista = emprestimoRepository.findAll();

        if (lista.isEmpty()) {
            throw new DataNotFoundException("EMPRESTIMOS NÃO ENCONTRADOS");
        }

        return lista.stream().map(e -> new EmprestimoDTO(
                e.getCelular(),
                e.getNome(),
                e.getLivro(),
                e.getAutor(),
                e.getRetirada(),
                e.getDevolucao(),
                e.isDevolvido()
        )).toList();
    }

    public List<EmprestimosAtrasadosDTO> EmprestimosAtrasados() {
        try {
            return emprestimoRepository.findAll().stream()
                    .filter(e -> LocalDate.now().isAfter(e.getRetirada().plusMonths(1)))
                    .filter(e -> !e.isDevolvido())
                    .map(e -> new EmprestimosAtrasadosDTO(
                            e.getId(),
                            e.getCelular(),
                            e.getNome(),
                            e.getLivro(),
                            e.getAutor()
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataNotFoundException("NÃO EXISTEM EMPRESTIMOS ATRASADOS!");
        }
    }

}

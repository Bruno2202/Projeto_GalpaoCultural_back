package Projeto_Padrao.Model.Service;

import Projeto_Padrao.Model.Dto.EmprestimoDTO;
import Projeto_Padrao.Model.Dto.VisualizarEmpDTO;
import Projeto_Padrao.Model.Entidade.Emprestimo;
import Projeto_Padrao.Model.Exception.DataNotFoundException;
import Projeto_Padrao.Model.Repository.EmprestimoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("emprestimoServicePrincipal")
public class EmprestimoService {

    final private EmprestimoRepository emprestimoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    //LISTAR TODOS OS REGISTROS POR CELULAR
    public List<VisualizarEmpDTO> ListaDeEmprestimos(String celular) {
        List<Emprestimo> lista = emprestimoRepository.findAllByCelular(celular);
        if (lista.isEmpty()) {
            throw new DataNotFoundException("CELULAR NÃO ENCONTRADO!");
        }
        return lista.stream().filter(e -> !e.isDevolvido()).map(e -> new VisualizarEmpDTO(e.getId(), e.getNome(), e.getLivro(), e.getAutor())).toList();
    }

    //ADCIONAR REGISTROS
    public void AdicionarEmprestimo(EmprestimoDTO emprestimoNovo) {
        try {
            emprestimoRepository.save(new Emprestimo(emprestimoNovo));
        } catch (Exception e) {throw new DataNotFoundException("NÃO FOI POSSÍVEL REGISTRAR ESSE EMPRÉSTIMO!");}
    }

    //DEVOLVER LIVROS
    public void DevolverLivro(Long id) {
            Emprestimo registro = emprestimoRepository.findById(id).filter(e -> !e.isDevolvido()).orElseThrow(() -> new DataNotFoundException(id));
            registro.setDevolucao(LocalDate.now());
            registro.setDevolvido(true);
            emprestimoRepository.save(registro);
    }

}

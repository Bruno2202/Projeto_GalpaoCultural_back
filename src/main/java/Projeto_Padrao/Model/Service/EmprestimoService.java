package Projeto_Padrao.Model.Service;

import Projeto_Padrao.Model.Dto.EmprestimoDTO;
import Projeto_Padrao.Model.Dto.VisualizarEmpDTO;
import Projeto_Padrao.Model.Dto.VisualizarLivrosDTO;
import Projeto_Padrao.Model.Entidade.Emprestimo;
import Projeto_Padrao.Model.Exception.DataNotFoundException;
import Projeto_Padrao.Model.Repository.EmprestimoRepository;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;
import com.google.genai.Client;

import java.time.LocalDate;
import java.util.List;

@Service("emprestimoServicePrincipal")
public class EmprestimoService {

    final private EmprestimoRepository emprestimoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    //LISTAR TODOS OS REGISTROS **ADM**
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
            Emprestimo emprestimoRevisado = new Emprestimo(emprestimoNovo);
            emprestimoRevisado.setLivro(CorrigirNomes(emprestimoNovo.livro()));
            emprestimoRevisado.setAutor(CorrigirNomes(emprestimoNovo.autor()));
            //ARRUMAR O TEMPO DE EXECUÇÃO SOMENTE SE NECESSARIO!!

            
            emprestimoRepository.save(emprestimoRevisado);
        } catch (Exception e) {
            throw new DataNotFoundException("NÃO FOI POSSÍVEL REGISTRAR ESSE EMPRÉSTIMO!");
        }
    }

    //DEVOLVER LIVROS
    public void DevolverLivro(Long id) {
        Emprestimo registro = emprestimoRepository.findById(id).filter(e -> !e.isDevolvido()).orElseThrow(() -> new DataNotFoundException(id));
        registro.setDevolucao(LocalDate.now());
        registro.setDevolvido(true);
        emprestimoRepository.save(registro);
    }

    public String CorrigirNomes(String nome) {
        Client client = Client.builder().apiKey("AIzaSyDv6IvXb0Ku5bV0BO8GHm9J0ceE6zA1wHU").build();

        String prompt = """
                Você é uma inteligência artificial especializada em literatura.
                Sua função é corrigir e padronizar nomes de livros e autores,
                mesmo quando são digitados com erros ortográficos, variações ou abreviações.
                
                Regras:
                1. Sempre devolva a resposta em letras maiúsculas (CAPSLOCK).
                2. Quando for um livro, retorne o título mais conhecido e correto.
                   Exemplo: DON CASMURO -> DOM CASMURRO
                            ALNISTA -> O ALIENISTA
                3. Quando for um autor, retorne o nome correto e completo.
                   Exemplo: MAÇADO -> MACHADO DE ASSIS
                4. Pode acontecer de a entrada ser apenas um livro ou apenas um autor. Trate os dois casos.
                5. Se não conseguir identificar com clareza, tente sugerir a forma mais próxima e conhecida.
                
                Entrada: "%s"
                
                Saída esperada (apenas em String):
                """.formatted(nome);

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        prompt,
                        null
                );

        return response.text().trim();
    }
}

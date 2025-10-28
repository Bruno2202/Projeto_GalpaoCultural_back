package Projeto_Padrao.Model.Service;

import Projeto_Padrao.Model.Dto.EmprestimoDTO;
import Projeto_Padrao.Model.Dto.EmprestimosAtrasadosDTO;
import Projeto_Padrao.Model.Dto.VisualizarEmpDTO;
import Projeto_Padrao.Model.Entidade.Emprestimo;
import Projeto_Padrao.Model.Exception.DataNotFoundException;
import Projeto_Padrao.Model.Repository.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Value;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;
import com.google.genai.Client;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service("emprestimoServicePrincipal")
public class EmprestimoService {

    @Value("${API_KEY_GEMINI_HOMG}")
    private String apiKey;

    final private EmprestimoRepository emprestimoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<VisualizarEmpDTO> ListaDeEmprestimos(String celular) {
        List<Emprestimo> lista = emprestimoRepository.findAllByCelular(celular);
        if (lista.isEmpty()) {
            throw new DataNotFoundException("CELULAR NÃO ENCONTRADO!");
        }
        return lista.stream().filter(e -> !e.isDevolvido()).map(e -> new VisualizarEmpDTO(e.getId(), e.getNome(), e.getLivro(), e.getAutor())).toList();
    }

    public void AdicionarEmprestimo(EmprestimoDTO emprestimoNovo) {
        try {
            Emprestimo emprestimoRevisado = new Emprestimo(emprestimoNovo);
            emprestimoRevisado.setLivro(CorrigirNomes(emprestimoNovo.livro()));
            emprestimoRevisado.setAutor(CorrigirNomes(emprestimoNovo.autor()));

            emprestimoRepository.save(emprestimoRevisado);
        } catch (Exception e) {
            throw new DataNotFoundException("NÃO FOI POSSÍVEL REGISTRAR ESSE EMPRÉSTIMO!");
        }
    }

    public void DevolverLivro(Long id) {
        Emprestimo registro = emprestimoRepository.findById(id).filter(e -> !e.isDevolvido()).orElseThrow(() -> new DataNotFoundException(id));
        registro.setDevolucao(LocalDate.now());
        registro.setDevolvido(true);
        emprestimoRepository.save(registro);
    }

    public String CorrigirNomes(String nome) {
        Client client = Client.builder().apiKey(apiKey).build();

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

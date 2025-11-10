package Projeto_Padrao.Model.Service;

import Projeto_Padrao.Model.Dto.EmprestimoDTO;
import Projeto_Padrao.Model.Dto.EmprestimosAtrasadosDTO;
import Projeto_Padrao.Model.Dto.VisualizarEmpDTO;
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

    ///Listar todos os emprestimo pelo telefone
    public List<VisualizarEmpDTO> VerEmprestimos(String celular){
        List<Emprestimo> listaDeLivro = emprestimoRepository.findAllByCelularAndDevolvidoIsFalse(celular);
        return listaDeLivro.stream()
                .map(e -> new VisualizarEmpDTO(
                        e.getId(),
                        e.getNome(),
                        e.getLivro(),
                        e.getAutor()
                )).toList();
    }

    public void AdicionarEmprestimo(EmprestimoDTO emprestimoNovo) {
        try {
            Emprestimo emprestimoRevisado = new Emprestimo(emprestimoNovo);

            ///FAZER BUSCA SE O NOME DO LIVRO JÁ NÃO ESTÁ NO BANCO PARA NÃO UTILIZAR A IA
            Emprestimo buscarNome = emprestimoRepository.findByLivroContainingIgnoreCaseAndDevolvidoIsTrue(emprestimoNovo.livro());

            if(buscarNome == null) {
            emprestimoRevisado.setLivro(CorrigirNomes(emprestimoNovo.livro()));
            emprestimoRevisado.setAutor(CorrigirNomes(emprestimoNovo.autor()));
            }else{
                emprestimoRevisado.setLivro(buscarNome.getLivro());
                emprestimoRevisado.setAutor(buscarNome.getAutor());
            }

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
        Client client = Client.builder().apiKey("AIzaSyBrgt3QkxzO6lNm8xSx6ipFYyNRinRsYBk").build();

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

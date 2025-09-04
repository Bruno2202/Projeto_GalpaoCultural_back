package Projeto_Padrao.Model.Entidade;

import Projeto_Padrao.Model.Dto.EmprestimoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "public", name = "emprestimos")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CELULAR", nullable = false)
    private String celular;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "LIVRO", nullable = false)
    private String livro;

    @Column(name = "AUTOR", nullable = false)
    private String autor;

    @Column(name = "RETIRADA", nullable = false)
    private LocalDate retirada;

    @Column(name = "DEVOLUCAO")
    private LocalDate devolucao;

    @Column(name = "DEVOLVIDO", nullable = false)
    private boolean devolvido;

    public Emprestimo(EmprestimoDTO emprestimoNovo) {
        this.celular = emprestimoNovo.celular();
        this.nome = emprestimoNovo.nome();
        this.livro = emprestimoNovo.livro();
        this.autor = emprestimoNovo.autor();
        this.retirada = LocalDate.now();
        this.devolucao = null;
        this.devolvido = emprestimoNovo.devolvido();
    }
}

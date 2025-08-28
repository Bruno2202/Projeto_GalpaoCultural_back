package Projeto_Padrao.Model.Entidade;

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
@Table(schema = "public", name = "EMPRESTIMOS")
public class Emprestimo {

    @Id
    @Column(name = "CELULAR", nullable = false)
    String celular;

    @Column(name = "NOME", nullable = false)
    String nome;

    @Column(name = "LIVRO", nullable = false)
    String livro;

    @Column(name = "AUTOR", nullable = false)
    String autor;

    @Column(name = "EMPRESTIMO", nullable = false)
    LocalDate emprestimo;

    @Column(name = "DEVOLUCAO")
    LocalDate devolucao;

    @Column(name = "DEVOLVIDO", nullable = false)
    boolean devolvido;
}

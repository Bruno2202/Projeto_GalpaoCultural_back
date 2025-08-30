package Projeto_Padrao.Model.Repository;

import Projeto_Padrao.Model.Entidade.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findAllByCelular(String celular);
}

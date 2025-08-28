package Projeto_Padrao.Model.Repository;

import Projeto_Padrao.Model.Entidade.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, String> {
}

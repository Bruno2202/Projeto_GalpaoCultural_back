package Projeto_Padrao.Model.Repository;

import Projeto_Padrao.Model.Entidade.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmRepository extends JpaRepository<Administrador, Long> {

    Administrador findByEmail(String nome);
}

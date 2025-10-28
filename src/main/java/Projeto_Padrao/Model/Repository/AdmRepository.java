package Projeto_Padrao.Model.Repository;

import Projeto_Padrao.Model.Entidade.Adm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AdmRepository extends JpaRepository<Adm, UUID> {
    Adm findByEmail(String email);
    boolean existsByEmail(String email);
}

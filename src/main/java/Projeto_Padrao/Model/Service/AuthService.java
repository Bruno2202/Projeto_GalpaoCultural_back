package Projeto_Padrao.Model.Service;

import Projeto_Padrao.Model.Entidade.Adm;
import Projeto_Padrao.Model.Repository.AdmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AdmRepository admRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Adm Login(String email, String password) {
        Adm adm = admRepository.findByEmail(email);

//        if (adm == null || !passwordEncoder.matches(password, adm.getSenha())) {
        if (adm == null || password != adm.getSenha()) {
//            throw new InvalidCredentialsException("Email ou senha incorretos");
        }

        return adm;
    }
}
package Projeto_Padrao.Model.Security;

import Projeto_Padrao.Model.Entidade.Administrador;
import Projeto_Padrao.Model.Repository.AdmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdmRepository admRepository;

    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException{
        Administrador administrador = admRepository.findByEmail(nome);
        return new UserDetailsImpl(administrador);
    }
}

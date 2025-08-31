package Projeto_Padrao.Model.Exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String objeto) {
        super(objeto + "NÃO ENCONTRADO!");
    }

    public DataNotFoundException(Long id) {super ("ID:" + id + " NÃO ENCONTRADO!");}
}

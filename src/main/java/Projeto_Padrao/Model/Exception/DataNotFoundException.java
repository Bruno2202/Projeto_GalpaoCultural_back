package Projeto_Padrao.Model.Exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String objeto) {
        super(objeto + "NÃO ENCONTRADO!");
    }
}

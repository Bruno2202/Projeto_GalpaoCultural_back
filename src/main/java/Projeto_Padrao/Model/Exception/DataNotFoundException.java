package Projeto_Padrao.Model.Exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {super(message);}
    public DataNotFoundException(Long id) {super ("ID:" + id + " NÃO ENCONTRADO OU JÁ DEVOLVIDO!");}
}

package exception;

public class AnimalNaoEncontradoException extends RegraNegocioException{
    public AnimalNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}

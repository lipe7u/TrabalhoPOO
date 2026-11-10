package exception;

public class SolicitacaoNaoEncontradaException extends RegraNegocioException{
    public SolicitacaoNaoEncontradaException(String mensagem){
        super(mensagem);
    }
}


package service;

import model.Notificacao;
import model.Usuario;
import model.enums.TipoNotificacao;
import repository.BancoMemoria;
import exception.RegraNegocioException;

import java.util.List;

public class NotificacaoService {

    public Notificacao enviarNotificacao(
            Usuario destinatario,
            String mensagem,
            TipoNotificacao tipo
    ) {
        if (destinatario == null ||
                !BancoMemoria.getUsuarios().contains(destinatario)) {
            throw new RegraNegocioException(
                    "Destinatário não cadastrado no sistema."
            );
        }

        Notificacao notificacao = new Notificacao(
                destinatario,
                mensagem,
                tipo
        );

        BancoMemoria.getNotificacoes().add(notificacao);

        return notificacao;
    }

    public List<Notificacao> listarNotificacoesPorUsuario(
            int idUsuario
    ) {
        return BancoMemoria.getNotificacoes().stream()
                .filter(n -> n.getDestinatario().getId() == idUsuario)
                .toList();
    }

    public List<Notificacao> listarNotificacoesNaoLidas(
            int idUsuario
    ) {
        return BancoMemoria.getNotificacoes().stream()
                .filter(n -> n.getDestinatario().getId() == idUsuario)
                .filter(n -> !n.isLida())
                .toList();
    }

    public void marcarComoLida(int idNotificacao, Usuario usuario) {
        if (usuario == null ||
                !BancoMemoria.getUsuarios().contains(usuario)) {
            throw new RegraNegocioException(
                    "Usuário não cadastrado no sistema."
            );
        }

        Notificacao notificacao = BancoMemoria.getNotificacoes().stream()
                .filter(n -> n.getId() == idNotificacao)
                .findFirst()
                .orElseThrow(() -> new RegraNegocioException(
                        "Notificação não encontrada."
                ));

        if (notificacao.getDestinatario().getId() != usuario.getId()) {
            throw new RegraNegocioException(
                    "Esta notificação pertence a outro usuário."
            );
        }

        notificacao.marcarComoLida();
    }

    public long contarNotificacoesNaoLidas(int idUsuario) {
        return listarNotificacoesNaoLidas(idUsuario).size();
    }
}

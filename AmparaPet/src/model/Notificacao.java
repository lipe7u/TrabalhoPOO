
package model;

import model.enums.TipoNotificacao;
import java.time.LocalDateTime;

public class Notificacao {

    private static int proximoId = 1;

    private final int id;
    private final Usuario destinatario;
    private final String mensagem;
    private final TipoNotificacao tipo;
    private final LocalDateTime dataCriacao;
    private boolean lida;

    public Notificacao(
            Usuario destinatario,
            String mensagem,
            TipoNotificacao tipo
    ) {
        if (destinatario == null || tipo == null) {
            throw new IllegalArgumentException(
                    "Destinatário e tipo são obrigatórios."
            );
        }

        if (mensagem == null || mensagem.isBlank()) {
            throw new IllegalArgumentException(
                    "A mensagem da notificação é obrigatória."
            );
        }

        this.id = proximoId++;
        this.destinatario = destinatario;
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.dataCriacao = LocalDateTime.now();
        this.lida = false;
    }

    public int getId() {
        return id;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public TipoNotificacao getTipo() {
        return tipo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public boolean isLida() {
        return lida;
    }

    public void marcarComoLida() {
        this.lida = true;
    }

    @Override
    public String toString() {
        return "Notificação: " + id
                + " | Destinatário: " + destinatario.getNome()
                + " | Tipo: " + tipo
                + " | Mensagem: " + mensagem
                + " | Data: " + dataCriacao
                + " | Lida: " + lida;
    }
}

package src.sistema;

import src.models.Usuario;

public class Sistema {
  public Usuario usuarioLogado;
  public static Sistema instancia = null;

  private String m_message = "=============\n\nINÍCIO\n\n1) Criação de perguntas\n2) Consultar/responder perguntas \n3) Notificações: 0\n\n0) Sair\n\nOpção: ";

  public static Sistema GetInstancia() {
    if (instancia == null) {
      instancia = new Sistema();
    }
    return instancia;
  }

  public Sistema() {
  }

  public static void Run(Usuario usuario) {
    EntradaSistema entradas = new EntradaSistema(usuario);
    GetInstancia().usuarioLogado = usuario;
    while (entradas.Rodar()) {
      entradas.AnalisarComandos(GetInstancia().m_message);
    }
  }
}

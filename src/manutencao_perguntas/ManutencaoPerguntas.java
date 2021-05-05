package src.manutencao_perguntas;

import src.models.Usuario;
import src.util.GerenciadosDeEntradas;

public class ManutencaoPerguntas {
  private static ManutencaoPerguntas m_instance;
  public Usuario usuario;
  private GerenciadosDeEntradas entradas;


  public static ManutencaoPerguntas getInstance() {
    if (m_instance == null)
      m_instance = new ManutencaoPerguntas();
    return m_instance;
  }

  public static void Run(Usuario usuario) {
    EntradasPerguntas manager = new EntradasPerguntas(usuario);
    getInstance().usuario = usuario;
    while (manager.Rodar()) {
      manager.AnalisarComandos(manager.message);
    }
  }
}

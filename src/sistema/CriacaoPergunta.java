package src.sistema;

import src.interfaces.Comando;
import src.manutencao_perguntas.ManutencaoPerguntas;
import src.models.Usuario;

public class CriacaoPergunta implements Comando {
  Usuario usuario;

  public CriacaoPergunta(Usuario user) {
    usuario = user;
  }

  @Override
  public boolean run() {
    ManutencaoPerguntas.Run(usuario);
    return true;
  }

}

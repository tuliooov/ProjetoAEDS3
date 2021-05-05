package src.manutencao_perguntas;

import src.interfaces.Comando;
import src.util.ConsoleView;

public class Retornar implements Comando {

  @Override
  public boolean run() {
    ConsoleView.PrintBaseMessage("Pressione qualquer tecla para continuar...");
    return false;
  }
}

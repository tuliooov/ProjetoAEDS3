package src.sistema;

import src.interfaces.Comando;
import src.util.ConsoleView;

public class Sair implements Comando {

  @Override
  public boolean run() {
    ConsoleView.PrintSuccessMessage("\nAguarde enquanto estamos desconectando seu usuário.\n");
    return false;
  }

}

package src.plataforma;

import src.interfaces.Comando;
import src.util.ConsoleView;

public class Sair implements Comando {

  @Override
  public boolean run() {
    ConsoleView.PrintSuccessMessage("\nAguarde enquanto o programa está sendo finalizado.\n");
    return false;
  }

}

package src.abstracts;

import src.interfaces.Comando;

public class Entrada {
  public String entrada = "";
  public Comando comando;

  public Entrada(String entrada, Comando comando) {
    this.entrada = entrada;
    this.comando = comando;
  }
}

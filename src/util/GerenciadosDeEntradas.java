package src.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import src.abstracts.Entrada;
import src.interfaces.Comando;

public class GerenciadosDeEntradas {
  private HashMap<String, Comando> m_commands;
  private Scanner m_reader;
  private boolean m_shouldKeepOnRunning;

  /**
   * API que serve como interface entre os comandos na tela e uma tabela hash de
   * funções responsáveis para tratar os comandos.
   * 
   */
  public GerenciadosDeEntradas(LinkedList<Entrada> m_entries) {
    m_commands = new HashMap<String, Comando>();
    Entrada e = null;
    m_reader = new Scanner(System.in);
    m_shouldKeepOnRunning = true;
    for (int i = 0; i < m_entries.size(); i++) {
      e = m_entries.get(i);
      m_commands.put(e.entrada, e.comando);
    }
  }

  public void AnalisarComandos(String mensagem) {
    this.v_CheckCommands(mensagem);
  }

  public boolean RodarDenovo() {
    return m_shouldKeepOnRunning;
  }

  private void v_CheckCommands(String p_message) {
    if (p_message != null) {
      ConsoleView.PrintBaseMessage(p_message + "\n");
    }
    String p_command = m_reader.nextLine();
    if (m_commands.containsKey(p_command)) {
      m_shouldKeepOnRunning = m_commands.get(p_command).run();
      ConsoleView.PrintBaseMessage("\n");
    } else {
      ConsoleView.PrintWarningMessage("\nO comando inserido não é possível de ser interpretado. Tente novamente. \n");
    }
  }
}

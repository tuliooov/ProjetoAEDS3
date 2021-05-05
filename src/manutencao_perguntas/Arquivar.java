package src.manutencao_perguntas;

import java.util.LinkedList;
import java.util.Scanner;

import src.interfaces.Comando;
import src.models.Pergunta;
import src.util.CRUD;
import src.util.ConsoleView;
import src.util.Controlador;
import src.util.EnumeradorCrud;

public class Arquivar implements Comando {
  int id;
  Scanner leitor;

  public Arquivar(int _id, Scanner _leitor) {
    this.id = _id;
    this.leitor = _leitor;
  }

  @Override
  public boolean run() {
    try {
      Pergunta t = null;
      LinkedList<Pergunta> perguntas = Controlador.PegarInstancia().ReadAll(EnumeradorCrud.PERGUNTAS, id);
      
      if (perguntas != null) {
        for (int i = 0; i < perguntas.size(); i++) {
          if(perguntas.get(i).getAtiva() == 1){
            ConsoleView.PrintBaseMessage(perguntas.get(i).getID() + " | " + perguntas.get(i).getPergunta() + "\n");
          }
        }
        
        ConsoleView.PrintBaseMessage("Qual o id da pergunta que você gostaria de alterar?\n");
        int pergunta = leitor.nextInt();
        
        for (int i = 0; i < perguntas.size(); i++) {
          if (perguntas.get(i).getID() == pergunta) {
            t = perguntas.get(i);
          }
        }


        if (t != null) {
          Pergunta perguntaOb = (Pergunta) Controlador.PegarInstancia().Read(EnumeradorCrud.PERGUNTAS, t.getID());
          perguntaOb.setAtiva(false);
          boolean b = Controlador.PegarInstancia().Update(EnumeradorCrud.PERGUNTAS, perguntaOb);
          if (b) {
            ConsoleView.PrintSuccessMessage("Pergunta arquivada com sucesso");
          } else {
            ConsoleView.PrintErrorMessage("Não foi possível alterar a pergunta.");
          }
          ConsoleView.PrintBaseMessage("\n");
        } else {
          ConsoleView.PrintWarningMessage("Não foi possível arquivar a pergunta, pois o id provido não existe.\n");
        }
      } else {
        ConsoleView.PrintWarningMessage("Não existem perguntas cadastradas para este usuário.\n");
      }
    } catch (Exception e) {
      ConsoleView.PrintErrorMessage("Não foi possível arquivar a pergunta.\n");
    }
    return true;
  }
}

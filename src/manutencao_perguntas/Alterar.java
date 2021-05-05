package src.manutencao_perguntas;
import src.models.Pergunta;

import java.util.LinkedList;
import java.util.Scanner;

import src.interfaces.Comando;
import src.util.CRUD;
import src.util.ConsoleView;
import src.util.Controlador;
import src.util.EnumeradorCrud;

public class Alterar implements Comando {
  int id;
  Scanner leitor;

  public Alterar(int _id, Scanner _leitor) {
    this.id = _id;
    this.leitor = _leitor;
  }

  @Override
  public boolean run() {
    try {
      Pergunta t = null;
      LinkedList<Pergunta> perguntas = Controlador.PegarInstancia().ReadAll(EnumeradorCrud.PERGUNTAS, ManutencaoPerguntas.getInstance().usuario.getID());

      if (perguntas != null) {
        for (int i = 0; i < perguntas.size(); i++) {
          ConsoleView.PrintBaseMessage(perguntas.get(i).getID() + " | " + perguntas.get(i).getPergunta() + "\n");
          // if (t != null && perguntas.get(i).getID() == p) {
          //   t = perguntas.get(i);
          // }
        }

        
        ConsoleView.PrintBaseMessage("Qual o id da pergunta que você gostaria de alterar?\n");
        int p = leitor.nextInt();

        for (int i = 0; i < perguntas.size(); i++) {
          if (perguntas.get(i).getID() == p) {
            t = perguntas.get(i);
            break;
          }
        }

        
        if (t != null) {

          Pergunta perguntaOb = (Pergunta) Controlador.PegarInstancia().Read(EnumeradorCrud.PERGUNTAS , t.getID());

          ConsoleView.PrintBaseMessage("Qual pergunta você gostaria de incluir?\n");
          String perguntaIncluir = leitor.nextLine();
          perguntaOb.setPergunta(perguntaIncluir);
          boolean b = Controlador.PegarInstancia().Update(EnumeradorCrud.PERGUNTAS, perguntaOb);
          if (b) {
            ConsoleView.PrintSuccessMessage("Pergunta alterada com sucesso");
          } else {
            ConsoleView.PrintErrorMessage("Não foi possível alterar a pergunta.");
          }
          ConsoleView.PrintBaseMessage("\n");
        } else {
          ConsoleView.PrintWarningMessage("Não foi possível alterar a pergunta, pois o id provido não existe.\n");
        }
      } else {
        ConsoleView.PrintWarningMessage("Não existem perguntas cadastradas para este usuário.\n");
      }
    } catch (Exception e) {
      ConsoleView.PrintErrorMessage("Não foi possível alterar a pergunta.\n");
    }
    return true;
  }
}

package src.manutencao_perguntas;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Scanner;

import src.interfaces.Comando;
import src.models.Pergunta;
import src.util.CRUD;
import src.util.ConsoleView;
import src.util.Controlador;
import src.util.EnumeradorCrud;

public class Listagem implements Comando {
  int id;
  Scanner leitor;

  public Listagem(int _id, Scanner _leitor) {
    id = _id;
    this.leitor = _leitor;
  }

  @Override
  public boolean run() {
    try {
      Pergunta t = null;
      LinkedList<Pergunta> perguntas = Controlador.PegarInstancia().ReadAll(EnumeradorCrud.PERGUNTAS, ManutencaoPerguntas.getInstance().usuario.getID());
      String mensagem = null;
      if (perguntas != null) {
        ConsoleView.PrintBaseMessage("MINHAS PERGUNTAS: \n");
        for (int i = 0; i < perguntas.size(); i++) {
          t = perguntas.get(i);
          
          if(perguntas.get(i).getAtiva() == 1){
            ConsoleView.PrintBaseMessage(t.getPergunta() + ("\n"));
          }else{
            ConsoleView.PrintBaseMessage("(Arquivada) "+t.getPergunta() + ("\n"));
          }
          // SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
          // mensagem += (i + 1) + ". " + ((t.isAtiva() == true) ? "(Arquivada)" : "") + "\n"
          //     + "\n" + t.getPergunta();
          // + ft.format(new Date(t.getCriacao()) + "\n" + t.getPergunta());
        }
        ConsoleView.PrintBaseMessage("Pressione qualquer tecla para continuar...");
        leitor.nextLine();
      } else {
        ConsoleView.PrintWarningMessage("O usuário não possui nenhuma mensagem no sistema");
      }
    } catch (Exception e) {
      ConsoleView.PrintWarningMessage("Erro ao listar as perguntas do usuário");
    }
    return true;
  }
}

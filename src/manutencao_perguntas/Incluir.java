package src.manutencao_perguntas;

import java.util.Scanner;

import src.interfaces.Comando;
import src.models.Pergunta;
import src.util.CRUD;
import src.util.ConsoleView;
import src.util.Controlador;
import src.util.EnumeradorCrud;

public class Incluir implements Comando {
  int id;
  Scanner leitor;

  public Incluir(int _id, Scanner l) {
    leitor = l;
    id = _id;
  }

  @Override
  public boolean run() {
    try {
      ConsoleView.PrintBaseMessage("Qual pergunta você gostaria de incluir?\n");
      String pergunta = leitor.nextLine();
      
      Pergunta p = new Pergunta();
      p.setPergunta(pergunta);

      System.out.println("IDUSUARIO>" + ManutencaoPerguntas.getInstance().usuario.getID());

      p.setIdUsuario(ManutencaoPerguntas.getInstance().usuario.getID()); 

      int id = Controlador.PegarInstancia().Create(EnumeradorCrud.PERGUNTAS, p);

      System.out.println("PERGUNTA" + p);
      System.out.println("ID PERGUNT INSERIDA" + id);
      if (id != -1) {
        ConsoleView.PrintSuccessMessage("Pergunta inserida com sucesso");
      } else {
        ConsoleView.PrintErrorMessage("Não foi possível inserir a pergunta.");
      }
      ConsoleView.PrintBaseMessage("\n");
    } catch (Exception e) {
      ConsoleView.PrintBaseMessage("Não foi possível fazer a inclusão da pergunta\n");
    }
    return true;
  }
}

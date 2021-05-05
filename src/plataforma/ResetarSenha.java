package src.plataforma;

import java.util.Scanner;

import src.interfaces.Comando;
import src.models.Usuario;
import src.util.CRUD;
import src.util.ConsoleView;
import src.util.Controlador;
import src.util.EnumeradorCrud;

public class ResetarSenha implements Comando {
  private Scanner leitor;

  public ResetarSenha() {
    leitor = new Scanner(System.in);
  }

  @Override
  public boolean run() {
    try {
      Usuario newUserPassword = new Usuario();
      ConsoleView.PrintBaseMessage("Digite seu email:\n");
      newUserPassword.setEmail(leitor.nextLine());
      ConsoleView.PrintBaseMessage("Digite sua nova senha:\n");
      String senhaNova = leitor.nextLine();
      newUserPassword.setSenha(senhaNova);
      // Controlador vem aqui  
      Usuario us = (Usuario) Controlador.PegarInstancia().Read(EnumeradorCrud.USUARIOS, newUserPassword.getEmail().hashCode());

      // System.out.println("encontrei usuario"+us);

      if (us.getEmail().equals(newUserPassword.getEmail())) {
        ConsoleView.PrintSuccessMessage("\nEncontramos seu email.!\n");
        us.setSenha(senhaNova);
        us.setID(us.chaveSecundaria());
        if (Controlador.PegarInstancia().Update(EnumeradorCrud.USUARIOS, us)) {
          ConsoleView.PrintSuccessMessage("\nSucesso! Senha atualizada.\n");
        } else {
          ConsoleView.PrintErrorMessage("\nFalha! Não foi possivel atualizar sua senha.\n");
        }
      } else {
        ConsoleView.PrintErrorMessage("\nFalha! Não foi possivel cadastrar seu usuário.!\n" + us);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }

}

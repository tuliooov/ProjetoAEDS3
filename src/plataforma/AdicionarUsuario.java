package src.plataforma;

import java.util.Scanner;

import src.interfaces.Comando;
import src.models.Usuario;
import src.util.CRUD;
import src.util.ConsoleView;
import src.util.Controlador;
import src.util.EnumeradorCrud;

public class AdicionarUsuario implements Comando {
  private Scanner leitor;

  public AdicionarUsuario() {
    leitor = new Scanner(System.in);
  }

  @Override
  public boolean run() {
    try {
      Usuario newUser = new Usuario();
      ConsoleView.PrintBaseMessage("Digite seu nome:\n");
      newUser.setNome(leitor.nextLine());

      ConsoleView.PrintBaseMessage("Digite seu email:\n");
      String email = leitor.nextLine();
      newUser.setEmail(email);
      newUser.setChaveSecundaria(email.hashCode());

      ConsoleView.PrintBaseMessage("Digite sua senha:\n");
      newUser.setSenha(leitor.nextLine());

      Usuario us = (Usuario) Controlador.PegarInstancia().Read( EnumeradorCrud.USUARIOS, email.hashCode());
      
      // System.out.println("tem usuario com email> "+us);

      if (us != null && us.getEmail().equals(newUser.getEmail())) {
        ConsoleView.PrintWarningMessage("\nFalha! Seu email já esta cadastrado.\n");
      } else if (
        Controlador.PegarInstancia().Create2(EnumeradorCrud.USUARIOS, newUser) == -1) {
        ConsoleView.PrintErrorMessage("\nFalha! Não foi possivel cadastrar seu usuário.!\n");
      } else {
        ConsoleView.PrintSuccessMessage("\nSucesso! " + newUser + " cadastrado(a).\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }

}

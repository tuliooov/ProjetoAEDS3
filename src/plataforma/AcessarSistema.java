package src.plataforma;

import java.util.Scanner;

import src.interfaces.Comando;
import src.models.Usuario;
import src.sistema.Sistema;
import src.util.CRUD;
import src.util.ConsoleView;
import src.util.ValidacaoUsuario;
import src.util.Controlador;
import src.util.EnumeradorCrud;

public class AcessarSistema implements Comando {
  private Scanner leitor;

  public AcessarSistema() {
    this.leitor = new Scanner(System.in);
  }

  @Override
  public boolean run() {
    boolean validate = false;
    while (!validate) {
      ConsoleView.PrintWarningMessage("\nVocê está acessando o sistema aguarde ...\n\n");
      ConsoleView.PrintBaseMessage("Digite seu email\n");
      String email = leitor.nextLine();
      ConsoleView.PrintBaseMessage("\nDigite sua senha\n");
      String senha = leitor.nextLine();
      ValidacaoUsuario validacaoUsuario = validateUser(email, senha);
      if (validacaoUsuario != null && validacaoUsuario.status) {
        ConsoleView
            .PrintSuccessMessage("\nSeja bem vindo " + validacaoUsuario.usuario.getNome().toUpperCase() + "\n\n");
        // m_message = "";
        validate = true;
        Sistema.Run(validacaoUsuario.usuario);
      } else {
        ConsoleView.PrintErrorMessage("\nEmail e senha não encontrado!\n");
      }
    }
    return true;
  }

  private ValidacaoUsuario validateUser(String email, String senha) {
    boolean validation = false;
    Usuario userLogin = null;
    try {
      userLogin = new Usuario(email, senha);
      userLogin = (Usuario) Controlador.PegarInstancia().Read(EnumeradorCrud.USUARIOS, email.hashCode());
      if (userLogin != null && userLogin.getID() != -1 && (userLogin.getSenha()).equals(Integer.toString(senha.hashCode()))) {
        validation = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
      validation = false;
    }
    ValidacaoUsuario validacaoUsuario = new ValidacaoUsuario();
    validacaoUsuario.status = validation;
    validacaoUsuario.usuario = userLogin;
    return validacaoUsuario;
  }
}

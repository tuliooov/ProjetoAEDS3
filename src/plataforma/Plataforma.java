package src.plataforma;

public class Plataforma {
  EntradaPlataforma entradas;

  private String mensagem = "=============\n  \nACESSO\n\n1 Acesso ao sistema\n2) Novo usuário (primeiro acesso)\n3) Recuperar Senha\n0) Sair\n\nOpção: ";

  public Plataforma() {
    entradas = new EntradaPlataforma();
  }

  public void Run() {
    while (entradas.Rodar()) {
      entradas.AnalisarComandos(mensagem);
    }
  }
}

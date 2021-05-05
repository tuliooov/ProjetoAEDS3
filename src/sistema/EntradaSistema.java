package src.sistema;

import java.util.LinkedList;

import src.abstracts.Entrada;
import src.interfaces.Entradas;
import src.util.GerenciadosDeEntradas;
import src.models.Usuario;

public class EntradaSistema implements Entradas {
  private GerenciadosDeEntradas entradas;

  public EntradaSistema(Usuario u) {
    LinkedList<Entrada> lista = new LinkedList<Entrada>();
    lista.add(new Entrada("0", new Sair()));
    lista.add(new Entrada("1", new CriacaoPergunta(u)));
    entradas = new GerenciadosDeEntradas(lista);
  }

  @Override
  public void AnalisarComandos(String mensagem) {
    entradas.AnalisarComandos(mensagem);
  }

  @Override
  public boolean Rodar() {
    return entradas.RodarDenovo();
  }

}

package src.plataforma;

import java.util.LinkedList;

import src.abstracts.Entrada;
import src.interfaces.Entradas;
import src.util.GerenciadosDeEntradas;

public class EntradaPlataforma implements Entradas {

  private GerenciadosDeEntradas entradas;

  public EntradaPlataforma() {
    LinkedList<Entrada> lista = new LinkedList<Entrada>();
    lista.add(new Entrada("0", new Sair()));
    lista.add(new Entrada("1", new AcessarSistema()));
    lista.add(new Entrada("2", new AdicionarUsuario()));
    lista.add(new Entrada("3", new ResetarSenha()));
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

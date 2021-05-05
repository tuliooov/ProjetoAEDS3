package src.manutencao_perguntas;

import java.util.LinkedList;
import java.util.Scanner;

import src.models.Usuario;
import src.util.GerenciadosDeEntradas;
import src.abstracts.Entrada;
import src.interfaces.Entradas;

public class EntradasPerguntas implements Entradas {

  private GerenciadosDeEntradas entradas;
  private Scanner m_reader;
  public Usuario user;

  public String message = "PERGUNTAS 1.0\n=============\n\nINÍCIO > CRIAÇÃO DE PERGUNTAS\n\n 1)Listar\n 2)Incluir\n 3)Alterar\n 4)Arquivar\n\n 0)Retornar ao  menu anterior\n\n Opção:";

  public EntradasPerguntas(Usuario u) {
    user = u;
    m_reader = new Scanner(System.in);
    LinkedList<Entrada> lista = new LinkedList<Entrada>();
    lista.add(new Entrada("1", new Listagem(user.getID(), m_reader)));
    lista.add(new Entrada("2", new Incluir(user.getID(), m_reader))); // create no crud perguntas
    lista.add(new Entrada("3", new Alterar(user.getID(), m_reader))); // update no crud perguntas
    lista.add(new Entrada("4", new Arquivar(user.getID(), m_reader))); // update no crud perguntas
    lista.add(new Entrada("0", new Retornar())); 
    entradas = new GerenciadosDeEntradas(lista);
  }

  public void AnalisarComandos(String p_message) {
    entradas.AnalisarComandos(p_message);
  }

  public boolean Rodar() {
    return entradas.RodarDenovo();
  }

}

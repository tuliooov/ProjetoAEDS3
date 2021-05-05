package src.util;

import src.interfaces.Registro;
import src.models.Usuario;
import src.models.Pergunta;
import java.io.IOException;
import java.util.LinkedList;
import src.util.EnumeradorCrud;

public class Controlador<T extends Registro> {
  private static Controlador instancia = null;
  private CRUD<Usuario> crudUsuario;
  private CRUD<Pergunta> crudPergunta;
  //private ArvoreBMais_Int_Int treeUsuario = new ArvoreBMais_Int_Int (20, "dados/PerguntasUsuarios.idx");

  public static Controlador PegarInstancia() throws Exception{
    if (instancia == null) {
      instancia = new Controlador();
    }
    return instancia;
  }

  public Controlador() throws Exception{
    crudUsuario = new CRUD<>(Usuario.class.getConstructor(), "usuarios");
    crudPergunta = new CRUD<>(Pergunta.class.getConstructor(), "perguntas");
  }

  public int Create( EnumeradorCrud enume, T objeto) throws Exception {
    try{
      return PegarCrudPorTipoDeClasse(enume).create(objeto);
    }catch(IOException e){
      return -1;
    }
  }
  
  public int Create2( EnumeradorCrud enume, T objeto) throws Exception {
    try{
      return PegarCrudPorTipoDeClasse(enume).create2(objeto);
    }catch(IOException e){
      return -1;
    }
  }

  public boolean Delete(EnumeradorCrud enume, int id) throws Exception {
    try{
      return PegarCrudPorTipoDeClasse(enume).delete(id);
    }catch(IOException e){
      return false;
    }
  }

  public T Read(EnumeradorCrud enume, int id) throws Exception {
    try{
      return PegarCrudPorTipoDeClasse(enume).read(id);
    }catch(IOException e){
      return null;
    }
  }

  // public T Read(EnumeradorCrud enume, String chaveSecundaria) throws Exception {
  //   try{
  //     return PegarCrudPorTipoDeClasse(enume).read(chaveSecundaria);
  //   }catch(IOException e){
  //     return null;
  //   }
  // }

  public LinkedList<T> ReadAll(EnumeradorCrud enume, int idUsuario) throws Exception {
    try{
      // System.out.println("idusuario> "+ idUsuario);
      LinkedList<T> lista = PegarCrudPorTipoDeClasse(enume).readAll(idUsuario);

      // System.out.println("\n\nlistatamanho> " + lista.size());
      // for(int i = 0; i < lista.size(); i++){
        // System.out.println("\n\nlista> " + lista.get(i));
      // }
      return lista;

    }catch(Exception e){
      return null;
    }
  }

  public boolean Update( EnumeradorCrud enume, T objeto) throws Exception {
    try{
      return PegarCrudPorTipoDeClasse(enume).update(objeto);
    }catch(IOException e){
      return false;
    }
  }

  private CRUD<T> PegarCrudPorTipoDeClasse(EnumeradorCrud enume) throws Exception {
    try{
      switch (enume){
        case USUARIOS:
        return PegarInstancia().crudUsuario;
        case PERGUNTAS:
        return PegarInstancia().crudPergunta;
        default :
        return null;
      }
    }catch(IOException e){
      return null;
    }
    
  }

}
package src.util;
import java.io.*;
import src.interfaces.Registro;
import java.lang.reflect.Constructor;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.LinkedList;

public class CRUD<T extends Registro> {

  Constructor<T> construtor;
  String nomeDoArquivo;
  RandomAccessFile arquivo;
  HashExtensivel indexDireto;
  // ArvoreBMais indexIndireto;
  ArvoreBMais_ChaveComposta_Int_Int indexIndireto;

  /**
   * Construtor CRUD genérico
   * 
   * @param construtor    Construtor da classe de arquivos
   * @param nomeDoArquivo
   * @throws IOException
   */
  public CRUD(Constructor<T> construtor, String nomeDoArquivo) throws IOException {
    this.nomeDoArquivo = nomeDoArquivo;
    this.construtor = construtor;
    try {
      arquivo = new RandomAccessFile("database/" + nomeDoArquivo +"/"+ nomeDoArquivo + ".db", "rw");
      indexDireto = new HashExtensivel(4, "database/" + nomeDoArquivo +"/"+ nomeDoArquivo+ ".diretorio.idx","database/" + nomeDoArquivo+"/"+nomeDoArquivo + ".cestos.idx");
      // indexIndireto = new ArvoreBMais(5, "database/" + nomeDoArquivo+"/"+nomeDoArquivo+ ".arvorebmais.idx");
      indexIndireto = new ArvoreBMais_ChaveComposta_Int_Int(5, "database/" + nomeDoArquivo+"/"+nomeDoArquivo+ ".arvorebmais.idx");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    if (arquivo.length() < 4) {
      arquivo.writeInt(0); // escreve o novo cabeçalho
    }
  }

  /**
   * Escreve o objeto no final do arquivo
   * 
   * @param objeto Objeto que será inserido no arquivo
   * @return (int) Id do Objeto
   * @throws IOException
   */
  public int create(T objeto) throws IOException {

    // System.out.println("criar objeto" + objeto);
    arquivo.seek(0); // inicio do arquivo
    int id = arquivo.readInt() + 1; // leitura do ultimo id no cabeçalho
    objeto.setID(id);

    try {

      long pos = arquivo.length();
      arquivo.seek(pos); // vai para o final do arquivo

      byte ba[] = objeto.toByteArray(); // array de bytes (registro)
      arquivo.writeByte(0);
      arquivo.writeInt(ba.length); // escreve tamanho do registro
      arquivo.write(ba); // escreve registro

      arquivo.seek(0); // volta pro inicio do arquivo
      arquivo.writeInt(id); // atualiza o cabeçalho

      indexDireto.create(objeto.getID(), pos); // atualiza indice direto [ID, posição]
      indexIndireto.create(objeto.chaveSecundaria(), objeto.getID()); // atualiza indice indireto [chave secundaria, ID]
      // bMais.create(objeto.getID(),(objeto.chaveSecundaria().hashCode() & Integer.MAX_VALUE));

    } catch (Exception e) {
      //System.out.println(e.getMessage());
    }

    return id;
  }


  public int create2(T objeto) throws IOException {

    // System.out.println("criar objeto" + objeto);
    arquivo.seek(0); // inicio do arquivo
    int id = arquivo.readInt() + 1; // leitura do ultimo id no cabeçalho
    objeto.setID(id);

    try {

      long pos = arquivo.length();
      arquivo.seek(pos); // vai para o final do arquivo

      byte ba[] = objeto.toByteArray(); // array de bytes (registro)
      arquivo.writeByte(0);
      arquivo.writeInt(ba.length); // escreve tamanho do registro
      arquivo.write(ba); // escreve registro

      arquivo.seek(0); // volta pro inicio do arquivo
      arquivo.writeInt(id); // atualiza o cabeçalho

      indexDireto.create(objeto.chaveSecundaria(), pos); // atualiza indice direto [ID, posição]
      indexIndireto.create(objeto.chaveSecundaria(), objeto.getID()); // atualiza indice indireto [chave secundaria, ID]
      // bMais.create(objeto.getID(),(objeto.chaveSecundaria().hashCode() & Integer.MAX_VALUE));

    } catch (Exception e) {
      //System.out.println(e.getMessage());
    }

    return id;
  }

  

  public LinkedList<T> readAll(int idUsuario) throws Exception {
    LinkedList<T> lista = new LinkedList<T>();
    try{
      T objeto = null; 

      int[] lido = indexIndireto.read(idUsuario);

      // System.out.println("lido>"+lido.length);
      for(int i = 0; i < lido.length; i++){
        objeto = construtor.newInstance();
        objeto = this.read(lido[i]);
        lista.add(objeto);
      }
    } catch(Exception e){
      lista = null;
    } finally{
      return lista;
    }
  }

  /**
   * Procura um registro através do ID
   * 
   * @param idChave Id de busca
   * @return (T) Objeto buscado
   * @throws Exception
   */

  public T read(int idChave) throws Exception {
    T objeto = construtor.newInstance(); // Cria objeto

    try {
      long pos = indexDireto.read(idChave); // posição do objeto

      if (pos == -1) {
        objeto = null; // caso não tenha encontrado o registro
        return objeto;
      }


      arquivo.seek(pos); // vai para a posição no arquivo
      byte lapide = arquivo.readByte(); // leitura da lápide

      if (lapide == 1) {
        return null;
      }

      int tamCampo = arquivo.readInt(); // Leitura do tamanho do registro
      byte[] ba = new byte[tamCampo];
      arquivo.read(ba); // leitura do registro em bytes
      objeto.fromByteArray(ba); // criação do objeto

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return objeto;
  }

  /**
   * Procura um registro dada a Chave secundaria dele
   * 
   * @param chaveSecundaria String que será buscada no Index
   * @return objeto criado
   * @throws Exception Se houver algum erro na busca
   */
  // public T read(String chaveSecundaria) throws Exception {
  //   try {
  //     int ID = indexIndireto.read(chaveSecundaria);
  //     if (ID != -1)
  //       return read(ID);
  //   } catch (Exception e) {
  //   }

  //   return null;
  // }

  /**
   * Atualiza os dados do registro
   * 
   * @param objeto Objeto que será atualizado
   * @return (boolean) Booleano indicando sucesso da operação
   * @throws IOException
   */
  public boolean update(T objetoNovo) throws IOException {
    boolean success = false; // controle
    // System.out.println("objetoNovo> "+ objetoNovo);
    try {
      long pos = indexDireto.read(objetoNovo.getID()); // posição de atualização

      if (pos != -1) {
        arquivo.seek(pos); // vai para posição no arquivo

        byte lapide = arquivo.readByte(); // leitura da lápide
        int tamAntigo = arquivo.readInt(); // leitura do tamanho do registro antigo
        byte[] baAntigo = new byte[tamAntigo];
        arquivo.read(baAntigo); // leitura do registro em bytes

        T objetoAntigo = construtor.newInstance();
        objetoAntigo.fromByteArray(baAntigo); // criação do objeto

        byte baNovo[] = objetoNovo.toByteArray();
        int tamNovo = baNovo.length; // tamanho do novo registro

        if (tamAntigo < tamNovo) { // Verifica se o tamanho do antigo é menor do que o do novo registro
          arquivo.seek(pos);
          arquivo.writeByte(1);

          pos = arquivo.length();
          arquivo.seek(pos); // vai para o final do arquivo

          // byte ba[] = objetoNovo.toByteArray(); // array de bytes (registro)
          arquivo.writeByte(0);
          arquivo.writeInt(baNovo.length); // escreve tamanho do registro
          arquivo.write(baNovo); // escreve registro

          indexDireto.update(objetoNovo.getID(), pos);
          success = true;

        } else {
          arquivo.seek(pos + 5);
          // arquivo.writeInt(baNovo.length);
          arquivo.write(baNovo);

          success = true;
        }

        if (! (objetoNovo.chaveSecundaria() == objetoAntigo.chaveSecundaria()) ) {
          indexIndireto.delete(objetoAntigo.getID(), objetoAntigo.chaveSecundaria());
          indexIndireto.create(objetoNovo.chaveSecundaria(), objetoNovo.getID());
          // bMais.delete(objetoAntigo.getID(),objetoAntigo.chaveSecundaria().hashCode() & Integer.MAX_VALUE);
          // bMais.create( objetoNovo.getID(),objetoNovo.chaveSecundaria().hashCode()&Integer.MAX_VALUE);
        }

      } else {
        return false;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return success;
  }

  /**
   * Excluí, mudando a lápide, o registro
   * 
   * @param ID ID do registro a ser excluído
   * @return (boolean) booleano indicando o sucesso da operação
   * @throws IOException
   */
  public boolean delete(int ID) throws IOException {

    boolean success = false; // controle
    try {

      long pos = indexDireto.read(ID);
      if (pos == -1)
        return false;

      arquivo.seek(pos); // posição para excluír registro
      arquivo.writeByte(1); // muda lápide

      T objeto = read(ID);
      indexDireto.delete(ID);
      indexIndireto.delete(objeto.getID(), objeto.chaveSecundaria());
      // bMais.delete(ID, objeto.chaveSecundaria().hashCode() & Integer.MAX_VALUE);
    } catch (Exception e) {
      return false;
    }

    return success;
  }

}
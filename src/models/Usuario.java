package src.models;
import java.io.*;
import java.text.DecimalFormat;
import src.interfaces.Registro;

public class Usuario implements Registro {
  protected int id;
  protected int chaveSecundaria;
  protected String email;
  protected String nome;
  protected String senha;

  public Usuario(int i, String e, String n, String s) {
    this.id = i;
    setEmail(e);
    setChaveSecundaria(i);
    setNome(n);
    setSenha(s);
  }

  public Usuario(String e, String s) {
    this.id = -1;
    setChaveSecundaria(-1);
    setEmail(e);
    this.nome = "";
    setSenha(s);
  }

  public Usuario() {
    this.id = -1;
    this.email = "";
    this.nome = "";
    this.chaveSecundaria = -1;
    setSenha("");
  }

  public int chaveSecundaria() {
    return getEmail().hashCode();
  }
  public void setChaveSecundaria(int c) {
    this.chaveSecundaria = c;
  }

  public int getID() {
    return id;
  }

  public void setID(int i){
    this.id = i;
  }

  public String getNome(){
    return this.nome;
  }

  public void setNome(String n){
    this.nome = n;
  }

  public String getEmail(){
    return this.email;
  }

  public void setEmail(String e){
    this.email = e;
  }

  public String getSenha(){
    return this.senha;
  }
  
  public void setSenha(String s){
    this.senha = Integer.toString(s.hashCode());

  }

  
  public String toString() {
    return "\nID: "+ this.id + "\nNOME: " + this.nome + "\nEMAIL: " + this.email+ "\nSENHA: "+this.senha+ "\nCHAVESECUNDARIA: "+this.chaveSecundaria;
  }
  
  
 //passar o objeto para array de byte's
  public byte[] toByteArray() throws IOException {
    try{
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DataOutputStream dos = new DataOutputStream(baos);
      dos.writeInt(id);
      dos.writeUTF(nome);
      dos.writeUTF(email);
      dos.writeInt(chaveSecundaria);
      dos.writeUTF(senha);
      return baos.toByteArray();
    }catch(Exception e){
      return null;
    }
  }

  public void fromByteArray(byte[] ba) {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        try {
            this.id = dis.readInt();
            this.nome = dis.readUTF();
            this.email = dis.readUTF();
            this.chaveSecundaria = dis.readInt();
            this.senha = dis.readUTF();
        } catch (Exception e) {

        }

    }

}
package src.models;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;
import src.interfaces.Registro;
import src.util.ConsoleView;

public class Pergunta implements Registro{

  private int idPergunta;
  private int idUsuario;
  private int chaveSecundaria;
  private long criacao;
  private short nota;
  private String pergunta;
  private boolean ativa;

  public Pergunta(int idPergunta, int idUsuario, long criacao, short nota, String pergunta, boolean ativa) {
    this.idPergunta = idPergunta;
    this.idUsuario = idUsuario;
    this.criacao = criacao;
    this.nota = nota;
    this.pergunta = pergunta;
    this.ativa = ativa;
  }

  public Pergunta(byte[] bytes) {
   try {
      fromByteArray(bytes);
    } catch (IOException e) {
      ConsoleView.PrintErrorMessage("O conjunto de bytes lidos não contem um objeto válido.\n");
      new Pergunta();
    }
  }

  public Pergunta() {
    this.idPergunta = -1;
    this.idUsuario = -1;
    this.criacao = 0;
    this.nota = 0;
    this.chaveSecundaria = -1;
    this.pergunta = "NENHUMA PERGUNTA";
    this.ativa = true;
  }


  public int chaveSecundaria() {
    return chaveSecundaria;
  }

  public void setChaveSecundaria(int c) {
    this.chaveSecundaria = c;
  }

  public int getIdPergunta() {
    return idPergunta;
  }

  public void setIdPergunta(int idPergunta) {
    this.idPergunta = idPergunta;
  }

  public int getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(int idUsuario) {
    setChaveSecundaria(idUsuario);
    this.idUsuario = idUsuario;
  }

  public long getCriacao() {
    return criacao;
  }

  public void setCriacao(Date criacao) {
    this.criacao = criacao.getTime();
  }

  public void setCriacao(long criacao) {
    this.criacao = criacao;
  }

  public short getNota() {
    return nota;
  }

  public void setNota(short nota) {
    this.nota = nota;
  }

  public String getPergunta() {
    return pergunta;
  }

  public void setPergunta(String pergunta) {
    this.pergunta = pergunta;
  }

  public boolean isAtiva() {
    return ativa;
  }

  public int getAtiva() {
    return ativa ? 1 : 0;
  }

  public void setAtiva(boolean ativa) {
    this.ativa = ativa;
  }

  public void setAtivaByte(int ativa) {
    this.ativa = (ativa == 1) ? true : false;
  }

  @Override
  public int getID() {
    return this.idPergunta;
  }

  @Override
  public void setID(int id) {
    this.idPergunta = id;
  }

   public String toString() {
    return "\nID: "+ this.idPergunta + "\nPERGUNTA: " + this.pergunta + "\nativo: " + this.ativa+ "\nusuario: "+this.idUsuario+ "\nCHAVESECUNDARIA: "+this.chaveSecundaria;
  }
  
  @Override
  public byte[] toByteArray() throws IOException {
    try{
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DataOutputStream dos = new DataOutputStream(baos);
      dos.writeInt(getIdPergunta());
      dos.writeLong(getCriacao());
      dos.writeShort(getNota());
      dos.writeUTF(getPergunta());
      dos.writeInt(chaveSecundaria());
      dos.writeByte(getAtiva());
      dos.writeInt(getIdUsuario());
      return baos.toByteArray();
    }catch(Exception e){
      return null;
    }
  }

  @Override
  public void fromByteArray(byte[] ba) throws IOException {
    try{
      ByteArrayInputStream bais = new ByteArrayInputStream(ba);
      DataInputStream dis = new DataInputStream(bais);
      this.setIdPergunta(dis.readInt());
      this.setCriacao(dis.readLong());
      this.setNota(dis.readShort());
      this.setPergunta(dis.readUTF());
      this.setChaveSecundaria(dis.readInt());
      this.setAtivaByte(dis.readByte());
      this.setIdUsuario(dis.readInt());
    }catch(Exception e){
      
    }
  }

  
}
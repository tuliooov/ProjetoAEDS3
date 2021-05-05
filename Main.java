
import java.io.*;
import java.util.Scanner;
import src.plataforma.Plataforma;

public class Main {

  // Arquivo declarado fora de main() para ser poder ser usado por outros métodos
  

  public static void main(String[] args) {
   
    try {
      Plataforma plataforma = new Plataforma();
      plataforma.Run();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}

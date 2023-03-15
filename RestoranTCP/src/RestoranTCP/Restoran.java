package RestoranTCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Restoran {
    static class Handler extends Thread{
        private final Socket conn;

        Handler(Socket conn) {
            this.conn= conn;
        }

        @Override
        public void run() {
            try {
                PrintWriter out=new PrintWriter(conn.getOutputStream(),true);
                BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                //NAPRAVI_NARACKA@{restoran_id}@{jadenje_id}
                String naracka=in.readLine();
                if(naracka.startsWith("NapraviNaracka")){
                    String [] parts=naracka.split("@");
                    Thread.sleep(5000);
                    out.println("Narackata "+ parts[2]+" e gotova");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(0);
        Scanner sc=new Scanner(System.in);
        //REGISTRIRAJ MENI
        System.out.println("Vnesi jadenje oddeleni so zapirka: ");
        String jadenja=sc.nextLine();
        Socket connection = new Socket("localhost",11223);
        BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
        PrintWriter out=new PrintWriter(connection.getOutputStream(),true);
        String registriraj="RegistrirajMeni@"+serverSocket.getLocalPort()+"@"+jadenja;
        out.println(registriraj);
        //NAPRAVI NARACKA
        while (true){
            Socket conn=serverSocket.accept();
            new Handler(conn).start();
        }

    }

}

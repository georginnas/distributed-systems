package Vakcinacija;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class CentralenServer {

static List<String> punktovi=new CopyOnWriteArrayList<>();

static class Handler extends Thread{
        private final Socket connection;

        public Handler(Socket connection) {
            this.connection = connection;
        }
        @Override
        public void run() {
            try {
                PrintWriter out=new PrintWriter(connection.getOutputStream(),true);
                BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String action=in.readLine();
                if(action.startsWith("Najava")){
                    String [] parts=action.split("@");
                    String port=parts[1];
                    punktovi.add(port);
                    out.println(parts[1]);
                }else if(action.startsWith("Odjava")){
                    String [] parts=action.split("@");
                    String id=parts[1];
                    punktovi.remove(id);
                    out.println("uspesna odjava");
                }else if(action.startsWith("Lista")){
                    String niza="";
                    for(String punkt : punktovi){
                        niza += punkt + ",";
                    }
                    out.println(niza);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket server=new ServerSocket(11223);
        while(true){
            Socket connection=server.accept();
            new Handler(connection).start();
        }
    }
}

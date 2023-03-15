package RestoranTCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Organizator {
    static Map<String,String> meni=new ConcurrentHashMap<>();
    static class Handler extends Thread{
        private final Socket connection;

        Handler(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                PrintWriter out=new PrintWriter(connection.getOutputStream(),true);
                BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String action=in.readLine();
                if(action.startsWith("BaraMeni")){
                    /**
                     *  12335: jadenje-1, jadenje-2, jadenje-3
                     *  22334: jadenje-1, jadenje-2, jadenje-3
                     *  44556: jadenje-1, jadenje-2, jadenje-3
                     */
                    String rezultat="";
                    for(String menu:meni.keySet()){
                        rezultat+="Restoran: "+menu+" Menu: "+meni.get(menu)+" | ";
                    }
                    out.println(rezultat);
                }else if(action.startsWith("NapraviNaracka")){
                    //NAPRAVINARACKA@{restoran_id}@{jadenje_id}
                    String []parts=action.split("@");
                    String restoranID=parts[1];
                    Socket connectionRestoran=new Socket("localhost",Integer.parseInt(restoranID));
                    PrintWriter outRestoran=new PrintWriter(connectionRestoran.getOutputStream(),true);
                    BufferedReader inRestoran=new BufferedReader(new InputStreamReader(connectionRestoran.getInputStream()));
                    outRestoran.println(action);
                    String gotovaNaracka=inRestoran.readLine();

                    out.println(gotovaNaracka);

                }else if(action.startsWith("RegistrirajMeni")){
                    // RegistrirajMeni@restoran@jadenje_1, jadenje_2,
                    String [] parts=action.split("@");
                    String restoranID=parts[1];
                    String jadenje=parts[2];
                    meni.put(String.valueOf(restoranID),jadenje);
                    out.println("Uspesno registrirano meni na restoran: "+restoranID);

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

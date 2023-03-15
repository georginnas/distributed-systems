package Vakcinacija;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Punkt {
    static Integer RB=0;
static class Handler extends Thread{
    private final Socket connection;

    public Handler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter out=new PrintWriter(connection.getOutputStream(),true);

            String line=in.readLine();
            if(line.startsWith("Reden_broj")){
            out.println(RB++);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    static class Server extends Thread{
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket=serverSocket;
    }

        @Override
        public void run() {
        try {
                    while(true){
                    Socket connection= serverSocket.accept();
                    new Handler(connection).start();}
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }

    public static void main(String[] args) throws IOException, InterruptedException {
    ServerSocket serverSocket=new ServerSocket(0);
    int port=serverSocket.getLocalPort();
    Socket najava=new Socket("localhost",11223);
        BufferedReader in=new BufferedReader(new InputStreamReader(najava.getInputStream()));
        PrintWriter out=new PrintWriter(najava.getOutputStream(),true);
        out.println("Najava@"+port);
        String id=in.readLine();
        System.out.println("Uspesna najava: "+ id);

        Thread thread=new Server(serverSocket);
        thread.start();

        System.out.println("Vnesi 2 za odjava: ");
        Scanner sc=new Scanner(System.in);
        int broj=Integer.parseInt(sc.nextLine());
        if(broj==2){
            Socket odjava=new Socket("localhost", 11223);
            BufferedReader inn=new BufferedReader(new InputStreamReader(odjava.getInputStream()));
            PrintWriter outt=new PrintWriter(odjava.getOutputStream(),true);
            outt.println("Odjava@"+port);
            String line=inn.readLine();
            System.out.println(line);
            serverSocket.close();

        }

    }
}

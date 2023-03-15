package VakcinacijaUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Punkt{
        static Integer ID=0;
        static class Handler extends Thread{
                private final DatagramPacket packet;

                Handler(DatagramPacket packet) {
                        this.packet = packet;
                }

                @Override
                public void run() {
                        try {
                        String baranje=new String(packet.getData(),0,packet.getLength());
                        if(baranje.startsWith("RedenBroj")){
                                ID++;
                                String id=ID.toString();
                                byte[]buff=id.getBytes();
                                DatagramPacket response=new DatagramPacket(buff,buff.length,packet.getAddress(),packet.getPort());

                                        new DatagramSocket().send(response);
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }}
        static class Server extends Thread{
                private final DatagramSocket datagramSocket;

                Server(DatagramSocket datagramSocket) {
                        this.datagramSocket = datagramSocket;
                }

                @Override
                public void run() {
                        try {
                        while(true){
                                byte[] buffer=new byte[5000];
                                DatagramPacket packet=new DatagramPacket(buffer,buffer.length);

                                        datagramSocket.receive(packet);

                                new Handler(packet).start();
                        } } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }
public static void main(String[]args)throws IOException {
        DatagramSocket datagramSocket=new DatagramSocket();
        int port=datagramSocket.getLocalPort();

        String action="Najava@"+port;
        byte[] actionBytes=action.getBytes();
        DatagramPacket najava=new DatagramPacket(actionBytes,actionBytes.length, InetAddress.getLocalHost(),11223);
        DatagramSocket socket=new DatagramSocket();
        socket.send(najava);

        byte[] responseBytes=new byte[5000];
        DatagramPacket response=new DatagramPacket(responseBytes,responseBytes.length);
        socket.receive(response);
        String res=new String(response.getData(),0,response.getLength());
        System.out.println(res);
Thread thread= new Server(datagramSocket);
thread.start();

        System.out.println("2. vnesi za odjava");
        Scanner sc=new Scanner(System.in);
        int n=Integer.parseInt(sc.nextLine());
        if(n==2){
                String odjavas="Odjava@"+port;
                byte[] odjavaBytes=odjavas.getBytes();
                DatagramPacket odjava=new DatagramPacket(odjavaBytes,odjavaBytes.length,InetAddress.getLocalHost(),11223);
                DatagramSocket socket1=new DatagramSocket();
                socket1.send(odjava);

                byte[] niza=new byte[5000];
                DatagramPacket odg=new DatagramPacket(niza,niza.length);
                socket1.receive(odg);

                String rs=new String(odg.getData(),0,odg.getLength());
                System.out.println(rs);
                datagramSocket.close();
        }



        }
        }
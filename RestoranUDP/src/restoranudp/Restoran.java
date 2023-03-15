package restoranudp;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Restoran {
    static class Handler extends Thread{
        private  final DatagramPacket packet;

        Handler(DatagramPacket packet) {
            this.packet = packet;
        }

        @Override
        public void run() {
            try {
            String action=new String(packet.getData(),0,packet.getLength());
            if(action.startsWith("NapraviNaracka")){
                String [] parts=action.split("@");
                String jadenje=parts[2];
               Thread.sleep(5000);
                String odg="Narackata "+jadenje+" e gotova!";
                byte[] bytes=odg.getBytes();

                DatagramPacket packet1=new DatagramPacket(bytes,bytes.length,packet.getAddress(),packet.getPort());
                new DatagramSocket().send(packet1);
                }} catch (UnknownHostException | SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }

    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket=new DatagramSocket();
        Scanner sc=new Scanner(System.in);
        System.out.println("Vnesuvaj jadenje vo menito so zapirka");
        String jadenja=sc.nextLine();

        String action="RegistrirajMeni@"+datagramSocket.getLocalPort()+"@"+jadenja;
        byte[] bytes=action.getBytes();
        DatagramPacket packet=new DatagramPacket(bytes,bytes.length, InetAddress.getLocalHost(),11223);
        DatagramSocket socket=new DatagramSocket();
        socket.send(packet);

        while(true){
            byte[] data=new byte[5000];
            DatagramPacket packet1=new DatagramPacket(data,data.length);
            datagramSocket.receive(packet1);
            new Handler(packet1).start();

        }



    }
}

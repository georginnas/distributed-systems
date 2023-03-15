package restoranudp;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Kupuvac {
    public static void main(String[] args) throws IOException {
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("Izberi opcija:");
            System.out.println("1. Pobaraj meni");
            System.out.println("2. Napravi naracka");
            int opcija=Integer.parseInt(sc.nextLine());
            if(opcija==1){
                String action="BaraMeni";
                byte[] bytes=action.getBytes();
                DatagramPacket packet=new DatagramPacket(bytes,bytes.length, InetAddress.getLocalHost(),11223);
                DatagramSocket socket=new DatagramSocket();
                socket.send(packet);

                byte[] odg=new byte[5000];
                DatagramPacket odgovor=new DatagramPacket(odg,odg.length);
                socket.receive(odgovor);
                String meni=new String(odgovor.getData(),0,odgovor.getLength());
                System.out.println(meni);


            }else if(opcija==2){
                System.out.println("Vnesi broj na restoran");
                String brRestoran=sc.nextLine();
                System.out.println("Vnesi jadenje");
                String jadenje=sc.nextLine();
                String naracka="NapraviNaracka@"+brRestoran+"@"+jadenje;
                byte[] bytes=naracka.getBytes();
                DatagramPacket packet=new DatagramPacket(bytes,bytes.length,InetAddress.getLocalHost(),11223);
                DatagramSocket socket=new DatagramSocket();
                socket.send(packet);

                byte []odg=new byte[5000];
                DatagramPacket odgovor=new DatagramPacket(odg,odg.length);
                socket.receive(odgovor);

                String odgNaracka=new String(odgovor.getData(),0,odgovor.getLength());
                System.out.println(odgNaracka);
            }
        }
    }
}

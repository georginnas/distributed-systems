package VakcinacijaUDP;


import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Korisnik {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Izberi opcija:");
            System.out.println("1. za lista na punktovi");
            System.out.println("2. za zemanje na reden broj");
            int broj = Integer.parseInt(sc.nextLine());

            if (broj == 1) {
                String action = "Lista";
                byte[] req = action.getBytes();
                DatagramPacket baranje = new DatagramPacket(req, req.length, InetAddress.getLocalHost(), 11223);
                DatagramSocket socket = new DatagramSocket();
                socket.send(baranje);

                byte[] res = new byte[5000];
                DatagramPacket odgovor = new DatagramPacket(res, res.length);
                socket.receive(odgovor);

                String response = new String(odgovor.getData(), 0, odgovor.getLength());
                System.out.println(response);

            } else if (broj == 2) {
                System.out.println("Vnesi broj na punkt");
                String brPunkt = sc.nextLine();
                String req = "RedenBroj";
                byte[] bytes = req.getBytes();
                DatagramPacket baranje = new DatagramPacket(bytes, bytes.length, InetAddress.getLocalHost(), Integer.parseInt(brPunkt));
                DatagramSocket socket = new DatagramSocket();
                socket.send(baranje);

                byte[] resp = new byte[5000];
                DatagramPacket odgovor = new DatagramPacket(resp, resp.length);
                socket.receive(odgovor);

                String res = new String(odgovor.getData(), 0, odgovor.getLength());
                System.out.println(res);
            }
        }

    }
}
package VakcinacijaUDP;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CentralenServer {
    static List<String> lista = new CopyOnWriteArrayList<>();

    static class Handler extends Thread {
        private final DatagramPacket packet;

        Handler(DatagramPacket packet) {
            this.packet = packet;
        }

        @Override
        public void run() {
            try {
                String action = new String(packet.getData(), 0, packet.getLength());
                if (action.startsWith("Najava")) {
                    String[] parts = action.split("@");
                    String port = parts[1];
                    lista.add(port);
                    String res = "Uspesna najava," + port;
                    byte[] resp = res.getBytes();
                    DatagramPacket response = new DatagramPacket(resp, resp.length, packet.getAddress(), packet.getPort());
                    new DatagramSocket().send(response);

                } else if (action.startsWith("Odjava")) {
                    String[] parts = action.split("@");
                    String port = parts[1];
                    lista.remove(port);
                    String res = "Uspesna odjava," + port;
                    byte[] resp = res.getBytes();
                    DatagramPacket response = new DatagramPacket(resp, resp.length, packet.getAddress(), packet.getPort());
                    new DatagramSocket().send(response);

                } else if (action.startsWith("Lista")) {
                    String rezultat = "";
                    for (String punkt : lista) {
                        rezultat += punkt + ",";
                    }
                    byte[] bytes = rezultat.getBytes();
                    DatagramPacket response = new DatagramPacket(bytes, bytes.length, packet.getAddress(), packet.getPort());
                    new DatagramSocket().send(response);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        DatagramSocket server = new DatagramSocket(11223);
        while (true) {
            byte[] bytes = new byte[5000];
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
            server.receive(packet);
            new Handler(packet).start();
        }

    }
}
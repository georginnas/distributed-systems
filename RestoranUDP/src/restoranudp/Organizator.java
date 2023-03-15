package restoranudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Organizator {
    static Map<String,String> meni=new ConcurrentHashMap<>();
    static class Handler extends Thread{
private final DatagramPacket packet;

        Handler(DatagramPacket packet) {
            this.packet = packet;
        }

        @Override
        public void run() {
            try {
          String action=new String(packet.getData(),0,packet.getLength());
          if(action.startsWith("BaraMeni")) {
              String rezultat = "";
              for (String menu : meni.keySet()) {
                  rezultat += "Restoran: " + menu + " Menu: " + meni.get(menu) + " | ";
              }
              byte[] odg = rezultat.getBytes();
              DatagramPacket odgovor = new DatagramPacket(odg, odg.length, packet.getAddress(), packet.getPort());
              new DatagramSocket().send(odgovor);
          }else if(action.startsWith("NapraviNaracka")){
              String []parts=action.split("@");
              String restoran=parts[1];
              byte [] odg=action.getBytes();
              DatagramPacket restoranPacket=new DatagramPacket(odg,odg.length,InetAddress.getLocalHost(),Integer.parseInt(restoran));
              DatagramSocket restoranSocket =new DatagramSocket();
              restoranSocket.send(restoranPacket);

              byte[] data=new byte[5000];
              DatagramPacket naracka=new DatagramPacket(data,data.length);
              restoranSocket.receive(naracka);

              String narackaRestoran=new String(naracka.getData(),0,naracka.getLength());
              byte[] nR=narackaRestoran.getBytes();
              DatagramPacket narackaKlient=new DatagramPacket(nR,nR.length,packet.getAddress(),packet.getPort());
              new DatagramSocket().send(narackaKlient);
          }else if(action.startsWith("RegistrirajMeni")){
              String [] parts=action.split("@");
              String restoran=parts[1];
              String jadenje=parts[2];
              meni.put(restoran,jadenje);
          }
            } catch (IOException e) {
                  e.printStackTrace();
              }

          }
        }

    public static void main(String[] args) throws IOException {
        DatagramSocket server=new DatagramSocket(11223);
        while(true){
            byte[] bytes=new byte[5000];
            DatagramPacket packet =new DatagramPacket(bytes,bytes.length);
            server.receive(packet);
            new Handler(packet).start();
        }
    }
}

package RestoranTCP;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Kupuvac {
    public static void main(String[] args) throws IOException {
        Scanner sc=new Scanner(System.in);
        while (true){
            System.out.println("Izberete edna od ponudenite opcii: ");
            System.out.println("1.Izberi meni");
            System.out.println("2.Napravi naracka");
            int opcija=Integer.parseInt(sc.nextLine());
            if(opcija==1){
                Socket connection=new Socket("localhost",11223);
                BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                PrintWriter out=new PrintWriter(connection.getOutputStream(),true);
                out.println("BaraMeni");
                String odgovor=in.readLine();
                System.out.println(odgovor);

            } else if (opcija == 2) {
                System.out.println("Vnesi broj na restoran! ");
                String brRestoran=sc.nextLine();
                System.out.println("Vnesi broj na jadenje! ");
                String brJadenje=sc.nextLine();
                String naracka="NapraviNaracka@"+brRestoran+"@"+brJadenje;
                Socket connection=new Socket("localhost",11223);
                BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                PrintWriter out=new PrintWriter(connection.getOutputStream(),true);
                out.println(naracka);
                String odgovor=in.readLine();
                System.out.println(odgovor);
            }
        }
    }
}

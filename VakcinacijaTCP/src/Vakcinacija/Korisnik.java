package Vakcinacija;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Korisnik {
    public static void main(String[] args) throws IOException {
        Scanner sc=new Scanner(System.in);
        while(true){
        System.out.println("Izberete opcija: ");
        System.out.println("1 za lista na vakcinalni punktovi");
        System.out.println("2 za zemanje na redno brojche ");
        int broj=Integer.parseInt(sc.nextLine());
        if(broj==1){
            Socket connection=new Socket("localhost",11223);
            BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter out=new PrintWriter(connection.getOutputStream(),true);
            out.println("Lista");
            String lista=in.readLine();
            System.out.println("Lista na vakcinalni punktovi: "+ lista);

        }else if(broj==2){
            System.out.println("Vnesete go brojot na vakcinalen punkt: ");
            int punkt=Integer.parseInt(sc.nextLine());
            Socket connection =new Socket("localhost",punkt);
            BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter out=new PrintWriter(connection.getOutputStream(),true);
            out.println("Reden_broj");
            String rbroj=in.readLine();
            System.out.println(rbroj);
        }}
    }}

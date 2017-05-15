/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import cpu.Machine;
import cpu.Program;
import java.util.Scanner;

/**
 *
 * @author Michael
 */
public class Main {

    public static void main(String[] args) { //bytes are signed in java which is why some are -56, so you can use an integer with a bit mask.
        System.out.println("Welcome to the awesome CPU program"); //Make a while loop in the program, so when it HALTs it will keep on going and not just completely stop the program.

        while (true) {
            System.out.println("Factorial = 'f', Calculus = 'c', Tail-Recursive = Enter");
//        Program program = new Program("00101001", "00001111", "10101010", "MOV B +3");
            
            //Factorial of 5
            Program Fact = new Program("01001010", "00010000", "00001100",
               "11000110", "00010010", "00001111", "00110010", "00000111",
               "10001100", "01000010", "00100001", "00011000", "00010000",
               "00010111", "00010000", "00001100", "11000110", "00010011",
               "00010010", "00000010", "00100001", "00011000");
            
            //Calculus of (5 + 11)*-3
            Program Calc = new Program("01001010", "00010000", "01010110", 
               "00010000", "01111010", "00010000", "00001100", "11001010", 
               "00010010", "00001111", "00110111", "00110100", "00000001", 
               "00110011", "00000010", "00100011", "00011010");
            
            /*Program TailRec = new Program("01001010", "00010000", "00001100",
                  "11000110", "00010010", "00001111", "00110010", "00000111",
                  "10001100", "01000010", "00100001", "00011000", "00010000",
                  "00010111", "00010000", "00001100", "10000110", "00110001",
                  "00100010", "00000010", "00100001", "00011000"); /* I RECUR: "MOV b+1", "MOV a+2" "JMP #6" istedet for call "#6"*/
            
            //TailRecursion of Factorial 5
            Program TailRec = new Program("01000010", "00010000", "01001010", 
                  "00010000", "00001100", "11001000", "00010010", "00001111", 
                  "00110010", "00000111", "10001100", "00011001", 
                  "00110101", "00000010", "00100010", "00110010", 
                  "00010111", "00100001", "00001100", "10001000");
            
            Scanner scan = new Scanner(System.in);
            Machine machine = new Machine();
            switch (scan.nextLine()) {
                case "f":
                    machine.load(Fact);
                    break;
                case "c":
                    machine.load(Calc);
                    break;
                default:
                    machine.load(TailRec);
                    break;
            }
            machine.print(System.out);

            Scanner scanner = new Scanner(System.in);
            System.out.println("\n-> Press [ENTER] to run next instruction");
            String input = scanner.nextLine();

            while (!input.equalsIgnoreCase("q") /*&& Halts.halt == false*/ && machine.getCpu().IsRunning()) {
                machine.tick();
                machine.print(System.out);
                System.out.println("\n-> Press [ENTER] to run next instruction");
                input = scanner.nextLine();
            }
            
            System.out.println("HALTED");
//            if(machine.getCpu().IsRunning() == false){
//                break;
//            }
        }

//        for(int line : newP2) System.out.println(">>> " + line);
//        Memory memory = new Memory();
//        Cpu cpu = new Cpu();
//        cpu.setA(7);
//        cpu.setB(13);
//        cpu.setIp(3);
//        cpu.setSp(62);
//        
//        memory.set(7, 57);
//        memory.set(17, 200);
//        memory.print(System.out);
//        cpu.print(System.out);
    }
}

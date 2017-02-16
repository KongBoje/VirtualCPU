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
        System.out.println("Welcome to the awesome CPU program");

//        Program program = new Program("00101001", "00001111", "10101010", "MOV B +3");
//        Program newP = new Program("01001010", "00010000", "00001100", "11000110", 
//                "00010010", "00001111", "00110010", "00000111",
//                "10001100", "01000010", "00100001", "00011000", "00010000",
//                "00010111", "00010000", "00001100", "11000110", "00010011", "00010010",
//                "00000010", "00100001", "00011000");
        
        Program newP2 = new Program("01001010", "00010000", "01010110", "00010000", "01111010", "00010000", "00001100", "11001010", "00010010", "00001111", "00110111", "00110100", "00000001",
                                    "00110011", "00000010", "00100011", "00011010");

        Machine machine = new Machine();
        machine.load(newP2);
        machine.print(System.out);

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n-> Press [ENTER] to run next instruction");
        String input = scanner.nextLine();
        
        while (!input.equalsIgnoreCase("q")) {
            machine.tick();
            machine.print(System.out);
            System.out.println("\n-> Press [ENTER] to run next instruction");
            input = scanner.nextLine();
        }

//        for(int line : program) System.out.println(">>> " + line);
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

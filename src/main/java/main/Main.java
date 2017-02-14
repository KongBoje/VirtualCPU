/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import cpu.Machine;
import cpu.Program;

/**
 *
 * @author Michael
 */
public class Main {
    public static void main(String[] args) { //bytes are signed in java which is why some are -56, so you can use an integer with a bit mask.
        System.out.println("Welcome to the awesome CPU program");
        Program program = new Program("00101001", "00001111", "10101010", "MOV B +3");
        Machine machine = new Machine();
        machine.load(program);
        machine.print(System.out);
        machine.tick();
        machine.print(System.out);
        
        
        
        
        
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

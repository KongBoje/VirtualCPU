/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu;

import java.io.PrintStream;

/**
 *
 * @author Michael
 */
public class Machine {
    private Cpu cpu = new Cpu();
    private Memory memory = new Memory();

    public void load(Program program) {
        int index = 0;
        for (int instr : program) {
            memory.set(index++, instr);
        }
    }

    public void tick() { //Use bit mask to reference what is being pushed, added etc.
        int instr = memory.get(cpu.getIp());
        if (instr == 0b0000_0000) {
            System.out.println("NOP");
            System.out.println("-----");
            // 0000 0000 NOP
            cpu.incIp();
            //cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0001) {
            System.out.println("ADD");
            System.out.println("-----");
            // 0000 0001 ADD A B
            System.out.println("Adding A and B = " + (cpu.getA() + cpu.getB()));
            cpu.setA(cpu.getA() + cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0000_0010) {
            System.out.println("MUL");
            System.out.println("-----");
            // 0000_0010 MUL A B
            cpu.setA(cpu.getA() * cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0000_0011) {
            System.out.println("DIV");
            System.out.println("-----");
            // 0000_0011 DIV A B
            cpu.setA(cpu.getA() / cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0000_0100) {
            System.out.println("ZERO");
            System.out.println("-----");
            // 0000_0100 ZERO A = 0
            cpu.setFlag(cpu.getA() == 0);
            cpu.incIp();
        } else if (instr == 0b0000_0101) {
            System.out.println("NEG");
            System.out.println("-----");
            // 0000_0101 NEG A < 0
            cpu.setFlag(cpu.getA() < 0);
            cpu.incIp();
        } else if (instr == 0b0000_0110) {
            System.out.println("POS");
            System.out.println("-----");
            // 0000_0110 POS A > 0
            cpu.setFlag(cpu.getA() > 0);
            cpu.incIp();
        } else if (instr == 0b0000_0111) {
            System.out.println("NZERO");
            System.out.println("-----");
            // 0000_0111 NZERO A ≠ 0
            cpu.setFlag(cpu.getA() != 0);
            cpu.incIp();
        } else if (instr == 0b0000_1000) {
            System.out.println("EQ");
            System.out.println("-----");
            // 0000_1000 EQ A = B
            cpu.setFlag(cpu.getA() == cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0000_1001) {
            System.out.println("LT");
            System.out.println("--|");
            // 0000_1001 LT A < B
            cpu.setFlag(cpu.getA() < cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0000_1010) {
            System.out.println("GT");
            System.out.println("--|");
            // 0000_1010 GT A > B
            cpu.setFlag(cpu.getA() > cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0000_1011) {
            System.out.println("NEQ");
            System.out.println("-------------");
            // 0000_1011 NEQ A ≠ B
            cpu.setFlag(cpu.getA() != cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0000_1100) {
            System.out.println("ALWAYS");
            System.out.println("------------");
            // 0000_1100 ALWAYS TRUE
            cpu.setFlag(true);
            cpu.incIp();
        } else if (instr == 0b0000_1111) {
            System.out.println("HALT");
            System.out.println("------------");
            // 0000_1111 HALT
            //Halts.halt = true;
            cpu.setIsRunning(false);
        } else if ((instr & 0b1111_1110) == 0b0001_0000) {
            // 0001_000r PUSH r. [--SP] ← r; IP++
            int r = (instr & 0b0000_0001);
            cpu.decSp();
            if (r == cpu.A) {
                memory.set(cpu.getSp(), cpu.getA());
            } else {
                memory.set(cpu.getSp(), cpu.getB());
            }
            cpu.incIp();
            System.out.println("PUSH "+ cpu.name(r));
            System.out.println("------------");
        } else if ((instr & 0b1111_1110) == 0b0001_0010) {
            // 0001_001r POP r. r ← [SP++]; IP++
            int r = (instr & 0b0000_0001);
            if (r == cpu.A) {
                cpu.setA(memory.get(cpu.getSp()));
            } else {
                cpu.setB(memory.get(cpu.getSp()));
            }
            cpu.setSp(cpu.getSp() + 1);
            cpu.incIp();
            System.out.println("POP "+ cpu.name(r));
            System.out.println("-----------");
        } else if (instr == 0b0001_0100) {
            System.out.println("MOV A B");
            System.out.println("-----------");
            // 0001 0100 MOV A B.    B ← A; IP++
            cpu.setB(cpu.getA());
            cpu.incIp();
        } else if (instr == 0b0001_0101) {
            System.out.println("MOV B A");
            System.out.println("-----------");
            // 0001 0101 MOV B A.    A ← B; IP++
            cpu.setA(cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0001_0110) {
            System.out.println("INC");
            System.out.println("-----------");
            // 0001 0110 INC.        A++; IP++
            cpu.setA(cpu.getA() + 1);
            cpu.incIp();
        } else if (instr == 0b0001_0111) {
            System.out.println("DEC");
            System.out.println("-----------");
            // 0001 0111 DEC.        A--; IP++
            cpu.setA(cpu.getA() - 1);
            cpu.incIp();
        } else if ((instr & 0b1111_1000) == 0b0001_1000) {
            // 0001 1ooo RTN +o.     IP ← [SP++]; SP += o; IP++
            int o = (instr & 0b0000_0111);
            cpu.setIp(memory.get(cpu.getSp()));
            cpu.setSp(cpu.getSp() + 1 + o);
            cpu.incIp();
            System.out.println("RTN " + o);
            System.out.println("-----------");
        } else if ((instr & 0b1111_0000) == 0b0010_0000) {
            // 0010 r ooo MOV r o    [SP + o] <-- r; IP++. get 0010 with a mask.

            // 0010 1 011 MOV B (=1) +3 [SP +3]. //Move register B to memory position of SP with offset 3.
            // 00101011 finding instruction
            // and
            // 11110000
            // ---------
            // 00100000
            // 00101011 finding offset
            // and
            // 00000111
            // ---------
            // 00000011 = 3
            // 00101011 finding register
            // and
            // 00001000
            // --------
            // 00001000 = 8
            // >> 3
            // 00000001 = 1
            int o = instr & 0b0000_0111; //rooo = 1s indicate the o's.
            int r = (instr & 0b0000_1000) >> 3; //rooo = 1 indicate the r.
            if (r == cpu.A) {
                memory.set(cpu.getSp() + o, cpu.getA());
            } else {
                memory.set(cpu.getSp() + o, cpu.getB());
            }
            cpu.incIp();
            System.out.println("MOV " + r + " to " + o);
            System.out.println("-----------");
        } else if ((instr & 0b1111_0000) == 0b0011_0000) {
            // 0011 ooor MOV o r.     r ← [SP + o]; IP++
            int o = (instr & 0b0000_1110) >> 1;
            int r = (instr & 0b0000_0001);
            if(r == cpu.A) {
                cpu.setA(memory.get(cpu.getSp() + o));
            } else {
                cpu.setB(memory.get(cpu.getSp() + o));
            }
            cpu.incIp();
            System.out.println("MOV +" + o + " to " + r);
            System.out.println("----------");
        
        } else if ((instr & 0b1100_0000) == 0b0100_0000) {
            // 01vv vvvr MOV v r.     r ← v; IP++
            int v = ((instr & 0b0011_1110) >> 1);
            /*if(((instr & 0b0010_0000)) == 0b0010_0000){
                v = - ((instr & 0b0001_1110) >> 1);
            }*/
            int r = (instr & 0b0000_0001);
            if(r == cpu.A){
                cpu.setA(v);
            } else {
                cpu.setB(v);
            }
            cpu.incIp();
            System.out.println("MOV " + v + " to " + r);
            System.out.println("----------");
            
        } else if ((instr & 0b1100_0000) == 0b1000_0000) {
            // 10aa aaaa JMP #a.      if F then IP ← a else IP++
            int a = instr & 0b0011_1111;
            if(cpu.isFlag()) {
                cpu.setIp(a);
            } else {
                cpu.incIp();
            }
            System.out.println("JUMP TO " + a);
            System.out.println("----------");
        } else if ((instr & 0b1100_0000) == 0b1100_0000) {
            // 11aa aaaa CALL #a.     if F then [--SP] ← IP; IP ← a else IP++
            int a = instr & 0b0011_1111;
            if (cpu.isFlag()) {
                cpu.decSp();
                memory.set(cpu.getSp(), cpu.getIp());
                //memory.set(cpu.getIp(), a);
                cpu.setIp(a);
            } else {
                cpu.incIp();
            }
            System.out.println("CALL " + a);
            System.out.println("---------");
        }
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void print(PrintStream out) {
        memory.print(out, cpu);
        out.println("----------------------------");
        cpu.print(out);
        out.println("----------------------------");
    }
}

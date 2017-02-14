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

    public void tick() {
        int instr = memory.get(cpu.getIp());
        if (instr == 0b0000_0000) {
            // 0000 0000 NOP
            cpu.incIp();
//            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0001) {
            // 0000 0001 ADD A B
            cpu.setA(cpu.getA() + cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0000_0010) {
            // 0000_0010 MUL A B
            cpu.setA(cpu.getA() + cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0000_0011) {
            // 0000_0011 DIV A B
            cpu.setA(cpu.getA() / cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0000_0100) {
            // 0000_0100 ZERO A = 0
            if (cpu.getA() == 0) {
                cpu.setFlag(true);
            }
            cpu.incIp();
        } else if (instr == 0b0000_0101) {
            // 0000_0101 NEG A < 0
            if (cpu.getA() < 0) {
                cpu.setFlag(true);
            }
            cpu.incIp();
        } else if (instr == 0b0000_0110) {
            // 0000_0110 POS A > 0
            if (cpu.getA() > 0) {
                cpu.setFlag(true);
            }
            cpu.incIp();
        } else if (instr == 0b0000_0111) {
            // 0000_0111 NZERO A ≠ 0
            if (cpu.getA() != 0) {
                cpu.setFlag(true);
            }
            cpu.incIp();
        } else if (instr == 0b0000_1000) {
            // 0000_1000 EQ A = B
            if (cpu.getA() == cpu.getB()) {
                cpu.setFlag(true);
            }
            cpu.incIp();
        } else if (instr == 0b0000_1001) {
            // 0000_1001 LT A < B
            if (cpu.getA() < cpu.getB()) {
                cpu.setFlag(true);
            }
            cpu.incIp();
        } else if (instr == 0b0000_1010) {
            // 0000_1010 GT A > B
            if (cpu.getA() > cpu.getB()) {
                cpu.setFlag(true);
            }
            cpu.incIp();
        } else if (instr == 0b0000_1011) {
            // 0000_1011 NEQ A ≠ B
            if (cpu.getA() != cpu.getB()) {
                cpu.setFlag(true);
            }
            cpu.incIp();
        } else if (instr == 0b0000_1100) {
            // 0000_1100 ALWAYS TRUE
            cpu.setFlag(true);
            cpu.incIp();
        } else if (instr == 0b0000_1111) {
            // 0000_1111 HALT
            cpu.setFlag(false);
        } else if ((instr & 0b1111_1110) == 0b0001_0000) {
            // 0001_000r PUSH r. [--SP] ← r; IP++
            int r = (instr & 0b0000_0001);
            if (r == cpu.A) {
                memory.set(cpu.getSp() - 1, cpu.getA());
            } else {
                memory.set(cpu.getSp() - 1, cpu.getB());
            }
            cpu.incIp();
            cpu.decSp();
        } else if ((instr & 0b1111_1110) == 0b0001_0010) {
            // 0001_001r POP r. r ← [SP++]; IP++
            int r = (instr & 0b0000_0001);
            if (r == cpu.A) {
                cpu.setA(memory.get(cpu.getSp() + 1));
            } else {
                cpu.setB(memory.get(cpu.getSp() + 1));
            }
            cpu.incIp();
        } else if (instr == 0b0001_0100) {
            // 0001 0100 MOV A B.    B ← A; IP++
            cpu.setB(cpu.getA());
            cpu.incIp();
        } else if (instr == 0b0001_0101) {
            // 0001 0101 MOV B A.    A ← B; IP++
            cpu.setA(cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0001_0110) {
            // 0001 0110 INC.        A++; IP++
            cpu.setA(cpu.getA() + 1);
            cpu.incIp();
        } else if (instr == 0b0001_0111) {
            // 0001 0111 DEC.        A--; IP++
            cpu.setA(cpu.getA() - 1);
            cpu.incIp();
        } else if ((instr & 0b1111_1000) == 0b0001_1000) {
            // 0001 1ooo RTN +o.     IP ← [SP++]; SP += o; IP++
            int o = (instr & 0b0000_0111);
            cpu.setSp(cpu.getSp() + 1);
            memory.set(cpu.getIp(), cpu.getSp());
            cpu.setSp(cpu.getSp() + o);
            cpu.incIp();
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
        } else if ((instr & 0b1111_0000) == 0b0011_0000) {
            // 0011 ooor MOV o r.     r ← [SP + o]; IP++
            int o = instr & 0b0000_1110 >> 1;
            int r = (instr & 0b0000_0001);
            if(r == cpu.A) {
                cpu.setA(memory.get(cpu.getSp() + o));
            } else {
                cpu.setB(memory.get(cpu.getSp() + o));
            }
            cpu.incIp();
        } else if ((instr & 0b1100_0000) == 0b0100_0000) {
            // 01vv vvvr MOV v r.     r ← v; IP++
            int v = (instr & 0b0011_1110) >> 1;
            int r = (instr & 0b0000_0001);
            if(r == cpu.A){
                cpu.setA(v);
            } else {
                cpu.setB(v);
            }
            cpu.incIp();
        } else if ((instr & 0b1100_0000) == 0b1000_0000) {
            // 10aa aaaa JMP #a.      if F then IP ← a else IP++
            int a = instr & 0b0011_1111;
            if(cpu.isFlag()) {
                memory.set(cpu.getIp(), a);
            } else {
                cpu.incIp();
            }
        } else if ((instr & 0b1100_0000) == 0b1100_0000) {
            // 11aa aaaa CALL #a.     if F then [--SP] ← IP; IP ← a else IP++
            int a = instr & 0b0011_1111;
            if (cpu.isFlag()) {
                cpu.decSp();
                memory.set(cpu.getSp(), cpu.getIp());
                memory.set(cpu.getIp(), a);
            } else {
                cpu.incIp();
            }
        }
    }

    public void print(PrintStream out) {
        memory.print(out);
        out.println("----------------------------");
        cpu.print(out);
        out.println("----------------------------");
    }
}

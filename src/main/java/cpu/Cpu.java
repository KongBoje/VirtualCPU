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
public class Cpu { //do with int for the same reasons, as memory.
    public static final int A = 0;
    public static final int B = 1;
    private int a = 0;
    private int b = 0;
    private int ip = 0;
    private int sp = 0;
    private boolean flag = false;
    private boolean isRunning = true;
    
    public void decSp(){
        if (sp == 0) sp = Memory.SIZE;
        sp = sp - 1;
    }
    
    public void incIp(){
        ip++;
        if (ip == Memory.SIZE) ip = 0;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    public int getSp() {
        return sp;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean IsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
    
    public void print(PrintStream out){
        out.printf("A:  %4d\n", a);
        out.printf("B:  %4d\n", b);
        out.printf("IP: %4d\n", ip);
        out.printf("SP: %4d\n", sp);
        out.printf("F:  "+flag);
        out.printf("\n");
    }
    
    public String name(int r){
        if(r == A) {
            return "A";
        } else {
            return "B";
        }
    } //Sytem.out.println("PUSH "+cpu.name(r))
}

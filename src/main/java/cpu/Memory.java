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
public class Memory {
    public static final int SIZE = 64;
    private final byte[] data = new byte[SIZE];
    
    public int get(int index) { // & makes it binary. 0b means that what comes after is binary. 255 = hexadecimal. 00377 = oqtal. also the same as writing 0b0000_0000_0000_0000_0000_0000_1111_1111. Where there are 0s it will be 0, where there are 1s it will be the same number as the one next to the 1s(1-0).
        int value = data[index]; // & is binary and: and on every bit in value and 0b1111_1111
        value = value & 255;
        return value;
    }
    
    public void set(int index, int value) {
        // TODO
        data[index] = (byte)(value & 0b1111_1111);
    }
    
    public String binary(int value){
        String result = ""; // might be 83 or 0b_0010_1011
        for(int i = 7; i >= 0; i--){ //00101011 = 76543210
            result += (value & (1 << i)) == 0 ? "0" : "1"; // i = 7, 00000001, 0b100010, 0_64.
        }
        return result;
    }
    
    public void print(PrintStream out, int index){ //on print out it should have 4 spaces == %4d. Have 2 places, space then 4 spaces.
        out.printf("%2d: %4d %s\t", index, get(index), binary(get(index))); // should get out as binary number.
    }
    
    public void print(PrintStream out){ //print out a memory.
        for(int index = 0; index < 16; index++){
            print(out, index);
            print(out, 16 + index);
            print(out, 32 + index);
            print(out, 48 + index);
            out.println();
        }
    }
}

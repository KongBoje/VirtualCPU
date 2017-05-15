/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
public class Example {
    public static void foo(int a, int b){
        System.out.println("foo called" + a + " " + b);
    }
    
    public static void main(String[] args) {
        System.out.println("Started");
        System.out.println("foo here");
        foo(7, 17);
        System.out.println("foo next");
        System.out.println("sub");
        foo(18, 25);
        System.out.println("Foo ended");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadedbitonic;

import static threadedbitonic.ThreadedBitonic.numberCount;

/**
 *
 * @author Wryd
 */
public class RandomNumberGenerator extends Thread{
    public int[] numbers = null;
    public RandomNumberGenerator(int[] target){
        numbers = target;
    }
    
    @Override
    public void run(){
        synchronized(this){
            numbers = new int[numberCount];
            for(int x = 0; x < numberCount; x++){
                numbers[x] = ((int)(Math.random() * ((numberCount - 1) + 1)));
                //System.out.print(numbers[x] + " ");
            }

            notify();
        }
    }
}

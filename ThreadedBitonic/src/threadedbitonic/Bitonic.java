/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadedbitonic;

import java.util.Scanner;

/**
 *
 * @author Wryd
 */
public class Bitonic{
    // Direction of the sorting network.
    public final static boolean
            ASCENDING = true,
            DESCENDING = false;
    
    // The sets that will be covered by each thread.
    public static int minimumLength = 1;
    
    // The numbers to sort. Will be used as shared resource for the threads.
    public static int[] numbers = null;
    
    // For measuring performance.
    private long startTime, endTime, elapsedTime;
    private int numberOfProcessors = 1;
    public static int threadUsed = 0;

    public void sort (boolean direction) {
        // Number of threads to use will be equal to the number of processors.
        minimumLength = numbers.length / numberOfProcessors;
        
        // Begin sort.
        sort (0, numbers.length, direction);
    }

    private void sort(int index, int size, boolean direction){
        
        // Start measuring execution time.
        startMeasure();
        
        //BitonicSortThread bt = new BitonicSortThread();
        //bt.sort(index, size, direction);
        BitonicSortRunnable btr = new BitonicSortRunnable();
        Thread bt = new Thread(btr);
        bt.start();
        btr.sort(index, size, direction);
        
        
        // End measuring.
        endMeasure();
    }
    
    public Bitonic(int[] numberSet){
        numbers = numberSet;
    }
    
    public static void compare(int x, int y, boolean direction) {
        //System.out.println("Comparing indices " + x + " and " + y + " with direction " + (direction ? "ASCENDING" : "DESCENDING"));
        if (direction == (numbers[x] > numbers[y])) {
            swap (x, y);
        }
    }

    public static void swap(int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
        
        //System.out.println("Swapped indices " + i + " and " + j + " values " + numbers[j] + " and " + numbers[i]);
        /*System.out.println("Now: " + numbers[0] + " "
            + numbers[1] + " "
            + numbers[2] + " "
            + numbers[3] + " "
            + numbers[4] + " "
            + numbers[5] + " "
            + numbers[6] + " "
            + numbers[7] + " ");*/
    }
    
    public void print(){
        if(numbers == null)
            return;
        
        System.out.println("\nFinished!");
        
        System.out.println("Processor count: " + numberOfProcessors);
        System.out.println("Total threads created: " + threadUsed);
        System.out.println("Sorting time: " + elapsedTime + " ms");
        
        System.out.println("");
        
    	// Print numbers?
        Scanner getInput = new Scanner(System.in);
       	System.out.println("Print the sorted numbers? (Y/N) ");
       	if(getInput.nextLine().toUpperCase().equals("Y")){
            System.out.println("\nPrinting...");

            for(int x = 0; x < numbers.length; x++){
                System.out.print(numbers[x] + " ");
            }
       	} 
        
        System.out.println("\nProgram ended successfully!");
    }
    
    public void startMeasure(){
        startTime = System.currentTimeMillis();
    }
    
    public void endMeasure(){
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
    }
}

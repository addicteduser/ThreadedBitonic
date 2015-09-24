/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadedbitonic;

/**
 *
 * @author Wryd
 * 
 * Heavily referenced from Mario Cossi's C# implementation of a multi threaded bitonic sorter.
 *  *      https://social.msdn.microsoft.com/Forums/en-US/bed08fc0-f29b-41d9-9898-ad6225b5a2b4/bitonic-sorter-multi-threaded-implementation?forum=netfxbcl
 * 
 * Other references:
 *  *      http://www.massey.ac.nz/~mjjohnso/notes/59735/myslides8.pdf
 *  *      http://www.tools-of-computing.com/tc/CS/Sorts/bitonic_sort.htm
 *  *      https://graphics.cg.uni-saarland.de/fileadmin/cguds/courses/ws1213/pp_cuda/slides/06_-_Sorting_in_Parallel.pdf (page 12)
 */

public class ThreadedBitonic {
    public static int[] numbers = null;
    
    // Recommended values: Power of 2 numbers. 
    // 2, 4, 8, ... , 4096, 8192, 16384, 32768, 65536, 131072, 524288, 1048576...
    public static int numberCount = 524288; 
    
    public static void main(String[] args) {
        System.out.println("Welcome to the ThreadedBitonic app.\n");
        
        // Generate the random numbers.
        RandomNumberGenerator generator = new RandomNumberGenerator(numbers);
        generator.start();
        
        // Begin bitonic sorting network immediately after generation.
        synchronized(generator){
            try{
                System.out.println("Generating " + numberCount + " random numbers for testing...");
                generator.wait();
                
                System.out.println("Done!\n");
                System.out.println("Now sorting. Please wait...");

                Bitonic b = new Bitonic(generator.numbers.clone());
                b.sort(Bitonic.ASCENDING);
                b.print();
            }
            catch(InterruptedException e){
                System.out.println("Execution failed.");
            }
        }
    }
}

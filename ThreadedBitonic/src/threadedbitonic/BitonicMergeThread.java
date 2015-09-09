/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadedbitonic;

/**
 *
 * @author Wryd
 */
public class BitonicMergeThread extends Thread{
    private int _index;
    private int _size;
    private boolean _direction;
    
    public BitonicMergeThread(){}
    
    public BitonicMergeThread(int index, int size, boolean direction){
        _index = index;
        _size = size;
        _direction = direction;
    }
    
    @Override
    public void run() {
        Bitonic.threadUsed++;
        merge(_index, _size, _direction);
    }
    
    public synchronized void merge(int index, int size, boolean direction)
    {
        if (size <= 1)
            return;
        
        int median = size / 2;
        for (int i = index; i < (index + median); i++) {
            Bitonic.compare (i, (i + median), direction);
        }

        if (size > Bitonic.minimumLength) {
            BitonicMergeThread bmtLeft = new BitonicMergeThread (index, median, direction);
            BitonicMergeThread bmtRight = new BitonicMergeThread (index + median, median, direction);
            bmtLeft.start ();
            bmtRight.start ();

            try{
                bmtLeft.join();
                bmtRight.join();
            }
            catch(InterruptedException e){
                System.out.println("Execution failed.");
            }
        } else {
            if (median > 1) {
                merge (index, median, direction);
                merge (index + median, median, direction);
            }
        }

    }
}

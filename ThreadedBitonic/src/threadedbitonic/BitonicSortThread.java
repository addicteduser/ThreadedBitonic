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
public class BitonicSortThread extends Thread {
    private int _index;
    private int _size;
    private boolean _direction;
    
    public BitonicSortThread(){}
    
    public BitonicSortThread(int index, int size, boolean direction){
        _index = index;
        _size = size;
        _direction = direction;
    }
    
    @Override
    public void run() {
        Bitonic.threadUsed++;
        sort(_index, _size, _direction);
    }
    
    public synchronized void sort(int index, int size, boolean direction){
        if (size <= 1)
            return;
        
        int median = size / 2;
        if (size > Bitonic.minimumLength) {
            BitonicSortThread btLeft = new BitonicSortThread(index, median, Bitonic.ASCENDING);
            BitonicSortThread btRight = new BitonicSortThread(index + median, median, Bitonic.DESCENDING);
            btLeft.start();
            btRight.start();
            try{
                btLeft.join();
                btRight.join();
            }
            catch(InterruptedException e){
                System.out.println("Execution failed.");
            }
        } else {
            if (median > 1) {
                sort (index, median, Bitonic.ASCENDING);
                sort (index + median, median, Bitonic.DESCENDING);
            }
        }

        BitonicMergeThread bmt = new BitonicMergeThread ();
        bmt.merge(index, size, direction);
    }
}

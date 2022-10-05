package oddeven;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * LinkedTransferQueue
 */
public class PrintOddEvenUseQueue {
    public static void main(String[] args) {
        NumQueue numQueue = new NumQueue();
        OddQueue oddQueue = new OddQueue(numQueue);
        EvenQueue evenQueue = new EvenQueue(numQueue);
        new Thread(oddQueue,"Odd").start();
        new Thread(evenQueue,"EVEN").start();

    }
}
class NumQueue {

    TransferQueue<Integer> queue = new LinkedTransferQueue<>();

    public int num;


    /**
     * 是否是偶数
     *
     * @return
     */
    public boolean isEven() {
        return (num & 1) == 0;
    }

    /**
     * 是否是奇数
     *
     * @return
     */
    public boolean isOdd() {
        return (num & 1) == 1;
    }
}

/**
 * 打印奇数
 */
class OddQueue implements Runnable {

    private NumQueue numQueue;

    public OddQueue(NumQueue numQueue) {
        this.numQueue = numQueue;
    }

    @Override
    public void run() {
        while (numQueue.num < 100) {

            if(!numQueue.isOdd()){
                try {
                    numQueue.queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ":num=" +  numQueue.num++);

            try {
                Thread.sleep(200);
                numQueue.queue.transfer(numQueue.num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }

    }
}

/**
 * 打印奇数
 */
class EvenQueue implements Runnable {

    private NumQueue numQueue;

    public EvenQueue(NumQueue numQueue) {
        this.numQueue = numQueue;
    }

    @Override
    public void run() {
        while (numQueue.num < 100) {
            if(!numQueue.isEven()){
                try {
                    numQueue.queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ":num=" +  numQueue.num++);
            try {
                Thread.sleep(200);
                numQueue.queue.transfer(numQueue.num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}


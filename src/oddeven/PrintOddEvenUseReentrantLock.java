package oddeven;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock
 */
public class PrintOddEvenUseReentrantLock {
    public static void main(String[] args) throws InterruptedException {

        NumShare numShare = new NumShare();
        OddPrint oddPrint = new OddPrint(numShare);
        EvenPrint evenPrint = new EvenPrint(numShare);
        new Thread(oddPrint, "odd").start();
        Thread.sleep(400);
        new Thread(evenPrint, "even").start();
    }


}

class NumShare {

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    public volatile int num;


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
class OddPrint implements Runnable {

    private NumShare share;

    public OddPrint(NumShare numShare) {
        this.share = numShare;
    }

    @Override
    public void run() {
        while (share.num < 100) {
            share.lock.lock();
            try {
                if (!share.isOdd()) {
                    share.condition.await();
                }

                System.out.println(Thread.currentThread().getName() + ":num=" + share.num++);
                Thread.sleep(200);
                share.condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                share.lock.unlock();
            }
        }

    }
}

/**
 * 打印奇数
 */
class EvenPrint implements Runnable {

    private NumShare share;

    public EvenPrint(NumShare numShare) {
        this.share = numShare;
    }

    @Override
    public void run() {
        while (share.num < 100) {
            share.lock.lock();
            try {
                if (!share.isEven()) {
                    share.condition.await();
                }
                System.out.println(Thread.currentThread().getName() + ":num=" + share.num++);
                Thread.sleep(200);
                share.condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                share.lock.unlock();
            }
        }

    }
}

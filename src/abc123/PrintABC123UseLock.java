package abc123;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABC123UseLock {
    public static void main(String[] args) {

        Res res = new Res();
        new Thread(() -> res.printABC()).start();

        new Thread(() -> res.print123()).start();

    }
}
class Res{

    char[] nums =  "1234567890".toCharArray();
    char[] chars = "ABCDEFGHIJ".toCharArray();
    Lock lock = new ReentrantLock();
    Condition abc = lock.newCondition();
    Condition num = lock.newCondition();
    /**
     * 先输出A
     */
    boolean flag = false;

    public void printABC(){
        for (char c :chars){
            lock.lock();
            try {
                if (flag){
                    num.await();
                }
                System.out.println(c);
                flag = !flag;
                abc.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
    }

    public void print123(){
        for (char c :nums){
            lock.lock();
            try {
                if (!flag){
                    abc.await();
                }
                System.out.println(c);
                flag = !flag;
                num.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
    }
}


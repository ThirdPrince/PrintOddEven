package oddeven;

/**
 * synchronized
 * 不推荐使用
 * 原因：不是线程交替打印，一个线程可以持续获取多次锁的，这里只不过用判断规避了这种情况而已。
 */
public class PrintOddEvenUseSync {
    public static void main(String[] args) {
        Num num = new Num();
        Even even = new Even(num);
        Odd odd = new Odd(num);
        new Thread(even, "EVEN").start();
        new Thread(odd, "ODD").start();
    }


}

class Even implements Runnable {
    private Num num;
    public Even(Num num) {
        this.num = num;
    }

    @Override
    public void run() {
        while (num.num < 1000) {
            synchronized (num) {
                if (num.isEven()) {
                    System.out.println(Thread.currentThread().getName() + ":" + num.num++);
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}

class Odd implements Runnable {
    private Num num;

    public Odd(Num num) {
        this.num = num;
    }

    @Override
    public void run() {
        while (num.num < 1000) {
            synchronized (num) {
                if (num.isOdd()) {
                    System.out.println(Thread.currentThread().getName() + ":" + num.num++);
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}


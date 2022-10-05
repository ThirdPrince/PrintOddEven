package oddeven;

/**
 * 使用wait notify
 * 线程间通讯
 * 推荐使用
 */
public class PrintOddEvenUseWait {
    public static void main(String[] args) {
        Num num = new Num();
        OddThread oddThread = new OddThread(num);
        EvenThread evenThread = new EvenThread(num);

        new Thread(oddThread,"ODD").start();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(evenThread,"EVEN").start();

    }

}

/**
 * 输出奇数
 */
class OddThread implements Runnable{

    private Num num;

    public OddThread(Num num){
        this.num = num;
    }

    @Override
    public void run() {
        while (num.num < 100){
            synchronized (num){
                if(!num.isOdd()){
                    try {
                        num.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName()+":num ="+num.num++);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                num.notify();

            }
        }
    }
}

/**
 * 输出偶数
 */
class EvenThread implements Runnable {
    private Num num;

    public EvenThread(Num num) {
        this.num = num;
    }

    @Override
    public void run() {
        while (num.num < 100) {
            synchronized (num) {
                if (!num.isEven()) {
                    try {
                        num.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName()+":"+num.num++);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                num.notify();
            }
        }
    }
}
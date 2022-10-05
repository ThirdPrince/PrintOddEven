package abcabc;

import java.util.Locale;

/**
 * 三个线程分别打印 A，B，C，要求这三个线程一起运行，打印 n 次，输出形如“ABCABCABC....”的字符串
 */
public class PrintABCUseSync {


    public static void main(String[] args) {

        Letter letter = new Letter(10);
        PrintABC printA = new PrintABC(letter, 0);
        PrintABC printB = new PrintABC(letter, 1);
        PrintABC printC = new PrintABC(letter, 2);
        new Thread(printA, "A").start();
        new Thread(printB, "B").start();
        new Thread(printC, "C").start();

    }


}


class Letter {
    public int times;
    /**
     * 0 -A
     * 1-B
     * 2-c
     */
    int letter;


    public Letter(int times) {
        this.times = times;
    }

    public int state;


}

class PrintABC implements Runnable {

    private Letter letter;
    /**
     * 0 -A
     * 1-B
     * 2-c
     */
    private int target;


    public PrintABC(Letter letter, int target) {
        this.letter = letter;
        this.target = target;
    }

    @Override
    public void run() {

        for (int i = 0; i < letter.times; i++) {
            synchronized (letter) {
                // 不能用if
                while (letter.state % 3 != target) {
                    try {
                        letter.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                letter.state++;
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                letter.notifyAll();
            }
        }
    }
}

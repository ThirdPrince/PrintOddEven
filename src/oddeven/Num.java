package oddeven;

/**
 * 共享资源
 * 线程间通信：
 * 其实就是多个线程操作同一个资源
 * 但是操作的工作不同
 *
 */
public class Num {
    public volatile int num = 0;

    /**
     * 是否是偶数
     * @return
     */
    public boolean isEven(){
        return (num & 1) == 0;
    }

    /**
     * 是否是奇数
     * @return
     */
    public boolean isOdd(){
        return (num & 1) == 1;
    }

}

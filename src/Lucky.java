public class Lucky {

    private static class xState{
        private static int x = 0;
        static synchronized int getX(){
            return x++;
        }
    }
    private static class countState{
        private static int count = 0;
        static synchronized void incCount(){
            count++;
        }

        public static synchronized int getCount() {
            return count;
        }
    }

    static class LuckyThread extends Thread {
        @Override
        public void run() {
            int x;
            while ((x = xState.getX()) < 999999) {
                if ((x % 10) + (x / 10) % 10 + (x / 100) % 10 == (x / 1000)
                        % 10 + (x / 10000) % 10 + (x / 100000) % 10) {
                    System.out.println(x);
                    countState.incCount();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new LuckyThread();
        Thread t2 = new LuckyThread();
        Thread t3 = new LuckyThread();
        long started = System.currentTimeMillis();
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        long finished = System.currentTimeMillis();
        System.out.println("Total: " + countState.getCount());
        System.out.println(finished - started);
    }
}
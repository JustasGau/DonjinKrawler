package donjinkrawler;

import donjinkrawler.logging.LoggerSingleton;

public class SingletonThreadSafetyTest {

    public static void main(String[] args) {
        Thread thread1 = new LoggerThread("thread1");
        Thread thread2 = new LoggerThread("thread2");
        thread1.start();
        thread2.start();
    }

    private static class LoggerThread extends Thread {

        private final String name;

        private LoggerThread(String name) {
            this.name = name;
        }

        public void run() {
            int i = 0;
            while (i < 5) {
                LoggerSingleton logger = LoggerSingleton.getInstance();
                logger.error(name + ": " + System.identityHashCode(logger));
                i++;
            }
        }
    }
}

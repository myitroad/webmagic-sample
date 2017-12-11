package cfda.drugs;

import cfda.DrugNameConsumer;
import cfda.DrugNameProducter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class DrugNameProducterTest {
    @org.junit.Test
    public void doWork() throws Exception {
        /*BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>();


        Thread threadConsumer = new Thread(){
            @Override
            public void run() {
                DrugNameConsumer consumer = new DrugNameConsumer(blockingQueue);
                consumer.run();
            }
        };
        threadConsumer.start();

        Thread threadProducter = new Thread(){
            @Override
            public void run() {
                DrugNameProducter producter = new DrugNameProducter(blockingQueue);
                producter.run();
            }
        };
        threadProducter.start();

        threadProducter.join();
        threadConsumer.join();*/

    }

}

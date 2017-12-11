package app;

import cfda.DrugNameConsumer;
import cfda.DrugNameProducter;

import java.util.concurrent.*;

/**
 * @author LTN
 * Created at 2017/12/12 0:59
 * 运行爬取食药监http://app2.sfda.gov.cn/datasearchp/gzcxSearch.do?formRender=cx&optionType=V1页面数据的入口
 */
public class App {
    public static void main(String[] args) throws Exception {

        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new DrugNameProducter(blockingQueue));
        executorService.submit(new DrugNameConsumer(blockingQueue));
    }
}

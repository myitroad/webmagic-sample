package cfda;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

/**
 * Created by liutingna on 2017/12/11 23:30.
 *
 * @author liutingna
 * <p>
 * 药品名称生产任务
 */
public class DrugNameProducter implements Runnable {
    private static BlockingQueue<String> blockingQueue;

    public DrugNameProducter(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    class AllDrugListPageProcessor implements PageProcessor {

        private int size = 0;// 共抓取到的药品数量

        // 抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
        private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

        @Override
        public Site getSite() {
            return site;
        }

        @Override
        // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
        public void process(Page page) {
            List<String> urls = page.getUrl().regex("http://app2.sfda.gov.cn/datasearchp/gzcxSearch.do\\?page=\\d+&searchcx=&optionType=V1&paramter0=null&paramter1=null&paramter2=null&formRender=cx").all();

            if (!urls.isEmpty()) {
                //添加药品列表页面地址
                //gzcxSearch.do?page=4&amp;searchcx=&amp;optionType=V1&amp;paramter0=null&amp;paramter1=null&amp;paramter2=null&amp;formRender=cx
                List<String> rawListUrls = page.getHtml().regex("gzcxSearch.do\\?page=\\d+&amp;searchcx=&amp;optionType=V1&amp;paramter0=null&amp;paramter1=null&amp;paramter2=null&amp;formRender=cx").all();
                List<String> listUrls = rawListUrls.stream().map(item -> "http://app2.sfda.gov.cn/datasearchp/" + item.replace("&amp;", "&")).collect(Collectors.toList());
                page.addTargetRequests(listUrls);
                // 获取药品名称，注意该xpath表达式存储药品名称为空的情况
                List<String> drugNames = page.getHtml().xpath("/html/body/center/table[4]/tbody/tr[2]/td/center/table/tbody/tr[3]/td/table[1]/tbody/*/td[1]/text()").all();
                //过滤掉空的药品名称

                List<String> refinedDrugNames = drugNames.stream().filter(item -> !item.isEmpty()).collect(Collectors.toList());
                try {
                    for (String name : refinedDrugNames) {
                        blockingQueue.put(name);
                    }
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }
    }

    @Override
    public void run() {
        long startTime, endTime;
        System.out.println("【药品名称爬虫开始】...");
        startTime = System.currentTimeMillis();
        // 从首页开始抓，开启5个线程，启动爬虫
        Spider.create(new AllDrugListPageProcessor()).addUrl("http://app2.sfda.gov.cn/datasearchp/gzcxSearch.do?page=1&searchcx=&optionType=V1&paramter0=null&paramter1=null&paramter2=null&formRender=cx").thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("【药品名称爬虫结束】共抓取x条数据，耗时约" + ((endTime - startTime) / 1000) + "秒！");
    }
}

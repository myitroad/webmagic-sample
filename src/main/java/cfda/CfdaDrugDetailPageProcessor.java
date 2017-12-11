package cfda;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liutingna on 2017/12/11.
 *
 * @author liutingna
 */
public class CfdaDrugDetailPageProcessor implements PageProcessor {

    private static int size = 0;// 共抓取到的药品数量

    // 抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        List<String> urls = page.getUrl().regex("http://app2.sfda.gov.cn/datasearchp/index1.do\\?tableId=25&tableName=TABLE25&tableView=国产药品&Id=\\d+").all();

        if (urls.isEmpty()) {
            //添加药品列表页面地址
            List<String> rawListUrls = page.getHtml().regex("all.do\\?page=\\d+&amp;name=葡萄糖注射液&amp;tableName=TABLE25&amp;formRender=cx&amp;searchcx=&amp;paramter0=&amp;paramter1=&amp;paramter2=").all();
            List<String> listUrls = rawListUrls.stream().map(item-> "http://app2.sfda.gov.cn/datasearchp/"+item.replace("&amp;","&")).collect(Collectors.toList());
            page.addTargetRequests(listUrls);
            // 添加药品详细页面地址
            List<String> rawDetailUrls = page.getHtml().regex("datasearchp/index1.do\\?tableId=25&amp;tableName=TABLE25&amp;tableView=国产药品&amp;Id=\\d+").all();
            List<String> detailUrls = rawDetailUrls.stream().map(item -> "http://app2.sfda.gov.cn/" + item.replace("&amp;","&")).collect(Collectors.toList());
            page.addTargetRequests(detailUrls);
        } else{
            size++;// 数量加1
            DrugDetailInfo drugDetailInfo = new DrugDetailInfo();
            // 使用XPath获取药品详细内容。这里以获取批准文号、产品名称、公司编号以及生产地址为例
            drugDetailInfo.setDrugBatchNum(page.getHtml().xpath("/html/body/center/table[2]/tbody/tr/td/table[2]/tbody/tr/td/table/tbody/tr[1]/td[1]/text()").get());
            drugDetailInfo.setDrugProductName(page.getHtml().xpath("/html/body/center/table[2]/tbody/tr/td/table[2]/tbody/tr/td/table/tbody/tr[1]/td[2]/text()").get());
            drugDetailInfo.setCompanyNum(page.getHtml().xpath("/html/body/center/table[2]/tbody/tr/td/table[5]/tbody/tr/td/table[1]/tbody/tr[1]/td[1]/text()").get());
            drugDetailInfo.setCompanyAddr(page.getHtml().xpath("/html/body/center/table[2]/tbody/tr/td/table[5]/tbody/tr/td/table[1]/tbody/tr[1]/td[2]/text()").get());
            //可定制输出，这里仅仅将其打印出来
            System.out.println(drugDetailInfo);
        }
    }

    public static void main(String[] args) {
        long startTime, endTime;
        System.out.println("【爬虫开始】...");
        startTime = System.currentTimeMillis();
        // 从首页开始抓，开启5个线程，启动爬虫
        Spider.create(new CfdaDrugDetailPageProcessor()).addUrl("http://app2.sfda.gov.cn/datasearchp/all.do?page=1&name=葡萄糖注射液&tableName=TABLE25&formRender=cx&searchcx=&paramter0=&paramter1=&paramter2=").thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】共抓取" + size + "条数据，耗时约" + ((endTime - startTime) / 1000) + "秒！");
    }
}

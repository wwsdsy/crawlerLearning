package cn.idcast.jd.task;

import cn.idcast.jd.pojo.Item;
import cn.idcast.jd.service.ItemService;
import cn.idcast.jd.util.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ItemTask {

    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private ItemService itemService;

    @Scheduled(fixedDelay = 100*1000)    //当下载任务完成后，间隔多长时间进行下一次的任务。每隔100秒
    public void itemTask() throws Exception{
        String url ="https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&cid2=653&cid3=655&s=160&click=0&page=";
        //按照页面对手机搜索结果进行遍历解析
        for(int i=1; i<10;i=i+2){
            String html = httpUtils.doGetHtml(url+i);

            //解析页面，获取商品数据并存储
            this.parse(html);

        }
        System.out.println("手机数据抓取成功");
    }


    private void parse(String html) {
        //解析html 获取Document
        Document doc = Jsoup.parse(html);
        //获取spu
        Elements spuElements = doc.select("div#J_goodsList>ul>li");
        //
        for(Element spuEle : spuElements){
            // 获取spu
            Long spu = Long .parseLong(spuEle.attr("data-spu"));
            //获取sku
            Elements skuEles = spuEle.select("li.ps-item");
            for (Element skuEle:skuEles){
                //获取sku
                Long sku = Long.parseLong(skuEle.select("[data-sku]").attr("data-sku"));

                //根据sku查询商品数据
                Item item = new Item();
                item.setSku(sku);
                List<Item> list = this.itemService.findAll(item);

                if(list.size()>0){
                    //如果商品存在，就进行下一个循环，因为商品已存在
                    continue;
                }
                //设置商品的spu
                item.setSpu(spu);

                //获取商品的详情的url
                String itemUrl = "https://item.jd.com/"+sku+"html";
                item.setUrl(itemUrl);

                //获取商品图片
                String picUrl = skuEle.select("img[data-sku]").first().attr("src");
//                item.setPic();
//
//                //获取商品价格
//                item.setPrice();
//
//                item.setTitle();

                item.setCreated(new Date());
                item.setUpdated(item.getCreated());

            }
        }
        //
    }
}

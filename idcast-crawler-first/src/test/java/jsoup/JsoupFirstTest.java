package jsoup;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Set;


//抓取到页面后需要对页面进行解析。可以使用字符串处理工具解析页面，也可以使用正则表达式，但这些方法开发成本高。需要一筐专门解析html页面的技术
//Jsoup是一款java的HTML解析器，可直接解析某个URL地址、HTML文本内容。提供了一套非常省力的API，可通过DOM，CSS以及类似于jQuery的操作方法取出和操作数据

//虽然Jsoup可替代HttpClient直接发起请求解析数据，但是往往不会这样用，因为在实际开发过程中，需要使用到多线程，连接池，代理等方式，
//而jsoup对这些的支持并不是很好，所以我们一般把jsoup仅仅作为html解析工具使用
public class JsoupFirstTest {

    @Test
    public void testUrl() throws Exception{
        //解析url地址，第一个参数是访问的url，第二个参数是访问时候的超时时间
        Document doc = Jsoup.parse(new URL("http://www.itcast.cn"),1000);

        //使用标签选择器，获取title标签中的内容
        String title = doc.getElementsByTag("title").first().text();

        //打印
        System.out.println(title);

    }

    @Test
    public void testString() throws Exception{
        //使用工具类读取文件，获取字符串
        String content = FileUtils.readFileToString(new File("C:\\Users\\tree\\Desktop\\test.html"),"utf-8");

        //解析字符串
        Document doc = Jsoup.parse(content);

        String title = doc.getElementsByTag("title").first().text();

        System.out.println(title);
    }

    @Test
    public void testFile() throws Exception{
        //解析文件
        Document doc =Jsoup.parse(new File("C:\\Users\\tree\\Desktop\\test.html"),"UTF-8");

        String title = doc.getElementsByTag("title").first().text();

        System.out.println(title);
    }

    @Test
    public void testDom() throws Exception{
        //解析文件，获取Document对象
        Document doc = Jsoup.parse(new File("C:\\Users\\tree\\Desktop\\test.html"), "UTF-8");

        //获取元素
        //根据id查询元素  getElementsById
        Element element = doc.getElementById("city_bj");

        //根据标签tag获取元素getElementsByTag
        Element element1 = doc.getElementsByTag("span").first();

        //根据class获取元素getElementsByClass
        Element element2 = doc.getElementsByClass("class_a").first();

        //根据属性获取元素getElementsByAttribute     getElementsByAttributeValue(key,value)
        Element element3 = doc.getElementsByAttribute("abc").first();

        //打印元素内容
        System.out.println(element.text());

    }

    //从元素中获取数据
    @Test
    public void testData() throws Exception{
        //解析文件获取Document
        Document doc = Jsoup.parse(new File("C:\\Users\\test.html"), "UTF-8");
        //根据id获取元素
        Element element = doc.getElementById("city_bj");

        String str = "";
        //元素中获取数据
        //从元素中获取id
        str = element.id();
        //从元素中获取className
        str = element.className();
        Set<String> classSet = element.classNames();
        for (String s : classSet){
            System.out.println(s);
        }
        //从元素中获取属性的值attr
        str=element.attr("class");
        //从元素中获取所有属性attributes
        Attributes attributes = element.attributes();
        //从元素中获取文本内容text
        str = element.text();

    }

    /*
    使用组合选择器获取元素
    el#id: 元素+ID，比如：h3#city_bj
    el.class: 元素+class,比如：li.class_a
    el[attr] : 元素+属性名，比如：span[abc]
    任意组合：比如：span[abc].s_name
    ancestor child: 查找某个元素下子元素，比如：。city_con li 查找“city_con”下的所有li
    parent>child:查找某个父元素下的 直接 子元素，比如：.city_con >ul > li 查找city_con第一级（直接子元素）的ul，再找所有ul下的第一级li
    parent>*:查找某个父元素下所有直接子元素
     */
    @Test
    public void selectorTest() throws Exception{
        //解析文件，获取Document对象
        Document doc = Jsoup.parse(new File("C:\\Users\\test.html"), "UTF-8");

        Element element = doc.select("h3#city_bj").first();

        Element element1 = doc.select("h1.hp").first();



        Elements elements = doc.select(".topbar>*");

        System.out.println(elements.text());

    }
/*
案例：结合HttpClient和Jsoup，掌握如何抓取数据和解析数据
需求分析：爬取京东页面商品数据：商品图片、价格、标题、商品详情页



 */
}

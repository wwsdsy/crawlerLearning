package cn.idcast.jd.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Component
// 实现HttpClient封装
public class HttpUtils {

    private PoolingHttpClientConnectionManager clientConnectionManager; //连接池管理器

    public HttpUtils(){
        this.clientConnectionManager = new PoolingHttpClientConnectionManager();

        //设置最大连接数
        this.clientConnectionManager.setMaxTotal(100);
        //设置每个主机的最大连接数
        this.clientConnectionManager.setDefaultMaxPerRoute(10);
    }

    /**
     * 根据请求地址下载页面数据
     * @param url
     * @return 页面数据
     */
    public String doGetHtml(String url){
        //获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.clientConnectionManager).build();
        //创建HttpClient请求对象，设置url地址
        HttpGet httpGet =new HttpGet(url);

        //设置请求信息
        httpGet.setConfig(this.getConfig());

        //使用httpClient发起请求，获取响应
        CloseableHttpResponse response=null;
        try {
            response = httpClient.execute(httpGet);
            //解析响应，返回结果
            if (response.getStatusLine().getStatusCode() ==200){
                //判断响应体Entity是否不为空，如果不为空就可以使用EntityUtils
                if (response.getEntity() !=null){
                    String content = EntityUtils.toString(response.getEntity(), "utf-8");
                    return content;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭response
            if(response !=null){
                try {
                    response.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 下载图片
     * @param url
     * @return 图片名称
     */
    public String doGetImage(String url){
        //获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.clientConnectionManager).build();
        //创建HttpClient请求对象，设置url地址
        HttpGet httpGet =new HttpGet(url);

        //设置请求信息
        httpGet.setConfig(this.getConfig());

        //使用httpClient发起请求，获取响应
        CloseableHttpResponse response=null;
        try {
            response = httpClient.execute(httpGet);
            //解析响应，返回结果
            if (response.getStatusLine().getStatusCode() ==200){
                //判断响应体Entity是否不为空，如果不为空就可以使用EntityUtils
                if (response.getEntity() !=null){
                    //下载图片
                    //获取图片后缀
                    String extName = url.substring(url.lastIndexOf("."));
                    //创建图片名，重命名图片
                    String picName = UUID.randomUUID().toString()+extName;
                    //下载图片

                    //声明OutPutStream    快捷键ctrl+h查看类或接口的继承关系
                    OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\wws16\\Desktop\\a\\images\\"+picName));

                    response.getEntity().writeTo(outputStream);
                    //返回图片名称
                    return picName;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭response
            if(response !=null){
                try {
                    response.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        //如果
        return "";

    }

    //设置请求信息
    private RequestConfig getConfig() {
        RequestConfig config =RequestConfig.custom()
                .setConnectTimeout(1000)   //创建连接的最长时间
                .setConnectionRequestTimeout(500)    //获取连接的最长时间
                .setSocketTimeout(10000)  //数据传输的最长时间
                .build();
        return config;
    }
}

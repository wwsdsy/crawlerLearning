package cn.idcast.crawler.test;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class HttpPostParamTest {
    public static void main(String[] args) throws Exception {
        //创建HttpClient对象
        CloseableHttpClient httpClient =  HttpClients.createDefault();

//
//        //创建URIBuilder
//        URIBuilder uriBuilder = new URIBuilder("http://yun.itheima.com/search");
//        //设置参数,多参数多次set
//        uriBuilder.setParameter("key","java");
//        //创建HttpPOST对象，设置url访问地址
//        HttpPost httpPost = new HttpPost(uriBuilder.build());


        //创建HttpPost对象，设置url访问地址
        HttpPost httpPost = new HttpPost("http://yun.itheima.com/search");
        //POST表单方式请求
        //声明list集合，封装表单中的参数
        List<NameValuePair> params = new ArrayList<NameValuePair>(); //NameValuePair接口
        params.add(new BasicNameValuePair("keys","java"));
        //创建表单的Entity对象.第一个参数就是封装好的表单数据，第二个参数就是编码
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params,"UTF-8");
        //设置表单的Entity对象到POST请求中
        httpPost.setEntity(formEntity);

        CloseableHttpResponse response = null;
        try {
            //使用HttpClient发起请求，获取response
             response = httpClient.execute(httpPost);

            //解析响应
            if(response.getStatusLine().getStatusCode() == 200){
                String content = EntityUtils.toString(response.getEntity());
                System.out.println(content.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭response
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }
}

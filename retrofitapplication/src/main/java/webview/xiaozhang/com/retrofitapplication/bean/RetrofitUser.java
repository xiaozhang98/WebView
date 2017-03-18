package webview.xiaozhang.com.retrofitapplication.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/18.
 */

public class RetrofitUser implements Serializable{
    private String name;
    private String url;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "RetrofitUser{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

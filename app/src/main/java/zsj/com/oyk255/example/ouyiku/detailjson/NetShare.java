package zsj.com.oyk255.example.ouyiku.detailjson;

import com.google.gson.annotations.Expose;
/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class NetShare {
    @Expose
    private String share_url;
    @Expose
    private String content;
    @Expose
    private String share_img;
    @Expose
    private String share_title;

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShare_img() {
        return share_img;
    }

    public void setShare_img(String share_img) {
        this.share_img = share_img;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }
}

package zsj.com.oyk255.example.ouyiku.personcenterjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonMessageDatum {

    @SerializedName("news_id")
    @Expose
    private String newsId;
    @Expose
    private String title;
    @Expose
    private String detail;
    @SerializedName("news_url")
    @Expose
    private String newsUrl;
    @Expose
    private String time;

    /**
     * 
     * @return
     *     The newsId
     */
    public String getNewsId() {
        return newsId;
    }

    /**
     * 
     * @param newsId
     *     The news_id
     */
    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * 
     * @param detail
     *     The detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * 
     * @return
     *     The newsUrl
     */
    public String getNewsUrl() {
        return newsUrl;
    }

    /**
     * 
     * @param newsUrl
     *     The news_url
     */
    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    /**
     * 
     * @return
     *     The time
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(String time) {
        this.time = time;
    }

}

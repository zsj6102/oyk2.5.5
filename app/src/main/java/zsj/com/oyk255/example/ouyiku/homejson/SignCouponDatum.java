package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;

public class SignCouponDatum {

    @Expose
    private String img;
    @Expose
    private String content;
    @Expose
    private String getid;
    @Expose
    private String status;

    /**
     * 
     * @return
     *     The img
     */
    public String getImg() {
        return img;
    }

    /**
     * 
     * @param img
     *     The img
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The getid
     */
    public String getGetid() {
        return getid;
    }

    /**
     * 
     * @param getid
     *     The getid
     */
    public void setGetid(String getid) {
        this.getid = getid;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}

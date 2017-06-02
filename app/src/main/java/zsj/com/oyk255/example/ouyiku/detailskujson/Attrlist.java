package zsj.com.oyk255.example.ouyiku.detailskujson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attrlist {

    @SerializedName("skunature_id")
    @Expose
    private String skunatureId;
    @Expose
    private String aname;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;

    /**
     * 
     * @return
     *     The skunatureId
     */
    public String getSkunatureId() {
        return skunatureId;
    }

    /**
     * 
     * @param skunatureId
     *     The skunature_id
     */
    public void setSkunatureId(String skunatureId) {
        this.skunatureId = skunatureId;
    }

    /**
     * 
     * @return
     *     The aname
     */
    public String getAname() {
        return aname;
    }

    /**
     * 
     * @param aname
     *     The aname
     */
    public void setAname(String aname) {
        this.aname = aname;
    }

    /**
     * 
     * @return
     *     The imgUrl
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 
     * @param imgUrl
     *     The img_url
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}

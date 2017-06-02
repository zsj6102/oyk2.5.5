package zsj.com.oyk255.example.ouyiku.hotjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("cattalent_id")
    @Expose
    private String cattalentId;
    @SerializedName("cattalent_name")
    @Expose
    private String cattalentName;
    @SerializedName("cattalent_pic_url")
    @Expose
    private String cattalentPicUrl;

    /**
     * 
     * @return
     *     The cattalentId
     */
    public String getCattalentId() {
        return cattalentId;
    }

    /**
     * 
     * @param cattalentId
     *     The cattalent_id
     */
    public void setCattalentId(String cattalentId) {
        this.cattalentId = cattalentId;
    }

    /**
     * 
     * @return
     *     The cattalentName
     */
    public String getCattalentName() {
        return cattalentName;
    }

    /**
     * 
     * @param cattalentName
     *     The cattalent_name
     */
    public void setCattalentName(String cattalentName) {
        this.cattalentName = cattalentName;
    }

    /**
     * 
     * @return
     *     The cattalentPicUrl
     */
    public String getCattalentPicUrl() {
        return cattalentPicUrl;
    }

    /**
     * 
     * @param cattalentPicUrl
     *     The cattalent_pic_url
     */
    public void setCattalentPicUrl(String cattalentPicUrl) {
        this.cattalentPicUrl = cattalentPicUrl;
    }

}

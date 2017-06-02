package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PinTuanBannerData {

    @SerializedName("pic_url")
    @Expose
    private String picUrl;

    /**
     * 
     * @return
     *     The picUrl
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * 
     * @param picUrl
     *     The pic_url
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

}

package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("pic_url")
    @Expose
    private String picUrl;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;

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

    /**
     * 
     * @return
     *     The merchantId
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * 
     * @param merchantId
     *     The merchant_id
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

}

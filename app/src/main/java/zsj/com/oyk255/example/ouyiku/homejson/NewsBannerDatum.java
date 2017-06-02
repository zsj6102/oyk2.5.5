package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsBannerDatum {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("pic_url")
    @Expose
    private String picUrl;

    /**
     * 
     * @return
     *     The productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 
     * @param productId
     *     The product_id
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

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

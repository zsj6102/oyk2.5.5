package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brand {

    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @Expose
    private String title;
    @Expose
    private String logo;

    /**
     * 
     * @return
     *     The brandId
     */
    public String getBrandId() {
        return brandId;
    }

    /**
     * 
     * @param brandId
     *     The brand_id
     */
    public void setBrandId(String brandId) {
        this.brandId = brandId;
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
     *     The logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 
     * @param logo
     *     The logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

}

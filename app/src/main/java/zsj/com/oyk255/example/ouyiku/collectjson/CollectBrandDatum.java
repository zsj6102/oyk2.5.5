package zsj.com.oyk255.example.ouyiku.collectjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CollectBrandDatum {

    @Expose
    private String title;
    @Expose
    private String logo;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @SerializedName("collect_id")
    @Expose
    private String collectId;
    @Expose
    private String xcount;
    @Expose
    private String pcount;
    @Expose
    private String scount;

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
     *     The collectId
     */
    public String getCollectId() {
        return collectId;
    }

    /**
     * 
     * @param collectId
     *     The collect_id
     */
    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    /**
     * 
     * @return
     *     The xcount
     */
    public String getXcount() {
        return xcount;
    }

    /**
     * 
     * @param xcount
     *     The xcount
     */
    public void setXcount(String xcount) {
        this.xcount = xcount;
    }

    /**
     * 
     * @return
     *     The pcount
     */
    public String getPcount() {
        return pcount;
    }

    /**
     * 
     * @param pcount
     *     The pcount
     */
    public void setPcount(String pcount) {
        this.pcount = pcount;
    }

    /**
     * 
     * @return
     *     The scount
     */
    public String getScount() {
        return scount;
    }

    /**
     * 
     * @param scount
     *     The scount
     */
    public void setScount(String scount) {
        this.scount = scount;
    }

}

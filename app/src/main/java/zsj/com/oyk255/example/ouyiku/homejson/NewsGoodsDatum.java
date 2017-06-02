package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsGoodsDatum {

    @SerializedName("activite_id")
    @Expose
    private String activiteId;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @Expose
    private String title;
    @Expose
    private String logo;
    @SerializedName("brandmerchant_id")
    @Expose
    private String brandmerchantId;
    @Expose
    private String photo;
    @Expose
    private String begintime;
    @Expose
    private String rebate;
    @Expose
    private String time;

    public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	/**
     * 
     * @return
     *     The activiteId
     */
    public String getActiviteId() {
        return activiteId;
    }

    /**
     * 
     * @param activiteId
     *     The activite_id
     */
    public void setActiviteId(String activiteId) {
        this.activiteId = activiteId;
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
     *     The brandmerchantId
     */
    public String getBrandmerchantId() {
        return brandmerchantId;
    }

    /**
     * 
     * @param brandmerchantId
     *     The brandmerchant_id
     */
    public void setBrandmerchantId(String brandmerchantId) {
        this.brandmerchantId = brandmerchantId;
    }

    /**
     * 
     * @return
     *     The photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 
     * @param photo
     *     The photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * 
     * @return
     *     The begintime
     */
    public String getBegintime() {
        return begintime;
    }

    /**
     * 
     * @param begintime
     *     The begintime
     */
    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    /**
     * 
     * @return
     *     The rebate
     */
    public String getRebate() {
        return rebate;
    }

    /**
     * 
     * @param rebate
     *     The rebate
     */
    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

}

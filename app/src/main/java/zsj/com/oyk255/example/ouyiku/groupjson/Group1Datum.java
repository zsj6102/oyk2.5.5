package zsj.com.oyk255.example.ouyiku.groupjson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Group1Datum {

    @SerializedName("b_url")
    @Expose
    private String bUrl;
    @SerializedName("m_name")
    @Expose
    private String mName;
    @SerializedName("m_detail")
    @Expose
    private String mDetail;
    @SerializedName("pcategory_id")
    @Expose
    private String pcategoryId;

    /**
     * 
     * @return
     *     The bUrl
     */
    public String getBUrl() {
        return bUrl;
    }

    /**
     * 
     * @param bUrl
     *     The b_url
     */
    public void setBUrl(String bUrl) {
        this.bUrl = bUrl;
    }

    /**
     * 
     * @return
     *     The mName
     */
    public String getMName() {
        return mName;
    }

    /**
     * 
     * @param mName
     *     The m_name
     */
    public void setMName(String mName) {
        this.mName = mName;
    }

    /**
     * 
     * @return
     *     The mDetail
     */
    public String getMDetail() {
        return mDetail;
    }

    /**
     * 
     * @param mDetail
     *     The m_detail
     */
    public void setMDetail(String mDetail) {
        this.mDetail = mDetail;
    }

    /**
     * 
     * @return
     *     The pcategoryId
     */
    public String getPcategoryId() {
        return pcategoryId;
    }

    /**
     * 
     * @param pcategoryId
     *     The pcategory_id
     */
    public void setPcategoryId(String pcategoryId) {
        this.pcategoryId = pcategoryId;
    }

}

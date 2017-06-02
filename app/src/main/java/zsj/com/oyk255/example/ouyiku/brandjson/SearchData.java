package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchData {

    @Expose
    private String tid;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @Expose
    private String status;

    /**
     * 
     * @return
     *     The tid
     */
    public String getTid() {
        return tid;
    }

    /**
     * 
     * @param tid
     *     The tid
     */
    public void setTid(String tid) {
        this.tid = tid;
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

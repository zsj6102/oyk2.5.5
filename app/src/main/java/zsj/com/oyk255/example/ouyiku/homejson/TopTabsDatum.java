package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopTabsDatum {

    @SerializedName("pcategory_id")
    @Expose
    private String pcategoryId;
    @SerializedName("m_name")
    @Expose
    private String mName;

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

}

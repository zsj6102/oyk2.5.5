package zsj.com.oyk255.example.ouyiku.personcenterjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FirstAdressDatum {

    @SerializedName("area_id")
    @Expose
    private String areaId;
    @Expose
    private String name;

    /**
     * 
     * @return
     *     The areaId
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * 
     * @param areaId
     *     The area_id
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

}

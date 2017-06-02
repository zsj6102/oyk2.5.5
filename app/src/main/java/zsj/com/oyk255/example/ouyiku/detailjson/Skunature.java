package zsj.com.oyk255.example.ouyiku.detailjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Skunature {

    @SerializedName("attr_name")
    @Expose
    private String attrName;
    @SerializedName("attr_val")
    @Expose
    private List<String> attrVal = new ArrayList<String>();

    /**
     * 
     * @return
     *     The attrName
     */
    public String getAttrName() {
        return attrName;
    }

    /**
     * 
     * @param attrName
     *     The attr_name
     */
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    /**
     * 
     * @return
     *     The attrVal
     */
    public List<String> getAttrVal() {
        return attrVal;
    }

    /**
     * 
     * @param attrVal
     *     The attr_val
     */
    public void setAttrVal(List<String> attrVal) {
        this.attrVal = attrVal;
    }

}

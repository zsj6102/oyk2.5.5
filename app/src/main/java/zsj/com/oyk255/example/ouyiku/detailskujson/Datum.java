package zsj.com.oyk255.example.ouyiku.detailskujson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("attribute_id")
    @Expose
    private String attributeId;
    @Expose
    private String name;
    @Expose
    private List<Attrlist> attrlist = new ArrayList<Attrlist>();

    /**
     * 
     * @return
     *     The attributeId
     */
    public String getAttributeId() {
        return attributeId;
    }

    /**
     * 
     * @param attributeId
     *     The attribute_id
     */
    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
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

    /**
     * 
     * @return
     *     The attrlist
     */
    public List<Attrlist> getAttrlist() {
        return attrlist;
    }

    /**
     * 
     * @param attrlist
     *     The attrlist
     */
    public void setAttrlist(List<Attrlist> attrlist) {
        this.attrlist = attrlist;
    }

}

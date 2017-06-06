package zsj.com.oyk255.example.ouyiku.detailjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import zsj.com.oyk255.example.ouyiku.detailskujson.Attrlist;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class Netskudata {
    @SerializedName("attribute_id")
    @Expose
    private String attribute_id;
    @Expose
    private String name;
    @Expose
    private List<Attrlist> attrlist = new ArrayList<Attrlist>();

    public String getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(String attribute_id) {
        this.attribute_id = attribute_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attrlist> getAttrlist() {
        return attrlist;
    }

    public void setAttrlist(List<Attrlist> attrlist) {
        this.attrlist = attrlist;
    }
}

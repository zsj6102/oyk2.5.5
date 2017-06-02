package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class TopListData {

    @Expose
    private java.util.List<ProductInfo> list = new ArrayList<ProductInfo>();
    @Expose
    private String img;

    /**
     * 
     * @return
     *     The list
     */
    public java.util.List<ProductInfo> getList() {
        return list;
    }

    /**
     * 
     * @param list
     *     The list
     */
    public void setList(java.util.List<ProductInfo> list) {
        this.list = list;
    }

    /**
     * 
     * @return
     *     The img
     */
    public String getImg() {
        return img;
    }

    /**
     * 
     * @param img
     *     The img
     */
    public void setImg(String img) {
        this.img = img;
    }

}

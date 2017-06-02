package zsj.com.oyk255.example.ouyiku.homejson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class SeaShopDatum {

    @Expose
    private String name;
    @Expose
    private String imgurl;
    @Expose
    private List<Product> product = new ArrayList<Product>();

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
     *     The imgurl
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * 
     * @param imgurl
     *     The imgurl
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    /**
     * 
     * @return
     *     The product
     */
    public List<Product> getProduct() {
        return product;
    }

    /**
     * 
     * @param product
     *     The product
     */
    public void setProduct(List<Product> product) {
        this.product = product;
    }

}

package zsj.com.oyk255.example.ouyiku.detailskujson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stock {

    @SerializedName("use_id")
    @Expose
    private String useId;
    @SerializedName("skunature_id")
    @Expose
    private String skunatureId;
    @Expose
    private String price;
    @Expose
    private String stock;

    /**
     * 
     * @return
     *     The useId
     */
    public String getUseId() {
        return useId;
    }

    /**
     * 
     * @param useId
     *     The use_id
     */
    public void setUseId(String useId) {
        this.useId = useId;
    }

    /**
     * 
     * @return
     *     The skunatureId
     */
    public String getSkunatureId() {
        return skunatureId;
    }

    /**
     * 
     * @param skunatureId
     *     The skunature_id
     */
    public void setSkunatureId(String skunatureId) {
        this.skunatureId = skunatureId;
    }

    /**
     * 
     * @return
     *     The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The stock
     */
    public String getStock() {
        return stock;
    }

    /**
     * 
     * @param stock
     *     The stock
     */
    public void setStock(String stock) {
        this.stock = stock;
    }

}

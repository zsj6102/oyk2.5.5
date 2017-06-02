package zsj.com.oyk255.example.ouyiku.detailskujson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class DetailSKU {

    @Expose
    private List<Datum> data = new ArrayList<Datum>();
    @Expose
    private List<Stock> stock = new ArrayList<Stock>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The stock
     */
    public List<Stock> getStock() {
        return stock;
    }

    /**
     * 
     * @param stock
     *     The stock
     */
    public void setStock(List<Stock> stock) {
        this.stock = stock;
    }

    /**
     * 
     * @return
     *     The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

}

package zsj.com.oyk255.example.ouyiku.invitejson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class ComfirmOrder {

    @Expose
    private Status status;
    @Expose
    private List<ComfirmOrderDatum> data = new ArrayList<ComfirmOrderDatum>();
    @Expose
    private String total;
    @Expose
    private String houbaototal;
    @Expose
    private String alltotal;


	/**
     * 
     * @return
     *     The status
     */
    public String getHoubaototal() {
    	return houbaototal;
    }
    
    public void setHoubaototal(String houbaototal) {
    	this.houbaototal = houbaototal;
    }
    
    public String getAlltotal() {
    	return alltotal;
    }
    
    public void setAlltotal(String alltotal) {
    	this.alltotal = alltotal;
    }
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

    /**
     * 
     * @return
     *     The data
     */
    public List<ComfirmOrderDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<ComfirmOrderDatum> data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

}

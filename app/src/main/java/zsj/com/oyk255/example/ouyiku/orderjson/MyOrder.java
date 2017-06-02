package zsj.com.oyk255.example.ouyiku.orderjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class MyOrder {

    @Expose
    private List<MyOrderDatum> data = new ArrayList<MyOrderDatum>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<MyOrderDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<MyOrderDatum> data) {
        this.data = data;
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

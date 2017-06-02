package zsj.com.oyk255.example.ouyiku.hotjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class HotNetRed {

    @Expose
    private List<HotNetDatum> data = new ArrayList<HotNetDatum>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<HotNetDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<HotNetDatum> data) {
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

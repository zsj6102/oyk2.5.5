package zsj.com.oyk255.example.ouyiku.detailjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class ZeroList {

    @Expose
    private Status status;
    @Expose
    private List<ZeroListDatum> data = new ArrayList<ZeroListDatum>();

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

    /**
     * 
     * @return
     *     The data
     */
    public List<ZeroListDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<ZeroListDatum> data) {
        this.data = data;
    }

}

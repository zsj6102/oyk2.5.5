package zsj.com.oyk255.example.ouyiku.detailjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class DetailGraphic2 {

    @Expose
    private List<DetailGraphic2Datum> data = new ArrayList<DetailGraphic2Datum>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<DetailGraphic2Datum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<DetailGraphic2Datum> data) {
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

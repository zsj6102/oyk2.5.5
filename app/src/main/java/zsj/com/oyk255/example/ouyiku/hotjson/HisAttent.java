package zsj.com.oyk255.example.ouyiku.hotjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class HisAttent {

    @Expose
    private List<HisAttentDatum> data = new ArrayList<HisAttentDatum>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<HisAttentDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<HisAttentDatum> data) {
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

package zsj.com.oyk255.example.ouyiku.brandjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class PinTuanList {

    @Expose
    private Status status;
    @Expose
    private List<PinTuanListDatum> data = new ArrayList<PinTuanListDatum>();

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
    public List<PinTuanListDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<PinTuanListDatum> data) {
        this.data = data;
    }

}

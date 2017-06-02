package zsj.com.oyk255.example.ouyiku.brandjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class PinTuanTuiJian {

    @Expose
    private Status status;
    @Expose
    private List<PinTuanTuiJianDatum> data = new ArrayList<PinTuanTuiJianDatum>();

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
    public List<PinTuanTuiJianDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<PinTuanTuiJianDatum> data) {
        this.data = data;
    }

}

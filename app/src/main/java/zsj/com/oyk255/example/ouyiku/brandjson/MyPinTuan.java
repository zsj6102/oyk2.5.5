package zsj.com.oyk255.example.ouyiku.brandjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class MyPinTuan {

    @Expose
    private Status status;
    @Expose
    private List<MyPinTuanDatum> data = new ArrayList<MyPinTuanDatum>();

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
    public List<MyPinTuanDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<MyPinTuanDatum> data) {
        this.data = data;
    }

}

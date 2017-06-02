package zsj.com.oyk255.example.ouyiku.groupjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class MeiBrand {

    @Expose
    private Status status;
    @Expose
    private List<MeiBrandDatum> data = new ArrayList<MeiBrandDatum>();

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
    public List<MeiBrandDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<MeiBrandDatum> data) {
        this.data = data;
    }

}

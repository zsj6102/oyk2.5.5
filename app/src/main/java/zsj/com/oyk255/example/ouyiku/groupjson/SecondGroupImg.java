package zsj.com.oyk255.example.ouyiku.groupjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class SecondGroupImg {

    @Expose
    private List<TopDatum> data = new ArrayList<TopDatum>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<TopDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<TopDatum> data) {
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

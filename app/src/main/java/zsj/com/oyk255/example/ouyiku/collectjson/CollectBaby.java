package zsj.com.oyk255.example.ouyiku.collectjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class CollectBaby {

    @Expose
    private Status status;
    @Expose
    private List<CollectBabyDatum> data = new ArrayList<CollectBabyDatum>();

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
    public List<CollectBabyDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<CollectBabyDatum> data) {
        this.data = data;
    }

}

package zsj.com.oyk255.example.ouyiku.collectjson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class CollectBrand {

    @Expose
    private Status status;
    @Expose
    private List<CollectBrandDatum> data = new ArrayList<CollectBrandDatum>();

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
    public List<CollectBrandDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<CollectBrandDatum> data) {
        this.data = data;
    }

}

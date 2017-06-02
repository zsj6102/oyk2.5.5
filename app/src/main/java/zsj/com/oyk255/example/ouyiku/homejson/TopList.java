package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;

public class TopList {

    @Expose
    private Status status;
    @Expose
    private TopListData data;

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
    public TopListData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(TopListData data) {
        this.data = data;
    }

}

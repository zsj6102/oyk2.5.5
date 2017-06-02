package zsj.com.oyk255.example.ouyiku.collectjson;

import com.google.gson.annotations.Expose;

public class CanTuanDetail {

    @Expose
    private Status status;
    @Expose
    private CanTuanDetailData data;

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
    public CanTuanDetailData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(CanTuanDetailData data) {
        this.data = data;
    }

}

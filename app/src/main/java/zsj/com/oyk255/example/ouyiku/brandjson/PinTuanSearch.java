package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;

public class PinTuanSearch {

    @Expose
    private Status status;
    @Expose
    private SearchData data;

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
    public SearchData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(SearchData data) {
        this.data = data;
    }

}

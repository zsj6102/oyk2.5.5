package zsj.com.oyk255.example.ouyiku.collectjson;

import com.google.gson.annotations.Expose;

public class DelBrand {

    @Expose
    private Status status;
    @Expose
    private DelBrandData data;

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
    public DelBrandData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(DelBrandData data) {
        this.data = data;
    }

}

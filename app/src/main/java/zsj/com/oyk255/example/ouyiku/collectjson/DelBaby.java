package zsj.com.oyk255.example.ouyiku.collectjson;

import com.google.gson.annotations.Expose;

public class DelBaby {

    @Expose
    private Status status;
    @Expose
    private DelBabyData data;

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
    public DelBabyData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(DelBabyData data) {
        this.data = data;
    }

}

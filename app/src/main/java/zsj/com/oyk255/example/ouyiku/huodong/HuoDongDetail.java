package zsj.com.oyk255.example.ouyiku.huodong;

import com.google.gson.annotations.Expose;

public class HuoDongDetail {

    @Expose
    private Status status;
    @Expose
    private HuoDongDetailData data;

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
    public HuoDongDetailData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(HuoDongDetailData data) {
        this.data = data;
    }

}

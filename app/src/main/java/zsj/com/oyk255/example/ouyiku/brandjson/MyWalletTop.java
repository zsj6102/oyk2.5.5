package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;

public class MyWalletTop {

    @Expose
    private Status status;
    @Expose
    private MyWalletTopData data;

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
    public MyWalletTopData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(MyWalletTopData data) {
        this.data = data;
    }

}

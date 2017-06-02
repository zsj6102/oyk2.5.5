package zsj.com.oyk255.example.ouyiku.huodong;

import com.google.gson.annotations.Expose;

public class WeChatPay {

    @Expose
    private WeChatPayData data;
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public WeChatPayData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(WeChatPayData data) {
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

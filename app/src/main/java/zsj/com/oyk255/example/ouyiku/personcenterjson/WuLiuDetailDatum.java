package zsj.com.oyk255.example.ouyiku.personcenterjson;

import com.google.gson.annotations.Expose;

public class WuLiuDetailDatum {

    @Expose
    private String time;
    @Expose
    private String ftime;
    @Expose
    private String context;

    /**
     * 
     * @return
     *     The time
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 
     * @return
     *     The ftime
     */
    public String getFtime() {
        return ftime;
    }

    /**
     * 
     * @param ftime
     *     The ftime
     */
    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    /**
     * 
     * @return
     *     The context
     */
    public String getContext() {
        return context;
    }

    /**
     * 
     * @param context
     *     The context
     */
    public void setContext(String context) {
        this.context = context;
    }

}

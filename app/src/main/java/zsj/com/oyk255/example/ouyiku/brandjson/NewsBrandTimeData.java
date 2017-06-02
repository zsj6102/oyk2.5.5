package zsj.com.oyk255.example.ouyiku.brandjson;

import com.google.gson.annotations.Expose;

public class NewsBrandTimeData {

    @Expose
    private String btime;
    @Expose
    private String ltime;

    /**
     * 
     * @return
     *     The btime
     */
    public String getBtime() {
        return btime;
    }

    /**
     * 
     * @param btime
     *     The btime
     */
    public void setBtime(String btime) {
        this.btime = btime;
    }

    /**
     * 
     * @return
     *     The ltime
     */
    public String getLtime() {
        return ltime;
    }

    /**
     * 
     * @param ltime
     *     The ltime
     */
    public void setLtime(String ltime) {
        this.ltime = ltime;
    }

}

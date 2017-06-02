package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;

public class Re {

    @Expose
    private String iscur;
    @Expose
    private String btime;
    @Expose
    private String name;

    /**
     * 
     * @return
     *     The iscur
     */
    public String getIscur() {
        return iscur;
    }

    /**
     * 
     * @param iscur
     *     The iscur
     */
    public void setIscur(String iscur) {
        this.iscur = iscur;
    }

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
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

}

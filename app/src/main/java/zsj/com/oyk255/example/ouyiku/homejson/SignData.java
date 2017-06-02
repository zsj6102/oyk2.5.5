package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;

public class SignData {

    @Expose
    private String issign;
    @Expose
    private String money;

    /**
     * 
     * @return
     *     The issign
     */
    public String getIssign() {
        return issign;
    }

    /**
     * 
     * @param issign
     *     The issign
     */
    public void setIssign(String issign) {
        this.issign = issign;
    }

    /**
     * 
     * @return
     *     The money
     */
    public String getMoney() {
        return money;
    }

    /**
     * 
     * @param money
     *     The money
     */
    public void setMoney(String money) {
        this.money = money;
    }

}

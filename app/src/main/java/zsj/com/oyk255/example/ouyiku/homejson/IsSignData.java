package zsj.com.oyk255.example.ouyiku.homejson;

import com.google.gson.annotations.Expose;

public class IsSignData {

    @Expose
    private String issign;
    @Expose
    private String signsum;
    @Expose
    private String monthtotal;
    @Expose
    private String img;
    @Expose
    private String isget;
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

    public String getIsget() {
		return isget;
	}

	public void setIsget(String isget) {
		this.isget = isget;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
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
     *     The signsum
     */
    public String getSignsum() {
        return signsum;
    }

    /**
     * 
     * @param signsum
     *     The signsum
     */
    public void setSignsum(String signsum) {
        this.signsum = signsum;
    }

    /**
     * 
     * @return
     *     The monthtotal
     */
    public String getMonthtotal() {
        return monthtotal;
    }

    /**
     * 
     * @param monthtotal
     *     The monthtotal
     */
    public void setMonthtotal(String monthtotal) {
        this.monthtotal = monthtotal;
    }

    /**
     * 
     * @return
     *     The img
     */
    public String getImg() {
        return img;
    }

    /**
     * 
     * @param img
     *     The img
     */
    public void setImg(String img) {
        this.img = img;
    }

}

package zsj.com.oyk255.example.ouyiku.invitejson;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComfirmOrderDatum {

    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @Expose
    private String title;
    @Expose
    private String logo;
    @SerializedName("brandmerchant_id")
    @Expose
    private String brandmerchantId;
    @SerializedName("brand_total")
    @Expose
    private String brandTotal;
    @Expose
    private String hongbao;
    @Expose
    private String brand_pay;

	@Expose
    private List<Product> product = new ArrayList<Product>();

    /**
     * 
     * @return
     *     The brandId
     */
	
	public String getBrand_pay() {
		return brand_pay;
	}
	
	public void setBrand_pay(String brand_pay) {
		this.brand_pay = brand_pay;
	}
    public String getBrandId() {
        return brandId;
    }

    public String getBrandTotal() {
		return brandTotal;
	}

	public void setBrandTotal(String brandTotal) {
		this.brandTotal = brandTotal;
	}

	public String getHongbao() {
		return hongbao;
	}

	public void setHongbao(String hongbao) {
		this.hongbao = hongbao;
	}

	/**
     * 
     * @param brandId
     *     The brand_id
     */
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 
     * @param logo
     *     The logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 
     * @return
     *     The brandmerchantId
     */
    public String getBrandmerchantId() {
        return brandmerchantId;
    }

    /**
     * 
     * @param brandmerchantId
     *     The brandmerchant_id
     */
    public void setBrandmerchantId(String brandmerchantId) {
        this.brandmerchantId = brandmerchantId;
    }

    /**
     * 
     * @return
     *     The product
     */
    public List<Product> getProduct() {
        return product;
    }

    /**
     * 
     * @param product
     *     The product
     */
    public void setProduct(List<Product> product) {
        this.product = product;
    }

}

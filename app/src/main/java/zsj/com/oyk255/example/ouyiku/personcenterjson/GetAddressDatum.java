package zsj.com.oyk255.example.ouyiku.personcenterjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAddressDatum {

    @SerializedName("address_id")
    @Expose
    private String addressId;
    @Expose
    private String province;
    @Expose
    private String city;
    @Expose
    private String country;
    @Expose
    private String address;
    @Expose
    private String name;
    @Expose
    private String mobile;
    @SerializedName("is_use")
    @Expose
    private String isUse;
    @Expose
    private String province_id;
    
	@Expose
    private String city_id;
    @Expose
    private String country_id;
    
    
    public String getProvince_id() {
		return province_id;
	}

	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getCountry_id() {
		return country_id;
	}

	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}


    /**
     * 
     * @return
     *     The addressId
     */
    public String getAddressId() {
        return addressId;
    }

    /**
     * 
     * @param addressId
     *     The address_id
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    /**
     * 
     * @return
     *     The province
     */
    public String getProvince() {
        return province;
    }

    /**
     * 
     * @param province
     *     The province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 
     * @return
     *     The city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
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

    /**
     * 
     * @return
     *     The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile
     *     The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 
     * @return
     *     The isUse
     */
    public String getIsUse() {
        return isUse;
    }

    /**
     * 
     * @param isUse
     *     The is_use
     */
    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

}

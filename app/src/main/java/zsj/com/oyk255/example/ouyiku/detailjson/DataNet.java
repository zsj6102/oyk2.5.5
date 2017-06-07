package zsj.com.oyk255.example.ouyiku.detailjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import zsj.com.oyk255.example.ouyiku.detailskujson.Datum;
import zsj.com.oyk255.example.ouyiku.detailskujson.Stock;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class DataNet {
    @Expose
    private String product_id;
    @Expose
    private List<String> product_image;
    @Expose
    private String marketprice;
    @Expose
    private String currprice;
    @Expose
    private String fpro_id;
    @Expose
    private String brandtotal;
    @Expose
    private String isbcollect;
    @Expose
    private String logo;
    @Expose
    private String brandsalenum;
    @Expose
    private String collectnum;
    @Expose
    private String brandtitle;
    @Expose
    private String brand_id;
    @Expose
    private String share_url;
    @Expose
    private List<Datum> skudata = new ArrayList<Datum>();
    @Expose
    private List<Stock> attr = new ArrayList<Stock>();

    public List<Datum> getSkudata() {
        return skudata;
    }

    public void setSkudata(List<Datum> skudata) {
        this.skudata = skudata;
    }

    public List<Stock> getAttr() {
        return attr;
    }

    public void setAttr(List<Stock> attr) {
        this.attr = attr;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Expose
    private String title;
    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getBrandtitle() {
        return brandtitle;
    }

    public void setBrandtitle(String brandtitle) {
        this.brandtitle = brandtitle;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }


    public String getBrandtotal() {
        return brandtotal;
    }

    public void setBrandtotal(String brandtotal) {
        this.brandtotal = brandtotal;
    }

    public String getIsbcollect() {
        return isbcollect;
    }

    public void setIsbcollect(String isbcollect) {
        this.isbcollect = isbcollect;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBrandsalenum() {
        return brandsalenum;
    }

    public void setBrandsalenum(String brandsalenum) {
        this.brandsalenum = brandsalenum;
    }

    public String getCollectnum() {
        return collectnum;
    }

    public void setCollectnum(String collectnum) {
        this.collectnum = collectnum;
    }

    public String getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(String marketprice) {
        this.marketprice = marketprice;
    }

    public String getCurrprice() {
        return currprice;
    }

    public void setCurrprice(String currprice) {
        this.currprice = currprice;
    }


    public String getFpro_id() {
        return fpro_id;
    }

    public void setFpro_id(String fpro_id) {
        this.fpro_id = fpro_id;
    }


    public List<String> getProduct_image() {
        return product_image;
    }

    public void setProduct_image(List<String> product_image) {
        this.product_image = product_image;
    }

    public String getProductId() {
        return product_id;
    }

    public void setProductId(String productId) {
        this.product_id = productId;
    }
}

package zsj.com.oyk255.example.ouyiku.collectjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntoShopCarData {

    @SerializedName("cart_id")
    @Expose
    private String cartId;

    /**
     * 
     * @return
     *     The cartId
     */
    public String getCartId() {
        return cartId;
    }

    /**
     * 
     * @param cartId
     *     The cart_id
     */
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

}

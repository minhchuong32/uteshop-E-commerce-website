package ute.shop.models;

import java.io.Serializable;
import java.util.Date;

public class StoreSettings implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String storeName;
    private String email;
    private String hotline;
    private String address;
    private String logo;
    private String theme;
    private boolean codEnabled;
    private boolean momoEnabled;
    private boolean vnpayEnabled;
    private Date createdAt;
    private Date updatedAt;

    // Getter + Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getHotline() { return hotline; }
    public void setHotline(String hotline) { this.hotline = hotline; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public boolean isCodEnabled() { return codEnabled; }
    public void setCodEnabled(boolean codEnabled) { this.codEnabled = codEnabled; }

    public boolean isMomoEnabled() { return momoEnabled; }
    public void setMomoEnabled(boolean momoEnabled) { this.momoEnabled = momoEnabled; }

    public boolean isVnpayEnabled() { return vnpayEnabled; }
    public void setVnpayEnabled(boolean vnpayEnabled) { this.vnpayEnabled = vnpayEnabled; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}

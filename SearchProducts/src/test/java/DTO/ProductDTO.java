package DTO;

public class ProductDTO {

	private String name;
	private String sku;
	private Boolean storeAvailability;
	private Boolean webAvailability;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Boolean getStoreAvailability() {
		return storeAvailability;
	}
	public void setStoreAvailability(Boolean storeAvailability) {
		this.storeAvailability = storeAvailability;
	}
	public Boolean getWebAvailability() {
		return webAvailability;
	}
	public void setWebAvailability(Boolean webAvailability) {
		this.webAvailability = webAvailability;
	}
}
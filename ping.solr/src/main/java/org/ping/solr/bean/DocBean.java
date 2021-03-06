package org.ping.solr.bean;

import org.apache.solr.client.solrj.beans.Field;

public class DocBean {
	
	@Field
	private String id;

	@Field
	private long vehicleId;
	
	@Field
	private long belongUid;
	
	@Field
	private Integer numberAuth;
	
	@Field
	private Integer licenseAuth;
	
	@Field
	private Integer type;
	
	@Field
	private Integer capacity;
	
	@Field
	private String contactPhone;
	
	@Field
	private Integer length;
	
	@Field
	private Double longitude;
	
	@Field
	private Double latitude;
	
	@Field
	private String lngLat;
	
	@Field
	private Integer valid;
	
	@Field
	private String beginAddress;
	
	@Field
	private String endAddress;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public long getBelongUid() {
		return belongUid;
	}

	public void setBelongUid(long belongUid) {
		this.belongUid = belongUid;
	}

	public Integer getNumberAuth() {
		return numberAuth;
	}

	public void setNumberAuth(Integer numberAuth) {
		this.numberAuth = numberAuth;
	}

	public Integer getLicenseAuth() {
		return licenseAuth;
	}

	public void setLicenseAuth(Integer licenseAuth) {
		this.licenseAuth = licenseAuth;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
		this.lngLat = getLongitude() + " " + getLatitude();
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
		this.lngLat = getLongitude() + " " + getLatitude();
	}

	public String getLngLat() {
		return lngLat;
	}

	public void setLngLat(String lngLat) {
		this.lngLat = lngLat;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public String getBeginAddress() {
		return beginAddress;
	}

	public void setBeginAddress(String beginAddress) {
		this.beginAddress = beginAddress;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	@Override
	public String toString() {
		return "DocBean [id=" + id + ", vehicleId=" + vehicleId
				+ ", belongUid=" + belongUid + ", numberAuth=" + numberAuth
				+ ", licenseAuth=" + licenseAuth + ", type=" + type
				+ ", capacity=" + capacity + ", contactPhone=" + contactPhone
				+ ", length=" + length + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", lngLat=" + lngLat + ", valid="
				+ valid + ", beginAddress=" + beginAddress + ", endAddress="
				+ endAddress + "]";
	}
	
}

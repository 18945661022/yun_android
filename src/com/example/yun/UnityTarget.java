package com.example.yun;

import java.io.Serializable;
/**
 * 璧勬簮鍖呬俊鎭�
 * @author ly
 *
 */
public class UnityTarget implements  Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1484811621998035326L;
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private Integer unityTargetId;//涓婚敭
	private String unityTargetName;//璧勬簮鍖呭悕绉�
	private String unityTargetKind;//绫诲埆锛堝崟瀵硅薄璧勬簮鍖咃紝澶氬璞¤祫婧愬寘锛�
	private String unityTargetVersion;//鐗堟湰锛堣祫婧愬寘鐗堟湰锛岃窡绋嬪簭鐗堟湰鏃犲叧锛�
	private String unityTargetAttr;//灞炴�э紙AssetBundles锛孶nity3D锛�
	private String unityTargetType;//绫诲瀷锛堣瘑鍒浘锛屾樉绀哄璞★級
	private String unityTargetDownloadUrl;//涓嬭浇鍦板潃
	private String unityTargetCode;//缂栧彿
	private Integer unityTargetSize;//璧勬簮鍖呭ぇ灏�
	private String createDate;
	private String modifyDate;
	private String goods;
	private String productId;
	
	public String getGoods(){
		return goods;
	}
	public void setGoods(String goods){
		this.goods=goods;
	}
	
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	public String getPors() {
		return pors;
	}
	public void setPors(String pors) {
		this.pors = pors;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(String deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	private Long stock;
	private String pors;
	private String shopName;
	private String deliveryFee;
	private String tel;
	private String companyId;
	
	
	
	
	
	private String unityTargetTruePath;//璧勬簮鐪熷疄瀛樻斁鐩綍
	
	
	public String getUnityTargetTruePath() {
		return unityTargetTruePath;
	}
	public void setUnityTargetTruePath(String unityTargetTruePath) {
		this.unityTargetTruePath = unityTargetTruePath;
	}
	public String getUnityTargetCode() {
		return unityTargetCode;
	}
	public void setUnityTargetCode(String unityTargetCode) {
		this.unityTargetCode = unityTargetCode;
	}
	public Integer getUnityTargetSize() {
		return unityTargetSize;
	}
	public void setUnityTargetSize(Integer unityTargetSize) {
		this.unityTargetSize = unityTargetSize;
	}
	public Integer getUnityTargetId() {
		return unityTargetId;
	}
	public void setUnityTargetId(Integer unityTargetId) {
		this.unityTargetId = unityTargetId;
	}
	public String getUnityTargetName() {
		return unityTargetName;
	}
	public void setUnityTargetName(String unityTargetName) {
		this.unityTargetName = unityTargetName;
	}
	public String getUnityTargetKind() {
		return unityTargetKind;
	}
	public void setUnityTargetKind(String unityTargetKind) {
		this.unityTargetKind = unityTargetKind;
	}
	public String getUnityTargetVersion() {
		return unityTargetVersion;
	}
	public void setUnityTargetVersion(String unityTargetVersion) {
		this.unityTargetVersion = unityTargetVersion;
	}
	public String getUnityTargetAttr() {
		return unityTargetAttr;
	}
	public void setUnityTargetAttr(String unityTargetAttr) {
		this.unityTargetAttr = unityTargetAttr;
	}
	public String getUnityTargetType() {
		return unityTargetType;
	}
	public void setUnityTargetType(String unityTargetType) {
		this.unityTargetType = unityTargetType;
	}
	public String getUnityTargetDownloadUrl() {
		return unityTargetDownloadUrl;
	}
	public void setUnityTargetDownloadUrl(String unityTargetDownloadUrl) {
		this.unityTargetDownloadUrl = unityTargetDownloadUrl;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	
}

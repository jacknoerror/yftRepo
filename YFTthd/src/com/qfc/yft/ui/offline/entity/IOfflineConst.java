package com.qfc.yft.ui.offline.entity;

public interface IOfflineConst {
	public static final int OFFSTATUS_EMPTY_DATA = -1;
	public static final int OFFSTATUS_COMMON_STATUS = 0;
	public static final int OFFSTATUS_NEED_DOWNLOAD = 1;
	public static final int OFFSTATUS_DOWNLOADING = 2;
	public static final int OFFSTATUS_NEED_DELETE = 3;
	public static final int OFFSTATUS_HAS_DELETE = 4;
	
	
	public final static String OFF_SHOPINFO = "shopInfo",OFF_SHOPBANNERPIC = "shopBannerPic",OFF_SHOPCERTARRAY="shopCertArray",OFF_PRODUCTPICSARRAY = "productPicsArray",
			OFF_SHOPID="shopId",OFF_SHOPLOGOPIC="shopLogoPic",OFF_SHOPPICSARRAY="shopPicsArray",OFF_STATUS="status",OFF_UPDATETIME="updateTime",
			OFF_DOWNLOADSTATUS = "downloadStatus" ,OFF_PERCENT="percent",OFF_SIZE="size",OFF_PERCENTWEIGHT="percentWeight",OFF_PRODUCTIMAGE="productImage",
			OFF_PRODUCTARRAY="productArray",OFF_SERIESARRAY="seriesArray",OFF_IMGURL="imageUrl",OFF_IMAGECODE = "imageCode",OFF_SERIESID="seriesId",
			OFF_PRODUCTID="productId";
	
	public final static String PREFOFF_SHOPINFO="shopinfo",
			PREFOFF_SERIES="series",PREFOFF_PRODUCTS="products";
	public static final String OFF_PRODUCT300X_IMAGE = "product300XImage";
	public static final String OFF_PRODUCT_X800_IMAGE = "productX800Image";
}

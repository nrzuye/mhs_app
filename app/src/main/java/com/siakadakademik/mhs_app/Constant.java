package com.siakadakademik.mhs_app;

import com.siakadakademik.mhs_app.util.Server;

import java.io.Serializable;

public class Constant implements Serializable {
 
	private static final long serialVersionUID = 1L;

	//this is the path of json url
	public static final String SERVER_IMAGE_CATEGORY= Server.URL + "detail/upload/category/";
	public static final String SERVER_IMAGE_NEWSLIST_THUMBS= Server.URL + "upload/thumbs/";
	public static final String SERVER_IMAGE_NEWSLISTDETAILS= Server.URL + "upload/";
	public static final String CATEGORY_URL = Server.URL + "detail/api.php";
	public static final String CATEGORY_ITEM_URL = Server.URL + "detail/api.php?cat_id=";
	public static final String TRANSKIP_URL ="transkip.php?nim=";
	public static final String KHS_URL = Server.URL + "khs_api2.php?nim=";
  
	public static final String CATEGORY_ARRAY_NAME="AndroidEbookApp";
	public static final String TRANSKIP_ARRAY_NAME="MahasiswaTranskip";
	public static final String KHS_ARRAY_NAME="Khs";

	//Transkip

	public static final String TRANSKIP_ITEM_KODEMK="kodemk";
	public static final String TRANSKIP_ITEM_NAMAMK="namamk";
	public static final String TRANSKIP_ITEM_KURIKULUM="kurikulum";
	public static final String TRANSKIP_ITEM_NILAI_HURUF="nilai_huruf";
	public static final String TRANSKIP_ITEM_NILAI_ANGKA="nilai_angka";
	public static final String TRANSKIP_ITEM_LULUS="lulus";
	public static final String TRANSKIP_ITEM_PERIODE="periode";
	public static final String TRANSKIP_ITEM_NIM="nim";

	//Khs

	public static final String KHS_ITEM_NIM="nim";
	public static final String KHS_ITEM_KODEMK="kodemk";
	public static final String KHS_ITEM_NAMAMK="namamk";
	public static final String KHS_ITEM_SKSMK="sksmk";
	public static final String KHS_ITEM_NAMA_KELAS="nama_kelas";
	public static final String KHS_ITEM_PENGAMPU="pengampu";
	public static final String KHS_ITEM_PERIODE="periode";
	public static final String KHS_ITEM_NILAI="nilai";


	public static final String CATEGORY_NAME="category_name";
	public static final String CATEGORY_CID="cid";
	public static final String CATEGORY_IMAGE="category_image";
	public static final String CATEGORY_AUTHOR="author";

	public static final String CATEGORY_ITEM_CID="cid";
	public static final String CATEGORY_ITEM_NAME="category_name";
	public static final String CATEGORY_ITEM_IMAGE="category_image";
	public static final String CATEGORY_ITEM_STATUS="status";
 	public static final String CATEGORY_ITEM_CAT_ID="nid";
	public static final String CATEGORY_ITEM_NEWSHEADING="news_heading";
	public static final String CATEGORY_ITEM_NEWSDESCRI="news_description";
	public static final String CATEGORY_ITEM_NEWSIMAGE="news_image";
	public static final String CATEGORY_ITEM_NEWSDATE="news_date";
	public static final String CATEGORY_ITEM_NEWSSTATUS="news_status";

	public static String CATEGORY_TITLE;
	public static int CATEGORY_IDD;
	public static String ID_MHS;
	public static int IDPERIODE;
	public static String NAMAPERIODE;
	public static String TAG_STATUS;


 
}

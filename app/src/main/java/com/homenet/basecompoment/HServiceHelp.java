package com.homenet.basecompoment;



//web����������
public class HServiceHelp 
{
	
	static String BaseServiceUrl="http://124.133.23.148:8300";	
	
	//��Ϣ����
	
	
	public static final String Picture_Home = BaseServiceUrl;	//��ȡ����ͼƬ�Ļ��ַ
	
	public static final String NewsProcessURL = BaseServiceUrl+"/Service1.asmx";	 
	public static final String METHOD_GetLatestNews = "GetLatestNews";		
	public static final String METHOD_GetNewsDetail = "GetNewsDetail";
	
	public  static final String NAMESPACE_NEWS = "http://service.homenet.org";
	
	
	//Sec ����
	public  static final String NAMESPACE_SECHOUSE = "http://Sechouse.Service.org/";
	public  static final String METHOD_GetSecHouseByAreaID = "GetSecHouseByAreaID";	//��ȡȫ��	
	public  static final String METHOD_GetSecHouseDetail_ = "GetSecHouseDetail";		//��ȡ��������
	public static String SecServiceURL = BaseServiceUrl+"/SecHouseService.asmx";
	
	
}

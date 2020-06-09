package com.um.psystem.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.xfire.client.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RTX {

	/**
	 * rtxUrl 调用RTX地址 title 消息提醒标题 recipients 接收单人 (郑圳金) 多人(郑圳金,钱意) msg 消息内容
	 * sender 发送人
	 */
	private static String rtxUrl;
	private static String send = "";

	@Value("${rtxurl}")
	public void setRtxUrl(String rtxUrl){
		this.rtxUrl = rtxUrl;
	}

	public String getRtxUrl(){
		return rtxUrl;
	}

	protected static final Logger logger = Logger.getLogger(RTX.class);

	/**
	 * 
	 * @param title
	 *            消息标题
	 * @param msg
	 *            消息内容
	 * @param receives
	 *            消息接收人 单人("郑圳金") 多人("郑圳金,钱意") 多人发送以逗号分隔接收人
	 * @return
	 */
	public static boolean sendMsg(String title, String msg, String receives) {
		try {
			if(null == title || null == msg || StringUtils.isEmpty(receives)){
				logger.error("入参为空");
				return false;
			}
			URL connectUrl = new URL(rtxUrl);
			Client client = new Client(connectUrl);
			String value = "{billID:\"\", " + "billType:\"ws_callback_customercomplainthandling\", "
					+ "functionDescription:\"" + title + "\"," + "title:\"" + title + "\"," + "recipients:\"" + receives
					+ "\"," + "type:\"0\"," + "sender:\"" + send + "\"} ";
			Object[] results = client.invoke("sendMessageByNames", new Object[] { value, msg });
			return true;
		} catch (Exception e) {
			logger.error("RTX发送消息失败 " + e.getMessage());
			return false;
		}
	}

	/**
	 * test
	 * 
	 * @throws Exception
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException, Exception {
		String title = "RTX消息";
		String recipients = "郑圳金";
		String sender = "";
		String content = "接口测试【RTX发送测试--请忽略】";
		String value = "{billID:\"\", " + "billType:\"ws_callback_customercomplainthandling\", "
				+ "functionDescription:\"" + title + "\"," + "title:\"" + title + "\"," + "recipients:\"" + recipients
				+ "\"," + "type:\"0\"," + "sender:\"" + sender + "\"} ";
		Client client;
		String url = "http://192.168.136.8:8088/WsCommonWebService/rtx.webservice?wsdl";
		client = new Client(new URL(url));
		Object[] results = client.invoke("sendMessageByNames", new Object[] { value, content });
	}
}

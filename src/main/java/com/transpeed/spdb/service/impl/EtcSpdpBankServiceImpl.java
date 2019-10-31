package com.transpeed.spdb.service.impl;

import com.transpeed.spdb.service.EtcSpdpBankService;
import com.transpeed.spdb.util.HttpResult;
import com.transpeed.spdb.util.SpdpBankHttpUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Service("etcSpdpBankService")
public class EtcSpdpBankServiceImpl implements EtcSpdpBankService {

    @Value("${bank.spdp.signServerAddress}")
    private String signServerAddress;

    @Value("${bank.spdp.sendServerAddress}")
    private String sendServerAddress;

    @Value("${bank.spdp.masterId}")
    private String masterID;
    // 签名请求
    public static final String signture = "INFOSEC_SIGN/1.0";
    // 验签请求
    public static final String unsignture = "INFOSEC_VERIFY_SIGN/1.0";
    public static final String succeed = "succeed";
    public static final String bankBody = "rtnBody";

    private Logger logger = LoggerFactory.getLogger( getClass() );

    @Autowired
    private SpdpBankHttpUtil spdpBankHttpUtil;

    @Override
    public HashMap<String, Object> spdpTrade(String tranCode, String tranContent) {
        // 生成document
        Document document = DocumentHelper.createDocument();
        // 获得加密数据
        Document doc = null;
        // 返回的报文解析
        Document rtnDoc = null;
        document.setXMLEncoding( "GB18030" );
        // 创建root
        Element root = document.addElement( "packet" );
        // 生成head
        Element head = root.addElement( "head" );
        // 生成transCode
        Element transCode = head.addElement( "transCode" );
        transCode.setText( tranCode );
        // 生成signFlag
        Element signFlag = head.addElement( "signFlag" );
        signFlag.setText( "1" );
        // 生成masterID
        Element masterId = head.addElement( "masterID" );
        masterId.setText( masterID );
        // 生成packetID
        long serNo = System.currentTimeMillis() + (long) (Math.random() * 1000000L);
        Element packetId = head.addElement( "packetID" );
        packetId.setText( Long.toString( serNo ) );
        // 生成timeStamp
        Element timeStamp = head.addElement( "timeStamp" );
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        timeStamp.setText( sdf.format( new Date() ) );
        // 返回Body Element
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        boolean isBankRtn = false;
        Element finalBody = null;
        logger.info( "请求浦发银企直连交易报文内容：" + tranContent );
        try {
            HttpResult result = spdpBankHttpUtil.doPostWithoutKey( signServerAddress, tranContent, signture );
            logger.info( "浦发银企直连签名处理报文响应:" + result.getData() );
            doc = DocumentHelper.parseText( result.getData() );
            Element rtnRoot = doc.getRootElement();
            String rtnBody = rtnRoot.element( "body" ).element( "sign" ).getText();
            // 生成body
            Element body = root.addElement( "body" );
            Element sign = body.addElement( "signature" );
            sign.setText( rtnBody );
            // 计算长度 + 6
            String preSendString = document.asXML();
            int length = preSendString.length() + 6;
            String sendString = fillSpace( String.valueOf( length ), 6 ) + preSendString;
            System.out.println( "sendString=" + sendString );
            // 直接发送报文至NC 安全http端
            HttpResult response = spdpBankHttpUtil.doPostToBank( sendServerAddress, sendString );
            logger.info( "浦发银企直连处理报文响应:" + response.getData() );
            rtnDoc = DocumentHelper.parseText( response.getData().substring( 6 ) );
            Element resultRoot = rtnDoc.getRootElement();
            Element signature = (Element) resultRoot.selectSingleNode( "body/signature" );
            if (signature != null) {
                // 需要验签
                HttpResult unsignResult = spdpBankHttpUtil.doPostWithoutKey( signServerAddress, signature.getText(),
                        unsignture );

                rtnDoc = DocumentHelper.parseText( unsignResult.getData() );
                System.out.println( "rtnDoc=" + rtnDoc.asXML() );
                Element unsignDocRoot = rtnDoc.getRootElement();
                finalBody = (Element) unsignDocRoot.selectSingleNode( "body/sic/body" );
                if ((unsignDocRoot.element( "head" ).element( "result" ).getText().equals( "0" ))) {
                    isBankRtn = true;
                } else {
                    finalBody = resultRoot;
                }

            } else {
                // 不需要验签
                Element resultHead = resultRoot.element( "head" );
                finalBody = resultRoot.element( "body" );
                if ("AAAAAAA".equals( resultHead.element( "returnCode" ).getText() )) {
                    isBankRtn = true;
                } else {
                    finalBody = resultRoot;
                }
            }

        } catch (Exception e) {
            logger.error( "处理报文失败:" + tranContent + ", " + "Exception: " + e.getMessage() );
            isBankRtn = false;
            finalBody = null;
        }
        hashMap.put( succeed, isBankRtn );
        hashMap.put( bankBody, finalBody );
        return hashMap;
    }

    /**
     * 右补空
     *
     * @param content
     * @param length
     * @return
     */
    public String fillSpace(String content, int length) {
        int strLength = content.length();
        while (strLength < length) {
            StringBuffer sb = new StringBuffer();
            sb.append( content ).append( ' ' );
            content = sb.toString();
            strLength = content.length();
        }
        return content;
    }
}

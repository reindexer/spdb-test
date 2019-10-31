package com.transpeed.spdb;

import com.transpeed.spdb.service.EtcSpdpBankService;
import com.transpeed.spdb.util.SpdpBankPacketUtils;
import org.dom4j.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankTest {

    @Autowired
    private EtcSpdpBankService etcSpdpBankService;

    @Test
    public void trade8801() throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        String elecChequeNo = String.valueOf( System.currentTimeMillis() );
        System.out.println( elecChequeNo );
        params.put( "elecChequeNo", elecChequeNo );
        params.put( "acctNo", "952A9997220008092" );//952A9997220008092
        params.put( "acctName", "浦发2489675304" );
        params.put( "payeeAcctNo", "952A9997220008349" );
        params.put( "payeeName", "浦发2878487587" );
        params.put( "payeeType", "1" );
        params.put( "payeeBankName", "" );
        params.put( "payeeAddress", "" );
        params.put( "amount", "1.0" );
        params.put( "sysFlag", "0" );
        params.put( "remitLocation", "0" );
        params.put( "note", "测试" );
        String tranContent = SpdpBankPacketUtils.packTrade8801( params );
        HashMap<String, Object> result = null;
        result = etcSpdpBankService.spdpTrade( "8801", tranContent );
        if (result != null) {
            System.out.println( result.get( "succeed" ) );
            Element resultElement = (Element) result.get( "rtnBody" );
            System.out.println( resultElement.asXML() );
        }
    }

    @Test
    public void trade8804() throws Exception {
        String tranDate = "20191017";
        String acctNo = "952A9997220008092";
        Long tranNo = 1571298452184L;//1571292392022L;//20191017100000008
        String tranContent = SpdpBankPacketUtils.packetTrade8804( tranDate, acctNo, tranNo );
        HashMap<String, Object> result = null;
        result = etcSpdpBankService.spdpTrade( "8804", tranContent );
        if (result != null) {
            System.out.println( result.get( "succeed" ) );
            Element resultElement = (Element) result.get( "rtnBody" );
            System.out.println( resultElement.asXML() );
        }
    }

    @Test
    public void trade9004() throws Exception {
        String tranDate = "20191017";
        String acctNo = "952A9997220008092";
        String tranContent = SpdpBankPacketUtils.packetTrade9004( tranDate, acctNo, "" );
        HashMap<String, Object> result = etcSpdpBankService.spdpTrade( "9004", tranContent );
        if (result != null) {
            System.out.println( result.get( "succeed" ) );
            Element resultElement = (Element) result.get( "rtnBody" );
            System.out.println( resultElement.asXML() );
        }
    }

    @Test
    public void trade8924() throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put( "acctNo", "952A9997220008092" );
        params.put( "beginDate", "20191017" );
        params.put( "endDate", "20191017" );
        params.put( "transAmount", "" );
        params.put( "subAccount", "" );
        params.put( "subAcctName", "" );
        String tranContent = SpdpBankPacketUtils.packetTrade8924( params );
        HashMap<String, Object> result = null;
        result = etcSpdpBankService.spdpTrade( "8924", tranContent );
        if (result != null) {
            System.out.println( result.get( "succeed" ) );
            Element resultElement = (Element) result.get( "rtnBody" );
            System.out.println( resultElement.asXML() );
        }
    }

    @Test
    public void trade4402() throws Exception {
        String acctNo = "952A7632920000012";
        String tranContent = SpdpBankPacketUtils.packetTrade4402( acctNo );
        HashMap<String, Object> result = null;
        result = etcSpdpBankService.spdpTrade( "4402", tranContent );
        if (result != null) {
            System.out.println( result.get( "succeed" ) );
            Element resultElement = (Element) result.get( "rtnBody" );
            System.out.println( resultElement.asXML() );
        }
    }
}

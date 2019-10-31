package com.transpeed.spdb.controller;

import com.transpeed.spdb.form.*;
import com.transpeed.spdb.service.EtcSpdpBankService;
import com.transpeed.spdb.util.R;
import com.transpeed.spdb.util.SpdpBankPacketUtils;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/spdb")
public class EtcSpdpBankController {

    @Autowired
    private EtcSpdpBankService etcSpdpBankService;

    /**
     * 单笔支付（8801）
     */
    @PostMapping("/trade8801")
    public R trade8801(@RequestBody Trade8801From form) {

        HashMap<String, String> params = new HashMap<>();
        // 电子凭证号
        params.put( "elecChequeNo", String.valueOf( System.currentTimeMillis() + (long) (Math.random() * 1000000L) ) );
        // 付款帐号
        params.put( "acctNo", form.getAcctNo() );
        // 付款人账户姓名
        params.put( "acctName", form.getAcctName() );
        // 收款人账号
        params.put( "payeeAcctNo", form.getPayeeAcctNo() );
        // 收款人名称
        params.put( "payeeName", form.getPayeeName() );
       /*
        0-对公账号 1-卡 2-活期一本通 3-定期一本通 4-定期存折 5-存单
        6-国债 7-外系统账号 8-活期存折 9-内部帐/表外帐（银行内部账户）
        S-对私内部账号 Z-客户号
        当收款人为本行个人账户时必须填写此项
       */
        params.put( "payeeType", form.getPayeeType() );
        // 收款行名称
        params.put( "payeeBankName", "" );
        // 收款行地址
        params.put( "payeeAddress", "" );
        // 支付金额
        params.put( "amount", form.getAmount() );
        // 本行/他行标志 0:表示本行 1:表示他行
        params.put( "sysFlag", form.getSysFlag() );
        // 同城异地标志 0:同城 1:异地
        params.put( "remitLocation", form.getRemitLocation() );
        // 附言
        params.put( "note", "测试" );
        String tranContent = SpdpBankPacketUtils.packTrade8801( params );
        HashMap<String, Object> result = etcSpdpBankService.spdpTrade( "8801", tranContent );
        if (result != null) {
            System.out.println( result.get( "succeed" ) );
            Element resultElement = (Element) result.get( "rtnBody" );
            System.out.println( resultElement.asXML() );
        }
        return R.ok();
    }

    /**
     * 转账信息查询（8804）
     */
    @PostMapping("/trade8804")
    public R trade8804(@RequestBody Trade8804From form) {
        String tranContent = SpdpBankPacketUtils.packetTrade8804( form.getTranDate(), form.getAcctNo(), Long.parseLong( form.getTranNo() ) );
        HashMap<String, Object> result = etcSpdpBankService.spdpTrade( "8804", tranContent );
        if (result != null) {
            System.out.println( result.get( "succeed" ) );
            Element resultElement = (Element) result.get( "rtnBody" );
            System.out.println( resultElement.asXML() );
        }

        return R.ok();
    }

    /**
     * 日间账户明细下载（9004）
     */
    @PostMapping("/trade9004")
    public R trade9004(@RequestBody Trade9004From form) {
        String tranContent = SpdpBankPacketUtils.packetTrade9004( form.getTranDate(), form.getAcctNo(), "" );
        HashMap<String, Object> result = etcSpdpBankService.spdpTrade( "9004", tranContent );
        if (result != null) {
            System.out.println( result.get( "succeed" ) );
            Element resultElement = (Element) result.get( "rtnBody" );
            System.out.println( resultElement.asXML() );
        }
        return R.ok();
    }

    /**
     * 账户明细查询（8924）
     */
    @PostMapping("/trade8924")
    public R trade8924(@RequestBody Trade8924From form) {
        HashMap<String, String> params = new HashMap<>();
        params.put( "acctNo", form.getAcctNo() );
        params.put( "beginDate", form.getBeginDate() );
        params.put( "endDate", form.getEndDate() );
        params.put( "transAmount", "" );
        params.put( "subAccount", "" );
        params.put( "subAcctName", "" );
        String tranContent = SpdpBankPacketUtils.packetTrade8924( params );
        HashMap<String, Object> result = etcSpdpBankService.spdpTrade( "8924", tranContent );
        if (result != null) {
            System.out.println( result.get( "succeed" ) );
            Element resultElement = (Element) result.get( "rtnBody" );
            System.out.println( resultElement.asXML() );
        }
        return R.ok();
    }

    /**
     * 账户余额查询（4402）
     */
    @PostMapping("/trade4402")
    public R trade4402(@RequestBody Trade4402From form) {
        String acctNo = form.getAcctNo();
        System.out.println( acctNo );
        String tranContent = SpdpBankPacketUtils.packetTrade4402( acctNo );
        HashMap<String, Object> result = etcSpdpBankService.spdpTrade( "4402", tranContent );
        if (result != null) {
            System.out.println( result.get( "succeed" ) );
            Element resultElement = (Element) result.get( "rtnBody" );
            System.out.println( resultElement.asXML() );
        }
        return R.ok();
    }
}

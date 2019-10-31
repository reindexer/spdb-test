package com.transpeed.spdb.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;

public class SpdpBankPacketUtils {
    /*
     * 转账打包
     */
    public static String packTrade8801(HashMap<String, String> params) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "body" );
        // 指定授权客户号
        Element authMasterID = root.addElement( "authMasterID" );
        authMasterID.setText( "" );
        // 电子凭证号
        Element elecChequeNo = root.addElement( "elecChequeNo" );
        elecChequeNo.setText( params.get( "elecChequeNo" ) );
        // 付款账号
        Element acctNo = root.addElement( "acctNo" );
        acctNo.setText( params.get( "acctNo" ) );
        // 付款人账户名称
        Element acctName = root.addElement( "acctName" );
        acctName.setText( params.get( "acctName" ) );
        // 预约日期
        Element bespeakDate = root.addElement( "bespeakDate" );
        bespeakDate.setText( "" );
        // 收款人账号
        Element payeeAcctNo = root.addElement( "payeeAcctNo" );
        payeeAcctNo.setText( params.get( "payeeAcctNo" ) );
        // 收款人名称
        Element payeeName = root.addElement( "payeeName" );
        payeeName.setText( params.get( "payeeName" ) );
        // 收款人账户类型
        Element payeeType = root.addElement( "payeeType" );
        payeeType.setText( params.get( "payeeType" ) );
        // 收款行名称
        Element payeeBankName = root.addElement( "payeeBankName" );
        payeeBankName.setText( params.get( "payeeBankName" ) );
        // 收款行地址
        Element payeeAddress = root.addElement( "payeeAddress" );
        payeeAddress.setText( params.get( "payeeAddress" ) );
        // 支付金额
        Element amount = root.addElement( "amount" );
        amount.setText( params.get( "amount" ) );
        // 本行它行标志
        Element sysFlag = root.addElement( "sysFlag" );
        sysFlag.setText( params.get( "sysFlag" ) );
        // 同城异地标志
        Element remitLocation = root.addElement( "remitLocation" );
        remitLocation.setText( params.get( "remitLocation" ) );
        // 附言
        Element note = root.addElement( "note" );
        note.setText( params.get( "note" ) );
        // 收款行速选标志
        Element payeeBankSelectFlag = root.addElement( "payeeBankSelectFlag" );
        payeeBankSelectFlag.setText( "" );
        // 支付号
        Element payeeBankNo = root.addElement( "payeeBankNo" );
        payeeBankNo.setText( "" );
        // 汇款用途
        Element remitPurpose = root.addElement( "remitPurpose" );
        remitPurpose.setText( "" );
        return document.getRootElement().asXML();
    }

    /**
     * 转账 交易结果查询
     */
    public static String packetTrade8804(String tranDate, String acctNo, Long tranNo) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "body" );
        // 电子凭证号
        Element elecChequeNo = root.addElement( "elecChequeNo" );
        elecChequeNo.setText( tranNo.toString() );
        // 付款账号
        Element acctNoElement = root.addElement( "acctNo" );
        acctNoElement.setText( acctNo );
        // 开始日期
        Element beginDate = root.addElement( "beginDate" );
        beginDate.setText( tranDate );
        // 结束日期
        Element endDate = root.addElement( "endDate" );
        endDate.setText( tranDate );
        // 受理编号
        Element acceptNo = root.addElement( "acceptNo" );
        acceptNo.setText( "" );
        // 序号
        Element serialNo = root.addElement( "serialNo" );
        serialNo.setText( "" );
        // 查询的笔数
        Element queryNumber = root.addElement( "queryNumber" );
        queryNumber.setText( "10" );
        // 查询的起始笔数
        Element beginNumber = root.addElement( "beginNumber" );
        beginNumber.setText( "1" );
        // 批量单笔标志
        Element singleOrBatchFlag = root.addElement( "singleOrBatchFlag" );
        singleOrBatchFlag.setText( "0" );
        return document.getRootElement().asXML();
    }

    /*
     * 明细下载
     */
    public static String packetTrade9004(String tranDate, String acctNo, String fileName) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "body" );
        // 付款账号
        Element acctNoElement = root.addElement( "acctNo" );
        acctNoElement.setText( acctNo );
        // 开始日期
        Element beginDate = root.addElement( "beginDate" );
        beginDate.setText( tranDate );
        // 结束日期
        Element endDate = root.addElement( "endDate" );
        endDate.setText( tranDate );
        // 交易包序号
        Element queryPage = root.addElement( "queryPage" );
        queryPage.setText( "1" );
        // 未结束文件名称
        Element fileNameElement = root.addElement( "fileName" );
        fileNameElement.setText( fileName );
        return document.getRootElement().asXML();
    }

    /*
     * 明细下载
     */
    public static String packetTrade8924(HashMap<String, String> params) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "body" );
        // 付款账号
        Element acctNoElement = root.addElement( "acctNo" );
        acctNoElement.setText( params.get( "acctNo" ) );
        // 开始日期
        Element beginDate = root.addElement( "beginDate" );
        beginDate.setText( params.get( "beginDate" ) );
        // 结束日期
        Element endDate = root.addElement( "endDate" );
        endDate.setText( params.get( "endDate" ) );
        // 查询的笔数
        Element queryNumber = root.addElement( "queryNumber" );
        queryNumber.setText( "20" );
        // 查询的起始笔数
        Element beginNumber = root.addElement( "beginNumber" );
        beginNumber.setText( "1" );
        // 交易金额
        Element transAmount = root.addElement( "transAmount" );
        transAmount.setText( params.get( "transAmount" ) );
        // 对方帐号
        Element subAccount = root.addElement( "subAccount" );
        subAccount.setText( params.get( "subAccount" ) );
        // 对方户名
        Element subAcctName = root.addElement( "subAcctName" );
        subAcctName.setText( params.get( "subAcctName" ) );

        return document.getRootElement().asXML();
    }

    /*
     * 明细下载
     */
    public static String packetTrade4402(String acctNo) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "body" );
        Element lists = root.addElement( "lists" );
        lists.addAttribute( "name", "acctList" );
        Element list = lists.addElement( "list" );
        // 付款账号
        Element acctNoElement = list.addElement( "acctNo" );
        acctNoElement.setText( acctNo );
        return document.getRootElement().asXML();
    }
}

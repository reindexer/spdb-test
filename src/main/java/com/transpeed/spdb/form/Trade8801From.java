package com.transpeed.spdb.form;

import lombok.Data;

/**
 * @author developerlizhi
 * @date 2019-10-31 13:27
 */
@Data
public class Trade8801From {

    private String acctNo;
    private String acctName;
    private String payeeAcctNo;
    private String payeeName;
    private String payeeType;
    private String amount;
    private String sysFlag;
    private String remitLocation;
}

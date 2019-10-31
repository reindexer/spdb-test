package com.transpeed.spdb.service;

import java.util.HashMap;


public interface EtcSpdpBankService {

    HashMap<String, Object> spdpTrade(String tranCode, String tranContent);
    
}

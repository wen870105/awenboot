package com.wen.awenboot.cache;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wen
 * @version 1.0
 * @date 2021/1/6 13:52
 */
@Slf4j
public class EsCntSerivce {
    private CntBean phoneCounter = new CntBean();
    private CntBean enumCounter = new CntBean();
    private CntBean rpcCounter = new CntBean();

    public long addRpcCounter() {
        return rpcCounter.getVal().incrementAndGet();
    }

    public long addPhoneCounter() {
        return phoneCounter.getVal().incrementAndGet();
    }

    public long addEnumCounter() {
        return enumCounter.getVal().incrementAndGet();
    }


    @Override
    public String toString() {
        return "{phoneCount="
                + phoneCounter.getVal().longValue()
                + ", enumCounter=" + enumCounter.getVal().longValue()
                + ", rpcCounter=" + rpcCounter.getVal().longValue()
                + '}';
    }
}

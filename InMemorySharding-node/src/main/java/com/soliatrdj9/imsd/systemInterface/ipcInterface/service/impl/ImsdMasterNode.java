package com.soliatrdj9.imsd.systemInterface.ipcInterface.service.impl;

import feign.Param;
import feign.RequestLine;

public interface ImsdMasterNode {
	//
    @RequestLine("PUT /imsd/management/slaves/{node}")
    String registerSlaveNode(@Param("node") String node);
}
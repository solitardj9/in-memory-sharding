package com.soliatrdj9.imsd.systemInterface.ipcInterface.service.impl;

import org.springframework.web.bind.annotation.RequestBody;

import feign.RequestLine;

// https://woowabros.github.io/experience/2019/05/29/feign.html
public interface BackupAndRestore {
	//
    @RequestLine("PUT /appliication/bakcupAndRestore/start")
    String executeBackupAndRestore(@RequestBody String requestBody);
}
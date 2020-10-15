package com.soliatrdj9.imsd.application.mainNode.backupAndRestoreManager.service;

import com.soliatrdj9.imsd.application.mainNode.backupAndRestoreManager.model.BackupAndRestoreEvent;

public interface BackupAndRestoreManager {
	//
	public void executeBackupAndRestore(BackupAndRestoreEvent backupAndRestoreEvent);
}
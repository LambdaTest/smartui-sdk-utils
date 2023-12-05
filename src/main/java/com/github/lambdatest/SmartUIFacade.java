package com.github.lambdatest;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

public class SmartUIFacade {
    private final SmartUIUtils smartUIUtils;
    private final Logger log;

    public SmartUIFacade() {
        this.smartUIUtils = new SmartUIUtils();
        this.log = LoggerUtil.createLogger(SmartUIFacade.class.getName());
    }

    public boolean isSmartUIRunning() {
        return smartUIUtils.isSmartUIRunning();
    }

    public String fetchDOMSerializer() throws Exception {
        return smartUIUtils.fetchDOMSerializer();
    }

    public String postSnapshot(String snapshotDOM, String snapshotName, String testType) throws Exception {
        return smartUIUtils.postSnapshot(snapshotDOM, snapshotName, testType);
    }

    public Logger getLogger() {
        return log;
    }

    public static void takeSnapshot(WebDriver driver, String snapshotName) throws Exception {
        SmartUISnapshot.smartuiSnapshot(driver, snapshotName);
    }
}

package io.github.lambdatest;

import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import io.github.lambdatest.utils.LoggerUtil;
import io.github.lambdatest.utils.SmartUIUtil;

public class SmartUIFacade {
    private final SmartUIUtil smartUIUtils;
    private final Logger log;

    public SmartUIFacade() {
        this.smartUIUtils = new SmartUIUtil();
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
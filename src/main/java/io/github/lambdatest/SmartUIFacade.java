package io.github.lambdatest;

import java.util.HashMap;
import java.util.Map;
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

    public String postSnapshot(String snapshotDOM, Map<String, Object> options, String url, String snapshotName, String testType) throws Exception {
        return smartUIUtils.postSnapshot(snapshotDOM, options, url, snapshotName, testType);
    }

    public Logger getLogger() {
        return log;
    }

    // Method with options parameter
    public static void takeSnapshot(WebDriver driver, String snapshotName, Map<String, Object> options) throws Exception {
        SmartUISnapshot.smartuiSnapshot(driver, snapshotName, options);
    }

    // Overloaded method without options parameter
    public static void takeSnapshot(WebDriver driver, String snapshotName) throws Exception {
        takeSnapshot(driver, snapshotName, new HashMap<>()); // Pass an empty map for options
    }
}

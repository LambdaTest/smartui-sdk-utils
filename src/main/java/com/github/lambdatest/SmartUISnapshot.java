package com.github.lambdatest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import java.util.Map;
import java.util.logging.Logger;
import org.json.JSONObject;

public class SmartUISnapshot {

    

    public static void smartuiSnapshot(WebDriver driver, String snapshotName) throws Exception {
        if (driver == null) {
            throw new IllegalArgumentException("An instance of the selenium driver object is required.");
        }
        if (snapshotName == null || snapshotName.isEmpty()) {
            throw new IllegalArgumentException("The `snapshotName` argument is required.");
        }

        Logger log = LoggerUtil.createLogger(SmartUISnapshot.class.getName());

        SmartUIUtils smartUIUtils = new SmartUIUtils();

        if (!smartUIUtils.isSmartUIRunning()) {
            throw new IllegalStateException("SmartUI server is not running.");
        }

       

        try {
            // Check if driver is an instance of JavascriptExecutor
            if (driver instanceof JavascriptExecutor) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

                
                // Fetch DOM serializer and execute in Selenium
                String response = smartUIUtils.fetchDOMSerializer();
   
                JSONObject resp = new JSONObject(response);
                
                // Extract the DOM string from the JSON object
                String domString = resp.getJSONObject("data").getString("dom");

                // Execute script with the fetched DOM string
                ((JavascriptExecutor) driver).executeScript(domString);

                // Execute another script and retrieve the DOM
                String script = "return {'dom':SmartUIDOM.serialize()}";

                Map<String, Object> resultMap = (Map<String, Object>) jsExecutor.executeScript(script, new Object[] {});
                Map<String, Object> dom = (Map<String, Object>) resultMap.get("dom");
    
                smartUIUtils.postSnapshot(dom.get("html"), snapshotName, SmartUISnapshot.class.getName());
                log.info("Snapshot captured: " + snapshotName);
            } else {
                throw new IllegalStateException("The driver does not support JavaScript execution.");
            }

        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new Exception(e);
        }
    }
}

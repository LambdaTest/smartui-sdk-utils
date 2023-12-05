package com.github.lambdatest;

import org.openqa.selenium.WebDriver;
import com.github.lambdatest.utils.LoggerUtil;
import com.github.lambdatest.utils.SmartUIUtil;
import com.github.lambdatest.constants.Constants;
import com.github.lambdatest.models.DOMData;
import com.github.lambdatest.models.ResponseData;
import org.openqa.selenium.JavascriptExecutor;
import java.util.Map;
import java.util.logging.Logger;
import org.json.JSONObject;

public class SmartUISnapshot {
    public static void smartuiSnapshot(WebDriver driver, String snapshotName) throws Exception {
        if (driver == null) {
            throw new IllegalArgumentException(Constants.Errors.SELENIUM_DRIVER_NULL);
        }
        if (snapshotName == null || snapshotName.isEmpty()) {
            throw new IllegalArgumentException(Constants.Errors.SNAPSHOT_NAME_NULL);
        }
        Logger log = LoggerUtil.createLogger(SmartUISnapshot.class.getName());

        SmartUIUtil smartUIUtils = new SmartUIUtil();

        if (!smartUIUtils.isSmartUIRunning()) {
            throw new IllegalStateException(Constants.Errors.SMARTUI_NOT_RUNNING);
        }
        try {
            // Check if driver is an instance of JavascriptExecutor
            if (driver instanceof JavascriptExecutor) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

                // Fetch DOM serializer and execute in Selenium
                String response = smartUIUtils.fetchDOMSerializer();
                if (response == null || response.isEmpty()) {
                    throw new IllegalStateException(Constants.Errors.EMPTY_RESPONSE_DOMSERIALIZER);
                }
   
                JSONObject resp = new JSONObject(response);
                if (!resp.has("data")) {
                    throw new IllegalStateException(Constants.Errors.EMPTY_DATA_FIELD);
                }
                
                JSONObject dataObj = resp.getJSONObject("data");
                if (dataObj == null || !dataObj.has("dom")) {
                    throw new IllegalStateException(Constants.Errors.NULL_DATA_OBJECT);
                }

                // Extract the DOM string from the JSON object
                String domString = dataObj.getString("dom");
                if (domString == null || domString.isEmpty()) {
                    throw new IllegalStateException(Constants.Errors.NULL_DOM_STRING);
                }

                DOMData domData = new DOMData();
                domData.setDom(domString);

                ResponseData responseData = new ResponseData();
                responseData.setData(domData);

                // Execute script with the fetched DOM string
                ((JavascriptExecutor) driver).executeScript(domString);

                // Execute another script and retrieve the DOM
                String script = "return {'dom':SmartUIDOM.serialize()}";

                Map<String, Object> resultMap = (Map<String, Object>) jsExecutor.executeScript(script, new Object[] {});
                if (resultMap == null || !resultMap.containsKey("dom")) {
                    throw new IllegalStateException(Constants.Errors.NULL_RESULT_MAP);
                }

                Map<String, Object> dom = (Map<String, Object>) resultMap.get("dom");
                if (dom == null || !dom.containsKey("html")) {
                    throw new IllegalStateException(Constants.Errors.MISSING_HTML_KEY);
                }
    
                smartUIUtils.postSnapshot(dom.get("html"), snapshotName, SmartUISnapshot.class.getName());
                log.info("Snapshot captured: " + snapshotName);
            } else {
                throw new IllegalStateException(Constants.Errors.JAVA_SCRIPT_NOT_SUPPORTED);
            }

        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new Exception(e);
        }
    }
}

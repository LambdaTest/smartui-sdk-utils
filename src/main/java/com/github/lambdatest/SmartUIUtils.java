package com.github.lambdatest;

import java.util.logging.Logger;
import org.json.JSONObject;

public class SmartUIUtils {
    private final HttpClientUtil httpClient;
    private Logger log;

    public SmartUIUtils() {
       this.httpClient = new HttpClientUtil();
       this.log = LoggerUtil.createLogger(SmartUIUtils.class.getName());
    }

    public boolean isSmartUIRunning() {
        try {
            httpClient.isSmartUIRunning();
            return true;
        } catch (Exception e) {
            log.fine(e.getMessage());
            return false;
        }
    }

    public String fetchDOMSerializer() throws Exception {
        try {
            return httpClient.fetchDOMSerializer();
        } catch (Exception e) {
            log.fine(e.getMessage());
            throw new Exception("fetch DOMSerializer failed", e);
        }
    }

    public String postSnapshot(Object snapshotDOM, String snapshotName, String testType) throws Exception {
        // Constructing JSON using JSONObject
        JSONObject snapshot = new JSONObject();
        snapshot.put("dom", snapshotDOM);
        snapshot.put("name", snapshotName);

        JSONObject data = new JSONObject();
        data.put("snapshot", snapshot);
        data.put("testType", testType);

        String jsonData = data.toString();

        try {
            return httpClient.postSnapshot(jsonData);
        } catch (Exception e) {
            log.fine(e.getMessage());
            throw new Exception("Post snapshot failed", e);
        }
    }


    
}

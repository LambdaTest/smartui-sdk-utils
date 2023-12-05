package com.github.lambdatest.utils;

import java.util.logging.Logger;
import org.json.JSONObject;

import com.github.lambdatest.constants.Constants;

public class SmartUIUtil {
    private final HttpClientUtil httpClient;
    private Logger log;

    public SmartUIUtil() {
        this.httpClient = new HttpClientUtil();
        this.log = LoggerUtil.createLogger(SmartUIUtil.class.getName());
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
            throw new Exception(Constants.Errors.FETCH_DOM_FAILED, e);
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
            throw new Exception(Constants.Errors.POST_SNAPSHOT_FAILED, e);
        }
    }

    public static String getSmartUIServerAddress() {
        return System.getenv().getOrDefault(Constants.SMARTUI_SERVER_ADDRESS, Constants.LOCAL_SERVER_HOST);
    }



}
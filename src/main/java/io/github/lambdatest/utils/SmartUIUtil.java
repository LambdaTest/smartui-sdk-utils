package io.github.lambdatest.utils;

import java.util.logging.Logger;
import org.json.JSONObject;
import com.google.gson.Gson;
import io.github.lambdatest.constants.Constants;
import io.github.lambdatest.models.Snapshot;
import io.github.lambdatest.models.SnapshotData;

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
            log.severe(e.getMessage());
            return false;
        }
    }

    public String fetchDOMSerializer() throws Exception {
        try {
            return httpClient.fetchDOMSerializer();
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new Exception(Constants.Errors.FETCH_DOM_FAILED, e);
        }
    }

    public String postSnapshot(Object snapshotDOM, String snapshotName, String testType) throws Exception {

       // Create Snapshot and SnapshotData objects
        Snapshot snapshot = new Snapshot();
        snapshot.setDom(snapshotDOM);
        snapshot.setName(snapshotName);

        SnapshotData data = new SnapshotData();
        data.setSnapshot(snapshot);
        data.setTestType(testType);

        // Serialize to JSON using Gson
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);
        
        try {
            return httpClient.postSnapshot(jsonData);
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new Exception(Constants.Errors.POST_SNAPSHOT_FAILED, e);
        }
    }

    public static String getSmartUIServerAddress() {
        return System.getenv().getOrDefault(Constants.SMARTUI_SERVER_ADDRESS, Constants.LOCAL_SERVER_HOST);
    }
}
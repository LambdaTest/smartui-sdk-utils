package io.github.lambdatest.models;

import java.util.HashMap;
import java.util.Map;

public class Snapshot {
    private Object dom;
    private String name;
    private Map<String, Object> options;
    private String url;

    // Getters and Setters
    public Object getDom() {
        return dom;
    }

    public void setDom(Object dom) {
        this.dom = dom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }
}

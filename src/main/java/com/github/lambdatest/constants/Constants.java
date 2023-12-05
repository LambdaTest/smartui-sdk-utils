package com.github.lambdatest.constants;

public interface Constants {

  String SMARTUI_SERVER_ADDRESS = "SMARTUI_SERVER_ADDRESS";
  String LOCAL_SERVER_HOST = "http://localhost:8080";

  //SmartUI API routes
  interface SmartUIRoutes {
    public static final String SMARTUI_HEALTHCHECK_ROUTE = "/healthcheck";
    public static final String SMARTUI_DOMSERIALIZER_ROUTE = "/domserializer";
    public static final String SMARTUI_SNPASHOT_ROUTE = "/snapshot";
  }

  //Request methods
  interface RequestMethods {
    public static final String POST = "POST";
    public static final String GET = "GET";
  }

  //Logger colors
  interface LoggerColors {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
  }

  //Logger env vars
  interface LogEnvVars {
    public static final String LT_SDK_DEBUG = "LT_SDK_DEBUG";
    public static final String LT_SDK_LOG_LEVEL = "LT_SDK_LOG_LEVEL";
  }

  //Error constants
  interface Errors {
    public static final String SELENIUM_DRIVER_NULL = "An instance of the selenium driver object is required.";
    public static final String SNAPSHOT_NAME_NULL = "The `snapshotName` argument is required.";
    public static final String SMARTUI_NOT_RUNNING = "SmartUI server is not running.";
    public static final String JAVA_SCRIPT_NOT_SUPPORTED = "The driver does not support JavaScript execution.";
    public static final String EMPTY_RESPONSE_DOMSERIALIZER = "Response from fetchDOMSerializer is null or empty.";
    public static final String EMPTY_DATA_FIELD = "Response JSON does not contain 'data' field.";
    public static final String NULL_DATA_OBJECT = "Data object is null or missing 'dom' field.";
    public static final String NULL_DOM_STRING = "'dom' string is null or empty.";
    public static final String NULL_RESULT_MAP = "Result map is null or missing 'dom' key.";
    public static final String MISSING_HTML_KEY = "DOM map is null or missing 'html' key.";
    public static final String FETCH_DOM_FAILED = "fetch DOMSerializer failed";
    public static final String POST_SNAPSHOT_FAILED = "Post snapshot failed";
  }
}
package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestFailure {
    private String testName;
    private String summary;
    private String url;
    private String screenshotPath;
    private String stepsToReproduce;
    private String expectedResult;
    private String actualResult;
    private String timestamp;
    
    public TestFailure(String testName, String summary, String url, String screenshotPath,
                      String stepsToReproduce, String expectedResult, String actualResult) {
        this.testName = testName;
        this.summary = summary;
        this.url = url;
        this.screenshotPath = screenshotPath;
        this.stepsToReproduce = stepsToReproduce;
        this.expectedResult = expectedResult;
        this.actualResult = actualResult;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
    }
    
    // Getters
    public String getTestName() { return testName; }
    public String getSummary() { return summary; }
    public String getUrl() { return url; }
    public String getScreenshotPath() { return screenshotPath; }
    public String getStepsToReproduce() { return stepsToReproduce; }
    public String getExpectedResult() { return expectedResult; }
    public String getActualResult() { return actualResult; }
    public String getTimestamp() { return timestamp; }
}
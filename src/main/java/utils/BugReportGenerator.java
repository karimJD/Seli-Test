package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BugReportGenerator {
    
    private static int bugIdCounter = 1000;
    
    public static void generateReports(List<TestFailure> failures, String outputDir) {
        // Create output directory if it doesn't exist
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        for (TestFailure failure : failures) {
            generateSingleReport(failure, outputDir);
        }
    }
    
    private static void generateSingleReport(TestFailure failure, String outputDir) {
        String fileName = outputDir + "/" + failure.getTestName() + "_bug_report_" + 
                         LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
        
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("=====================================\n");
            writer.write("           BUG REPORT\n");
            writer.write("=====================================\n\n");
            
            writer.write("ID Number: #" + (bugIdCounter++) + "\n\n");
            
            writer.write("Name: " + formatTestName(failure.getTestName()) + "\n\n");
            
            writer.write("Reporter: Automated Test Suite\n\n");
            
            writer.write("Submit Date: " + failure.getTimestamp() + "\n\n");
            
            writer.write("Summary:\n");
            writer.write(failure.getSummary() + "\n\n");
            
            writer.write("URL: " + failure.getUrl() + "\n\n");
            
            writer.write("Screenshot: " + failure.getScreenshotPath() + "\n\n");
            
            writer.write("Platform: " + System.getProperty("os.name") + "\n\n");
            
            writer.write("Operating System: " + System.getProperty("os.name") + " " + 
                        System.getProperty("os.version") + "\n\n");
            
            writer.write("Browser: Chrome\n\n");
            
            writer.write("Severity: " + determineSeverity(failure.getTestName()) + "\n\n");
            
            writer.write("Assigned to: /\n\n");
            
            writer.write("Priority: " + determinePriority(failure.getTestName()) + "\n\n");
            
            writer.write("Description:\n");
            writer.write(failure.getSummary() + "\n\n");
            
            writer.write("Steps to Reproduce:\n");
            writer.write(failure.getStepsToReproduce() + "\n\n");
            
            writer.write("Expected Result:\n");
            writer.write(failure.getExpectedResult() + "\n\n");
            
            writer.write("Actual Result:\n");
            writer.write(failure.getActualResult() + "\n\n");
            
            writer.write("Notes:\n");
            writer.write("This bug was automatically detected during automated testing.\n");
            writer.write("Test execution timestamp: " + failure.getTimestamp() + "\n");
            
            System.out.println("✓ Bug report generated: " + fileName);
            
        } catch (IOException e) {
            System.err.println("✗ Failed to generate bug report for " + failure.getTestName() + ": " + e.getMessage());
        }
    }
    
    private static String formatTestName(String testName) {
        // Convert camelCase to Title Case with spaces
        return testName.replaceAll("([A-Z])", " $1")
                      .replaceAll("([a-z])([A-Z])", "$1 $2")
                      .trim()
                      .toUpperCase();
    }
    
    private static String determineSeverity(String testName) {
        // Critical features that affect core functionality
        if (testName.contains("Cart") || testName.contains("addToCart") || 
            testName.contains("removeFromCart") || testName.contains("addMultiple")) {
            return "Major";
        }
        
        // Login/Authentication issues
        if (testName.contains("login") || testName.contains("logout")) {
            return "High";
        }
        
        // UI/Display issues
        if (testName.contains("Details") || testName.contains("Elements")) {
            return "Medium";
        }
        
        return "Medium";
    }
    
    private static String determinePriority(String testName) {
        // Critical features that affect core functionality
        if (testName.contains("Cart") || testName.contains("addToCart") || 
            testName.contains("checkout")) {
            return "High";
        }
        
        // Login/Authentication issues
        if (testName.contains("login")) {
            return "High";
        }
        
        // Search and navigation
        if (testName.contains("search") || testName.contains("select")) {
            return "Medium";
        }
        
        return "Medium";
    }
}
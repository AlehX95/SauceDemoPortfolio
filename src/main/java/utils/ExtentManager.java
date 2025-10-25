package utils;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance("target/extent-report.html");
        }
        return extent;
    }

    public static ExtentReports createInstance(String fileName) {
    	 ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileName);
    	 sparkReporter.config().setDocumentTitle("Automation Test Report");
    	 sparkReporter.config().setReportName("Selenium Automation Results");
    	 sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Tester", "Alejandro Rosas Garcia");
        extent.setSystemInfo("Environment", "QA");

        return extent;
    }
}


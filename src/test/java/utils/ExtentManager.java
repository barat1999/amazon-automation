package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    public static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports createExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("target/extent-reports/index.html");
        reporter.config().setDocumentTitle("Amazon Functional Tests");
        reporter.config().setReportName("Amazon Functional Tests");
        extentReports.attachReporter(reporter);
        return extentReports;
    }
}

package utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import static utils.ExtentTestManager.getTest;

public class ScreenshotManager {
    public static String convertScreenshotToBase64(File screenshotFile) throws IOException {
        byte[] fileBytes = Files.readAllBytes(screenshotFile.toPath());
        return Base64.getEncoder().encodeToString(fileBytes);
    }

    public void takeScreenshot(WebDriver driver) throws IOException {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotBase64 = convertScreenshotToBase64(screenshotFile);

        try {
            getTest().info(MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
        } catch (Exception e) {
            Logger.warn("Failed to attach screenshot.");
        }
    }
}

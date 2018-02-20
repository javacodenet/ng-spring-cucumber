package com.acceptance.tests.selenium;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class SharedWebDriverBackup {

    public static final int DEFAULT_WAIT_TIME = 10;
    private Logger log = LoggerFactory.getLogger(getClass());

    private static SharedWebDriver instance;

    private RemoteWebDriver driver;
    private NgWebDriver webDriver;

    enum Browser {CHROME, FIREFOX, HTMLUNIT}

    private String baseUrl;

    public static SharedWebDriver getInstance() {
        if (instance == null) {
            instance = new SharedWebDriver();
            instance.init();
        }

        return instance;
    }

    @PostConstruct//?
    public void init() {
        switch (getBrowser()) {

            case CHROME:
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/browsers/chrome/chromedriver.exe");

                ChromeOptions options = new ChromeOptions();
                options.addArguments("disable-extensions");
                options.addArguments("window-size=1280,1024");
                options.addArguments("window-position=0,0");
                options.addArguments("no-sandbox");
                options.addArguments("disable-infobars");

                LoggingPreferences logPrefs = new LoggingPreferences();
                logPrefs.enable(LogType.BROWSER, Level.ALL);
                DesiredCapabilities caps = DesiredCapabilities.chrome();
                caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

                caps.setCapability(ChromeOptions.CAPABILITY, options);
                driver = new ChromeDriver(caps);
                break;
            case FIREFOX:

                final String firefoxPath = System.getProperty("user.dir")
                        + File.separator + "browsers" + File.separator
                        + "firefox" + File.separator + "firefox.exe";

                final String firefoxProfilePath = System.getProperty("user.dir")
                        + File.separator + "browsers" + File.separator
                        + "firefox" + File.separator + "profile";

                final FirefoxProfile profile = new FirefoxProfile(new File(firefoxProfilePath));
                profile.setPreference("network.proxy.type", 0);
                profile.setPreference("browser.cache.disk.enable", false);
                profile.setPreference("browser.cache.memory.enable", false);
                profile.setPreference("browser.cache.offline.enable", false);
                driver = new FirefoxDriver(new FirefoxBinary(new File(firefoxPath)), profile);

                break;
            case HTMLUNIT:
                break;
        }
        driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_TIME, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
//        driver.manage().window().setSize(new Dimension(1280, 1024));

        webDriver = new NgWebDriver(driver);
        // close selenium when all of the tests are finished
        attachShutDownHook();
    }

    public void cleanUp() {
        instance.getDriver().manage().deleteAllCookies();

        try {
            executeJavascript("javascript:localStorage.clear();");
        } catch (Exception e) {
            // ignore, cannot clear localStorage if the page hasn't been loaded
        }
        executeJavascript("console.clear()");
        // this clears the current console log store.
        getDriver().manage().logs().get(LogType.BROWSER);
    }

    /**
     * our UI keeps elements hidden sometimes when we do not want them to be, we have to remove the css
     * so we can click it
     * <p>
     * use this if you get an error with something like this
     * <p>
     * 'You may only interact with visible elements'
     *
     * @param elementId the dom elementId of it
     */
    public void makeElementVisible(final String elementId) {
        executeJavascript("document.getElementById('" + elementId + "').setAttribute('class', '')");
    }

    private void executeJavascript(String javascript) {
        driver.executeScript(javascript);

    }

    public void createBaseUrl(int port) {
        createBaseUrl(port, "web-core");
    }

    public void createBaseUrl(int port, String contextRoot) {
        baseUrl = "http://localhost:" + port + "/";
        log.info("running with base url: {}", baseUrl);
    }

    public void home() {
        navigate("");
    }

    public void navigate(final String path) {
        getDriver().navigate().to(baseUrl + path);
    }

    /**
     * do not wait ages for this element because we know it should be there right away - it will
     * just slow the tests down
     */
    public boolean elementExists(By by) {
        boolean exists = false;
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        try {
            exists = getDriver().findElement(by) != null;
        } catch (Exception e) {
            log.error("element does not exist", e);
        } finally {
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_TIME, TimeUnit.SECONDS);
        }

        return exists;

    }

    public void shutDown() {
        log.info("shutting down selenium driver");
        try {

            if (driver != null) {
                driver.close();
                driver.quit();
            }
        } catch (Exception e) {
            log.error("could not close browser, close it by hand", e);
        }
    }

   /* public void saveScreenShot(final String name) throws IOException {
        File scrFile = ((TakesScreenshot) instance.getDriver()).getScreenshotAs(OutputType.FILE);
        final String directory = com.ngahr.myview.hermes.test.framework.utils.FileUtils.getCurrentWorkingDirectory()
                + File.separator + "target" + File.separator + "selenium";
        System.err.println("Saving Screenshot to: " + directory + name);
        FileUtils.copyFile(scrFile, new File(directory, name));
    }*/

    public WebDriver getDriver() {
        return driver;
    }

    private Browser getBrowser() {
        Browser browser = Browser.CHROME;
        log.info("creating BROWSER of type: {}", browser.name());
        return browser;
    }

    public void attachShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                shutDown();
            }
        });
        log.info("Shut Down Hook Attached.");
    }

    public static void main(String[] args) {
        SharedWebDriver.getInstance();
    }

    public void waitForAngular() {
        webDriver.waitForAngularRequestsToFinish();
    }


    public void printConsoleLogs() {
        LogEntries logEntries = SharedWebDriver.getInstance().getDriver().manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            log.error(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
        }
    }

    // These methods can be used to change the timeout setting, useful for speeding up checking for elements you expect NOT to be on the screen.
    public void setTimeout(int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public void resetTimeout() {
        driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_TIME, TimeUnit.SECONDS);
    }

    public void navbarSafeClick(WebElement clickElement) {
        try {
            clickElement.click();
        } catch (WebDriverException e) {
            driver.executeScript("$(window).scrollTop($(window).scrollTop() + 40);");
            clickElement.click();
        }
    }

    public void scrollElementIntoView(WebElement webElement) {
        driver.executeScript("arguments[0].scrollIntoView(true)", webElement);
    }

    public void scrollElementIntoTopView(WebElement webElement) {
        driver.executeScript("arguments[0].scrollIntoView(false)", webElement);
    }

    public void navbarSafeDoubleClick(WebElement clickElement) {
        driver.executeScript("arguments[0].scrollIntoView(true)", clickElement);

        Actions action = new Actions(driver);
        action.doubleClick(clickElement).perform();
    }

    public WebElement findOptionalElement(By selector, SearchContext context) {
        SearchContext temp = context;
        if (temp == null) {
            temp = driver;
        }
        WebElement result = null;
        try {
            setTimeout(1);
            result = temp.findElement(selector);
            resetTimeout();
        } catch (NoSuchElementException e) {
            log.debug("Element not found", e);
        }
        return result;
    }

    public List<WebElement> findOptionalElements(By selector, SearchContext context) {
        SearchContext temp = context;
        if (temp == null) {
            temp = driver;
        }
        List<WebElement> result = new ArrayList<>();
        try {
            setTimeout(1);
            result = temp.findElements(selector);
            resetTimeout();
        } catch (NoSuchElementException e) {
            log.debug("Element not found", e);
        }
        return result;
    }

    public String getChildlessText(WebElement element) {
        String text = element.getText();
        List<WebElement> c = element.findElements(By.xpath("./*"));
        for (WebElement child : c) {
            String childText = child.getText();
            text = text.replaceFirst(Pattern.quote(childText), "");
        }
        return text.trim();
    }

    public String getHiddenElementText(WebElement element) {
        return String.valueOf(driver.executeScript("return arguments[0].innerHTML", element)).trim();
    }

}

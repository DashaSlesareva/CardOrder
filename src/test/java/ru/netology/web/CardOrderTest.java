package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardOrderTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
//        System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @Test
    void positiveTest() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Андрей");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+78964758369");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.className("paragraph")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void negativeTestIncorrectName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Andrey");
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("span[data-test-id='name']")).getAttribute("class");
        assertTrue(text.contains("input_invalid"));
    }

    @Test
    void negativeTestIncorrectPhoneNumber() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Андрей");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7896475830");
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("span[data-test-id='phone']")).getAttribute("class");
        assertTrue(text.contains("input_invalid"));
    }

    @Test
    void negativeTestFormNotFilledIn() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("span[data-test-id='name']")).getAttribute("class");
        assertTrue(text.contains("input_invalid"));
    }

    @Test
    void negativeTestNameNotFilledIn() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+78964758369");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("span[data-test-id='name']")).getAttribute("class");
        assertTrue(text.contains("input_invalid"));
    }

    @Test
    void negativeTestPhoneNumberNotFilledIn() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Андрей");        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("span[data-test-id='phone']")).getAttribute("class");
        assertTrue(text.contains("input_invalid"));
    }

    @Test
    void negativeTestCheckboxNotChecked() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Андрей");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+78964758369");
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.className("checkbox")).getAttribute("class");
        assertTrue(text.contains("input_invalid"));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

}

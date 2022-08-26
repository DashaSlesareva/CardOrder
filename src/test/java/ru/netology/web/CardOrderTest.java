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
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @Test
    void positiveTestSingleName() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Андрей");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+78964758369");
        driver.findElement(By.cssSelector("label[data-test-id='agreement'] span")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("p[data-test-id=\"order-success\"]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void positiveTestNameAndSurname() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Андрей Петров");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+78964758369");
        driver.findElement(By.cssSelector("label[data-test-id='agreement'] span")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("p[data-test-id=\"order-success\"]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void positiveTestNameAndDoubleSurname() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Андрей Петров-Иванов");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+78964758369");
        driver.findElement(By.cssSelector("label[data-test-id='agreement'] span")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("p[data-test-id=\"order-success\"]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void negativeTestIncorrectName() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Andrey");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+78964758369");
        driver.findElement(By.cssSelector("label[data-test-id='agreement'] span")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void negativeTestIncorrectPhoneNumber() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Андрей");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7896475830");
        driver.findElement(By.cssSelector("label[data-test-id='agreement'] span")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());

    }

    @Test
    void negativeTestFormNotFilledIn() {
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void negativeTestNameNotFilledIn() {
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+78964758369");
        driver.findElement(By.cssSelector("label[data-test-id='agreement'] span")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void negativeTestPhoneNumberNotFilledIn() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Андрей");
        driver.findElement(By.cssSelector("label[data-test-id='agreement'] span")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void negativeTestCheckboxNotChecked() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Андрей");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+78964758369");
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"agreement\"].input_invalid .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

}

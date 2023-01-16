package ru.smpr.web;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationTests {

    @BeforeEach
    void settings() {
        Configuration.baseUrl = "http://192.168.30.181/smpr/";
        Configuration.timeout = 10000;
        open(Configuration.baseUrl);
    }

    @Test
    void successAuthentication() {
        $(By.name("username")).shouldHave(type("text")).shouldHave(attribute("placeholder", "Логин")).sendKeys("polygon");
        $(By.name("password")).shouldHave(type("password")).shouldHave(attribute("placeholder", "Пароль")).sendKeys("123123");
        $("button").click();
        $x("//*[contains(text(),'Формы визуализации')]").shouldBe(visible);
        closeWindow();
    }

    @Test
    void successAuthenticationCapsLogin() {
        $(By.name("username")).sendKeys("POLYGON");
        $(By.name("password")).sendKeys("123123");
        $("button").click();
        $x("//*[contains(text(),'Формы визуализации')]").shouldBe(visible);
        closeWindow();
    }

    @Test
    void unsuccessfulAuthentication() {
        $(By.name("username")).sendKeys("falseLogin");
        $(By.name("password")).sendKeys("falsePassword");
        $("button").click();
        $x("//*[contains(text(),'попытка входа не удалась')]").shouldBe(visible);
        String expectedMessage = "Ваша попытка входа не удалась, попробуйте еще раз.\n" +
                "Причина: Bad credentials";
        String actualMessage = $x("//*[contains(text(),'попытка входа не удалась')]").getText().trim();
        assertEquals(actualMessage, expectedMessage);
        closeWindow();
    }

    @Test
    void unsuccessfulAuthenticationEmptyLogin() {
        $(By.name("password")).sendKeys("123123");
        $("button").click();
        $x("//*[contains(text(),'попытка входа не удалась')]").shouldBe(visible);
        String expectedMessage = "Ваша попытка входа не удалась, попробуйте еще раз.\n" +
                "Причина: Empty Username";
        String actualMessage = $x("//*[contains(text(),'попытка входа не удалась')]").getText().trim();
        assertEquals(actualMessage, expectedMessage);
        closeWindow();
    }

    @Test
    void unsuccessfulAuthenticationEmptyPassword() {
        $(By.name("username")).sendKeys("polygon");
        $("button").click();
        $x("//*[contains(text(),'попытка входа не удалась')]").shouldBe(visible);
        String expectedMessage = "Ваша попытка входа не удалась, попробуйте еще раз.\n" +
                "Причина: Empty Password";
        String actualMessage = $x("//*[contains(text(),'попытка входа не удалась')]").getText().trim();
        assertEquals(actualMessage, expectedMessage);
        closeWindow();
    }

    @Test
    void unsuccessfulAuthenticationEmptyForm() {
        $("button").click();
        $x("//*[contains(text(),'попытка входа не удалась')]").shouldBe(visible);
        String expectedMessage = "Ваша попытка входа не удалась, попробуйте еще раз.\n" +
                "Причина: Empty Username";
        String actualMessage = $x("//*[contains(text(),'попытка входа не удалась')]").getText().trim();
        assertEquals(actualMessage, expectedMessage);
        closeWindow();
    }
}

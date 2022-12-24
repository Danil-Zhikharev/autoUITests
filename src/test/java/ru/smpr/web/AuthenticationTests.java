package ru.smpr.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;


import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationTests {

    @BeforeEach
    void settings() {
        Configuration.timeout = 10000;
        Configuration.headless = true;
        open("http://192.168.30.181/smpr/");
    }

    @Test
    void successAuthentication() {
        $(By.name("username")).sendKeys("polygon");
        $(By.name("password")).sendKeys("123123");
        $(By.cssSelector("button")).click();
        String actualWeb = $(By.xpath("//span[contains(text(),'Формы визуализации')]")).getText().trim();
        String expectedWeb = "Формы визуализации";
        Assertions.assertEquals(actualWeb, expectedWeb);
        closeWindow();
    }

    @Test
    void successAuthenticationCapsLogin() {
        $(By.name("username")).sendKeys("POLYGON");
        $(By.name("password")).sendKeys("123123");
        $(By.cssSelector("button")).click();
        String actualWeb = $(By.xpath("//span[contains(text(),'Формы визуализации')]")).getText().trim();
        String expectedWeb = "Формы визуализации";
        Assertions.assertEquals(actualWeb, expectedWeb);
        closeWindow();
    }

    @Test
    void unsuccessfulAuthentication() {
        $(By.name("username")).sendKeys("falseLogin");
        $(By.name("password")).sendKeys("falsePassword");
        $(By.cssSelector("button")).click();
        String expectedMessage = "Ваша попытка входа не удалась, попробуйте еще раз.\n" +
                "Причина: Bad credentials";
        String actualMessage = $(By.xpath("//span[contains(text(),'попытка входа не удалась')]")).getText().trim();
        assertEquals(actualMessage, expectedMessage);
        closeWindow();
    }

    @Test
    void unsuccessfulAuthenticationEmptyLogin() {
        $(By.name("password")).sendKeys("123123");
        $(By.cssSelector("button")).click();
        String expectedMessage = "Ваша попытка входа не удалась, попробуйте еще раз.\n" +
                "Причина: Empty Username";
        String actualMessage = $(By.xpath("//span[contains(text(),'попытка входа не удалась')]")).getText().trim();
        assertEquals(actualMessage, expectedMessage);
        closeWindow();
    }

    @Test
    void unsuccessfulAuthenticationEmptyPassword() {
        $(By.name("username")).sendKeys("polygon");
        $(By.cssSelector("button")).click();
        String expectedMessage = "Ваша попытка входа не удалась, попробуйте еще раз.\n" +
                "Причина: Empty Password";
        String actualMessage = $(By.xpath("//span[contains(text(),'попытка входа не удалась')]")).getText().trim();
        assertEquals(actualMessage, expectedMessage);
        closeWindow();
    }

    @Test
    void unsuccessfulAuthenticationEmptyForm() {
        $(By.cssSelector("button")).click();
        String expectedMessage = "Ваша попытка входа не удалась, попробуйте еще раз.\n" +
                "Причина: Empty Username";
        String actualMessage = $(By.xpath("//span[contains(text(),'попытка входа не удалась')]")).getText().trim();
        assertEquals(actualMessage, expectedMessage);
        closeWindow();
    }
}

package ru.smpr.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorizationTest {

    @Test
    void successAuthorization() {
        Configuration.timeout = 10000;
        open("http://192.168.30.181/smpr/");
        $(By.name("username")).sendKeys("polygon");
        $(By.name("password")).sendKeys("123123");
        $(By.cssSelector("button")).click();
        String actualWeb = $(By.xpath("//span[contains(text(),'Формы визуализации')]")).getText().trim();
        String expectedWeb = "Формы визуализации";
        Assertions.assertEquals(actualWeb, expectedWeb);
    }

    @Test
    void unsuccessfulAuthorization() {
        open("http://192.168.30.181/smpr/system/login.zul");
        $(By.name("username")).sendKeys("falseLogin");
        $(By.name("password")).sendKeys("falsePassword");
        $(By.cssSelector("button")).click();
        String expectedMessage = "Ваша попытка входа не удалась, попробуйте еще раз.\n" +
                "Причина: Bad credentials";
        String actualMessage = $(By.xpath("//span[contains(text(),'Ваша попытка входа не удалась, попробуйте еще раз.')]")).getText().trim();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void shouldTestUnsuccessfulAuthorization() {
        open("http://192.168.30.181/smpr/system/login.zul");
        $(By.name("username")).sendKeys("falseLogin");
        $(By.name("password")).sendKeys("falsePassword");
        $(By.cssSelector("button")).click();
        String expectedMessage = "Ваша попытка входа не удалась, попробуйте еще раз.\n" +
                "Причина: Bad credentials";
        String actualMessage = $(By.xpath("//span[contains(text(),'Ваша попытка входа не удалась, попробуйте еще раз.')]")).getText().trim();
        assertEquals(actualMessage, expectedMessage);
    }


}

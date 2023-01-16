package ru.smpr.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;



import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class TopologyAddSearchDeleteRegion {

    static String login = "polygon";
    static String  password = "123123";

    String region = "Земля";
    String desc = "Тестовое описание";
    Integer id = 12345;




    @BeforeAll
    public static void authentication() {
        Configuration.baseUrl = "http://192.168.30.181/smpr/";
        open(Configuration.baseUrl);
        $(By.name("username")).shouldHave(type("text")).shouldHave(attribute("placeholder", "Логин")).sendKeys(login);
        $(By.name("password")).shouldHave(type("password")).shouldHave(attribute("placeholder", "Пароль")).sendKeys(password);
        $(By.cssSelector("button")).click();
        $(By.xpath("//*[contains(text(),'Формы визуализации')]")).shouldBe(visible);
    }

    @BeforeEach
    void settings() {
        Configuration.timeout = 30000;
    }

    void saveAndReload() {
        $x("//button[contains(text(), 'Сохранить') and @title['Сохранить все изменения']]").click();
        $x("//*[contains(text(), 'сохранен')]/parent::*[not(contains(@style, 'display'))]/child::*[contains(text(), 'сохранен')]").shouldHave(visible);
        $x("//button[contains(text(), 'Перезагрузить')]").click();
        $x("//*[contains(text(), 'успешно')]/parent::*[not(contains(@style, 'display'))]/child::*[contains(text(), 'успешно')]").shouldHave(visible);
    }

    @Test
    void addRegion() throws InterruptedException {
        $x("//*[contains(text(), 'Справочник объектов')]").click();
        $x("//*[contains(text(), 'Добавить регион')]").click();
        $x("//*[contains(text(), 'Идентификатор')][contains(@class, 'z-label')]/ancestor::td/following-sibling::td/descendant::*[@type='text']").setValue(String.valueOf(id));
        $x("//*[contains(text(), 'Наименование')][contains (@class,'z-label')]/ancestor::td/following-sibling::td/descendant::*[@type='text']").setValue(region);
        $x("//*[contains(text(), 'Хост')][contains (@class,'z-label')]/ancestor::td/following-sibling::td/descendant::*[@type='text']").setValue(String.valueOf(id));
        $(".z-spinner-input").setValue(String.valueOf(id));
        $x("//*[@class='z-spinner-icon z-spinner-up']").hover().click();
        $x("//*[@class='z-spinner-icon z-spinner-down']").hover().click();
        $x("//*[contains(text(), 'Описание')][contains (@class,'z-label')]/ancestor::td/following-sibling::td/descendant::textarea").setValue(desc);
        saveAndReload();
        $x("//button[contains(text(), 'Свернуть')]/parent::*/following-sibling::*/*[contains(@placeholder, 'Поиск')]").setValue(region);
        $x("//*[@title='Удалить']/parent::div/child::*[@title='Удалить']").hover();
        $x("//*[@title='Удалить']/parent::div/child::*[@title='Удалить']").click();
        Thread.sleep(1000);
        saveAndReload();
        $x("//button[contains(text(), 'Свернуть')]/parent::*/following-sibling::*/*[contains(@placeholder, 'Поиск')]").setValue(region);
        $x("//span[contains(text(), 'не найдены')][@style='width: 150px;']").isDisplayed();
        closeWindow();
    }
}

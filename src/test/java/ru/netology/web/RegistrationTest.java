package ru.netology.web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class RegistrationTest {

    @Test
    void shouldRegisterCardDelivery() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataGenerator.generateFirstDateOfMeeting());
        $("[data-test-id=name] input").setValue(DataGenerator.generateByFakerName("ru"));
        $("[data-test-id=phone] input").setValue(DataGenerator.generateByFakerPhone("ru"));
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("Встреча успешно запланирована на " + DataGenerator.generateFirstDateOfMeeting()));
        $(byText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataGenerator.generateSecondDateOfMeeting());
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("Встреча успешно запланирована на " + DataGenerator.generateSecondDateOfMeeting()));
    }

    @Test
    void shouldNotRegisterByName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataGenerator.generateFirstDateOfMeeting());
        $("[data-test-id=name] input").setValue(DataGenerator.generateByFakerNameForeign("es"));
        $("[data-test-id=phone] input").setValue(DataGenerator.generateByFakerPhone("ru"));
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=name].input_invalid").shouldHave(exactText("Фамилия и имя Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
        //тест падает, на форме нет проверки на валидность номера телефона, на ввод принимается даже 1 цифра
    void shouldNotRegisterByPhone() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataGenerator.generateFirstDateOfMeeting());
        $("[data-test-id=name] input").setValue(DataGenerator.generateByFakerName("ru"));
        $("[data-test-id=phone] input").setValue("0");
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=phone] .input_invalid").waitUntil(visible, 15000).shouldHave(text("Ошибка"));  //указала произвольный текст ошибки, т.к. на форме ее нет
    }


    @Test
    void shouldRegisterWithSymbolName() {   //тест не должен падать, но падает из-за символа ё
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataGenerator.generateFirstDateOfMeeting());
        $("[data-test-id=name] input").setValue(DataGenerator.setNameWithSymbols());
        $("[data-test-id=phone] input").setValue(DataGenerator.generateByFakerPhone("ru"));
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("Встреча успешно запланирована на " + DataGenerator.generateFirstDateOfMeeting()));
        $(byText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataGenerator.generateSecondDateOfMeeting());
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("Встреча успешно запланирована на " + DataGenerator.generateSecondDateOfMeeting()));
    }


}

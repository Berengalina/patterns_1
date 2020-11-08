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
        $("[data-test-id=city] input").setValue(DataGenerator.Registration.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.generateFirstDateOfMeeting());
        $("[data-test-id=name] input").setValue(DataGenerator.Registration.generateByFakerName("ru"));
        $("[data-test-id=phone] input").setValue(DataGenerator.Registration.generateByFakerPhone("ru"));
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("Встреча успешно запланирована на " + DataGenerator.Registration.generateFirstDateOfMeeting()));
        $(byText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.generateSecondDateOfMeeting());
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("Встреча успешно запланирована на " + DataGenerator.Registration.generateSecondDateOfMeeting()));
    }

    @Test
    void shouldNotRegisterByName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.Registration.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.generateFirstDateOfMeeting());
        $("[data-test-id=name] input").setValue(DataGenerator.Registration.generateByFakerNameForeign("es"));
        $("[data-test-id=phone] input").setValue(DataGenerator.Registration.generateByFakerPhone("ru"));
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=name].input_invalid").shouldHave(exactText("Фамилия и имя Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
        //тест должен падать, на форме нет проверки на валидность номера телефона (начинается на 7), на ввод принимается даже 1 цифра
    void shouldNotRegisterByPhone() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.Registration.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.generateFirstDateOfMeeting());
        $("[data-test-id=name] input").setValue(DataGenerator.Registration.generateByFakerName("ru"));
        $("[data-test-id=phone] input").setValue(DataGenerator.Registration.generateByFakerPhoneForeign("es"));
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("Встреча успешно запланирована на " + DataGenerator.Registration.generateFirstDateOfMeeting()));
        $(byText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.generateSecondDateOfMeeting());
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("Встреча успешно запланирована на " + DataGenerator.Registration.generateSecondDateOfMeeting()));
    }


    @Test
    void shouldRegisterWithSymbolName() {   //тест не должен падать, но падает из-за символа ё
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.Registration.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.generateFirstDateOfMeeting());
        $("[data-test-id=name] input").setValue(DataGenerator.Registration.generateNameWithRussianSymbolsAndHyphen());
        $("[data-test-id=phone] input").setValue(DataGenerator.Registration.generateByFakerPhone("ru"));
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("Встреча успешно запланирована на " + DataGenerator.Registration.generateFirstDateOfMeeting()));
        $(byText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.generateSecondDateOfMeeting());
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("Встреча успешно запланирована на " + DataGenerator.Registration.generateSecondDateOfMeeting()));
    }


}

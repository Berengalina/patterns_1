package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class RegistrationTest {

    @Test
    void shouldRegisterCardDelivery() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataHelper.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataHelper.generateFirstDateOfMeeting());
        $("[data-test-id=name] input").setValue(DataHelper.generateByFakerName("ru"));
        $("[data-test-id=phone] input").setValue(DataHelper.generateByFakerPhone("ru"));
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("Встреча успешно запланирована на " + DataHelper.generateFirstDateOfMeeting()));
        $(byText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataHelper.generateSecondDateOfMeeting());
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("Встреча успешно запланирована на " + DataHelper.generateSecondDateOfMeeting()));
    }

    @Test
    void shouldNotRegisterByName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataHelper.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataHelper.generateFirstDateOfMeeting());
        $("[data-test-id=name] input").setValue(DataHelper.generateByFakerNameForeign("es"));
        $("[data-test-id=phone] input").setValue(DataHelper.generateByFakerPhone("ru"));
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=name].input_invalid").shouldHave(exactText("Фамилия и имя Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
        //тест падает, на форме нет проверки на валидность номера телефона, на ввод принимается даже 1 цифра
    void shouldNotRegisterByPhone() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataHelper.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataHelper.generateFirstDateOfMeeting());
        $("[data-test-id=name] input").setValue(DataHelper.generateByFakerName("ru"));
        $("[data-test-id=phone] input").setValue(DataHelper.generateByFakerDigit());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=phone] .input_invalid").waitUntil(visible, 15000).shouldHave(text("Ошибка"));  //указала произвольный текст ошибки, т.к. на форме ее нет
    }


    @Test
    void shouldRegisterWithSymbolName() {   //тест не должен падать, но падает из-за символа ё
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataHelper.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataHelper.generateFirstDateOfMeeting());
        $("[data-test-id=name] input").setValue(DataHelper.setNameWithSymbols());
        $("[data-test-id=phone] input").setValue(DataHelper.generateByFakerPhone("ru"));
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("Встреча успешно запланирована на " + DataHelper.generateFirstDateOfMeeting()));
        $(byText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(DataHelper.generateSecondDateOfMeeting());
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("Встреча успешно запланирована на " + DataHelper.generateSecondDateOfMeeting()));
    }


}

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
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.generateSecondDateOfMeeting());
        $(byText("Запланировать")).click();
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content").waitUntil(visible, 15000).shouldHave(text("Встреча успешно запланирована на " + DataGenerator.Registration.generateSecondDateOfMeeting()));
    }

}

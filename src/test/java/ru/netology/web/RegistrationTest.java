package ru.netology.web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationTest {

    DataGenerator dataGenerator = new DataGenerator();

    @Test
    void shouldRegisterCardDelivery() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(dataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        $("[data-test-id=name] input").setValue(dataGenerator.getName());
        $("[data-test-id=phone] input").setValue(dataGenerator.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=success-notification]").waitUntil(text("Встреча успешно запланирована на " + LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))), 15000).shouldBe(visible);
        $("[data-test-id=date] input").setValue(LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        $(byText("Запланировать")).click();
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification]").waitUntil(text("Встреча успешно запланирована на " + LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))), 15000).shouldBe(visible);
    }

}


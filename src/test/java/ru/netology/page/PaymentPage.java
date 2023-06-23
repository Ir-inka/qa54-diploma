package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class PaymentPage {
    private final SelenideElement buyButton = $x("//span[text()='Купить']//ancestor::button");
    private final SelenideElement buyByCredit = $x("//span[text()='Купить в кредит']//ancestor::button");
    private final SelenideElement fieldCardNumber = $("[placeholder=\"0000 0000 0000 0000\"]");
    private final SelenideElement monthField = $("[placeholder=\"08\"]");
    private final SelenideElement fieldYear = $("[placeholder=\"22\"]");
    private final SelenideElement OwnerField = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cvcField = $("[placeholder=\"999\"]");
    private final SelenideElement continueButton = $(withText("Продолжить"));
    private final SelenideElement notification = $x("//div[contains(@class, 'notification_status_ok')]");
    private final SelenideElement notificationError = $x("//div[contains(@class, 'notification_status_error')]");
    private final SelenideElement wrongFormat = $(withText("Неверный формат"));

    public void openPageBuyByCard() {                                                                           // Переход на страницу оплаты картой
        buyButton.click();
    }

    public void openPageBuyOnCredit() {                                                                          // Переход на страницу покупки в кредит
        buyByCredit.click();
    }

    public void fillCardNumberField(String cardNumber) {                                                               // Заполнение поля карты
        fieldCardNumber.setValue(cardNumber);
    }

    public void fillMonthField(String month) {                                                                      // Заполнение поля месяц
        monthField.setValue(month);
    }

    public void fillYearField(String year) {                                                                      // Заполнение поля год
        fieldYear.setValue(year);
    }

    public void fillOwnerField(String owner) {                                                                    // Заполнение поля владелец
        OwnerField.setValue(owner);
    }

    public void fillCvcField(String cvc) {                                                                            // Заполнение поля CVC
        cvcField.setValue(cvc);
    }

    public void clickContinueButton() {                                                                                  // Нажать Продолжить
        continueButton.click();
    }

    public void successNotificationOutput() {                                                                         // уведомление об успехе
        notification.shouldHave(Condition.text("Операция одобрена Банком."), Duration.ofSeconds(15));
    }

    public void errorNotificationOutput() {                                                                                 // уведомление об ошибке
        notificationError.shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции"), Duration.ofSeconds(15));
    }

    public void errorMessageOutputInWrongFormat() {                                                                   // неверный формат заполнения полей
        wrongFormat.shouldHave(Condition.text("Неверный формат"));
    }

}

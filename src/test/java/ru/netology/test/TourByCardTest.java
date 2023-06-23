package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.PaymentPage;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.clearTables;

public class TourByCardTest {
    PaymentPage paymentPage = new PaymentPage();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
    }

   @AfterEach
    public void cleanTables() {
        clearTables();
    }

    @Test
    void checkCardApproved() {                                                               //Оплата картой Approved с валидными данными
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(getMonth());
        paymentPage.fillYearField(getYear());
        paymentPage.fillOwnerField(getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        paymentPage.successNotificationOutput();
        assertEquals(getStatusApprovedCard(), SQLHelper.getStatusByCard());
    }

    @Test
    void checkCardDeclined() {                                                                //Оплата картой Declined с валидными данными
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(declinedCardNumber);
        paymentPage.fillMonthField(getMonth());
        paymentPage.fillYearField(getYear());
        paymentPage.fillOwnerField(getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        paymentPage.errorNotificationOutput();
        assertEquals(getStatusDeclinedCard(), SQLHelper.getStatusByCard());
    }

    @Test
    void checkMissingCard() {                                                                  // Проверка поля карты с данными из несуществующей БД
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(getRandomCard());
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        paymentPage.errorNotificationOutput();
        assertNull(SQLHelper.getStatusByCard());
    }


    @Test
    void checkCardShortNumber() {                                                               // Проверка поля карты с заполнением меньше 16 цифр
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(shortCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        paymentPage.errorMessageOutputInWrongFormat();
        assertNull(SQLHelper.getStatusByCard());

    }

    @Test
    void checkCardNumberZero() {                                                   // Проверка поля карты ноли
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(zeroCard);
        paymentPage.fillMonthField(getMonth());
        paymentPage.fillYearField(getYear());
        paymentPage.fillOwnerField(getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        paymentPage.errorMessageOutputInWrongFormat();
        assertNull(SQLHelper.getStatusByCard());

    }

    @Test
    void checkCardNumberEmpty() {                                                                   // Проверка карты пустое поле
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(emptyCard);
        paymentPage.fillMonthField(getMonth());
        paymentPage.fillYearField(getYear());
        paymentPage.fillOwnerField(getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        paymentPage.errorMessageOutputInWrongFormat();
        assertNull(SQLHelper.getStatusByCard());

    }


    @Test
    void checkPreviousMonth() {                                                                           // предыдущий месяц
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(getInvalidMonth());
        paymentPage.fillYearField(getYear());
        paymentPage.fillOwnerField(getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        $(byText("Неверно указан срок действия карты")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void checkMonthOneNumber() {                                                                      // месяц одно число
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(invalidMonth);
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        $(byText("Неверный формат")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void checkMonthOutOfRange() {                                                                            // месяц выход из диапазона
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(falseMonth);
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        $(byText("Неверно указан срок действия карты")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void checkMonthZero() {                                                                                         // месяц нули
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(zeroMonth);
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        $(byText("Неверно указан срок действия карты")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void checkMonthEmpty() {                                                                               // месяц пусто
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(emptyMonth);
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        $(byText("Неверный формат")).shouldBe(visible, Duration.ofSeconds(15));
    }


    @Test
    void checkPreviousYear() {                                                                                // предыдущий год
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(getPreviousYear());
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        $(byText("Истёк срок действия карты")).shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    void checkTheMaximumRangeLimit() {                                                                     // год максимальная граница диапазона
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(getFrontierYear());
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        $(byText("Операция одобрена Банком.")).shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    void checkOutOfRange() {                                                                                    // год больше диапазона
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(invalidYear);
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        $(byText("Неверно указан срок действия карты")).shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    void checkYearOneNumber() {                                                                                                // год одно число
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(falseYear);
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        $(byText("Неверный формат")).shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    void checkYearZero() {                                                                                                  // год нули
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(zeroYear);
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        $(byText("Истёк срок действия карты")).shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    void checkYearNumber() {                                                                                     // год пусто
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(emptyYear);
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        $(byText("Неверный формат")).shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    void checkOwnerOnRu() {                                                                                  // Владелец на кириллице
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(getRandomValidOwnerRu());
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        paymentPage.errorMessageOutputInWrongFormat();
        assertNull(SQLHelper.getStatusByCard());
    }

    @Test
    void checkOwnerOneWord() {                                                                                   // Владелец одно слово
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(invalidOwnerOneWord);
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        paymentPage.errorMessageOutputInWrongFormat();
        assertNull(SQLHelper.getStatusByCard());
    }

    @Test
    void checkOwnerThreeWords() {                                                                                 // Владелец три слова
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(invalidOwnerThreeWord);
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        paymentPage.errorMessageOutputInWrongFormat();
        assertNull(SQLHelper.getStatusByCard());
    }

    @Test
    void checkOwnerSymbols() {                                                                                  // Владелец символы
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(charactersOwner);
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        paymentPage.errorMessageOutputInWrongFormat();
        assertNull(SQLHelper.getStatusByCard());
    }

    @Test
    void checkOwnerByNumbers() {                                                                              // Владелец цифры
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(figuresOwner);
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        paymentPage.errorMessageOutputInWrongFormat();
        assertNull(SQLHelper.getStatusByCard());
    }

    @Test
    void checkOwnerByZero() {                                                                                 // Владелец нули
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(zeroOwner);
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        paymentPage.errorMessageOutputInWrongFormat();
        assertNull(SQLHelper.getStatusByCard());
    }

    @Test
    void checkOwnerByEmpty() {                                                                             // Владелец пусто
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(emptyOwner);
        paymentPage.fillCvcField(getRandomValidCvc());
        paymentPage.clickContinueButton();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(15));
    }


    @Test
    void checkTwoNumbersCVCByCard() {                                                                     // CVC 2 числа
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(getTwoCVC());
        paymentPage.clickContinueButton();
        paymentPage.errorMessageOutputInWrongFormat();
        assertNull(SQLHelper.getStatusByCard());
    }

    @Test
    void checkOneNumbersCVCByCard() {                                                         // CVC 1 число
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(getOneCVC());
        paymentPage.clickContinueButton();
        paymentPage.errorMessageOutputInWrongFormat();
        assertNull(SQLHelper.getStatusByCard());
    }

    @Test
    void checkZeroNumbersCVCByCard() {                                                         // CVC ноль
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(zeroCVC);
        paymentPage.clickContinueButton();
        paymentPage.errorMessageOutputInWrongFormat();
        assertNull(SQLHelper.getStatusByCard());
    }

    @Test
    void checkEmptyNumbersCVCByCard() {                                                         // CVC пустое поле
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(approvedCardNumber);
        paymentPage.fillMonthField(DataHelper.getMonth());
        paymentPage.fillYearField(DataHelper.getYear());
        paymentPage.fillOwnerField(DataHelper.getRandomValidOwner());
        paymentPage.fillCvcField(emptyCvc);
        paymentPage.clickContinueButton();
        paymentPage.errorMessageOutputInWrongFormat();
        assertNull(SQLHelper.getStatusByCard());
    }

    @Test
    void checkFieldsFilledWithZeros() {                                                           // Проверка полей заполненные нулями
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(zeroCard);
        paymentPage.fillMonthField(zeroMonth);
        paymentPage.fillYearField(zeroYear);
        paymentPage.fillOwnerField(zeroOwner);
        paymentPage.fillCvcField(zeroCVC);
        paymentPage.clickContinueButton();
        paymentPage.errorMessageOutputInWrongFormat();
        assertNull(SQLHelper.getStatusByCard());
    }

    @Test
    void checkYearValidFieldsZeros() {                                                                 // Поля кроме года нули
        paymentPage.openPageBuyOnCredit();
        paymentPage.fillCardNumberField(zeroCard);
        paymentPage.fillMonthField(zeroMonth);
        paymentPage.fillYearField(getNextFromCurrentYear());
        paymentPage.fillOwnerField(zeroOwner);
        paymentPage.fillCvcField(zeroCVC);
        paymentPage.clickContinueButton();
        paymentPage.errorMessageOutputInWrongFormat();
        assertNull(SQLHelper.getStatusByCredit());
    }

    @Test
    void checkFieldsFilledWithEmpty() {                                                                  // Проверка пустых полей
        paymentPage.openPageBuyByCard();
        paymentPage.fillCardNumberField(emptyCard);
        paymentPage.fillMonthField(emptyMonth);
        paymentPage.fillYearField(emptyYear);
        paymentPage.fillOwnerField(emptyOwner);
        paymentPage.fillCvcField(emptyCvc);
        paymentPage.clickContinueButton();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(15));
    }

}
package ru.netology.cucumberSteps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.*;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferSteps {
    LoginPage loginPage;
    VerificationPage verifyPage;
    DashboardPage dashboard;

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void auth(String login, String password) {
        open("http://localhost:9999/");
        loginPage = new LoginPage();
        var verifyPage = loginPage.validLogin(login, password);
    }

    /*@Пусть("открыта страница с формой авторизации {string}")
    public void openAuthPage(String url) {
        loginPage = open(url, LoginPage.class);
    }


    @Когда("пользователь пытается авторизоваться с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        verifyPage = loginPage.validLogin(login, password);
    }*/

    @И("c проверочным кодом {string}")
    public void setValidCode(String verifyCode) {
        verifyPage = new VerificationPage();
        dashboard = verifyPage.verify(verifyCode);
    }

    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою {int} карту с главной страницы")
    public void successTransfer(String amount, String cardFrom, int indexCardTo) {
        var transferPage = dashboard.transferClick(indexCardTo - 1);
        transferPage.importTransferData(amount, cardFrom);
    }

    @Тогда("баланс его {string} карты из списка на главной странице должен стать {string} рублей")
    public void matchesBalance(String indexCard, String expectedBalance) {
        dashboard.reloadBalance();
        String[] balance = expectedBalance.split(" ");
        String sum = "";
        for (String tmp : balance) {
            sum = sum + tmp;
        }
        int actualBalance = dashboard.getBalance(Integer.valueOf(indexCard) - 1);
        assertEquals(Integer.valueOf(sum), actualBalance);
    }
}

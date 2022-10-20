package ru.netology.cucumberSteps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.*;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferSteps {
    LoginPage loginPage;
    VerificationPage verifyPage;
    DashboardPage dashboard;

    @Пусть("открыта страница с формой авторизации {string}")
    public void openAuthPage(String url) {
        loginPage = Selenide.open(url, LoginPage.class);
    }


    @Когда("пользователь пытается авторизоваться с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        verifyPage = loginPage.validLogin(login, password);
    }

    @И("c проверочным кодом {string}")
    public void setValidCode(String verifyCode) {
        dashboard = verifyPage.verify(verifyCode);
    }

    @Когда("Когда пользователь переводит {string} рублей с карты с номером {string} на свою {int} карту с главной страницы")
    public void successTransfer(String amount, String cardFrom, int indexCardTo) {
        var transferPage = dashboard.transferClick(indexCardTo - 1);
        transferPage.importTransferData(amount, cardFrom);
        //dashboard = transferPage.checkNotification(hidden);
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

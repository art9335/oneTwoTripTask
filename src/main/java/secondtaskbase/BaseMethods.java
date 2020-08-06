package secondtaskbase;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.Dimension;

import static com.codeborne.selenide.Selenide.open;

public class BaseMethods {

    @Step("Open page by url")
    public void openPageForTest(String urlPage) {

        open(urlPage);
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(1420, 1400));
    }
    @Step("Refresh the page")
    public void refreshPage() {
        WebDriverRunner.getWebDriver().navigate().refresh();
    }


    @Step("Get value of current url")
    public String getUrl() {
        return WebDriverRunner.url();
    }


    public boolean checkUrl(String paramForCompare, String referenceString) {
        boolean result = paramForCompare.regionMatches(0, referenceString, 0, referenceString.length());
        System.out.println("Результат сравнения подстрок - " + result);
        return result;
    }



}

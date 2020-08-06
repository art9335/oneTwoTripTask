package pretestpackage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import secondtaskbase.BaseMethods;

public class BasePretest  extends BaseMethods {
    @BeforeAll
    static void setUp(){
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)); // add screenshot as attachments
        Configuration.timeout = 10000;
        Configuration.holdBrowserOpen = true;

    }


}


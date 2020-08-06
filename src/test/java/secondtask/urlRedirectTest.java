package secondtask;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pretestpackage.BaseParameters;
import pretestpackage.BasePretest;

public class urlRedirectTest {

    BasePretest pageForTest = new BasePretest();
    BaseParameters.urlPages urlBase;

    String urlForCompare = "https://www.onetwotrip.com/";

    @Test
    public void forTestTask() {
        pageForTest.openPageForTest(urlBase.oneTwoTripUrl);
        String urlForCheck = pageForTest.getUrl();
        System.out.println(urlForCheck);
        boolean compareResult = pageForTest.checkUrl(urlForCheck, urlForCompare);
        Assert.assertTrue(compareResult);
        pageForTest.refreshPage();
        urlForCheck = pageForTest.getUrl();
        compareResult = pageForTest.checkUrl(urlForCheck, urlForCompare);
        Assert.assertTrue(compareResult);
    }

}

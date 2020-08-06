package pretestpackage;

import firsttaskbase.BaseApiMethods;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.ArrayList;

public class BaseApiPretest {
    protected BaseApiMethods forApiTest = new BaseApiMethods();
    protected BaseParameters.urlPages urlBase ;
    protected BaseParameters.parametersForApiTest apiParams;
    protected BaseParameters.testDataForApiTest testData;



    /*
     * возможнные проверки:
     * 1) Позитивная - ввод слова "Москва", проверка корректности тела ответа
     * 2) Позитивная - проверка что М */




    public void checkSearchResults(JSONObject responseBody, JSONArray responseSubArray, String searchVariant) {
        //метод для удобства
        String cityName;
        String firstPart;
        String translit;
        ArrayList<String> cityNames = new ArrayList<>();
        //System.out.println( " длина " + responseSubArray.length() + "subarray + " + responseSubArray );
        for (int j = 0; j < responseSubArray.length(); j ++) {
            JSONObject cityInfo = forApiTest.getJsonFromArrayByIndex(responseBody, testData.keyValueToGetCityJson, j);
            cityName = forApiTest.getValueByKey(cityInfo, testData.keyValueOfCityName);
            translit = forApiTest.getValueByKey(cityInfo, testData.translitKey);
            firstPart = forApiTest.getPartOfString(cityName, 0, searchVariant.length());

            Assert.assertEquals(firstPart, searchVariant);

            if (responseSubArray.length() > 2) {
                cityNames.add(translit);
            }

        }

        //System.out.println(cityNames + " длина  " + cityNames.size());
        if (cityNames.size() > 1) {
            for (int k = 0; k < cityNames.size() - 1; k++ ) {
                //System.out.println(k);
                Assert.assertEquals(false, cityNames.get(k).equals(cityNames.get(k + 1)));
            }

        }
    }

    public void statusCodeAssertion(JSONObject response, String key) {
        String statusCodeValue = forApiTest.getValueByKey(response, key);
        Assert.assertEquals(Integer.parseInt(statusCodeValue), 200);
    }

    public void keyParameterAssertion (JSONObject arg) {
        JSONArray keyValues = forApiTest.getKeySet(arg);
        Assert.assertEquals(Long.valueOf(keyValues.length()), Long.valueOf(testData.lenghtOfCitiInfo));
        JSONArray jsonCityInfoKeys = new JSONArray(testData.citiKeyList);
        boolean compareResult = forApiTest.comparingString(jsonCityInfoKeys.toString(), keyValues.toString());
        Assert.assertTrue(compareResult);
    }
}

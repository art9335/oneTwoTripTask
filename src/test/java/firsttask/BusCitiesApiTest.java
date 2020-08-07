package firsttask;

import firsttaskbase.BaseApiMethods;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pretestpackage.BaseApiPretest;
import pretestpackage.BaseParameters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class BusCitiesApiTest  extends BaseApiPretest {
    protected String forJquerry = apiParams.busRequest.concat(apiParams.addJQuerry);
    protected String urlForRequest;
    protected JSONObject responseResult = new JSONObject();
    protected JSONObject cityInfo = new JSONObject();
    protected JSONObject errorInfo = new JSONObject();
    protected JSONArray keyValues = new JSONArray();
    protected JSONArray cityArray = new JSONArray();
    protected JSONArray cityNames = new JSONArray();



    @DisplayName("Проверка на некорректный код символа")
    @Test
    public void incorrectSymbolCode() throws IOException, InterruptedException {

        String hexValue;
        for (int i = 1040; i <= 1103; i++ ) {
            hexValue = forApiTest.toHexWithPersentFormat(forApiTest.getSymbolByNumber(1040));
            hexValue = hexValue.replace("D", String.valueOf(i));
            urlForRequest = forApiTest.formatUrlForRequest(urlBase.redirectUrl, forJquerry, hexValue);
            responseResult = forApiTest.makeHttpResponse(urlForRequest);

            errorInfo = forApiTest.getJsonByKey(responseResult, testData.errorJsonKey);
            Assert.assertEquals(forApiTest.getKeySet(responseResult).toString(), testData.errorKeyArray.toString());
            Assert.assertEquals(forApiTest.getValueByKey(errorInfo, testData.errorCodeKey),
                    testData.errorCodeValue);
            Assert.assertEquals(forApiTest.getValueByKey(errorInfo, testData.errorMessageKey),
                    testData.errorMessageValue);


        }
    }


    @DisplayName("Проверка пустого ввода")
    @Test
    public void checkingEmptyInputData() throws IOException, InterruptedException {

        /*В данном тесте проверка отправки пустого запроса и корректности вывода данных в таком случае -
        * 1-й город ближайший, следующие - по популярности*/
        urlForRequest = forApiTest.formatUrlForRequest(urlBase.redirectUrl,
                forJquerry, "");
        responseResult = forApiTest.makeHttpResponse(urlForRequest);
        cityArray = forApiTest.getJsonArray(responseResult, testData.keyValueToGetCityJson);
        System.out.println(cityArray.getJSONObject(0));
        for (int i = 0; i < cityArray.length(); i++) {
            if (i == 0) {
                Assert.assertEquals(forApiTest.getValueByKey(cityArray.getJSONObject(0), testData.typeKey),
                        testData.typeValues[0]);
            } else {
                Assert.assertEquals(forApiTest.getValueByKey(cityArray.getJSONObject(i), testData.typeKey),
                testData.typeValues[1]);
            }

        }

    }

    @DisplayName("Проверка длинного ввода")
    @Test
    public void checkingBigInputData() throws IOException, InterruptedException {

        String hashResult = new String();
        System.out.println(hashResult);
        urlForRequest = forApiTest.formatUrlForRequest(urlBase.redirectUrl, forJquerry,
                forApiTest.fullStringConding(testData.bigCityName));
        responseResult = forApiTest.makeHttpResponse(urlForRequest);
        statusCodeAssertion(responseResult, testData.keyStatusCode);
        System.out.println(forApiTest.getKeySet(responseResult).toString() + "  " + testData.successKeyArray.toString() );
        Assert.assertEquals(forApiTest.getKeySet(responseResult).toString(), testData.successKeyArray.toString());
    }





    @DisplayName("Проверка на корректность приема чисел")
    @Test
    public void intSearch() throws IOException, InterruptedException {

        /*в данном тесте проводится проверка на корректность приема цифр*/
        for (int i = 0; i < 10; i++) {
            urlForRequest = forApiTest.formatUrlForRequest(urlBase.redirectUrl,
                    forJquerry, String.valueOf(i));
            responseResult = forApiTest.makeHttpResponse(urlForRequest);
            statusCodeAssertion(responseResult, testData.keyStatusCode);
            Assert.assertEquals(forApiTest.getKeySet(responseResult).toString(), testData.successKeyArray.toString());


        }
    }

    @DisplayName("Проверка на регистр")
    @Test
    public void registerChecking() throws IOException, InterruptedException {

        /*в данном тесте проводится проверка на то, что поиск не зависит от регистра*/
        String hexData;
        for (int i = 0; i < testData.registerNameArray.length; i ++) {
            //получаем массив с поисковым словом в верхнем и нижнем регистре, формируем запрос, проверяем
            hexData = forApiTest.fullStringConding(testData.registerNameArray[i]);
            urlForRequest = forApiTest.formatUrlForRequest(urlBase.redirectUrl, forJquerry, hexData);
            responseResult = forApiTest.makeHttpResponse(urlForRequest);
            statusCodeAssertion(responseResult, testData.keyStatusCode); //проверка на код ответа
            //cityInfo = forApiTest.getJsonFromArrayByIndex(responseResult, "data", 0);
            cityArray.put(forApiTest.getJsonFromArrayByIndex(responseResult, "data", 0));
            cityNames.put(forApiTest.getValueByKey(cityArray.getJSONObject(i), testData.keyValueOfCityName ));
        }

        for (int i = 0; i < cityArray.length() - 1; i++) {
            Assert.assertEquals(cityArray.get(i).toString(), cityArray.get(i + 1 ).toString());
            Assert.assertEquals(cityNames.get(i).toString(), cityNames.get(i +1).toString());
            //проверка, что на в запросе с верхним и нижним регистром вернулся одинаковый город
        }

    }

    @DisplayName("Тест на ошибку ввода - первые символы английские")
    @Test
    public void incorrectCity() throws IOException, InterruptedException {

        //в данном тесте первые три символа названия - в английской раскладке, проводится проверка на код ошибки
        String hexData = forApiTest.fullStringConding(testData.engSingleSearchTown);
        urlForRequest = forApiTest.formatUrlForRequest(urlBase.redirectUrl, forJquerry, hexData);
        responseResult = forApiTest.makeHttpResponse(urlForRequest);
        errorInfo = forApiTest.getJsonByKey(responseResult, testData.errorJsonKey);
        Assert.assertEquals(forApiTest.getKeySet(responseResult).toString(), testData.errorKeyArray.toString());
        Assert.assertEquals(forApiTest.getValueByKey(errorInfo, testData.errorCodeKey),
                testData.errorCodeValue);
        Assert.assertEquals(forApiTest.getValueByKey(errorInfo, testData.errorMessageKey),
                testData.errorMessageValue);
    }




    @DisplayName("Поиск по полному имени города")
    @Test
    public void singleTownSearch() throws IOException, InterruptedException {

        /*
        * в данном тесте проводится поиск по трем различным вариациям имени города
        * 1) из одного слова
        * 2) из нескольких через тире
        * 3) с цифрой в названии
        * Для результатов поиска производятся следующие проверки:
        *   1) код ответа должен быть двухсотым
        *   2) проверка количества ключей в инфомации по городу
        *   3) проверка корректности ключей
        *   4) проверка на то, что нашелся тот город, который искали*/
        String hexData;

        String cityName;
        String firstPartWithInt = "1-";
        String secondPartWithInt = "е Гнездилово";
        for (int i = 0; i < testData.singleTownNames.size(); i++) {
            hexData = forApiTest.fullStringConding(testData.singleTownNames.get(i));

            urlForRequest = forApiTest.formatUrlForRequest(urlBase.redirectUrl,
                    forJquerry, hexData);
            responseResult = forApiTest.makeHttpResponse(urlForRequest);
            cityInfo = forApiTest.getJsonFromArrayByIndex(responseResult, "data", 0);

            cityName = forApiTest.getValueByKey(cityInfo, testData.keyValueOfCityName);
            statusCodeAssertion(responseResult, testData.keyStatusCode);
            keyParameterAssertion(cityInfo);
            Assert.assertEquals(cityName, testData.singleTownNames.get(i)); //нашелся правильный город
        }
        hexData = firstPartWithInt.concat(forApiTest.fullStringConding(secondPartWithInt));
        urlForRequest = forApiTest.formatUrlForRequest(urlBase.redirectUrl,
                forJquerry, hexData);
        responseResult = forApiTest.makeHttpResponse(urlForRequest);
        cityInfo = forApiTest.getJsonFromArrayByIndex(responseResult, "data", 0);
        cityName = forApiTest.getValueByKey(cityInfo, testData.keyValueOfCityName);
        statusCodeAssertion(responseResult, testData.keyStatusCode);
        keyParameterAssertion(cityInfo);
        Assert.assertEquals(firstPartWithInt.concat(secondPartWithInt), cityName);

    }

    @DisplayName("Проверка на равенство первых вариантов")
    @Test
    public void firstVariantAreEquals() throws IOException, InterruptedException {
        /*в данном тесте происходит проверка на равенство того, что при клиентском вводе строки
        * "М", "Мо", "Мос", "Моск", "Москв", "Москва" первые приходящие возможные варианты  эквивалентны
        * Так же проводятся проверки, аналогичные проверка в тесте на полный ввод*/


        String[] searchParameter = testData.searchVariant;

        ArrayList<String> cityNames = new ArrayList<>();

        for (int i = 0; i < searchParameter.length; i++) {
            //System.out.println(searchParameter[i]);
            String hexData = forApiTest.fullStringConding(searchParameter[i]);
            System.out.println(hexData);
            String urlForRequest = forApiTest.formatUrlForRequest(urlBase.redirectUrl,
                    forJquerry, hexData);

            JSONObject responseResult = forApiTest.makeHttpResponse(urlForRequest);
            JSONArray someArray = forApiTest.getJsonArray(responseResult, testData.keyValueToGetCityJson);
            checkSearchResults(responseResult, someArray, searchParameter[i]);
            statusCodeAssertion(responseResult, testData.keyStatusCode);

            for (int j = 0; j < someArray.length(); j++) {
                keyParameterAssertion(someArray.getJSONObject(j));//проверка на корректность приходящих форматов json
            }
            //System.out.println("someArray.getJSONObject(0) " + someArray.getJSONObject(0));
            JSONObject cityInfo = someArray.getJSONObject(0);
            String cityName = cityInfo.getString(testData.keyValueOfCityName);
            cityNames.add(cityName);

        }

        for (int i = 0; i < cityNames.size() - 1 ; i++ ) {

            Assert.assertEquals(cityNames.get(i), cityNames.get(i + 1));

        }
    }




}

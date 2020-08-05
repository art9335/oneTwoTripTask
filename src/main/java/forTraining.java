import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class forTraining {
    public static void main(String[] args) throws IOException {
        for (int i = 1040; i <= 1103; i++ ) {

            String symbolForJQueery = getSymbolByNumber(i);
            System.out.println(symbolForJQueery);
            String hexValue = toHexWithPersentFormat(symbolForJQueery);
            System.out.println("Буква алфавита - " + symbolForJQueery + "; Eё код " + hexValue);
        }

        for (int i = 65; i <= 122; i++ ) {

            String symbolForJQueery = getSymbolByNumber(i);
            System.out.println(symbolForJQueery);
            String hexValue = toHexWithPersentFormat(symbolForJQueery);
            System.out.println("Буква алфавита - " + symbolForJQueery + "; Eё код " + hexValue);
        }
        String urlBody = "https://www.onetwotrip.com/_bus/geo/suggest?query=%s&limit=2";
        String someCharacter = getSymbolByNumber(1040);
        String someCher = toHexWithPersentFormat(someCharacter);
        String urlForResponse = String.format(urlBody, "%D0&*(?");
        System.out.println("URl с параметрами " + urlForResponse);
        JSONObject codeResult = makeHttpResponse(urlForResponse);

        System.out.println("Код " + codeResult);
        JSONObject errorsJson = (JSONObject) codeResult.get("error");
        System.out.println("errorsjson " + errorsJson );
        System.out.println(codeResult.get("error"));
        System.out.println(codeResult.getJSONArray("error"));
        System.out.println(codeResult.get("error"));
        JSONArray newArray = codeResult.getJSONArray("error");
        JSONObject cityInfo = newArray.getJSONObject(0);

        System.out.println("Инфа по городу " + cityInfo);

        System.out.println(codeResult.keySet());
        System.out.println(codeResult.has("error"));
        JSONArray keySet = cityInfo.names();

        for (int i = 0; i < cityInfo.length(); i++) {
            System.out.println(cityInfo.get((String) keySet.get(i)));


        }

    }

//    public static JSONObject getNestedJsonFromArray(JSONObject arg, String keyValue, int indexJson) {
//        JSONArray newArray = arg.getJSONArray(keyValue);
//        JSONObject nestedJson = newArray.getJSONObject(indexJson);
//        return nestedJson;
//
//    }
//
//    public static JSONObject getJsonByKey(JSONObject arg, String keyValue) {
//        JSONObject
//    }

    public static String toHexWithPersentFormat(String arg) throws UnsupportedEncodingException {
        String f = String.format("%04x", new BigInteger(1, arg.getBytes()));
        StringBuffer sb = new StringBuffer(f);
        sb.insert(0,"%");
        sb.insert(3,"%");
        System.out.println(sb);
        String finalResult = String.valueOf(sb);
        finalResult = finalResult.toUpperCase();
        return finalResult;
    }

    public static String getSymbolByNumber(int number) {
        char[] chars1 = Character.toChars(number);
        String resultForReturn = String.valueOf(chars1);
        return resultForReturn;
    }

    public static JSONObject makeHttpResponse(String url) throws IOException {
        String result = "";
        System.out.println("Url for response " + url);
        HttpGet get = new HttpGet(url);
        //System.out.println("Что отправляется в запросе " + jsonForPayload);
        //post.setEntity(new StringEntity(jsonForPayload.toString()));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(get);
        //System.out.println(response.getStatusLine().getStatusCode());
        String statusCode = String.valueOf(response.getStatusLine().getStatusCode());


        result = EntityUtils.toString(response.getEntity());
        JSONObject responseBody = new JSONObject(result/* строка result парсится в json*/);
        responseBody.put("Status code ", statusCode);
        //System.out.println("Тело ответа в строку " + responseBody);
        return responseBody;

    }


}
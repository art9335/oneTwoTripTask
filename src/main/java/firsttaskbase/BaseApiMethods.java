package firsttaskbase;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class BaseApiMethods {


    public String fullStringConding(String arg) throws UnsupportedEncodingException {
        String fullStringCode = "";
        for (int i = 0; i < arg.length();i++) {
            String character = String.valueOf(arg.charAt(i));
            if (character.equals("-")) {
               fullStringCode =  fullStringCode.concat(character);
            } else {
                String encodedCharacter = toHexWithPersentFormat(character);
                fullStringCode = fullStringCode.concat(encodedCharacter);
            }


        }
        return fullStringCode;
    }


    public void compareElementsOfArray(ArrayList<String> arg) {
        String[] searchVariant =  new String[] {"М", "М", "М", "М", "М", "М"};
        boolean result;
        for (int i = 0; i < searchVariant.length; i++) {
            System.out.println(searchVariant[i]);
            if (searchVariant[i].equals(searchVariant[i+1 ])) {
                System.out.println("Элементы массива равны");
                result = true;

            } else {
                System.out.println("Элементы массива не равны");
                result = false;
            }
        }
    }

    public String getPartOfString(String arg, int startIndex, int finishIndex) {
        return arg.substring(startIndex, finishIndex);
    }

    public boolean comparingString (String origin, String referer) {
        String strOrigin = origin.toString();
        String strReferer = referer.toString();
        boolean result;
        if (strOrigin.equals(strReferer)) {
            result = true;
            //System.out.println("Два массива равны");
        } else {
            result = false;
            //System.out.println("Два массива не равны");
        }
        return result;
    }

    public String formatUrlForRequest(String urlBody, String urlPart, String requestData) {
        String resultUrl = urlBody.concat(urlPart);
        resultUrl = String.format(resultUrl, requestData);
       // System.out.println(resultUrl);
        return resultUrl;
    }
    public JSONArray  getKeySet(JSONObject arg) {
        /*
        для получения значений ключей json
        */
        Set<String> arrayList = arg.keySet();
        JSONArray newArray = new JSONArray();
        for (String s : arrayList) {
            //System.out.println(s.toString());
            newArray.put(s);
        }
        return newArray;
    }

    public JSONObject getJsonFromArrayByIndex(JSONObject arg, String keyValue, int indexJson) {
        JSONArray nestedArray = getJsonArray(arg, keyValue);
        JSONObject nestedJson = getNestedJsonByIndex(nestedArray, indexJson);
        return nestedJson;
    }

    public String getValueByKey(JSONObject arg, String keyValue) {
        Object value = arg.get(keyValue);
        value = value.toString();
        return (String) value;
    }





    public JSONArray getJsonArray(JSONObject arg, String keyValue) {
        /*
        для получения массива
        */
        JSONArray nestedArray = arg.getJSONArray(keyValue);
        return nestedArray;
    }

    public JSONObject getNestedJsonByIndex(JSONArray arg, int indexJson) {
        /*
        для получения вложенных  json-тел в массив

        */
        JSONObject nestedJson = arg.getJSONObject(indexJson);
        return nestedJson;
    }

    public JSONObject getJsonByKey(JSONObject arg, String keyValue) {
        JSONObject nestedJson = arg.getJSONObject(keyValue);
        return nestedJson;
    }


    public String toHexWithPersentFormat(String arg) throws UnsupportedEncodingException {
        String f = String.format("%04x", new BigInteger(1, arg.getBytes()));
        StringBuffer sb = new StringBuffer(f);
        //System.out.println(sb.length());
        sb.insert(0, "%");
        sb.insert(3, "%");
        // System.out.println(sb);
        String finalResult = String.valueOf(sb);
        finalResult = finalResult.toUpperCase();
        if (finalResult.equals("%00%20")) {
            finalResult = finalResult.substring(3, finalResult.length());
        }
        return finalResult;
    }

    public String getSymbolByNumber(int number) {
        char[] chars1 = Character.toChars(number);
        String resultForReturn = String.valueOf(chars1);
        return resultForReturn;
    }

    public JSONObject makeHttpResponse(String url) throws IOException {
        String result = "";
       // System.out.println("Url for response " + url);
        HttpGet get = new HttpGet(url);
        //System.out.println("Что отправляется в запросе " + jsonForPayload);
        //post.setEntity(new StringEntity(jsonForPayload.toString()));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(get);
        //System.out.println(response.getStatusLine().getStatusCode());
        String statusCode = String.valueOf(response.getStatusLine().getStatusCode());


        result = EntityUtils.toString(response.getEntity());
        JSONObject responseBody = new JSONObject(result/* строка result парсится в json*/);
        responseBody.put("Status code", statusCode);
        //System.out.println("Тело ответа в строку " + responseBody);
        return responseBody;

    }


}

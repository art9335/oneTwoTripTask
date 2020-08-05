package firsttaskbase;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class BaseMethods {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String someResult = fullStringConding("Ростов-На-Дону");
        System.out.println("Значение до кодировки - Мо" + " Значение после - " + someResult );
    }

    public static String fullStringConding(String arg) throws UnsupportedEncodingException {
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

    public static String toHexWithPersentFormat(String arg) throws UnsupportedEncodingException {
        String f = String.format("%04x", new BigInteger(1, arg.getBytes()));
        StringBuffer sb = new StringBuffer(f);
        sb.insert(0, "%");
        sb.insert(3, "%");
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

    public static String makeHttpResponse(String url, String parameter) throws IOException {
        String result = "";
        System.out.println("Url for response " + url);
        HttpGet get = new HttpGet(url);
        //System.out.println("Что отправляется в запросе " + jsonForPayload);
        //post.setEntity(new StringEntity(jsonForPayload.toString()));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(get);
        //System.out.println(response.getStatusLine().getStatusCode());
        String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
        System.out.println(statusCode);

        result = EntityUtils.toString(response.getEntity());
        JSONObject responseBody = new JSONObject(result/* строка result парсится в json*/);

        System.out.println("Результат запроса " + result);
        if (parameter.equals("status")) {
            return statusCode;
        } else {
            return result;
        }
    }
}

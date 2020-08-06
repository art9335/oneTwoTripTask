package pretestpackage;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseParameters {

    public interface urlPages {
        String oneTwoTripUrl = "https://onetwotrip.com";
        String redirectUrl = "https://www.onetwotrip.com";

    }

    public interface parametersForApiTest {
        String busRequest = "/_bus/geo/suggest";
        String addJQuerry = "?query=%s&limit=10";
    }

    public interface testDataForApiTest {
        String keyStatusCode = "Status code";
        String translitKey = "trnslt";
        String singleSearchTown = "Москва";
        String keyValueOfCityName = "name";
        String engSingleSearchTown = "Mocква";
        String townWithDash = "Ростов-На-Дону";
        String keyValueToGetCityJson = "data";
        String bigCityName = "Крун Тхеп Маханакхон Амон Раттанакосин Махинтараюттхая Махадилок Пхоп Ноппарат Ратчатхани Буриром Удомратчанивет Махасатан Амон Пиман Аватан Сатит Саккатхаттийя Витсанукам Прасит";
        String[]  registerNameArray = new String[] {singleSearchTown.toUpperCase(), singleSearchTown.toLowerCase()};
        String typeKey = "type";
        String[] typeValues = new String[]{"nearest", "top"};


        //Связанные с кодами ошибок
        String errorJsonKey = "error";
        String errorCodeKey = "code";
        String errorCodeValue = "801";
        String errorMessageKey = "message";
        String errorMessageValue = "Ошибка сервиса";

        String[] successKeyOrigin = new String[] {"Status code", "data"};
        JSONArray successKeyArray = new JSONArray(successKeyOrigin);
        String[] successKeyError = new String[] {"Status code", "error"};
        JSONArray errorKeyArray = new JSONArray(successKeyError);
        String[] searchVariant =  new String[] {"М", "Мо", "Мос", "Моск", "Москв", "Москва"};
        List<String> citiKeyList = Arrays.asList("country","district","geopointId","trnslt","name","rating","region","type");
        Integer lenghtOfCitiInfo = 8;
        List<String> singleTownNames = Arrays.asList("Москва","Ростов-на-Дону");




    }

}

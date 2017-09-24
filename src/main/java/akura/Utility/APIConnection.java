package akura.Utility;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.cloud.language.v1beta2.LanguageServiceClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.NoSuchElementException;

/**
 * Class representing API Connection methods.
 */
public class APIConnection {

    /**
     * MS graph connection
     * @param text - short text.
     * @param defaultValue - default value.
     * @return
     */
    public static String understandShortWordConcept(String text, String defaultValue) {
        String url = "http://concept.research.microsoft.com/api/Concept/ScoreByProb?instance=".concat(text).concat("&topK=1").replaceAll(" ","%20");
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(url);
        try {
            HttpResponse httpResponse = client.execute(getRequest);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream inputStream = httpEntity.getContent();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(EntityUtils.toString(httpEntity, "UTF-8"));
            return jsonObject.keySet().iterator().next().toString();
        } catch (IOException e) {
            System.out.println(e);
            return defaultValue;
        } catch (ParseException e) {
            System.out.println(e);
            return defaultValue;
        } catch (NoSuchElementException e){
            System.out.println("Element not found!");
            return defaultValue;
        }
    }

    /**
     * Authenticate Google API
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    private static GoogleCredential authorize() throws IOException, GeneralSecurityException {
        GoogleCredential credential = GoogleCredential.getApplicationDefault();
        return credential;
    }

    /**
     * Provide Authentication with client.
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static LanguageServiceClient provideLanguageServiceClient() throws IOException, GeneralSecurityException {
        authorize();
        return LanguageServiceClient.create();
    }
}

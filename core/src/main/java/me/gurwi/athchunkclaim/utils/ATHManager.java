package me.gurwi.athchunkclaim.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.gurwi.athchunkclaim.ATHChunkClaim;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class ATHManager {
    private final URI host;
    private final String licenseKey;
    private final String product;
    private final String version;
    private final String apiKey;
    private final String hwid;

    private int statusCode;
    private String discordName;
    private String discordID;
    private String statusMsg;

    public boolean check() {
        HttpPost post = new HttpPost(host);

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("licensekey", licenseKey));
        urlParameters.add(new BasicNameValuePair("product", product));
        urlParameters.add(new BasicNameValuePair("version", version));
        urlParameters.add(new BasicNameValuePair("hwid", hwid));

        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        post.setHeader("Authorization", apiKey);

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post)) {
            String data = EntityUtils.toString(response.getEntity());
            JSONObject obj = new JSONObject(data);

            if(!obj.has("status_msg") || !obj.has("status_id")) {
                return false;
            }

            statusCode = obj.getInt("status_code");
            statusMsg = obj.getString("status_msg");

            if(obj.getString("status_overview") == null){
                return false;
            }

            discordName = obj.getString("clientname"); // You can set discord_username too!
            discordID = obj.getString("discord_id");

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getHWID() {
        try {
            String toEncrypt =  System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toEncrypt.getBytes());
            StringBuilder hexString = new StringBuilder();

            byte[] byteData = md.digest();

            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

}

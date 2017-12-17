package com.ruslanabzalov.pocketdoc.docs;

import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, для получение списка врачей посредством API.
 */
public class DocsFetch {

    private static final String TAG = "DocsFetch";

    /**
     * Метод, получающий низкоуровневые данные по URL.
     * @param urlSpec
     * @return
     * @throws IOException
     */
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        // Создание объекта подключения к URL-адресу
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String basicAuth = "Basic " + new String(Base64.encode("partner.13849:BIQWlAdw".getBytes(), Base64.NO_WRAP ));
        connection.setRequestProperty ("Authorization", basicAuth);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // Get the response code
            int statusCode = connection.getResponseCode();
            InputStream in;
            if (statusCode >= 200 && statusCode < 400) {
                in = connection.getInputStream();
            }
            else {
                in = connection.getErrorStream();
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    /**
     * Метод, преобразующий массив URL-байт в строку.
     * @param urlSpec
     * @return
     * @throws IOException
     */
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    /**
     * Метод, формирующий URL-запрос и загружающий его содержимое.
     */
    public List<Doc> fetchItems() {
        List<Doc> docs = new ArrayList<>();
        try {
            // Тестовое получение всех врачей в Москве
            String jsonString =
                    getUrlString("https://partner.13849:BIQWlAdw@back.docdoc.ru" +
                            "/api/rest/1.0.6/json/doctor/list/start/0/count/345/city/1/" +
                            "speciality/87/stations/168/near/mixed");
            Log.i(TAG, "JSON получен: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(docs, jsonBody);
        } catch (IOException ex) {
            Log.e(TAG, "Ошибка при получении данных: ", ex);
        } catch (JSONException ex) {
            Log.e(TAG, "Ошибка парсинга JSON: " + ex);
        }
        return docs;
    }

    /**
     * Метод для парсинга JSON-данных.
     * @param items
     * @param jsonBody
     * @throws IOException
     * @throws JSONException
     */
    private void parseItems(List<Doc> items, JSONObject jsonBody) throws IOException,
            JSONException {
        JSONArray docsJsonArray = jsonBody.getJSONArray("DoctorList");
        for (int counter = 0; counter < docsJsonArray.length(); counter++) {
            JSONObject docJsonObject = docsJsonArray.getJSONObject(counter);
            Doc doc = new Doc();
            doc.setId(docJsonObject.getString("Id"));
            doc.setName(docJsonObject.getString("Name"));
            doc.setDescription(docJsonObject.getString("Description"));
            items.add(doc);
        }
    }
}

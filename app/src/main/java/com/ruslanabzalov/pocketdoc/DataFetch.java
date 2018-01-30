package com.ruslanabzalov.pocketdoc;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.ruslanabzalov.pocketdoc.docs.Doc;
import com.ruslanabzalov.pocketdoc.map.Hospital;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, для получение списка врачей посредством API.
 */
public class DataFetch {

    // Логин и пароль для получения данных посредством API.
    private static final String LOGIN = "partner.13849";
    private static final String PASSWORD = "BIQWlAdw";

    private static final String TAG = "DataFetch";

    /**
     * Метод, получающий низкоуровневые данные по URL.
     * @param urlSpec
     * @return
     * @throws IOException
     */
    private byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        // Создание объекта подключения к URL-адресу по протоколу HTTP.
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Авторизация.
        String basicAuth ="Basic " +
                new String(Base64.encode((LOGIN + ":" + PASSWORD).getBytes(), Base64.NO_WRAP ));
        connection.setRequestProperty ("Authorization", basicAuth);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int statusCode = connection.getResponseCode();
            InputStream in;
            if (statusCode >= 200 && statusCode < 400) {
                in = connection.getInputStream();
            } else {
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
    private String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<String> fetchDocsTypes() {
        List<String> typesList = new ArrayList<>();
        try {
            String url = Uri
                    .parse("https://" + LOGIN + ":" + PASSWORD + "@back.docdoc.ru")
                    .buildUpon()
                    .appendPath("api")
                    .appendPath("rest")
                    .appendPath("1.0.6")
                    .appendPath("json")
                    .appendPath("speciality")
                    .appendPath("city")
                    .appendPath("1")
                    .build()
                    .toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "JSON получен: " + jsonString);
            JSONObject json = new JSONObject(jsonString);
            parseDocsTypesToList(typesList, json);
        } catch (IOException ex) {
            Log.e(TAG, "Ошибка при получении данных: ", ex);
        } catch (JSONException ex) {
            Log.e(TAG, "Ошибка парсинга JSON: " + ex);
        }
        return typesList;
    }

    public Map<String, String> fetchDocsTypesAndIds() {
        Map<String, String> docsTypes = new HashMap<>();
        try {
            String url = Uri
                    .parse("https://" + LOGIN + ":" + PASSWORD + "@back.docdoc.ru")
                    .buildUpon()
                    .appendPath("api")
                    .appendPath("rest")
                    .appendPath("1.0.6")
                    .appendPath("json")
                    .appendPath("speciality")
                    .appendPath("city")
                    .appendPath("1")
                    .build()
                    .toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "JSON получен: " + jsonString);
            JSONObject json = new JSONObject(jsonString);
            parseDocsTypesToMap(docsTypes, json);
        } catch (IOException ex) {
            Log.e(TAG, "Ошибка при получении данных: ", ex);
        } catch (JSONException ex) {
            Log.e(TAG, "Ошибка парсинга JSON: " + ex);
        }
        return docsTypes;
    }

    /**
     * Метод для парсинга JSON-данных, связанных со специализациями врачей.
     * @param docsTypes
     * @param json
     * @throws IOException
     * @throws JSONException
     */
    private void parseDocsTypesToMap(Map<String, String> docsTypes, JSONObject json)
            throws IOException, JSONException {
        JSONArray docsTypesJsonArray = json.getJSONArray("SpecList");
        for (int counter = 0; counter < docsTypesJsonArray.length(); counter++) {
            JSONObject docsTypeJsonObject = docsTypesJsonArray.getJSONObject(counter);
            String docsTypeName = docsTypeJsonObject.getString("Name");
            String docsTypeId = docsTypeJsonObject.getString("Id");
            docsTypes.put(docsTypeName, docsTypeId);
        }
    }

    private void parseDocsTypesToList(List<String> typesList, JSONObject json)
            throws IOException, JSONException {
        JSONArray docsTypesJsonArray = json.getJSONArray("SpecList");
        for (int counter = 0; counter < docsTypesJsonArray.length(); counter++) {
            JSONObject docsTypeJsonObject = docsTypesJsonArray.getJSONObject(counter);
            String docsType = docsTypeJsonObject.getString("Name");
            typesList.add(docsType);
        }
    }

    public List<String> fetchDocsMetros() {
        List<String> metrosList = new ArrayList<>();
        try {
            String url = Uri
                    .parse("https://" + LOGIN + ":" + PASSWORD + "@back.docdoc.ru")
                    .buildUpon()
                    .appendPath("api")
                    .appendPath("rest")
                    .appendPath("1.0.6")
                    .appendPath("json")
                    .appendPath("metro")
                    .appendPath("city")
                    .appendPath("1")
                    .build()
                    .toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "JSON получен: " + jsonString);
            JSONObject json = new JSONObject(jsonString);
            parseDocsMetrosToList(metrosList, json);
        } catch (IOException ex) {
            Log.e(TAG, "Ошибка при получении данных: ", ex);
        } catch (JSONException ex) {
            Log.e(TAG, "Ошибка парсинга JSON: " + ex);
        }
        return metrosList;
    }

    public Map<String, String> fetchDocsMetrosAndIds() {
        Map<String, String> docsMetros = new HashMap<>();
        try {
            String url = Uri
                    .parse("https://" + LOGIN + ":" + PASSWORD + "@back.docdoc.ru")
                    .buildUpon()
                    .appendPath("api")
                    .appendPath("rest")
                    .appendPath("1.0.6")
                    .appendPath("json")
                    .appendPath("metro")
                    .appendPath("city")
                    .appendPath("1")
                    .build()
                    .toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "JSON получен: " + jsonString);
            JSONObject json = new JSONObject(jsonString);
            parseDocsMetrosToMap(docsMetros, json);
        } catch (IOException ex) {
            Log.e(TAG, "Ошибка при получении данных: ", ex);
        } catch (JSONException ex) {
            Log.e(TAG, "Ошибка парсинга JSON: " + ex);
        }
        return docsMetros;
    }

    private void parseDocsMetrosToMap(Map<String, String> docsMetros, JSONObject json)
            throws IOException, JSONException {
        JSONArray docsMetrosJsonArray = json.getJSONArray("MetroList");
        for (int counter = 0; counter < docsMetrosJsonArray.length(); counter++) {
            JSONObject docsMetroJsonObject = docsMetrosJsonArray.getJSONObject(counter);
            String docsMetroName = docsMetroJsonObject.getString("Name");
            String docsMetroId = docsMetroJsonObject.getString("Id");
            docsMetros.put(docsMetroName, docsMetroId);
        }
    }

    private void parseDocsMetrosToList(List<String> metrosList, JSONObject json)
            throws IOException, JSONException {
        JSONArray docsMetrosJsonArray = json.getJSONArray("MetroList");
        for (int counter = 0; counter < docsMetrosJsonArray.length(); counter++) {
            JSONObject docsMetroJsonObject = docsMetrosJsonArray.getJSONObject(counter);
            String docsMetroName = docsMetroJsonObject.getString("Name");
            metrosList.add(docsMetroName);
        }
    }

    /**
     * Метод, формирующий URL-запрос и загружающий его содержимое.
     */
    public List<Doc> fetchDocs(String docsTypeId, String docsMetroId) {
        List<Doc> docs = new ArrayList<>();
        try {
            String url = Uri
                    .parse("https://" + LOGIN + ":" + PASSWORD + "@back.docdoc.ru")
                    .buildUpon()
                    .appendPath("api")
                    .appendPath("rest")
                    .appendPath("1.0.6")
                    .appendPath("json")
                    .appendPath("doctor")
                    .appendPath("list")
                    .appendPath("start")
                    .appendPath("0")
                    .appendPath("count")
                    .appendPath("500")
                    .appendPath("city")
                    .appendPath("1")
                    .appendPath("speciality")
                    .appendPath(docsTypeId)
                    .appendPath("stations")
                    .appendPath(docsMetroId)
                    .appendPath("near")
                    .appendPath("strict")
                    .appendPath("order")
                    .appendPath("-rating")
                    .appendPath("deti")
                    .appendPath("0")
                    .appendPath("na-dom")
                    .appendPath("0")
                    .appendPath("withSlots")
                    .appendPath("1")
                    .appendPath("slotsDays")
                    .appendPath("14")
                    .build()
                    .toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "JSON получен: " + jsonString);
            JSONObject json = new JSONObject(jsonString);
            parseDocs(docs, json, docsTypeId, docsMetroId);
        } catch (IOException ex) {
            Log.e(TAG, "Ошибка при получении данных: ", ex);
        } catch (JSONException ex) {
            Log.e(TAG, "Ошибка парсинга JSON: " + ex);
        }
        return docs;
    }

    /**
     * Метод для парсинга JSON-данных, связанных с информацией о врачах.
     * @param docs
     * @param json
     * @throws IOException
     * @throws JSONException
     */
    private void parseDocs(List<Doc> docs, JSONObject json, String docTypeId, String docsMetroId)
            throws IOException, JSONException {
        JSONArray docsJsonArray = json.getJSONArray("DoctorList");
        for (int i = 0; i < docsJsonArray.length(); i++) {
            JSONObject docJsonObject = docsJsonArray.getJSONObject(i);
            // TODO: Проверить на отсутствие расписания у врача.
//            if (docJsonObject.getString("Slots") == null) {
//                continue;
//            }
            Doc doc = new Doc();
            doc.setId(docJsonObject.getString("Id"));
            doc.setName(docJsonObject.getString("Name"));
            doc.setType(docTypeId);
            doc.setDescription(docJsonObject.getString("Description"));
            doc.setPrice(docJsonObject.getString("Price"));
            doc.setExperience(docJsonObject.getString("ExperienceYear"));
            doc.setRating(docJsonObject.getString("Rating"));
            JSONArray docClinicsArray = docJsonObject.getJSONArray("ClinicsInfo");
            for (int j = 0; j < docClinicsArray.length(); j++) {
                JSONObject docClinic = docClinicsArray.getJSONObject(j);
                doc.setDocsClinicId(docClinic.getString("ClinicId"));
                JSONArray docStationsArray = docClinic.getJSONArray("Stations");
                for (int k = 0; k < docStationsArray.length(); k++) {
                    if (docsMetroId.equals(docStationsArray.getString(k))) {
                        doc.setAddress(getClinicData(doc.getDocsClinicId()));
                    }
                }
            }
            docs.add(doc);
        }
    }

    /**
     * Метод для нахождения адреса клиники.
     * @param clinicId идентификатор клиники.
     * @return адрес клиники.
     */
    private String getClinicData(String clinicId) {//, String dataType) {
        String data = "Пусто!";
        try {
            String url = Uri
                    .parse("https://" + LOGIN + ":" + PASSWORD + "@back.docdoc.ru")
                    .buildUpon()
                    .appendPath("api")
                    .appendPath("rest")
                    .appendPath("1.0.6")
                    .appendPath("json")
                    .appendPath("clinic")
                    .appendPath(clinicId)
                    .build()
                    .toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "JSON получен: " + jsonString);
            JSONObject json = new JSONObject(jsonString);
            JSONArray clinicsJsonArray = json.getJSONArray("Clinic");
            String name ="", street = "", house = "", description = "";
            for (int i = 0; i < clinicsJsonArray.length(); i++) {
                JSONObject clinicJsonObject = clinicsJsonArray.getJSONObject(i);
                street = clinicJsonObject.getString("Street");
                house = clinicJsonObject.getString("House");
            }
            data = String.format("%s, %s", street, house);
        } catch (IOException ex) {
            Log.e(TAG, "Ошибка при получении данных: ", ex);
        } catch (JSONException ex) {
            Log.e(TAG, "Ошибка парсинга JSON: " + ex);
        }
        return data;
    }

    public List<Hospital> fetchHospitals() {
        List<Hospital> hospitals = new ArrayList<>();
        try {
            String url = Uri
                    .parse("https://" + LOGIN + ":" + PASSWORD + "@back.docdoc.ru")
                    .buildUpon()
                    .appendPath("api")
                    .appendPath("rest")
                    .appendPath("1.0.6")
                    .appendPath("json")
                    .appendPath("clinic")
                    .appendPath("list")
                    .appendPath("start")
                    .appendPath("0")
                    .appendPath("count")
                    .appendPath("200")
                    .appendPath("city")
                    .appendPath("1")
                    .appendPath("type")
                    .appendPath("1")
                    .build()
                    .toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "JSON получен: " + jsonString);
            JSONObject json = new JSONObject(jsonString);
            parseHospitals(hospitals, json);
        } catch (IOException ex) {
            Log.e(TAG, "Ошибка при получении данных: ", ex);
        } catch (JSONException ex) {
            Log.e(TAG, "Ошибка парсинга JSON: " + ex);
        }
        return hospitals;
    }

    private void parseHospitals(List<Hospital> hospitals, JSONObject json) throws IOException,
            JSONException {
        JSONArray hospitalsJsonArray = json.getJSONArray("ClinicList");
        for (int i = 0; i < hospitalsJsonArray.length(); i++) {
            JSONObject hospitalJsonObject = hospitalsJsonArray.getJSONObject(i);
            Hospital hospital = new Hospital();
            hospital.setId(hospitalJsonObject.getString("Id"));
            hospital.setName(hospitalJsonObject.getString("Name"));
            hospital.setDescription(hospitalJsonObject.getString("Description"));
            hospital.setAddress(hospitalJsonObject.getString("Street") + "," +
                    hospitalJsonObject.getString("House"));
            hospital.setPhone(hospitalJsonObject.getString("PhoneAppointment"));
            hospital.setUrl(hospitalJsonObject.getString("URL"));
            hospital.setLongitude(hospitalJsonObject.getString("Longitude"));
            hospital.setLatitude(hospitalJsonObject.getString("Latitude"));
            hospitals.add(hospital);
        }
    }

    /**
     * Метод для создания заявки.
     * @param userName имя клиента.
     * @param phoneNumber номер телефона клиента.
     * @param date желаемая дата посещения.
     * @param docId идентификатор доктора.
     * @param clinicId идентификатор клиники.
     */
    public static void docPostRequest(String userName, String phoneNumber, String date, String docId,
                                      String clinicId) {
        Thread thread = new Thread(() -> {
            try {
                String uri = Uri
                        .parse("https://" + LOGIN + ":" + PASSWORD + "@back.docdoc.ru")
                        .buildUpon()
                        .appendPath("api")
                        .appendPath("rest")
                        .appendPath("1.0.6")
                        .appendPath("json")
                        .appendPath("request")
                        .build()
                        .toString();
                URL url = new URL(uri);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String basicAuth ="Basic " +
                        new String(Base64.encode((LOGIN + ":" + PASSWORD).getBytes(),
                                Base64.NO_WRAP));
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept","application/json");
                connection.setRequestProperty ("Authorization", basicAuth);
                connection.setDoOutput(true);
                connection.setDoInput(true);
                JSONObject json = new JSONObject();
                json.put("name", "test");
                json.put("phone", phoneNumber + "");
                json.put("doctor", docId);
                json.put("clinic", clinicId);
                json.put("dateAdmission", date);
                json.put("validate", 0);
                Log.i("JSON", json.toString());
                DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                os.writeBytes(json.toString());
                os.flush();
                os.close();
                Log.i("STATUS", String.valueOf(connection.getResponseCode()));
                Log.i("MSG" , connection.getResponseMessage());
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}

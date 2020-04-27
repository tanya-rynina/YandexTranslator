package com.example.yandextranslater;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.LongAccumulator;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LangsTask extends AsyncTask<Void, Void, LangsData> {

    private LoadLangCallBack loadLangCallBack;


    public LangsTask(LoadLangCallBack loadLangCallBack) {
        this.loadLangCallBack = loadLangCallBack;
    }

    @Override
    protected LangsData doInBackground(Void... voids) {

        LangsData langsData = new LangsData();


        try{
            Log.i("T", "Loading langs...");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(YandexAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            YandexAPI yandexAPI = retrofit.create(YandexAPI.class);

            Call<Langs> resultCall = yandexAPI.getLangs("ru", YandexAPI.API_KEY);
            Response<Langs> resultResponse = resultCall.execute();

            if (resultResponse.code() != 200) {
                throw new RuntimeException("Ошибка перевода. Код: " + resultResponse.code());

            }


            Log.i("T", "Langs loaded");

            Langs langs = resultResponse.body();

            langsData.setDirs(langs.getDirs());
            langsData.setLangsMap(langs.getLangs());

            Map<String, String > reverseMap = new TreeMap<>();

            for (Map.Entry<String, String> entry :langs.getLangs().entrySet()){
                reverseMap.put(entry.getValue(), entry.getKey());
            }
            langsData.setReverseMap(reverseMap);

        } catch (Exception e){
            Log.e("T", "Cannot load lags", e);
            langsData.setException(e);

        }
        return langsData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadLangCallBack.preLoadLang();
    }

    @Override
    protected void onPostExecute(LangsData langsData) {
        super.onPostExecute(langsData);
        loadLangCallBack.postLoadLang(langsData);
    }
}

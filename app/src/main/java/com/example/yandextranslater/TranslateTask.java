package com.example.yandextranslater;

import android.os.AsyncTask;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TranslateTask extends AsyncTask<Params, Void, Result> {

    private TransCallBack transCallBack;


    public TranslateTask(TransCallBack transCallBack) {
        this.transCallBack = transCallBack;
    }

    @Override
    protected Result doInBackground(Params... params) {

        Result result = new Result();

        try {

            Log.i("T", "Translating...");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(YandexAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            YandexAPI yandexAPI = retrofit.create(YandexAPI.class);

            Call<Result> resultCall = yandexAPI.translate(params[0].getText(),
                    params[0].getSrcLang() + "-" + params[0].getTransLang(), YandexAPI.API_KEY);
            Response<Result> resultResponse = resultCall.execute();

            if (resultResponse.code() != 200) {
                throw new RuntimeException("Ошибка перевода. Код: " + resultResponse.code());

            }

            Log.i("T", "Translated");
            result = resultResponse.body();

        } catch (Exception e) {
            Log.e("T", "Cannot translate", e);
            result.setException(e);
        }

        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        transCallBack.pretranslate();
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        transCallBack.posttranslate(result);
    }
}

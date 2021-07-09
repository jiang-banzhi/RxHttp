package com.banzhi.rxhttpsample;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 * @author : jiang
 * @time : 2021/7/9.
 * @desciption :
 * @version :
 * </pre>
 */

public class CoverterFactory extends Converter.Factory {
    GsonConverterFactory factory;

    public static CoverterFactory create() {
        return create(new Gson());
    }


    public static CoverterFactory create(Gson gson) {
        return new CoverterFactory(gson);
    }

    private final Gson gson;

    private CoverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        this.gson = gson;
        factory = GsonConverterFactory.create(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new ResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return factory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}

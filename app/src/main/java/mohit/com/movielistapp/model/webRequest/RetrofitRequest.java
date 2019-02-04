package mohit.com.movielistapp.model.webRequest;

import mohit.com.movielistapp.common.Constants;
import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RetrofitRequest {

    public static RetrofitGet retrofitGetRequest() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitGet.class);
    }

    private static OkHttpClient getOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLSv1");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create a ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.sslSocketFactory(sslSocketFactory);
            httpClient.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request request = chain.request();
                    request = request.newBuilder()
                            .method(request.method(), request.body())
                            .build();
                    return chain.proceed(request);
                }
            });
            httpClient.connectTimeout(1, TimeUnit.MINUTES);
            httpClient.readTimeout(1, TimeUnit.MINUTES);
            return httpClient.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

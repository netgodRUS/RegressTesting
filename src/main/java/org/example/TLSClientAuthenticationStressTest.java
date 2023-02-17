package org.example;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;

public class TLSClientAuthenticationStressTest {
    public static void main(String[] args) throws Exception {
        int numThreads = 10;
        int numRequestsPerThread = 100;
        String url = "https://91.244.183.36:30012/";

        KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
        FileInputStream clientKeyStoreFile = new FileInputStream("client.p12");
        clientKeyStore.load(clientKeyStoreFile, "password".toCharArray());

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(clientKeyStore, "password".toCharArray());

        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .build();

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < numRequestsPerThread; j++) {
                    try {
                        HttpGet httpGet = new HttpGet(url);
                        HttpResponse response = httpClient.execute(httpGet);
                        int statusCode = response.getStatusLine().getStatusCode();
                        System.out.println("Thread " + Thread.currentThread().getId() +
                                " request " + j + " response status code: " + statusCode);
                    } catch (Exception e) {
                        System.out.println("Thread " + Thread.currentThread().getId() +
                                " request " + j + " failed: " + e.getMessage());
                    }
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }

        httpClient.close();
    }
}



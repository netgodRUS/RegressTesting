package org.example;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WebPageTest {
    private static final String BASE_URL = "https://91.244.183.36:30012/";
    private static final int MAX_THREADS = 1000;
    private static final int RAMP_UP_TIME = 10;

    private static final String RESULTS_FILE = "results.txt";

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(BASE_URL);
        httpGet.addHeader("User-Agent", "Mozilla/5.0");

        try (FileWriter writer = new FileWriter(RESULTS_FILE)) {
            writer.write("Thread Count,Response Time,Status Code\n");

            for (int i = 1; i <= MAX_THREADS; i++) {
                int threadCount = i;

                Thread.sleep(TimeUnit.SECONDS.toMillis(RAMP_UP_TIME) / MAX_THREADS);
                long startTime = System.currentTimeMillis();

                for (int j = 0; j < threadCount; j++) {
                    httpClient.execute(httpGet);
                }

                long responseTime = System.currentTimeMillis() - startTime;
                int statusCode = httpClient.execute(httpGet).getStatusLine().getStatusCode();

                writer.write(threadCount + "," + responseTime + "," + statusCode + "\n");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}



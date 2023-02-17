package org.example;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

public class StressTest {

    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://91.244.183.36:30012/"))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0")
                .build();

        int numRequests = 100;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numRequests; i++) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Request " + i + " status code: " + response.statusCode());
            TimeUnit.MILLISECONDS.sleep(10);
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Total time: " + (endTime - startTime) + " ms");
        System.out.println("Requests per second: " + ((double) numRequests / (double) (endTime - startTime)) * 1000);
    }
}



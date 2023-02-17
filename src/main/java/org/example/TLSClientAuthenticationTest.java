package org.example;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;

public class TLSClientAuthenticationTest {
    public static void main(String[] args) throws Exception {
        KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
        FileInputStream clientKeyStoreFile = new FileInputStream("client.p12");
        clientKeyStore.load(clientKeyStoreFile, "password".toCharArray());

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(clientKeyStore, "password".toCharArray());

        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
    }
}


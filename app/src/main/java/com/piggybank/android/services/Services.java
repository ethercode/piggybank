package com.piggybank.android.services;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Base64;

import com.piggybank.android.application.PiggyBankApplication;
import com.piggybank.android.logging.PiggyBankLog;
import com.piggybank.android.services.converter.PiggyBankGsonConverter;
import com.piggybank.android.services.implementations.RetrofitAccountService;
import com.piggybank.android.services.implementations.RetrofitTransactionService;
import com.piggybank.android.services.implementations.RetrofitUserService;
import com.piggybank.android.util.SecureUtil;
import com.piggybank.android.util.TagUtil;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import be.shouldit.proxy.lib.APL;
import be.shouldit.proxy.lib.APLNetworkId;
import be.shouldit.proxy.lib.WiFiApConfig;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;

public class Services {
    private static Services ourInstance = new Services();
    private final RetrofitUserService userService;
    private final RetrofitAccountService accountService;
    private final RetrofitTransactionService transactionService;
    private RestAdapter secureRestAdapter;
    private RestAdapter insecureRestAdapter;

    private Services() {
        Services.ourInstance = this;
        createRestAdapter();

        this.userService = new RetrofitUserService();
        this.accountService = new RetrofitAccountService();
        this.transactionService = new RetrofitTransactionService();
    }

    public static String getCurrentSsid(Context context) {
        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                ssid = connectionInfo.getSSID();
            }
        }
        return ssid;
    }


    private String getPacFile() {
        APL.setup(PiggyBankApplication.getInstance());
        WifiConfiguration currentConnection = null;
        URL pacUrl = null;
        try {
            Map<APLNetworkId, WifiConfiguration> map = APL.getConfiguredNetworks();
            for (APLNetworkId id : map.keySet()) {
                WifiConfiguration configuration = map.get(id);
                if (configuration.SSID != null && configuration.SSID.equalsIgnoreCase(getCurrentSsid(PiggyBankApplication.getInstance()))) {
                    currentConnection = configuration;
                    break;
                }
            }
            if (currentConnection != null) {
                WiFiApConfig config = APL.getWiFiAPConfiguration(currentConnection);
                //to check if it's even a valid address
                if (android.text.TextUtils.isEmpty(config.getPacFileUri().toString()) || !config.getPacFileUri().toString().contains(".")) {
                    pacUrl = null;
                }
                //to check that the scheme isn't already defined.
                else if (!config.getPacFileUri().toString().contains("://")) {
                    pacUrl = new URL("http://" + config.getPacFileUri().toString());
                }
                else {
                    pacUrl = new URL(config.getPacFileUri().toString());
                }
            }
        } catch (Exception e) {
            PiggyBankLog.e(TagUtil.shortFrom(this), "Could not get proxy configuration", e);
        }

        return pacUrl == null ? "" : pacUrl.toString();
    }

    private void createRestAdapter() {
        PiggyBankLog.d(TagUtil.shortFrom(this), "createRestAdapter() started");

        String pac = getPacFile();

        PiggyBankLog.d(TagUtil.shortFrom(this), "PAC file discovered: " + pac);

        List<Proxy> proxyList = null;
        try {
            proxyList = ProxySelector.getDefault().select(new URI("http://www.google.com"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        finally {
            if (proxyList == null) {
                proxyList = new ArrayList<>();
            }
        }

        for (Proxy p : proxyList) {
            PiggyBankLog.d(TagUtil.shortFrom(this), p.toString());
        }

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(PiggyBankApplication.getConfiguration().getApiEndpoint())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new Client.Provider() {
                    @Override
                    public Client get() {
                        return new OkClient(createClient());
                    }
                })
                .setConverter(PiggyBankGsonConverter.getInstance().getGsonConverter());

        this.insecureRestAdapter = builder.build();
        this.secureRestAdapter = builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                String authToken = SecureUtil.getUserKey() + ":" + SecureUtil.getUserSecret();
                String auth = "Basic " + Base64.encodeToString(authToken.getBytes(), Base64.NO_WRAP);
                request.addHeader("Authorization", auth);
            }
        }).build();

        PiggyBankLog.d(TagUtil.shortFrom(this), "createRestAdapter() complete");
    }

    private OkHttpClient createClient() {
        int cacheSize = 256 * 1024 * 1024;
        File cacheDirectory = new File(PiggyBankApplication.getInstance().getCacheDir().getAbsolutePath(), "HttpCache");
        Cache cache = new Cache(cacheDirectory, cacheSize);
        OkHttpClient client = new OkHttpClient();
        client.setCache(cache);
        return client;
    }

    public static Services getInstance() {
        return ourInstance;
    }

    public RestAdapter getInsecureRestAdapter() {
        return insecureRestAdapter;
    }

    public RestAdapter getSecureRestAdapter() {
        return secureRestAdapter;
    }

    public RetrofitUserService getUserService() {
        return userService;
    }

    public RetrofitAccountService getAccountService() {
        return accountService;
    }

    public RetrofitTransactionService getTransactionService() {
        return transactionService;
    }
}

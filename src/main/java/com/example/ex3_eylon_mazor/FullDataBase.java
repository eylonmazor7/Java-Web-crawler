package com.example.ex3_eylon_mazor;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Full data base.
 */
public class FullDataBase {
    /**
     * main DB of all the program. synchronized DB collection to protect the DB from all the threads that send data
     * and received data from this DB.
     */
    private static Map<Integer, RequestDataBase> users = Collections.synchronizedMap(new HashMap<>());

    /**
     * Insert request method. when new user login - we create a RequestDataBase Object and insert to main Map with his ID.
     * @param id  the request id
     * @param rdb the rdb Object.
     */
    public void insertRequest(int id, RequestDataBase rdb){
        users.put(id, rdb);
    }

    /**
     * Get RequestDataBase Object by his id for get data from this Object.
     * @param id the request id
     * @return the UserDataBase Object.
     */
    public RequestDataBase getUserById(int id){
        return users.get(id);
    }

    /**
     * static method - Insert data from the threads to the specific ID in the map.
     * @param id          the request id
     * @param amountToAdd the amount of images that found to User counter.
     */
    public static void insertData(int id, int amountToAdd){
        users.get(id).updateURLCounter(amountToAdd);
    }

    /**
     * static method - Check url boolean.
     * check if the url is valid.
     * @param url the url to check
     * @return true if is valid, otherwise if no.
     */
//check url with checking the head, without get all the body
    public static boolean checkUrl(String url) {
        URL checkUrl;
        HttpURLConnection huc;
        int res;

        try {
            checkUrl = new URL(url);
            huc = (HttpURLConnection) checkUrl.openConnection();
            huc.setRequestMethod("HEAD");
            res = huc.getResponseCode();
        } catch (Exception e) { //if isn't valid
            return false;
        }

        return  res == HttpURLConnection.HTTP_OK; //make sure this url return ok!
    }
}

package com.example.ex3_eylon_mazor;

/**
 * The type Request data base. each user/client get his own UserDataBase object.
 */
public class RequestDataBase {
    /**
     * URLCounter - for counter the number of images
     */
    private int URLCounter;
    /**
     * url - url to check.
     */
    private final String url;
    /**
     * myThreads - the first thread to run - when the user start the crawling.
     */
    private MyThreads myThreads;

    /**
     * c-tor. create the first thread that start to check the url page
     * @param id    the request id
     * @param depth the max depth to go down for the recursion process.
     * @param url   the main url, that the user entered.
     */
    public RequestDataBase(int id, int depth, String url) {
        this.url = url;
        myThreads = new MyThreads(id, depth-1, url);
        myThreads.start();
    }

    /**
     * Get url string.
     * @return the main url string
     */
    public String getUrl(){
        return this.url;
    }

    /**
     * Gets the images counter for this user.
     * @return the counter
     */
    public int getCounter() {
        return this.URLCounter;
    }

    /**
     * Check if all the threads of this user are finish.
     * @return the boolean
     */
    public boolean checkUserProcess(){
        return !myThreads.isFinish();
    }

    /**
     * Update imagesUrl counter for this user.
     * @param add the add
     */
    public void updateURLCounter(int add)
    {
        this.URLCounter += add;
    }
}

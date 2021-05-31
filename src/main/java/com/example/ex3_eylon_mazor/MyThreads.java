package com.example.ex3_eylon_mazor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type My threads. create a thread with the user id. url to check and the current depth to check for the recursion.
 */
public class MyThreads extends Thread{
    /**
     * userId - for know which user update the number of images
     */
    private final int requestID;
    /**
     * depth - stop condition for the recursion
     */
    private final int depth;
    /**
     * mainUrl - url to check
     */
    private final String mainUrl;
    /**
     * finish - boolean variable to know if the thread is finish (relevant only for the first thread of any user)
     */
    public boolean finish = false;

    /**
     * c-tor
     * @param id    the request id
     * @param depth the current depth for recursion
     * @param url   the url to check
     */
    public MyThreads(int id, int depth, String url) {
        this.depth = depth;
        this.mainUrl = url;
        this.requestID = id;
    }

    /**
     * The main function of the thread that try to connect to the url, and if it success - count to number of images,
     * send the data to main DB - and continue to create pool thread for all the Urls in his Url (if pass the stop
     * condition of the recursion).
     * *****************************
     * why thread pool? why let to single thread do all the work if I can to create more?
     * to increase performance and still limit the number of threads.
     * *****************************
     */
    @Override
    public void run() {
        super.run();
        System.out.println("Starting Thread for url<"+this.mainUrl+">");
        System.out.println("Begin crawling <"+this.mainUrl+"> at depth <"+this.depth+">");
        Document doc = null;

        try { doc = Jsoup.connect(this.mainUrl).get();
        } catch (IOException e) {
            System.out.println("Invalid URL : " + this.mainUrl);
            return;
        }
        int n = doc.select("img").size();
        FullDataBase.insertData(requestID, n);
        System.out.println("Number of image found for <"+this.mainUrl+"> : " + n);

        //stop condition of the recursion
        if(this.depth>0) {
            //limit the threads the increase performance
            ExecutorService es= Executors.newFixedThreadPool(15);

            for (Element e : doc.select("a")) {
                String s = e.attr("abs:href");
                if(!FullDataBase.checkUrl(s)) continue;

                MyThreads myThreads = new MyThreads(this.requestID, this.depth - 1, s);
                es.submit(myThreads);
            }
        }

        System.out.println("End crawling <"+this.mainUrl+"> at depth <"+this.depth+">");
        System.out.println("End of Thread for url<"+this.mainUrl+">");
        this.finish = true;
    }

    /**
     * check if the main thread is finish. (unfortunately, more accurate than isAlive() method)
     * @return the boolean
     */
    public boolean isFinish() {
        return this.finish;
    }
}

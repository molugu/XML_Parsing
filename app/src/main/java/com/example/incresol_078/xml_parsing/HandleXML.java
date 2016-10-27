package com.example.incresol_078.xml_parsing;

import android.content.res.XmlResourceParser;
import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Incresol-078 on 20-06-2016.
 */
public class HandleXML {
    private String urlString = null;
    public volatile boolean parsingComplete = true;
    private XmlPullParserFactory xmlFactoryObject;
    private String Status = "status";

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public HandleXML(String url){
        this.urlString = url;
    }


    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;

        String text=null;
        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:

                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(name.equals("GeocodeResponse")) {
                          int event2= myParser.next();
                            Log.i("in geo ***", "=============================>");
                            while (event2 != XmlPullParser.END_DOCUMENT) {
                                Log.i("in while ***", "=============================>");
                                String name2=myParser.getName();
                                switch (event2){
                                    case XmlPullParser.END_TAG:
                                        if(name2.equals("status")){
                                            Log.i("in status ***", "=============================>");
                                        }break;
                                }

                            }
                        }
                    break;

                }
                event = myParser.next();
            }
            parsingComplete = false;
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void fetchXML(){
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();


                InputStream inputStream=conn.getInputStream();
                xmlFactoryObject=XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser=xmlFactoryObject.newPullParser();
                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
                xmlPullParser.setInput(inputStream,null);
                parseXMLAndStoreIt(xmlPullParser);
                inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }


            }
        });
        thread1.start();
    }

}

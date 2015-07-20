package com.niraj.apps.library_new;

/**
 * Created by niraj on 11/04/2015.
 */
import android.content.Context;
import android.util.Log;

import static xdroid.toaster.Toaster.toast;
import com.github.kevinsawicki.http.HttpRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class User {

    public Context getContext() {
        return context;
    }

    public void setContext(Context activity) {
        this.context = context;
    }

    private String rollNumber;
    private Context context;
    public List<Book> getBooks() {
        return books;
    }

    private List<Book> books = new ArrayList<Book>();

    private final String URL = "http://library.iiita.ac.in/cgi-bin/OPAC.exe?UName=&Option=queryPage&baseTbl=StatusCirculation&searchField=MCode";

    private final String data1="StatusCirculation&searchField=MCode&searchValue=";
    private final String data2="&submit=SEARCH&matchCriteria=1&pageSize=+20&orderBy=1&orderType=asc";

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public void fetchData() {
        String DATA = data1 + rollNumber + data2;
        Log.d("response", "fetching data....");

        if(isInternetAvailable()) {
            String htmlResponse = HttpRequest.post(URL).send(DATA).body();
            Log.d("response", "html");
            //Log.d("response", htmlResponse);
            Document doc = Jsoup.parse(htmlResponse);
            Elements mainTable = doc.select("table[border=0]"); //finally my table is here.

            //Log.d("response", mainTable.text().toString());
            Log.d("response", "displaying...");

            int rowNumber = 0;
            for(Element row: mainTable.select("tr")) {
                Log.d("response", "starts\n");
                int count = 0;
                //new row means new book
                // create a new book
                //and append to the users book list.
                rowNumber++;
                if(rowNumber >= 2) {
                    Book book = new Book();
                    for(Element td: row.select("td")) {
                        Log.d("response", td.text()+ count);

                        switch (count) {
                            case 3: book.setId(td.text()); break;
                            case 4: book.setName(td.text()); break;
                            case 5: book.setAuthor(td.text()); break;
                            case 6: book.setIssueDate(td.text()); break;
                            case 7: book.setReturningDate(td.text()); break;
                        }
                        count++;
                    }
                    books.add(book);
                }
                Log.d("response", String.format("ends\n%d", books.size()));
            }
            if(rowNumber < 2) {
                toast("Wrong Roll Number");
            }
        }
    }


    private boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");

            if (ipAddr.equals("")) {
                toast("Internet Not Working");
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            Log.d("response", "no internet available");
            toast("Check your Internet Connection");
            return false;
        }
    }


}

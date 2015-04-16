package com.niraj.apps.materialdemo;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private List<Book> myBooks;
    User user;
    ProgressDialog mProgressDialog;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchEditText = (EditText) findViewById(R.id.searchEditText);
    }

    public void onClickSearchButton(View view) {
        user = new User();
        user.setContext(this.getApplicationContext());
        user.setRollNumber(searchEditText.getText().toString());
        new FetchWebsiteData().execute();
    }

    //TODO set the roll number of user.

    private void populateBookList() {
        //books has been populated in user.fetchData
        myBooks = user.getBooks();
    }

    private void populateListView() {
        ArrayAdapter<Book> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this,
//                R.layout.da_item,
//                myBooks);
//
//        ListView list = (ListView) findViewById(R.id.listView);
//        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Book> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_view, myBooks);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if(itemView == null) {
              itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            Book currentBook = myBooks.get(position);

            TextView sNoTextView = (TextView) itemView.findViewById(R.id.sNoTextView);
            TextView nameTextView = (TextView) itemView.findViewById(R.id.bookNameTextView);
            TextView idTextView = (TextView) itemView.findViewById(R.id.bookIdtextView);
            TextView issueDateTextView = (TextView) itemView.findViewById(R.id.issuedOntextView);
            TextView returningDateTextView = (TextView) itemView.findViewById(R.id.returningDateTextView);

            sNoTextView.setText(Integer.toString(position+ 1));
            nameTextView.setText(currentBook.getName());
            idTextView.setText(currentBook.getId());
            issueDateTextView.setText(currentBook.getIssueDate());
            returningDateTextView.setText(currentBook.getReturningDate());

            return itemView;
            //return super.getView(position, convertView, parent);
        }
    }

    private class FetchWebsiteData extends AsyncTask<Void, Void, Void> {
        String htmlText, websiteDescription;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //FetchData example = new FetchData();
            user.fetchData();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            populateBookList();
            populateListView();
            mProgressDialog.dismiss();
        }
    }

    public void showToast(final String toast)
    {
        runOnUiThread (new Runnable() {
            public void run()
            {
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

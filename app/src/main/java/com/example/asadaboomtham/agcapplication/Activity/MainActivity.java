package com.example.asadaboomtham.agcapplication.Activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.asadaboomtham.agcapplication.NewsAdapter;
import com.example.asadaboomtham.agcapplication.NewsItem;
import com.example.asadaboomtham.agcapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsAdapter.ContactsAdapterListener, SwipeRefreshLayout.OnRefreshListener {
//    private static final String TAG = MainActivity.class.();
        private RecyclerView recyclerView;
        private List<NewsItem> contactList;
        private NewsAdapter mAdapter;
        private SearchView searchView;
        private SwipeRefreshLayout swipeRefreshLayout;

        // url to fetch contacts jsongetSimpleName
        private static final String URL = "http://10.105.1.250/reddit/ThaiPbs_data0.php";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

            // toolbar fancy stuff
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.toolbar_title);

            contactList = new ArrayList<>();
//        mAdapter = new NewsAdapter.ContactsAdapterListener(getApplicationContext(), c);


            recyclerView = findViewById(R.id.recycler_view);
            contactList = new ArrayList<>();
            mAdapter = new NewsAdapter(this, contactList, this);

            // white background notification bar
            whiteNotificationBar(recyclerView);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
            recyclerView.setAdapter(mAdapter);

//        fetchContacts();

            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            swipeRefreshLayout.setRefreshing(true);
                                            if (contactList != null) {

                                                contactList.clear();
                                            }
                                            getData();

                                        }
                                    }
            );
        }

        /**
         * fetches json by making http calls
         */


    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        NewsItem movie = new NewsItem();
                        movie.setNews_head(jsonObject.getString("news_head"));
                        movie.setNews_id(jsonObject.getInt("news_id"));
                        movie.setNews_des(jsonObject.getString("news_des"));
                        movie.setNews_pics(jsonObject.getString("news_pics"));
                        movie.setNews_link(jsonObject.getString("news_link"));
                        movie.setNews_ref(jsonObject.getString("news_ref"));

                        contactList.add(movie);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                mAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();

            }
        }) {
//            @Override
//            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
//                try {
//                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
//                    if (cacheEntry == null) {
//                        cacheEntry = new Cache.Entry();
//                    }
//                    final long cacheHitButRefreshed = 0 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
//                    final long cacheExpired = 1 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
//                    long now = System.currentTimeMillis();
//                    final long softExpire = now + cacheHitButRefreshed;
//                    final long ttl = now + cacheExpired;
//                    cacheEntry.data = response.data;
//                    cacheEntry.softTtl = softExpire;
//                    cacheEntry.ttl = ttl;
//                    String headerValue;
//                    headerValue = response.headers.get("Date");
//                    if (headerValue != null) {
//                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
//                    }
//                    headerValue = response.headers.get("Last-Modified");
//                    if (headerValue != null) {
//                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
//                    }
//                    cacheEntry.responseHeaders = response.headers;
//                    final String jsonString = new String(response.data,
//                            HttpHeaderParser.parseCharset(response.headers));
//                    return Response.success(new JSONArray(jsonString), cacheEntry);
//                } catch (UnsupportedEncodingException | JSONException e) {
//                    return Response.error(new ParseError(e));
//                }
//            }

            @Override
            protected void deliverResponse(JSONArray response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };
//        MyApplication.getInstance().addToRequestQueue(jsonArrayRequest);
        swipeRefreshLayout.setRefreshing(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    ////searcrh
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(NewsItem contact) {
        Toast.makeText(getApplicationContext(), "Selected: " + contact.getNews_head() + ", " + contact.getNews_des(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        getData();
        if (contactList != null)
            Toast.makeText(
                    getApplicationContext(),
                    "Data has refresh ",
                    Toast.LENGTH_SHORT
            ).show();
        {
            contactList.clear();
        }
    }

}


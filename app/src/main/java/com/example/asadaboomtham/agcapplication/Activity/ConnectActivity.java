package com.example.asadaboomtham.agcapplication.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asadaboomtham.agcapplication.Weather.Function;
import com.example.asadaboomtham.agcapplication.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConnectActivity extends AppCompatActivity {
    private final String TAG = "ConnectActivity";

    private LinearLayout linearLayout;
    private LinearLayout linearLayout_Dailynews;
    private LinearLayout linearLayout_facebook;
    private LinearLayout linearLayout_AllNews;
    private LinearLayout linearLayout_Kaokaset;
    private LinearLayout facebook_connect_layout;


    private CircleImageView photoImageView;
    private TextView nameTextView;
    private TextView emailTextView;
    private ProfileTracker profileTracker;

    TextView customFontTextView;
    TextView customFontTextView2;
    TextView customFontTextView3;
    Typeface typeface;


    //Start Declare Weatehr method
    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;

    Typeface weatherFont;
    //End Declare Weatehr method


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
//        getSupportActionBar().hide();

        photoImageView = (CircleImageView) findViewById(R.id.photoImageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
//        emailTextView = (TextView) findViewById(R.id.emailTextView);
//            idTextView = (TextView) findViewById(R.id.idTextView);


        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null) {
                    displayProfileInfo(currentProfile);
                }
            }
        };

        if (AccessToken.getCurrentAccessToken() == null) {
            goLoginScreen();
        } else {
            requestEmail(AccessToken.getCurrentAccessToken());

            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                displayProfileInfo(profile);
            } else {
                Profile.fetchProfileForCurrentAccessToken();
            }
        }
        typeface = Typeface.createFromAsset(getAssets(), "fonts/TH Baijam.ttf");

        customFontTextView = (TextView) findViewById(R.id.customFontTextView);

        customFontTextView.setTypeface(typeface);

//        customFontTextView2 = (TextView) findViewById(R.id.customFontTextView2);
//
//        customFontTextView2.setTypeface(typeface);

        customFontTextView3 = (TextView) findViewById(R.id.customFontTextView3);

        customFontTextView3.setTypeface(typeface);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout_Thaipbs);
        linearLayout.setOnClickListener(OnClickListener);

        linearLayout_Dailynews = (LinearLayout) findViewById(R.id.linearLayout_Dailynews);
        linearLayout_Dailynews.setOnClickListener(OnClickListenerDai);

        linearLayout_AllNews = (LinearLayout) findViewById(R.id.linearLayout_AllNews);
        linearLayout_AllNews.setOnClickListener(OnClickListenerAll);

        linearLayout_facebook = (LinearLayout) findViewById(R.id.linearLayout_facebook);
        linearLayout_facebook.setOnClickListener(OnClickListenerFace);

        linearLayout_Kaokaset = (LinearLayout) findViewById(R.id.linearLayout_Kaokaset);
        linearLayout_Kaokaset.setOnClickListener(OnClickListenerKao);

        facebook_connect_layout = (LinearLayout) findViewById(R.id.facebook_connect_layout);
        facebook_connect_layout.setOnClickListener(OnClickListenerConFace);

        whiteNotificationBar(linearLayout);

        //Weather
        weatherFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/weathericons-regular-webfont.ttf");

        cityField = (TextView) findViewById(R.id.city_field);
        //updatedField = (TextView)findViewById(R.id.updated_field);
        detailsField = (TextView) findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
//        humidity_field = (TextView)findViewById(R.id.humidity_field);
//        pressure_field = (TextView)findViewById(R.id.pressure_field);
        weatherIcon = (TextView) findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);


        Function.placeIdTask asyncTask = new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {

                cityField.setText(weather_city);
                // updatedField.setText(weather_updatedOn);
                //detailsField.setText(weather_description);
                currentTemperatureField.setText(weather_temperature);
                // humidity_field.setText("Humidity: " + weather_humidity);
                // pressure_field.setText("Pressure: " + weather_pressure);
                weatherIcon.setText(Html.fromHtml(weather_iconText));

            }
        });
        asyncTask.execute("13.2811658", "100.9262629"); //  asyncTask.execute("Latitude", "Longitude")
        getSupportActionBar().hide();

    }


    private View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), MainActivity
                    .class);
            startActivity(i);
        }
    };

    private View.OnClickListener OnClickListenerDai = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), DailyNewsActivity
                    .class);
            startActivity(i);
        }
    };
    private View.OnClickListener OnClickListenerFace = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), FacebookActivity
                    .class);
            startActivity(i);
        }
    };
    private View.OnClickListener OnClickListenerKao = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), KaoKasetActivity
                    .class);
            startActivity(i);
        }
    };
    private View.OnClickListener OnClickListenerAll = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), AllNewsActivity
                    .class);
            startActivity(i);
        }
    };
    private View.OnClickListener OnClickListenerConFace = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), LogoutActivity
                    .class);
            startActivity(i);
        }
    };

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }


    private void requestEmail(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (response.getError() != null) {
                    Toast.makeText(getApplicationContext(), response.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    String email = object.getString("email");
//                    setEmail(email);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void setEmail(String email) {
//        emailTextView.setText(email);
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view) {
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }

    private void displayProfileInfo(Profile profile) {
        String id = profile.getId();
        String name = profile.getName();
        String photoUrl = profile.getProfilePictureUri(600, 480).toString();

        nameTextView.setText(name);
//            idTextView.setText(id);

        Glide.with(getApplicationContext())
                .load(photoUrl)
                .into(photoImageView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        profileTracker.stopTracking();
    }
}



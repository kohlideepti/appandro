package com.vehicletracking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.vehicletracking.googleConstants.GoogleClient;
import com.vehicletracking.model.User;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private SignInButton googleSignIn;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount account;
    private static final int RC_GOOGLE_SIGN_IN = 9001;
    private User user;
    private Button mLoginButton;
    private EditText mEmail;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient =  new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        initialize();


    }

    private void initialize(){
        googleSignIn = (SignInButton) findViewById(R.id.google_plus_login);
        googleSignInListener();

        user = new User();

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mLoginButton = (Button) findViewById(R.id.button_login);
        setLoginButtonListener();
    }

    private void setLoginButtonListener(){
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                    //send email and password to the server
                    user.setEmail(email);
                    user.setLoginMethod(User.LOGIN_METHOD_NORMAL);

                     goToHome();
            }
        });
    }


    private void  googleSignInListener(){
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   signInThroughGoogle();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            isGoogleSignInSuccess(result);
        } else {

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    isGoogleSignInSuccess(googleSignInResult);
                }
            });
        }
    }



    private void signInThroughGoogle(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_GOOGLE_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_GOOGLE_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            isGoogleSignInSuccess(result);
        }
    }


   private void isGoogleSignInSuccess(GoogleSignInResult result){
       if(result.isSuccess()){
            account = result.getSignInAccount();
            user.setLoginMethod(User.LOGIN_METHOD_GOOGLE);
            user.setEmail(account.getEmail());
            user.setUsername(account.getDisplayName());
            goToHome();
            Toast.makeText(this,"Google Sign in Success  \n email:"+account.getEmail()+"\nDisplay name: "+account.getDisplayName(),Toast.LENGTH_SHORT).show();
       }
   }

    private void goToHome(){
        Intent intent = new Intent(this,HomeActivity.class);
        GoogleClient.setInstanceVariables(account);
        intent.putExtra(User.INTENT_NAME,user);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
             Log.d(getClass().getSimpleName(),connectionResult.toString());
    }
}

package com.vehicletracking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private SignInButton googleSignIn;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_GOOGLE_SIGN_IN = 9001;

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
    }


    private void  googleSignInListener(){
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   signInThroughGoogle();
            }
        });
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
           GoogleSignInAccount account = result.getSignInAccount();
            Toast.makeText(this,"Google Sign in Success  \n email:"+account.getEmail()+"\nDisplay name: "+account.getDisplayName(),Toast.LENGTH_SHORT).show();
       }
       else{
           Toast.makeText(this,"Google Sign in Failed",Toast.LENGTH_SHORT).show();
       }

   }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

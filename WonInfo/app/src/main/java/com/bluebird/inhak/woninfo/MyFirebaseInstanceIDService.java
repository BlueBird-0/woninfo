package com.bluebird.inhak.woninfo;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // 설치할때 여기서 토큰을 자동으로 만들어 준다
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        Log.d("FirebaseCloud", "Refreshed token: " + refreshedToken);
        Log.d("FirebaseCloud", "Refreshed token: " + UserManager.firebaseUser.getIdToken(true));


        // 생성한 토큰을 서버로 날려서 저장하기 위해서 만든거
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Users")
                .document(UserManager.firebaseUser.getUid())
                .update("message_token", token);
        Log.d("FirebaseCloud", "Token update successful.");
    }
}

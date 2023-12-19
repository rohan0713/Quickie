package com.delivery.quickie.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.delivery.quickie.R;
import com.delivery.quickie.databinding.ActivityHomeBinding;
import com.delivery.quickie.network.ImageResponse;
import com.delivery.quickie.network.ProfileResponse;
import com.delivery.quickie.network.RetrofitClient;
import com.delivery.quickie.ui.adapters.postAdapter;
import com.delivery.quickie.ui.fragments.exploreFragment;
import com.delivery.quickie.ui.fragments.feedFragment;
import com.delivery.quickie.ui.fragments.homeFragment;
import com.delivery.quickie.ui.fragments.profileFragment;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home extends AppCompatActivity {

    ActivityHomeBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    SharedPreferences sharedPreferences;
    com.delivery.quickie.ui.fragments.homeFragment homeFragment = new homeFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    homeFragment).commit();
        }

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        getProfileData();

        binding.navMenu.setBackground(null);
        binding.navMenu.getMenu().getItem(2).setEnabled(false);

        binding.navMenu.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;

            if(item.getItemId() == R.id.home){
                fragment = new homeFragment();
            }else if(item.getItemId() == R.id.explore){
                fragment = new exploreFragment();
            }else if (item.getItemId() == R.id.feed){
                fragment = new feedFragment();
            } else if (item.getItemId() == R.id.profile) {
                fragment = new profileFragment();
            }

            assert fragment!=null;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).commit();
            return true;
        });

        binding.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    private void getProfileData() {

        String email = sharedPreferences.getString("email", "");
        try {
            Call<ProfileResponse> res = RetrofitClient.Companion.getDbApi().getProfile(email.toString());
            res.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                    if (response.isSuccessful()) {
                        senData(response.body());
                    } else {
                        Toast.makeText(home.this, "Response Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    Toast.makeText(home.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Log.e("error", e.getMessage());
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }

    }

    private void senData(ProfileResponse response){
        EventBus.getDefault().postSticky(response);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getData() != null) {
                // Handle the selected image URI here
                Uri selectedImageUri = data.getData();
                String selectedImage = selectedImageUri.toString();
                Log.d("SelectedImage", "Uri: " + selectedImage);

                MultipartBody.Part imagePart = prepareFilePart("image", selectedImageUri);
                String email = sharedPreferences.getString("email", "");

                //specifying the media type for the email content and RequestBody.create(...) is used to create a RequestBody instance for the email content.
                RequestBody emailRequestBody = RequestBody.create(MediaType.parse("text/plain"), email);
                Call<ImageResponse> call = RetrofitClient.Companion.getDbApi().uploadImage(imagePart, emailRequestBody);
                call.enqueue(new Callback<ImageResponse>() {
                    @Override
                    public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(home.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                            Log.d("res", response.body().getStatus());
                        }else{
                            Log.d("res", response.body().toString());
                            Toast.makeText(home.this, "Image Upload failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageResponse> call, Throwable t) {
                        Log.d("res", t.getMessage());
                        Toast.makeText(home.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    // Helper method to create a file part from the image URI
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        String filePath = getFilePathFromContentUri(this, fileUri);
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private static String getFilePathFromContentUri(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        android.database.Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }

        return null;
    }

}
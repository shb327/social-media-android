package com.example.choose.community;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.choose.R;
import com.example.choose.api.CommunityController;
import com.example.choose.create.ChooseType;
import com.example.choose.dto.CommunityDTO;
import com.example.choose.dto.CreateTextPostRequestDTO;
import com.example.choose.dto.EditCommunityRequestDTO;
import com.example.choose.dto.PostDTO;
import com.example.choose.home.HomeActivity;
import com.example.choose.home.SettingsActivity;
import com.example.choose.retrofit.RetrofitUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCommunityActivity extends AppCompatActivity {

    TextInputEditText titleText;
    TextInputEditText descriptionText;
    TextInputLayout titleLayout;
    TextInputLayout descriptionLayout;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_community);

        button = findViewById(R.id.sendBtn);
        titleText = findViewById(R.id.titleTxt);
        descriptionText = findViewById(R.id.contentTxt);
        titleLayout = findViewById(R.id.titleLayout);
        descriptionLayout = findViewById(R.id.contentLayout);
        titleLayout.setErrorEnabled(true);
        descriptionLayout.setErrorEnabled(true);
        titleLayout.setCounterTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));

        Bundle extras = getIntent().getExtras();
        CommunityDTO community = (CommunityDTO) extras.getSerializable("community");

        titleLayout.setHint(community.getName());
        descriptionLayout.setHint(community.getDescription());

        CommunityController communityController = RetrofitUtils
                .getInstance().getRetrofit().create(CommunityController.class);

        titleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (titleText.getText().length() == 21) {
                    titleLayout.setErrorEnabled(true);
                    titleLayout.setError("Character Limit Exceeded");
                    titleText.setTextColor(Color.parseColor("#F75010"));

                } else {
                    titleLayout.setErrorEnabled(false);
                    titleLayout.setError(null);
                    titleText.setTextColor(Color.parseColor("#68B2A0"));
                    titleLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                    titleLayout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                }
            }
        });


        descriptionText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (descriptionText.getText().length() > 256) {
                    descriptionLayout.setErrorEnabled(true);
                    descriptionLayout.setError("Character limit exceeded");
                    descriptionText.setTextColor(Color.parseColor("#F75010"));
                } else {
                    descriptionLayout.setErrorEnabled(false);
                    descriptionLayout.setError(null);
                    descriptionText.setTextColor(Color.parseColor("#68B2A0"));
                    descriptionLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                    descriptionLayout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                    descriptionLayout.setCounterTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((titleText.getText().length() == 0) && (descriptionText.getText().length() == 0)) {
                    titleLayout.setErrorEnabled(true);
                    titleLayout.setError("Name cannot be empty");
                    titleLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    titleText.setTextColor(Color.parseColor("#F75010"));
                    descriptionLayout.setErrorEnabled(true);
                    descriptionLayout.setError("The description cannot be empty");
                    descriptionLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    descriptionText.setTextColor(Color.parseColor("#F75010"));
                    descriptionLayout.setCounterTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    return;
                } else if (titleText.getText().length() == 0) {
                    titleLayout.setErrorEnabled(true);
                    titleLayout.setError("Name cannot be empty");
                    titleLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    titleText.setTextColor(Color.parseColor("#F75010"));
                    return;
                } else if (descriptionText.getText().length() == 0) {
                    descriptionLayout.setErrorEnabled(true);
                    descriptionLayout.setError("The description cannot be empty");
                    descriptionLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    descriptionText.setTextColor(Color.parseColor("#F75010"));
                    descriptionLayout.setCounterTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    return;
                } else if (descriptionLayout.isErrorEnabled()) { return;
                } else if (titleLayout.isErrorEnabled()) { return; }

                communityController
                        .updateCommunity(community.getId().intValue(),
                                new EditCommunityRequestDTO(
                                        descriptionText.getText().toString(),
                                        titleText.getText().toString()))
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Intent i = new Intent(EditCommunityActivity.this, HomeActivity.class);
                                i.putExtra("fragment", 3);
                                startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(EditCommunityActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        Button btn = findViewById(R.id.closeBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditCommunityActivity.this, HomeActivity.class);
                i.putExtra("fragment", 3);
                startActivity(i);
            }
        });
    }
}
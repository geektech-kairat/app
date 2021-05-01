package com.example.myapplication.ui.profile;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.myapplication.App;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class ProfileFragment extends Fragment {

    private com.example.myapplication.databinding.FragmentProfileBinding binding;
    private ActivityResultLauncher<String> mGetContent;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = com.example.myapplication.databinding.FragmentProfileBinding.inflate(inflater, container, false);
        spinnerForCurrency();
        saveNameAndSalary();
        getImage();
        binding.debt2.setText(App.share.getForDebt());
        return binding.getRoot();
    }

    public void spinnerForCurrency() {
        String[] currency = {"Доллар", "Сом", "Рубль"};
        ArrayAdapter<String> aa = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, currency);
        binding.spinnerForCurrency.setAdapter(aa);
        binding.spinnerForCurrency2.setAdapter(aa);

    }

    public void saveNameAndSalary() {

        binding.salary2.setText(App.share.getForSalary());
        binding.nameProfile2.setText(App.share.getForName());

        binding.nameProfile2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                App.share.setForName(Objects.requireNonNull(binding.nameProfile2.getText()).toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.salary2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                App.share.setForSalary(Objects.requireNonNull(binding.salary2.getText()).toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }private void openGallery() {
        mGetContent.launch("image/*");

    }
    private void getImage() {
       ImageView imageView = binding.profileImage;
        imageView.setOnClickListener(v ->
                ProfileFragment.this.openGallery());
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        imageView.setImageURI(uri);
                    }
                });
    }



}
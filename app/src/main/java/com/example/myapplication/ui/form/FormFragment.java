package com.example.myapplication.ui.form;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentFormBinding;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;


public class FormFragment extends Fragment {

    private FragmentFormBinding binding;
    private NavController navController;
    private String s = "Поле не должен быть пустым ";
    private long id;
    private String a ;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);
        binding = FragmentFormBinding.inflate(inflater, container, false);
        getData();
        initListeners();

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd MMMM HH : mm");
        a = dateFormat.format(new Date());
        binding.dateAdd.setText(a);

        return binding.getRoot();
    }

    public void getData() {
        getParentFragmentManager().setFragmentResultListener("2", getViewLifecycleOwner(), (requestKey, result) -> {
            if (requestKey.equals("2") && result != null)
                Log.e("TAG", "onFragmentResult:  " + result.getString("number1"));
            binding.debtName2.setText(result.getString("number1"));
            Log.e(TAG, "getData: ");
            binding.description.setText(result.getString("description1").trim());
            binding.dateAdd.setText(result.getString("date").trim());
            binding.debt2.setText(result.getString("debt").trim());
            binding.currentDateTime.setText(result.getString("currentDate").trim());
            id = result.getLong("id");
        });
    }

    public void initListeners() {


        binding.btnSave.setOnClickListener(v -> {
            if (binding.debtName2.getText().toString().isEmpty() && binding.debt2.getText().toString()
                    .isEmpty() && binding.description
                    .getText().toString().isEmpty() && binding.currentDateTime.getText().equals("")) {
                binding.debtName2.setError(s);
                binding.debt2.setError(s);
                binding.description.setError(s);
                binding.currentDateTime.setError("Укажите дату когда надо вернуть долг ");
            } else if (binding.debtName2.getText().toString().isEmpty()) {
                binding.debtName2.setError(s);
            } else if (binding.debt2.getText().toString().isEmpty()) {

            }else if (binding.currentDateTime.getText().toString().equals("")){
                binding.currentDateTime.setError("Укажите дату");
            }else if (binding.description.getText().toString().isEmpty()){
                binding.description.setError(s);
            }

            else {
                Bundle bundle = new Bundle();
                bundle.putString("1", binding.debtName2.getText().toString());
                bundle.putString("2", binding.description.getText().toString());
                bundle.putLong("id", id);
                bundle.putString("3", binding.debt2.getText().toString());
                bundle.putString("4", binding.currentDateTime.getText().toString());
                bundle.putString("5" , a);
                getParentFragmentManager().setFragmentResult("key", bundle);
                int old = Integer.parseInt(App.share.getForDebt());
                int now = Integer.parseInt(binding.debt2.getText().toString());
                int mat = old + now;
                App.share.setForDebt(String.valueOf(mat));
                close();
            }
        });
        binding.currentDate.setOnClickListener(v -> {
            int selectedYear = 2021;
            int selectedMonth = 4;
            int selectedDayOfMonth = 1;

// Date Select Listener.
            DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> binding.currentDateTime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
// Create DatePickerDialog (Spinner Mode):
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);
// Show

            datePickerDialog.show();

        });

    }


    private void close() {
        navController.navigateUp();
    }


}
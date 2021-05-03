package com.example.myapplication.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.ui.home.adapter.Adapter;
import com.example.myapplication.ui.home.adapter.HomeModel;
import com.example.myapplication.ui.home.adapter.Listen;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements Listen {

    private FragmentHomeBinding binding;
    private NavController navController;
    private Adapter adapter;
    private String s;
    private long id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new Adapter((Listen) this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        App.fillDataBase.fillDao().getAll().observe(getViewLifecycleOwner(), homeModelList -> adapter.addList(homeModelList));
        binding.rv.setAdapter(adapter);

        getDataInForm();
        click();

        requireActivity().getOnBackPressedDispatcher().
                addCallback(
                        getViewLifecycleOwner(),
                        new OnBackPressedCallback(true) {
                            @Override
                            public void handleOnBackPressed() {
                                alert();
                            }
                        });
        return binding.getRoot();

    }

    public void click() {
        binding.fabAdd.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_formFragment);
        });
    }
    private void getDataInForm() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd MMMM HH : mm");
        s = dateFormat.format(new Date());
        //добавление
        Log.e(TAG, "getDataInForm: " + s);
        getParentFragmentManager().setFragmentResultListener("key",
                getViewLifecycleOwner(), (requestKey, result) -> {
                    String a = result.getString("1");
                    String b = result.getString("2");
                    String c = result.getString("3");
                    String d = result.getString("4");
                    id = result.getLong("id");

                    HomeModel model = adapter.getModelToId(id);
                    if (model != null) {
                        model.setName(a);
                        model.setDescription(b);
                        model.setDebt(d);
                        App.fillDataBase.fillDao().update(model);
                    } else {
                        App.fillDataBase.fillDao().insert(new HomeModel(a, b,c ,s, d ));
                    }
                });
    }

    @Override
    //ОТПРАВЛЕНИЕ ДАННЫХ
    public void setDataForForm(HomeModel homeModel, int position) {
        Bundle bundle = new Bundle();

        bundle.putString("number1", homeModel.getName());
        bundle.putString("description1", homeModel.getDescription());
        bundle.putString("date", homeModel.getDate());
        bundle.putString("debt", homeModel.getDebt());
        bundle.putString("currentDate", homeModel.getEditDate());

        bundle.putLong("id", homeModel.getId());
        getParentFragmentManager().setFragmentResult("2", bundle);
        navController.navigate(R.id.action_navigation_home_to_formFragment);
    }

    @Override
    public void del(int position) {

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sortAZ) {
            adapter.addList(App.fillDataBase.fillDao().getAllBySort());
            binding.rv.setAdapter(adapter);
            Snackbar.make(requireView(), "Отсортирован A-Я", Snackbar.LENGTH_SHORT).show();

        } else if (item.getItemId() == R.id.sortZA) {
            adapter.addList(App.fillDataBase.fillDao().getAllBySortRes());
            binding.rv.setAdapter(adapter);
            Snackbar.make(requireView(), "Отсортирован Я-А", Snackbar.LENGTH_SHORT).show();

        }
        else if (item.getItemId() == R.id.deleteAll){
            App.fillDataBase.fillDao().deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }
    public void alert() {
        AlertDialog.Builder adg = new AlertDialog.Builder(binding.getRoot().getContext());
        String positive = "Да";
        String negative = "Нет";
        adg.setMessage("Вы хотите выйти ?");
        adg.setPositiveButton(positive, (dialog, which) -> requireActivity().finish());
        adg.setNegativeButton(negative, null);
        adg.show();
    }

}
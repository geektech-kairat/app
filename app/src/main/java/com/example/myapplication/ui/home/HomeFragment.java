package com.example.myapplication.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.ui.home.adapter.Adapter;
import com.example.myapplication.ui.home.adapter.HomeModel;
import com.example.myapplication.ui.home.adapter.Listen;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements Listen {

    private FragmentHomeBinding binding;
    private NavController navController;
    private Adapter adapter;
    private String a, b, c, d, s;
    private long id;
    private boolean loading = true;

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
        check();
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

        //добавление
        Log.e(TAG, "getDataInForm: " + s);
        getParentFragmentManager().setFragmentResultListener("key",
                getViewLifecycleOwner(), (requestKey, result) -> {

                    a = result.getString("1");
                    b = result.getString("2");
                    c = result.getString("3");
                    d = result.getString("4");
                    s = result.getString("5");
                    id = result.getLong("id");

                    HomeModel model = adapter.getModelToId(id);
                    if (model != null) {
                        model.setName(a);
                        model.setDescription(b);
                        model.setDebt(c);
                        App.fillDataBase.fillDao().update(model);
                    } else {
                        App.fillDataBase.fillDao().insert(new HomeModel(a, b, c, s, d));
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
    public void del(HomeModel homeModel, int position) {

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.custom_alertdialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button button = dialog.findViewById(R.id.all);
        Button button2 = dialog.findViewById(R.id.part);
        Button button3 = dialog.findViewById(R.id.ok);
        Button cancel = dialog.findViewById(R.id.ok);
        TextInputLayout editText = dialog.findViewById(R.id.debtDialog1);
        TextInputEditText editText2 = dialog.findViewById(R.id.debtDialog2);
        button.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.INVISIBLE);
        dialog.show();

        cancel.setOnClickListener(v -> {
            dialog.cancel();
        });
        button.setOnClickListener(v -> {
            int old = Integer.parseInt(App.share.getForDebt());
            int now = Integer.parseInt(homeModel.getDebt());
            int mat = old - now;
            App.share.setForDebt(String.valueOf(mat));
            adapter.remove(homeModel, position);
            dialog.cancel();
        });
        button2.setOnClickListener(v -> {
            TextView textView = dialog.findViewById(R.id.title1);
            textView.setText("Укажите сколько нужно погасить ");
            editText.setVisibility(View.VISIBLE);

            button.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.INVISIBLE);
            button3.setVisibility(View.VISIBLE);
            editText.setVisibility(View.VISIBLE);
            button3.setOnClickListener(v1 -> {
                int old = Integer.parseInt(App.share.getForDebt());
                int now = Integer.parseInt(editText2.getText().toString());
                int model = Integer.parseInt(homeModel.getDebt());
                if (old > now){
                    int mat = model - now;
                    int mat2 = old - now;
                    homeModel.setDebt(String.valueOf(mat));
                    App.fillDataBase.fillDao().update(homeModel);
                    App.share.setForDebt(String.valueOf(mat2));
                    dialog.cancel();
                }else if (old == now){
                    adapter.remove(homeModel, position);
                    int mat2 = old - now;
                    App.share.setForDebt(String.valueOf(mat2));
                    dialog.cancel();

                }

                else {
                    editText2.setError(" Сумма больше чем вы должны !");
                }
            });
        });


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

        } else if (item.getItemId() == R.id.deleteAll) {
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

    public void check() {


        binding.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (loading) {
                    if (dy > 0) //check for scroll down
                    {
                        Log.e(TAG, "onScrolled: -");
                        binding.fabAdd.hide();

                    } else {
                        Log.e(TAG, "onScrolled: + ");
                        binding.fabAdd.show();

                    }
                }
            }
        });
    }

}
package com.example.foodintaketracking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodintaketracking.databinding.FragmentFoodHistoryBinding;
import com.example.foodintaketracking.dbProvider.Food;
import com.example.foodintaketracking.dbProvider.FoodViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FoodHistoryFragment extends Fragment {
    private FragmentFoodHistoryBinding binding;
    private FoodViewModel foodViewModel;
    private ArrayList<Food> foodList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFoodHistoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        setDateOfMeal();
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);

        FoodListAdapter adapter = new FoodListAdapter(new FoodListAdapter.FoodDiff());
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        // Update the cached copy of the foods in the adapter.
        foodViewModel.getAllFood().observe(getViewLifecycleOwner(), adapter::submitList);


        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodViewModel.deleteAll();
                Toast.makeText(getContext(), "All data has been deleted.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void setDateOfMeal() {
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("EEE dd-MM-yyyy", Locale.ENGLISH);
        binding.dateTextView.setText(formatter.format(date));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

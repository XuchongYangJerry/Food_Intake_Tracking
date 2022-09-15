package com.example.foodintaketracking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.foodintaketracking.databinding.FragmentFoodHistoryBinding;

public class FoodHistoryFragment extends Fragment {
    private FragmentFoodHistoryBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFoodHistoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();









        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

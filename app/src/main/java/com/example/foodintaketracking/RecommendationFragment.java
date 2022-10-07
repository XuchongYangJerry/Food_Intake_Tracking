package com.example.foodintaketracking;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodintaketracking.databinding.FragmentRecommendationBinding;

public class RecommendationFragment extends Fragment {
    private FragmentRecommendationBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecommendationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        /**
        SharedViewModel model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        model.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                binding.nutrition.setText(message);
            }
        });*/

        SharedPreferences sharedPref = requireActivity().getSharedPreferences("Nutrition", Context.MODE_PRIVATE);
        String nutrition = sharedPref.getString("nutrition",null);
        String foodName = sharedPref.getString("foodName", null);
        float sugar = sharedPref.getFloat("sugar",0);
        float fat = sharedPref.getFloat("fat",0);
        binding.nutrition.setText("Food Name: " + foodName + "\n" +nutrition);
        if(!nutrition.isEmpty()) {
            if (sugar > 20 || fat > 20) {
                binding.imageView.setImageResource(R.drawable.red);
                binding.recommendationView1.setCardBackgroundColor(getResources().getColor(R.color.so_red));
                binding.textView1.setText(R.string.Red);
            } else if ((sugar > 15 && sugar < 20) || (fat > 15 && fat < 20)) {
                binding.imageView.setImageResource(R.drawable.orange);
                binding.recommendationView1.setCardBackgroundColor(getResources().getColor(R.color.orange));
                binding.textView1.setText(R.string.Orange);
            } else {
                binding.imageView.setImageResource(R.drawable.green);
                binding.recommendationView1.setCardBackgroundColor(getResources().getColor(R.color.green));
                binding.textView1.setText(R.string.Green);
            }
        }
        //binding.textView.setText(R.string.Green);

        //binding.imageView2.setImageResource(R.drawable.green);

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

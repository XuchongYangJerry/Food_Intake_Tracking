package com.example.foodintaketracking;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodintaketracking.databinding.FragmentRecommendationBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RecommendationFragment extends Fragment {
    private FragmentRecommendationBinding binding;
    private SharedPreferences sharedPref;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecommendationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("EEE dd-MM-yyyy", Locale.ENGLISH);
        binding.dateTextView.setText(formatter.format(date));

        sharedPref = requireActivity().getSharedPreferences("Nutrition", Context.MODE_PRIVATE);
        String nutrition1 = sharedPref.getString("nutrition1","");
        String nutrition2 = sharedPref.getString("nutrition2", "");
        String foodName = sharedPref.getString("foodName", "");
        float sugar = sharedPref.getFloat("sugar",0);
        float fat = sharedPref.getFloat("fat",0);
        if(!nutrition1.isEmpty()) {
            binding.nutrition1.setText("Food Name: " + foodName + "\n" + nutrition1);
            binding.nutrition2.setText("\n" + nutrition2);
            if (sugar > 20 || fat > 20) {
                binding.imageView.setImageResource(R.drawable.red);
                binding.recommendationView1.setCardBackgroundColor(getResources().getColor(R.color.so_red));
                binding.textView1.setText(R.string.Red);
            } else if ((sugar > 13 && sugar < 20) || (fat > 15 && fat < 20)) {
                binding.imageView.setImageResource(R.drawable.orange);
                binding.recommendationView1.setCardBackgroundColor(getResources().getColor(R.color.orange));
                binding.textView1.setText(R.string.Orange);
            } else {
                binding.imageView.setImageResource(R.drawable.green);
                binding.recommendationView1.setCardBackgroundColor(getResources().getColor(R.color.so_green));
                binding.textView1.setText(R.string.Green);
            }
        }

        binding.resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSharedPreference();
                binding.nutrition1.setText("Nutrition");
                binding.nutrition2.setText("");
                binding.imageView.setImageResource(R.drawable.traffic_light);
                binding.recommendationView1.setCardBackgroundColor(getResources().getColor(R.color.white));
                binding.textView1.setText("Recommendation");
                Toast.makeText(getContext(), "All data has been reset.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void clearSharedPreference(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

package com.example.foodintaketracking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodintaketracking.databinding.RecyclerviewItemBinding;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class FoodViewHolder extends RecyclerView.ViewHolder{
    private RecyclerviewItemBinding binding;

    public FoodViewHolder(RecyclerviewItemBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(String imagePath, String foodName, Double foodEatenPercent, int foodQuantity, String foodCategory, String foodEatenTime){
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        Bitmap foodImage = BitmapFactory.decodeFile(imagePath, bmOptions);
//        foodImage = Bitmap.createScaledBitmap(foodImage, 224, 224,false);
//        binding.foodImage.setImageBitmap(foodImage);

        setFoodImageToView(imagePath);
        binding.textView1.setText(foodName);
        binding.textView2.setText(new StringBuilder().
                append("Quantity: ").append(foodQuantity).
                append("\nCategory: ").append(foodCategory).
                append("\nConsumption: ").append(foodEatenPercent*100).append("%").
                append("\nTime: ").append(foodEatenTime).toString());
    }

    private void setFoodImageToView(String imagePath){
        Bitmap takenImage = BitmapFactory.decodeFile(imagePath);
        ExifInterface ei;
        Bitmap rotatedBitmap = null;
        try {
            ei = new ExifInterface(imagePath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(takenImage, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(takenImage, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(takenImage, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = takenImage;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(rotatedBitmap != null){
            Bitmap foodImageRounded = Bitmap.createBitmap(rotatedBitmap.getWidth(), rotatedBitmap.getHeight(), rotatedBitmap.getConfig());
            Canvas canvas = new Canvas(foodImageRounded);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(rotatedBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.drawRoundRect((new RectF(0, 0, rotatedBitmap.getWidth(), rotatedBitmap.getHeight())), 80, 80, paint); // Round Image Corner 100 100 100 100

            binding.foodImage.setImageBitmap(foodImageRounded);
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public FoodViewHolder onCreate(@NonNull ViewGroup parent){
        binding = RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodViewHolder(binding);
    }
}

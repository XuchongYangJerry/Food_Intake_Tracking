package com.example.foodintaketracking;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.opengl.GLU;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.foodintaketracking.databinding.ActivityMainBinding;
import com.example.foodintaketracking.dbProvider.Food;
import com.example.foodintaketracking.dbProvider.FoodViewModel;
//import com.example.foodintaketracking.ml.InceptionFloat;
//import com.example.foodintaketracking.ml.Lite;
//import com.example.foodintaketracking.ml.CkptExtraLabel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions;


import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import org.tensorflow.lite.support.label.Category;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.tensorflow.lite.Interpreter;
//import org.tensorflow.lite.gpu.CompatibilityList;
//import org.tensorflow.lite.gpu.GpuDelegate;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    /**
    private long mealConsumptionMilliseconds = 0;
    // private boolean isMealTimerRunning = false;
    //String photoTimestamp = "";
    private static final String TAG = "FoodDetection";
    private boolean isMealTimerRunning = false;
    File photoFile;
    Handler handler;
    long startMilliSecs;
    private FoodViewModel mFoodViewModel;
//    String[] mealList = {"Breakfast", "Lunch", "Snack", "Dinner"};
//    String[] foodCategoryList = {"Grains", "Fruits", "Vegetables", "Fish & Meats", "Dairy", "Sugars & Oils", "Others"};
    int imageSize = 224;
    Interpreter tflite;
    String outputText = "";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // replaceFragment(new AddFoodFragment());

        setSupportActionBar(binding.appBar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.AddFoodFragment,
                R.id.RecommendationFragment,
                R.id.GlucoseReportFragment,
                R.id.FoodHistoryFragment)
                .build();
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment)
                fragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        //Sets up a NavigationView for use with a NavController.
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
        //Sets up a Toolbar for use with a NavController.
        NavigationUI.setupWithNavController(binding.appBar.toolbar, navController, appBarConfiguration);

        binding.bottomNavigation.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        /**
        binding.bottomNavigation.setOnItemSelectedListener(item -> {

                    switch (item.getItemId()) {

                        case R.id.AddFoodFragment:
                            replaceFragment(new AddFoodFragment()); break;
                        case R.id.RecommendationFragment:
                            replaceFragment(new RecommendationFragment()); break;
                        case R.id.GlucoseReportFragment:
                            replaceFragment(new GlucoseReportFragment()); break;
                        case R.id.FoodHistoryFragment:
                            replaceFragment(new FoodHistoryFragment()); break;
                    }
                    return true;
                });*/
    }

    /**
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }*/

        /**
        // Upload tensorflow file
        LocalModel localModel = new LocalModel.Builder()
                        .setAssetFilePath("model.tflite")
                        .build();

        binding.recognitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomImageLabelerOptions customImageLabelerOptions =
                        new CustomImageLabelerOptions.Builder(localModel)
                                .setConfidenceThreshold(0.5f)
                                .setMaxResultCount(5)
                                .build();
                ImageLabeler labeler = ImageLabeling.getClient(customImageLabelerOptions);

                Bitmap recognitionImage = ((BitmapDrawable)binding.imageView.getDrawable()).getBitmap();
                InputImage image = InputImage.fromBitmap(recognitionImage, 0);

                labeler.process(image)
                        .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                            @Override
                            public void onSuccess(List<ImageLabel> labels) {
                                // Task completed successfully
                                for (ImageLabel label : labels) {
                                    String text = label.getText();
                                    float confidence = label.getConfidence();
                                    int index = label.getIndex();
                                    outputText += text + " :" + confidence + "\n";
                                }
                                binding.RecognitionResult.setText(outputText);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...
                            }
                        });
            }
        });


        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        }

        //handler to handle the meal consumption timer
        handler = new Handler(Looper.getMainLooper());

        //initialising values, UI elements and other defaults
        setDateOfMeal();
        handleMealTimer();
        setUpCameraService();
        // setupSpinners();
        mFoodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        mFoodViewModel.getAllFood().observe(this, newData -> {
            newData.forEach( item -> {
                Log.i(TAG, item.getFoodEatenAt().toString());
            });
        });

        binding.saveMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFoodToRoom();
                Snackbar.make(view, "Food item has been added to the database", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDataSharedPreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreDataSharedPreferences();
    }

    private void saveDataSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("InstanceData",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putLong("startMilliSecs", startMilliSecs);
        myEdit.putLong("mealConsumptionMilliseconds", mealConsumptionMilliseconds);
        myEdit.putBoolean("isMealTimerRunning", isMealTimerRunning);
        if(photoFile != null) {
            myEdit.putString("photoFile", photoFile.getAbsolutePath());
        }
        myEdit.apply();
    }

    private void restoreDataSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("InstanceData", MODE_PRIVATE);
        startMilliSecs = sharedPreferences.getLong("startMilliSecs", 0L);
        mealConsumptionMilliseconds = sharedPreferences.getLong("mealConsumptionMilliseconds", 0L);
        isMealTimerRunning = sharedPreferences.getBoolean("isMealTimerRunning", false);
//        if(sharedPreferences.getBoolean("photoTaken", false)){
//            photoFile = getRandomFileUri(true);
//            setFoodImageToView(photoFile);
//        }
//        else{
//            binding.imageView.setImageResource(R.drawable.ic_launcher_foreground);
//        }
        photoFile = getRandomFileUri(true);
        //setFoodImageToView(photoFile);
    }



    private void setUpCameraService(){

        binding.foodCaptureButton.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photoFile = getRandomFileUri(true);
            Uri fileProvider = FileProvider.getUriForFile(this, getPackageName(), photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
            if (intent.resolveActivity(getPackageManager()) != null) {
                // Start the image capture intent to take photo
                //startActivityForResult(intent, 1001);
                cameraResultLauncher.launch(intent);
                //File pictureDirectory = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Camera");

            }
        });
    }

    private void saveFoodToRoom(){
        Food item = new Food("Unknown",
                photoFile.getAbsolutePath(),
                Calendar.getInstance().getTime(),
                0.0,
                binding.foodItemTextView.getText().toString(),
                "Dinner",
                0.0,
                "testMetric",
                (int)mealConsumptionMilliseconds);
        mFoodViewModel.insert(item);

        // getApplicationContext().deleteFile(photoFile.getAbsolutePath());

    }
    */

    /**
    private void setupSpinners(){
        binding.foodCatSpinner.setOnItemSelectedListener(this);
        ArrayAdapter foodCatSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, foodCategoryList);
        foodCatSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.foodCatSpinner.setAdapter(foodCatSpinnerAdapter);

        binding.mealSpinner.setOnItemSelectedListener(this);
        ArrayAdapter mealSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mealList);
        mealSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.mealSpinner.setAdapter(mealSpinnerAdapter);
    }*/

    /**
    //as startActivityForResult is deprecated, using the ActivityResultLauncher
    //callback for handling the results of camera image capture session
    ActivityResultLauncher<Intent> cameraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                // by this point we have the camera photo on disk
                //    Bitmap photo = (Bitmap) result.getData();
                //    imageView.setImageBitmap(photo);
                    SharedPreferences sharedPreferences = getSharedPreferences("InstanceData", MODE_PRIVATE);
                    startMilliSecs = sharedPreferences.getLong("startMilliSecs", 0L);
                    mealConsumptionMilliseconds = sharedPreferences.getLong("mealConsumptionMilliseconds", 0L);
                    isMealTimerRunning = sharedPreferences.getBoolean("isMealTimerRunning", false);
                    photoFile = getRandomFileUri(true);
                    boolean isImageSet = setFoodImageToView(photoFile);
                    setDateOfPhoto();
                    if(!isImageSet){
                        Toast.makeText(this, "There was an error capturing the image" , Toast.LENGTH_SHORT).show();
                    }
                } else { // Result was a failure
                    Toast.makeText(this, "Error taking picture", Toast.LENGTH_SHORT).show();
                }
            }
    );

    private boolean setFoodImageToView(@NonNull File file){
        Bitmap takenImage = BitmapFactory.decodeFile(file.getAbsolutePath());
        ExifInterface ei;
        Bitmap rotatedBitmap = null;
        try {
            ei = new ExifInterface(photoFile.getAbsolutePath());
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
            Bitmap imageRounded=Bitmap.createBitmap(rotatedBitmap.getWidth(), rotatedBitmap.getHeight(), rotatedBitmap.getConfig());
            Canvas canvas=new Canvas(imageRounded);
            Paint mpaint=new Paint();
            mpaint.setAntiAlias(true);
            mpaint.setShader(new BitmapShader(rotatedBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.drawRoundRect((new RectF(0, 0, rotatedBitmap.getWidth(), rotatedBitmap.getHeight())), 100, 100, mpaint); // Round Image Corner 100 100 100 100

            binding.imageView.setImageBitmap(imageRounded);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap anotherMap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);
            anotherMap = Bitmap.createScaledBitmap(anotherMap, imageSize, imageSize,false);
            Log.i(TAG, "Classification Start: " +  Calendar.getInstance().getTime());
            // classifyImage(anotherMap);
            return true;
        }
        return false;
    }

    public File getRandomFileUri(boolean isTemporary) {
        String photoTimestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        if(isTemporary){
            photoTimestamp = "temporary";
        }
        String TAG = "FoodDetection";
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
        //File mediaStorageDir = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), TAG);

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + photoTimestamp + ".jpeg");
    }

    private void setDateOfMeal() {
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("EEE dd-MM-yyyy", Locale.ENGLISH);
        binding.dateTextView.setText(formatter.format(date));
    }
    private void setDateOfPhoto() {
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("HH:MM:SS", Locale.getDefault());
        binding.photoTimestampTextView.setText(formatter.format(date));
    }

    private void handleMealTimer() {
        binding.playPauseMealButton.setImageResource(android.R.drawable.ic_media_play);
        binding.playPauseMealButton.setOnClickListener(view -> {
            isMealTimerRunning = !isMealTimerRunning;
            if(isMealTimerRunning){
                binding.playPauseMealButton.setImageResource(android.R.drawable.ic_media_pause);
                binding.playPauseMealButton.setBackgroundResource(R.drawable.button_background_red);
                startMilliSecs = System.currentTimeMillis();
                handler.postDelayed(runnable, 0);
            } else {
                binding.playPauseMealButton.setImageResource(android.R.drawable.ic_media_play);
                binding.playPauseMealButton.setBackgroundResource(R.drawable.button_background_blue);
                mealConsumptionMilliseconds += System.currentTimeMillis() - startMilliSecs;
                handler.removeCallbacks(runnable);
            }

        });
    }

    //Looping runnable that executes every millisecond on background thread.
    public Runnable runnable = new Runnable() {
        @SuppressLint("DefaultLocale")
        public void run() {
            long seconds = (mealConsumptionMilliseconds + System.currentTimeMillis() - startMilliSecs) / 1000;
            long s = seconds % 60;
            long m = (seconds / 60) % 60;
            long h = (seconds / (60 * 60)) % 24;
            binding.mealDurTextView.setText(String.format("%02d:%02d:%02d", h, m, s));
            handler.postDelayed(this, 0);
        }
    };

    //Utility method to rotate the image at the given angle
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    */

    /**
     public void classifyImage(Bitmap image){
     try {
     //InceptionFloat model = InceptionFloat.newInstance(getApplicationContext());
     Lite model = Lite.newInstance(getApplicationContext());

     // Creates inputs for reference.
     TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, imageSize, imageSize, 3}, DataType.FLOAT32);
     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
     byteBuffer.order(ByteOrder.nativeOrder());

     // get 1D array of 224 * 224 pixels in image
     int [] intValues = new int[imageSize * imageSize];
     image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

     // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
     int pixel = 0;
     for(int i = 0; i < imageSize; i++){
     for(int j = 0; j < imageSize; j++){
     int val = intValues[pixel++]; // RGB
     byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
     byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
     byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
     }
     }
     inputFeature0.loadBuffer(byteBuffer);

     // Runs model inference and gets result.
     //            CkptExtraLabel.Outputs outputs = model.process(inputFeature0);
     //            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
     //            float[] confidences = outputFeature0.getFloatArray();

     Lite.Outputs outputs = model.process(TensorImage.fromBitmap(image));
     List<Category> probability = outputs.getProbabilityAsCategoryList();
     probability.sort(Comparator.comparing(Category::getScore, Comparator.reverseOrder()));

     for (int i=0; i<3; i++){
     Log.i(TAG, probability.get(i).getLabel() + " " + probability.get(i).getScore());
     }
     Log.i(TAG, "Classification End: " +  Calendar.getInstance().getTime());

     //            String[] labels = new String[]{"background","tench","goldfish","great white shark","tiger shark","hammerhead","electric ray","stingray","cock","hen","ostrich","brambling","goldfinch","house finch","junco","indigo bunting","robin","bulbul","jay","magpie","chickadee","water ouzel","kite","bald eagle","vulture","great grey owl","European fire salamander","common newt","eft","spotted salamander","axolotl","bullfrog","tree frog","tailed frog","loggerhead","leatherback turtle","mud turtle","terrapin","box turtle","banded gecko","common iguana","American chameleon","whiptail","agama","frilled lizard","alligator lizard","Gila monster","green lizard","African chameleon","Komodo dragon","African crocodile","American alligator","triceratops","thunder snake","ringneck snake","hognose snake","green snake","king snake","garter snake","water snake","vine snake","night snake","boa constrictor","rock python","Indian cobra","green mamba","sea snake","horned viper","diamondback","sidewinder","trilobite","harvestman","scorpion","black and gold garden spider","barn spider","garden spider","black widow","tarantula","wolf spider","tick","centipede","black grouse","ptarmigan","ruffed grouse","prairie chicken","peacock","quail","partridge","African grey","macaw","sulphur-crested cockatoo","lorikeet","coucal","bee eater","hornbill","hummingbird","jacamar","toucan","drake","red-breasted merganser","goose","black swan","tusker","echidna","platypus","wallaby","koala","wombat","jellyfish","sea anemone","brain coral","flatworm","nematode","conch","snail","slug","sea slug","chiton","chambered nautilus","Dungeness crab","rock crab","fiddler crab","king crab","American lobster","spiny lobster","crayfish","hermit crab","isopod","white stork","black stork","spoonbill","flamingo","little blue heron","American egret","bittern","crane","limpkin","European gallinule","American coot","bustard","ruddy turnstone","red-backed sandpiper","redshank","dowitcher","oystercatcher","pelican","king penguin","albatross","grey whale","killer whale","dugong","sea lion","Chihuahua","Japanese spaniel","Maltese dog","Pekinese","Shih-Tzu","Blenheim spaniel","papillon","toy terrier","Rhodesian ridgeback","Afghan hound","basset","beagle","bloodhound","bluetick","black-and-tan coonhound","Walker hound","English foxhound","redbone","borzoi","Irish wolfhound","Italian greyhound","whippet","Ibizan hound","Norwegian elkhound","otterhound","Saluki","Scottish deerhound","Weimaraner","Staffordshire bullterrier","American Staffordshire terrier","Bedlington terrier","Border terrier","Kerry blue terrier","Irish terrier","Norfolk terrier","Norwich terrier","Yorkshire terrier","wire-haired fox terrier","Lakeland terrier","Sealyham terrier","Airedale","cairn","Australian terrier","Dandie Dinmont","Boston bull","miniature schnauzer","giant schnauzer","standard schnauzer","Scotch terrier","Tibetan terrier","silky terrier","soft-coated wheaten terrier","West Highland white terrier","Lhasa","flat-coated retriever","curly-coated retriever","golden retriever","Labrador retriever","Chesapeake Bay retriever","German short-haired pointer","vizsla","English setter","Irish setter","Gordon setter","Brittany spaniel","clumber","English springer","Welsh springer spaniel","cocker spaniel","Sussex spaniel","Irish water spaniel","kuvasz","schipperke","groenendael","malinois","briard","kelpie","komondor","Old English sheepdog","Shetland sheepdog","collie","Border collie","Bouvier des Flandres","Rottweiler","German shepherd","Doberman","miniature pinscher","Greater Swiss Mountain dog","Bernese mountain dog","Appenzeller","EntleBucher","boxer","bull mastiff","Tibetan mastiff","French bulldog","Great Dane","Saint Bernard","Eskimo dog","malamute","Siberian husky","dalmatian","affenpinscher","basenji","pug","Leonberg","Newfoundland","Great Pyrenees","Samoyed","Pomeranian","chow","keeshond","Brabancon griffon","Pembroke","Cardigan","toy poodle","miniature poodle","standard poodle","Mexican hairless","timber wolf","white wolf","red wolf","coyote","dingo","dhole","African hunting dog","hyena","red fox","kit fox","Arctic fox","grey fox","tabby","tiger cat","Persian cat","Siamese cat","Egyptian cat","cougar","lynx","leopard","snow leopard","jaguar","lion","tiger","cheetah","brown bear","American black bear","ice bear","sloth bear","mongoose","meerkat","tiger beetle","ladybug","ground beetle","long-horned beetle","leaf beetle","dung beetle","rhinoceros beetle","weevil","fly","bee","ant","grasshopper","cricket","walking stick","cockroach","mantis","cicada","leafhopper","lacewing","dragonfly","damselfly","admiral","ringlet","monarch","cabbage butterfly","sulphur butterfly","lycaenid","starfish","sea urchin","sea cucumber","wood rabbit","hare","Angora","hamster","porcupine","fox squirrel","marmot","beaver","guinea pig","sorrel","zebra","hog","wild boar","warthog","hippopotamus","ox","water buffalo","bison","ram","bighorn","ibex","hartebeest","impala","gazelle","Arabian camel","llama","weasel","mink","polecat","black-footed ferret","otter","skunk","badger","armadillo","three-toed sloth","orangutan","gorilla","chimpanzee","gibbon","siamang","guenon","patas","baboon","macaque","langur","colobus","proboscis monkey","marmoset","capuchin","howler monkey","titi","spider monkey","squirrel monkey","Madagascar cat","indri","Indian elephant","African elephant","lesser panda","giant panda","barracouta","eel","coho","rock beauty","anemone fish","sturgeon","gar","lionfish","puffer","abacus","abaya","academic gown","accordion","acoustic guitar","aircraft carrier","airliner","airship","altar","ambulance","amphibian","analog clock","apiary","apron","ashcan","assault rifle","backpack","bakery","balance beam","balloon","ballpoint","Band Aid","banjo","bannister","barbell","barber chair","barbershop","barn","barometer","barrel","barrow","baseball","basketball","bassinet","bassoon","bathing cap","bath towel","bathtub","beach wagon","beacon","beaker","bearskin","beer bottle","beer glass","bell cote","bib","bicycle-built-for-two","bikini","binder","binoculars","birdhouse","boathouse","bobsled","bolo tie","bonnet","bookcase","bookshop","bottlecap","bow","bow tie","brass","brassiere","breakwater","breastplate","broom","bucket","buckle","bulletproof vest","bullet train","butcher shop","cab","caldron","candle","cannon","canoe","can opener","cardigan","car mirror","carousel","carpenter's kit","carton","car wheel","cash machine","cassette","cassette player","castle","catamaran","CD player","cello","cellular telephone","chain","chainlink fence","chain mail","chain saw","chest","chiffonier","chime","china cabinet","Christmas stocking","church","cinema","cleaver","cliff dwelling","cloak","clog","cocktail shaker","coffee mug","coffeepot","coil","combination lock","computer keyboard","confectionery","container ship","convertible","corkscrew","cornet","cowboy boot","cowboy hat","cradle","crane","crash helmet","crate","crib","Crock Pot","croquet ball","crutch","cuirass","dam","desk","desktop computer","dial telephone","diaper","digital clock","digital watch","dining table","dishrag","dishwasher","disk brake","dock","dogsled","dome","doormat","drilling platform","drum","drumstick","dumbbell","Dutch oven","electric fan","electric guitar","electric locomotive","entertainment center","envelope","espresso maker","face powder","feather boa","file","fireboat","fire engine","fire screen","flagpole","flute","folding chair","football helmet","forklift","fountain","fountain pen","four-poster","freight car","French horn","frying pan","fur coat","garbage truck","gasmask","gas pump","goblet","go-kart","golf ball","golfcart","gondola","gong","gown","grand piano","greenhouse","grille","grocery store","guillotine","hair slide","hair spray","half track","hammer","hamper","hand blower","hand-held computer","handkerchief","hard disc","harmonica","harp","harvester","hatchet","holster","home theater","honeycomb","hook","hoopskirt","horizontal bar","horse cart","hourglass","iPod","iron","jack-o'-lantern","jean","jeep","jersey","jigsaw puzzle","jinrikisha","joystick","kimono","knee pad","knot","lab coat","ladle","lampshade","laptop","lawn mower","lens cap","letter opener","library","lifeboat","lighter","limousine","liner","lipstick","Loafer","lotion","loudspeaker","loupe","lumbermill","magnetic compass","mailbag","mailbox","maillot","maillot","manhole cover","maraca","marimba","mask","matchstick","maypole","maze","measuring cup","medicine chest","megalith","microphone","microwave","military uniform","milk can","minibus","miniskirt","minivan","missile","mitten","mixing bowl","mobile home","Model T","modem","monastery","monitor","moped","mortar","mortarboard","mosque","mosquito net","motor scooter","mountain bike","mountain tent","mouse","mousetrap","moving van","muzzle","nail","neck brace","necklace","nipple","notebook","obelisk","oboe","ocarina","odometer","oil filter","organ","oscilloscope","overskirt","oxcart","oxygen mask","packet","paddle","paddlewheel","padlock","paintbrush","pajama","palace","panpipe","paper towel","parachute","parallel bars","park bench","parking meter","passenger car","patio","pay-phone","pedestal","pencil box","pencil sharpener","perfume","Petri dish","photocopier","pick","pickelhaube","picket fence","pickup","pier","piggy bank","pill bottle","pillow","ping-pong ball","pinwheel","pirate","pitcher","plane","planetarium","plastic bag","plate rack","plow","plunger","Polaroid camera","pole","police van","poncho","pool table","pop bottle","pot","potter's wheel","power drill","prayer rug","printer","prison","projectile","projector","puck","punching bag","purse","quill","quilt","racer","racket","radiator","radio","radio telescope","rain barrel","recreational vehicle","reel","reflex camera","refrigerator","remote control","restaurant","revolver","rifle","rocking chair","rotisserie","rubber eraser","rugby ball","rule","running shoe","safe","safety pin","saltshaker","sandal","sarong","sax","scabbard","scale","school bus","schooner","scoreboard","screen","screw","screwdriver","seat belt","sewing machine","shield","shoe shop","shoji","shopping basket","shopping cart","shovel","shower cap","shower curtain","ski","ski mask","sleeping bag","slide rule","sliding door","slot","snorkel","snowmobile","snowplow","soap dispenser","soccer ball","sock","solar dish","sombrero","soup bowl","space bar","space heater","space shuttle","spatula","speedboat","spider web","spindle","sports car","spotlight","stage","steam locomotive","steel arch bridge","steel drum","stethoscope","stole","stone wall","stopwatch","stove","strainer","streetcar","stretcher","studio couch","stupa","submarine","suit","sundial","sunglass","sunglasses","sunscreen","suspension bridge","swab","sweatshirt","swimming trunks","swing","switch","syringe","table lamp","tank","tape player","teapot","teddy","television","tennis ball","thatch","theater curtain","thimble","thresher","throne","tile roof","toaster","tobacco shop","toilet seat","torch","totem pole","tow truck","toyshop","tractor","trailer truck","tray","trench coat","tricycle","trimaran","tripod","triumphal arch","trolleybus","trombone","tub","turnstile","typewriter keyboard","umbrella","unicycle","upright","vacuum","vase","vault","velvet","vending machine","vestment","viaduct","violin","volleyball","waffle iron","wall clock","wallet","wardrobe","warplane","washbasin","washer","water bottle","water jug","water tower","whiskey jug","whistle","wig","window screen","window shade","Windsor tie","wine bottle","wing","wok","wooden spoon","wool","worm fence","wreck","yawl","yurt","web site","comic book","crossword puzzle","street sign","traffic light","book jacket","menu","plate","guacamole","consomme","hot pot","trifle","ice cream","ice lolly","French loaf","bagel","pretzel","cheeseburger","hotdog","mashed potato","head cabbage","broccoli","cauliflower","zucchini","spaghetti squash","acorn squash","butternut squash","cucumber","artichoke","bell pepper","cardoon","mushroom","Granny Smith","strawberry","orange","lemon","fig","pineapple","banana","jackfruit","custard apple","pomegranate","hay","carbonara","chocolate sauce","dough","meat loaf","pizza","potpie","burrito","red wine","espresso","cup","eggnog","alp","bubble","cliff","coral reef","geyser","lakeside","promontory","sandbar","seashore","valley","volcano","ballplayer","groom","scuba diver","rapeseed","daisy","yellow lady's slipper","corn","acorn","hip","buckeye","coral fungus","agaric","gyromitra","stinkhorn","earthstar","hen-of-the-woods","bolete","ear","toilet tissue"};
     //            int maxPos = 0;
     //            float maxConfidence = 0;
     //            for (int i = 0; i < confidences.length; i++) {
     //                if (confidences[i] > maxConfidence) {
     //                    maxConfidence = confidences[i];
     //                    maxPos = i;
     //                }
     //            }
     //            Log.i(TAG, maxPos + " " +  maxConfidence );
     //            binding.foodQtyLabel.setText(labels[maxPos]);


     //GPU integration

     //            Interpreter.Options options = new Interpreter.Options();
     //            CompatibilityList compatList = new CompatibilityList();
     //
     //            if(compatList.isDelegateSupportedOnThisDevice()){
     //                // if the device has a supported GPU, add the GPU delegate
     //                GpuDelegate.Options delegateOptions = compatList.getBestOptionsForThisDevice();
     //                GpuDelegate gpuDelegate = new GpuDelegate(delegateOptions);
     //                options.addDelegate(gpuDelegate);
     //            } else {
     //                // if the GPU is not supported, run on 4 threads
     //                options.setNumThreads(4);
     //            }
     //
     //            Interpreter interpreter = new Interpreter(model, options);
     //            interpreter.run(inputFeature0, outputFeature0);

     // Releases model resources if no longer used.
     model.close();
     } catch (IOException e) {
     // TODO Handle the exception
     }

     }*/

}
package com.karigor.tolet_seeker.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karigor.tolet_seeker.R;
import com.karigor.tolet_seeker.adapter.ImageSliderAdapter;
import com.karigor.tolet_seeker.adapter.PostDetailsAttributeAdapter;
import com.karigor.tolet_seeker.data.model.HouseModel;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostDetailsActivity extends AppCompatActivity {

    private ArrayList<Pair<String ,String>> houseAttributeArrayList;

    private int switch_attribute_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        HouseModel houseModel = (HouseModel) getIntent().getSerializableExtra("houseModel");
        initSlider();
        setUpList(houseModel);
        initRecycler();

    }

    private void initRecycler() {

        RecyclerView recyclerView = findViewById(R.id.attribute_recycler);
        PostDetailsAttributeAdapter postDetailsAttributeAdapter = new
                PostDetailsAttributeAdapter(this,houseAttributeArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(postDetailsAttributeAdapter);
    }

    private void initSlider(){

        SliderView sliderView = findViewById(R.id.imageSlider);
        ImageSliderAdapter adapter = new ImageSliderAdapter(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

    }


    private <T> List<Field> getFields(T t) {
        List<Field> fields = new ArrayList<>();
        Class clazz = t.getClass();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }


    private void setUpList(HouseModel houseModel) {

        // houseSwitchAttributeArrayList = new ArrayList<>();
        //houseTextAttributeArrayList = new ArrayList<>();
        houseAttributeArrayList = new ArrayList<>();

        List<Field> fields = getFields(houseModel);

        for(Field attribute : fields){

            attribute.setAccessible(true);
            try {
                Pair<String,String> pair = new Pair<>(attribute.getName(),attribute.get(houseModel)+"");
                houseAttributeArrayList.add(pair);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }
}

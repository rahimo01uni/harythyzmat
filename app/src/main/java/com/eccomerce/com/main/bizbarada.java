package com.eccomerce.com.main;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eccomerce.com.R;

public class bizbarada extends AppCompatActivity {
    TextView textView;
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dushin_activity);
        textView = findViewById(R.id.dushindir);

        textView.setText("#О НАС \n\n\n" +
                "РАДЫ ПРИВЕТСВОВАТЬ ВАС!\n\n\n" +
                "На интернет торговой площадке ''harythyzmat’’ осуществляется выставления различного рода товара и рекламирования разного рода услуг. \n" +
                "\n\n" +
                "Наша цель\n\n" +
                "Наша важнейшая цель обиспечить физическим  и юридическим лицам удобное и современное решение доступа ко всем магазинам и продавцам страны для более удобной навигации между ними.\n\n\n" +
                "Продажа и выставление товаров и услуг на торговой площадке ‘’harythyzmat’’ позволяет предпринимателям и предприятиям предоставить удобный доступ клиентам без владения веб- страниц в качестве каталога товаров. \n");
imageView=findViewById(R.id.imageMain);
imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        onBackPressed();
    }
});
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //   mDemoSlider.removeAllViews();
        MainActivity.s1.sendEmptyMessage(1);
    }
}

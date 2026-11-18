package com.example.lab8bun;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImaginiActivity extends AppCompatActivity {
    private List<ImaginiDomeniu> lista;
    private List<Bitmap> imagini=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_imagini);
        List<String> linkuriImagini=new ArrayList<>();
        linkuriImagini.add("https://upload.wikimedia.org/wikipedia/commons/7/73/Lion_waiting_in_Namibia.jpg?utm_source=ro.wikipedia.org&utm_campaign=imageinfo&utm_content=thumbnail_unscaled");
        linkuriImagini.add("https://thumb.wikimedia.org/wikipedia/commons/thumb/9/9b/Afrikanischer_Elefant%2C_Zoo_Miami_1.jpg/1280px-Afrikanischer_Elefant%2C_Zoo_Miami_1.jpg?utm_source=ro.wikipedia.org&utm_campaign=imageinfo&utm_content=thumbnail");
        linkuriImagini.add("https://ro.wikipedia.org/wiki/Giraf%C4%83#/media/Fi%C8%99ier:Giraffe_Mikumi_National_Park.jpg");
        linkuriImagini.add("https://en.wikipedia.org/wiki/Dinosaur#/media/File:Tyrannosaurus_Rex_Holotype.jpg");
        linkuriImagini.add("https://en.wikipedia.org/wiki/Monkey#/media/File:Ubud_Monkey_Family.jpg");
        Executor executor= Executors.newSingleThreadExecutor();
        Handler handler=new Handler(Looper.myLooper());

        executor.execute(new Runnable(){
            public void run(){
                for (String link : linkuriImagini) {
                    HttpURLConnection con = null;
                    try {
                        URL url = new URL(link);
                        con = (HttpURLConnection) url.openConnection();
                        InputStream is = con.getInputStream();
                        imagini.add(BitmapFactory.decodeStream(is));
                    } catch (Exception e) {
                        e.printStackTrace();
                        imagini.add(null);
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        lista=new ArrayList<>();
                        lista.add(new ImaginiDomeniu("Leu",imagini.get(0),"https://upload.wikimedia.org/wikipedia/commons/7/73/Lion_waiting_in_Namibia.jpg?utm_source=ro.wikipedia.org&utm_campaign=imageinfo&utm_content=thumbnail_unscaled"));
                        lista.add(new ImaginiDomeniu("elefant",imagini.get(1),"https://thumb.wikimedia.org/wikipedia/commons/thumb/9/9b/Afrikanischer_Elefant%2C_Zoo_Miami_1.jpg/1280px-Afrikanischer_Elefant%2C_Zoo_Miami_1.jpg?utm_source=ro.wikipedia.org&utm_campaign=imageinfo&utm_content=thumbnail"));
                        lista.add(new ImaginiDomeniu("Girafa",imagini.get(2),"https://ro.wikipedia.org/wiki/Giraf%C4%83#/media/Fi%C8%99ier:Giraffe_Mikumi_National_Park.jpg"));
                        lista.add(new ImaginiDomeniu("trex",imagini.get(3),"https://en.wikipedia.org/wiki/Dinosaur#/media/File:Tyrannosaurus_Rex_Holotype.jpg"));
                        lista.add(new ImaginiDomeniu("maimuta",imagini.get(4),"https://en.wikipedia.org/wiki/Monkey#/media/File:Ubud_Monkey_Family.jpg"));
                    }
                });
            }
        });
        ListView lv = findViewById(R.id.imagini);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getApplicationContext(), webViewActivity.class);
                it.putExtra("link", lista.get(i).getLink());
                startActivity(it);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
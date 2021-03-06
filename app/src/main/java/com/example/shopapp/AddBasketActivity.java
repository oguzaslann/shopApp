package com.example.shopapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddBasketActivity extends AppCompatActivity {
    EditText name, orderLocName, orderLoc, orderDate, note;
    Button changeDateButton, imageButton, senderButton;
    ImageView imageView;
    Uri imageUri;
    static final int SELECT_IMAGE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_basket);

        defineVeriables();

        changeDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int yearC = calendar.get(Calendar.YEAR);
                int mounthC = calendar.get(Calendar.MONTH);
                int dayC = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(AddBasketActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                month += 1;
                                orderDate.setText(dayOfMonth + "/" + month + "/" + year);
                            }
                        }, yearC, mounthC, dayC);

                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", dpd);
                dpd.show();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageUploadIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imageUploadIntent.setType("image/*");
                startActivityForResult(imageUploadIntent, SELECT_IMAGE);
            }
        });

        senderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    /*
                int a = 0;
                if(imageUri == null)
                    a = 10;

                Toast.makeText(getApplicationContext(), String.valueOf(a),Toast.LENGTH_SHORT).show();
                    */
                try {
                    Basket b = new Basket();
                    b.setName(name.getText().toString().trim());
                    b.setOrderDate(orderDate.getText().toString());
                    b.setOrderLocName(orderLocName.getText().toString().trim());
                    b.setOrderLoc(orderLoc.getText().toString().trim());
                    b.setNote(note.getText().toString().trim());
                    if(imageUri != null)
                       b.setImgPath(imageUri.getPath());

                    SqlLiteHelperBasket sqlLiteHelperBasket = new SqlLiteHelperBasket(AddBasketActivity.this);
                    boolean addBasket = sqlLiteHelperBasket.AddBasket(b);

                    if(!addBasket)
                        Toast.makeText(getApplicationContext(), "Beklenmeyen bir hata oluştu.",Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getApplicationContext(), "Kayıt başarılı.", Toast.LENGTH_SHORT).show();
                    Intent basketsIntent = new Intent(AddBasketActivity.this, BasketsActivity.class);
                    AddBasketActivity.this.startActivity((basketsIntent));
                    }
                } catch (Exception ex){
                    Log.i("SENDERBUTTON!", ex.toString());
                }
            }
        });

    }

    private void defineVeriables() {
        name = findViewById(R.id.name);
        orderDate = findViewById(R.id.orderDate);
        orderLocName = findViewById(R.id.orderLocName);
        orderLoc = findViewById(R.id.orderLoc);
        note = findViewById(R.id.note);
        changeDateButton = findViewById(R.id.changeDateButton);
        imageButton = findViewById(R.id.imageButton);
        imageView = findViewById(R.id.imageView);
        senderButton = findViewById(R.id.senderButton);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

}
package com.example.qrcodescannerandgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ACTION_PICK extends AppCompatActivity {

    private TextView scannedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_pick);
        scannedData = findViewById(R.id.idTVScannedData2);
    }

    public void BtnBrowse(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1000);
    }
    @Override

    protected void onActivityResult(int reqCode, int resultCode, Intent data)
    {

        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {

            try {

                final Uri imageUri = data.getData();

                final InputStream imageStream = getContentResolver().openInputStream(imageUri);

                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                try
                {

                    Bitmap bMap = selectedImage;

                    String contents = null;

                    int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];

                    bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

                    LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);

                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                    Reader reader = new MultiFormatReader();

                    Result result = reader.decode(bitmap);

                    contents = result.getText();

                    scannedData.setText(contents);

                }
                catch (Exception e)
                {

                    e.printStackTrace();

                }

                //  image_view.setImageBitmap(selectedImage);

            }
            catch (FileNotFoundException e)
            {

                e.printStackTrace();

                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();

            }


        }
        else
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
    }
}
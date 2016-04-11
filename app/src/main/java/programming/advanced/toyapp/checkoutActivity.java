package programming.advanced.toyapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class checkoutActivity extends AppCompatActivity {

    ArrayList<Toy> cart = new ArrayList<Toy>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkoutscreen);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            cart = (ArrayList<Toy>) extras.get("shoppingCart");
        }
    }

    public void goTo (View view) {
        goToUrl("http://georgetown.edu");
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public class Toy
    {
        private String toyName=null;
        private Bitmap toyImage=null;
        private int toyPrice=0;

        public Toy(byte[] toyByteArray)
        {
            ByteBuffer toyBuffer=ByteBuffer.wrap(toyByteArray);
            int nameLength=toyBuffer.getInt();
            byte[] namebuffer=new byte[nameLength];
            toyBuffer.get(namebuffer, 0, nameLength);
            this.toyName=new String(namebuffer);

            this.toyPrice=toyBuffer.getInt();

            int imageLength=toyBuffer.getInt();
            Bitmap toyBitMap=null;
            final byte[] imagebuffer=new byte[imageLength];
            toyBuffer.get(imagebuffer, 0, imageLength);
            toyImage= BitmapFactory.decodeByteArray(imagebuffer, 0, imagebuffer.length);
        }

        public Toy(String name, int price) {
            toyName = name;
            toyPrice = price;
        }
    }
}
package programming.advanced.toyapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class searchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchscreen);
        Intent intent=getIntent();
        new getToyData().execute();
    }

    String[] toys;

    public void checkOut(View view)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(searchActivity.this, checkoutActivity.class);
            //    intent.putExtra("cart", );
                searchActivity.this.startActivity(intent);
            }
        });
    }

    public void cancel(View view)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(searchActivity.this, homeActivity.class);
                searchActivity.this.startActivity(intent);
            }
        });
    }
    public class getToyData extends AsyncTask<String, Byte, Bitmap>
    {
        @Override
        protected Bitmap doInBackground(String... params)
        {
            URL toyURL=null;

            try
            {
                toyURL=new URL("http://people.cs.georgetown.edu/~wzhou/toy.data");
            }

            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }

            HttpURLConnection toyConnection=null;

            try
            {
                toyConnection=(HttpURLConnection)toyURL.openConnection();
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }

            InputStream in=null;

            try
            {
                if (toyConnection!=null)
                {
                    in=new BufferedInputStream(toyConnection.getInputStream());
                    //I am getting an error here for some reason and the program is crashing because of it
                }
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                if (in!=null)
                {
                    int byteLength = toyConnection.getContentLength();
                    byte[] toyByte = new byte[byteLength];
                    int cursor = 0;
                    while (cursor < toyConnection.getContentLength())
                    {
                        int readCursor = in.read(toyByte, cursor, byteLength - cursor);
                        cursor += readCursor;
                    }

                    makeToys(toyByte, byteLength);
                }
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }

            toyConnection.disconnect();

            return null;
        }
    }

    public void makeToys(byte[] toyByte, int byteLength)
    {
        ToyList toyList = new ToyList(toyByte, byteLength);
        int getToys = 0;
        int toyListSize = toyList.ToyList.size();
        while (getToys!=toyListSize)
        {
            ImageView toyImage = new ImageView(null);
            TextView toyName = new TextView(null);
            toyImage.setImageBitmap(toyList.ToyList.get(getToys).toyImage);
            toyName.append(toyList.ToyList.get(getToys).toyName);
            setContentView(toyImage);
            //Get the toy price later
            getToys++;
        }
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
    }

    public class ToyList
    {
        private ArrayList<Toy> ToyList=new ArrayList<Toy>();

        ToyList(byte[] toyListArray, int length)
        {
            ByteBuffer buffer=ByteBuffer.wrap(toyListArray);
            int cursor=0;
            while(cursor<length)
            {
                int toylength=buffer.getInt();
                byte[] toyBuffer= new byte[toylength];
                buffer.get(toyBuffer, 0, toylength);
                Toy toy=new Toy(toyBuffer);
                ToyList.add(toy);
                cursor+=Integer.SIZE/8+toylength;
            }
        }
    }
}

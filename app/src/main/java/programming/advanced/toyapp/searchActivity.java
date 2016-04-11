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

    public String searchQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchscreen);
        Intent intent=getIntent();
        Bundle p = getIntent().getExtras();
        searchQuery = p.getString("searchQuery");
        new getToyData().execute();
    }

    byte[] toyByte=null;
    int byteLength=0;

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
                    byteLength=toyConnection.getContentLength();
                    toyByte=new byte[byteLength];
                    int cursor=0;
                    while(cursor<toyConnection.getContentLength())
                    {
                        int readCursor=in.read(toyByte, cursor, byteLength-cursor);
                        cursor+=readCursor;
                    }
                }

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        makeToys(toyByte, byteLength);
                    }
                });
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                in.close();
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
        ToyList toys=new ToyList(toyByte, byteLength);

        if(toys.ToyList.get(0).toyName.toLowerCase().contains(searchQuery.toLowerCase())) {
            ImageView view = (ImageView) findViewById(R.id.toyImages);
            view.setImageBitmap(toys.ToyList.get(0).toyImage);
            view.setVisibility(view.VISIBLE);
            TextView viewT = (TextView) findViewById(R.id.toyName);
            viewT.setText(toys.ToyList.get(0).toyName);
            TextView viewP = (TextView) findViewById(R.id.toyPrice);
            viewP.setText(Integer.toString(toys.ToyList.get(0).toyPrice));
        }
        //Toy1

        if(toys.ToyList.get(1).toyName.toLowerCase().contains(searchQuery.toLowerCase())) {
            ImageView view2 = (ImageView) findViewById(R.id.toyImages2);
            view2.setImageBitmap(toys.ToyList.get(1).toyImage);
            view2.setVisibility(view2.VISIBLE);
            TextView viewT2 = (TextView) findViewById(R.id.toyName2);
            viewT2.setText(toys.ToyList.get(1).toyName);
            TextView viewP2 = (TextView) findViewById(R.id.toyPrice2);
            viewP2.setText(Integer.toString(toys.ToyList.get(1).toyPrice));
        }
        //Toy2
        if(toys.ToyList.get(2).toyName.toLowerCase().contains(searchQuery.toLowerCase())){
            ImageView view3 = (ImageView) findViewById(R.id.toyImages3);
            view3.setImageBitmap(toys.ToyList.get(2).toyImage);
            view3.setVisibility(view3.VISIBLE);
            TextView viewT3 = (TextView) findViewById(R.id.toyName3);
            viewT3.setText(toys.ToyList.get(2).toyName);
            TextView viewP3 = (TextView) findViewById(R.id.toyPrice3);
            viewP3.setText(Integer.toString(toys.ToyList.get(2).toyPrice));
            //Toy3
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

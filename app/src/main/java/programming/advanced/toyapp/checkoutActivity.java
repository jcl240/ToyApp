package programming.advanced.toyapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class checkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkoutscreen);
    }

    public void goTo (View view) {
        goToUrl("http://georgetown.edu");
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

//    public void makeToys()
//    {
//        searchActivity.ToyList toys;
//
//        if(toys.ToyList.get(0).toyName) {
//            ImageView view = (ImageView) findViewById(R.id.checkouttoyImages);
//            view.setImageBitmap(toys.ToyList.get(0).toyImage);
//            view.setVisibility(view.VISIBLE);
//            TextView viewT = (TextView) findViewById(R.id.checkouttoyName);
//            viewT.setText(toys.ToyList.get(0).toyName);
//            TextView viewP = (TextView) findViewById(R.id.checkouttoyPrice);
//            viewP.setText(Integer.toString(toys.ToyList.get(0).toyPrice));
//        }
//        //Toy1
//
//        if(toys.ToyList.get(1).toyName.toLowerCase().contains(searchQuery.toLowerCase())) {
//            ImageView view2 = (ImageView) findViewById(R.id.checkouttoyImages2);
//            view2.setImageBitmap(toys.ToyList.get(1).toyImage);
//            view2.setVisibility(view2.VISIBLE);
//            TextView viewT2 = (TextView) findViewById(R.id.checkouttoyName2);
//            viewT2.setText(toys.ToyList.get(1).toyName);
//            TextView viewP2 = (TextView) findViewById(R.id.checkouttoyPrice2);
//            viewP2.setText(Integer.toString(toys.ToyList.get(1).toyPrice));
//        }
//        //Toy2
//        if(toys.ToyList.get(2).toyName.toLowerCase().contains(searchQuery.toLowerCase())){
//            ImageView view3 = (ImageView) findViewById(R.id.checkouttoyImages3);
//            view3.setImageBitmap(toys.ToyList.get(2).toyImage);
//            view3.setVisibility(view3.VISIBLE);
//            TextView viewT3 = (TextView) findViewById(R.id.checkouttoyName3);
//            viewT3.setText(toys.ToyList.get(2).toyName);
//            TextView viewP3 = (TextView) findViewById(R.id.checkouttoyPrice3);
//            viewP3.setText(Integer.toString(toys.ToyList.get(2).toyPrice));
//            //Toy3
//        }
 //   }
}

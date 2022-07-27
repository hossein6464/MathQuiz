package diana.soleil.android1finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import diana.soleil.android1finalproject.utilities.Constant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {
    CardView kidsCardView,adultsCardView,advancedCardView,geniusCardView;
    ImageView imageView;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    private  void  initialize() {
        imageView = findViewById(R.id.imageKids);

        kidsCardView = findViewById(R.id.kidsCardView);

        adultsCardView = findViewById(R.id.adultsCardView);

        advancedCardView = findViewById(R.id.advancedCardView);

        geniusCardView = findViewById(R.id.geniusCardView);


    }

    public void runCardView(View view) {
        intent = new Intent(StartActivity.this, MainActivity.class);
        System.out.println("GoClicked");
        switch (view.getTag().toString()) {
            case Constant.kids:
                intent.putExtra(Constant.difficulty, Constant.kids);

                break;
            case Constant.adults:
                intent.putExtra(Constant.difficulty, Constant.adults);
                System.out.println("GoClicked");
                break;
            case Constant.advanced:
                intent.putExtra(Constant.difficulty, Constant.advanced);
                System.out.println("GoClicked");
                break;
            case Constant.genius:
                intent.putExtra(Constant.difficulty, Constant.genius);
                System.out.println("GoClicked");
                break;
        }
        startActivity(intent);
    }
}
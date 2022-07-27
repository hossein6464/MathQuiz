package diana.soleil.android1finalproject;

import androidx.appcompat.app.AppCompatActivity;
import diana.soleil.android1finalproject.model.Score;
import diana.soleil.android1finalproject.model.SortByScore;
import diana.soleil.android1finalproject.utilities.Constant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ListViewActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView scoreListView;
    private EditText usernameEditText;
    private TextView scorePercentageTextView, title;
    private Button backButton, sortBtn;
    private ArrayAdapter<Score> scoreAdapter;
    private ArrayList<Score> scores;
    private String valueOfScoreTotal;
    private String difficultyTransfered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_acitivty);
        initialize();
    }

    private void initialize (){
        title = findViewById(R.id.scoreListTitleTextView);
        title.setOnClickListener(this);
        sortBtn = findViewById(R.id.sortBtn);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("scoreBundle");
        String scoreTotal = intent.getStringExtra("percentageScore");
        difficultyTransfered = intent.getStringExtra(Constant.difficulty);
        Serializable bundledListOfScores = bundle.getSerializable("scoresArray");

        scores = (ArrayList<Score>) bundledListOfScores;
        scoreListView = findViewById(R.id.listViewOfScores);
        usernameEditText = findViewById(R.id.userName);
        scorePercentageTextView = findViewById(R.id.scorePercentageTextView);
        valueOfScoreTotal = scoreTotal + "%";
        scorePercentageTextView.setText(valueOfScoreTotal);
        backButton = findViewById(R.id.goBackButton);
        scoreAdapter = new ArrayAdapter<Score>(this,
                android.R.layout.simple_list_item_1,
                scores);
        scoreListView.setAdapter(scoreAdapter);
    }
    public void backButtonToCall(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        String nameOfTheUser = usernameEditText.getText().toString();
        intent.putExtra("name",nameOfTheUser);
        intent.putExtra("score",valueOfScoreTotal);
        intent.putExtra(Constant.difficulty, difficultyTransfered);
        setResult(RESULT_OK,intent);
        finish();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.scoreListTitleTextView) {
        }
    }
    public void sortArray(View view) {
       Collections.sort(scores, new SortByScore());
       scoreAdapter.notifyDataSetChanged();
    }
}
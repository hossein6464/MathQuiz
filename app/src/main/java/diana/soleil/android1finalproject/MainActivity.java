package diana.soleil.android1finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import diana.soleil.android1finalproject.model.Score;
import diana.soleil.android1finalproject.utilities.Constant;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    int[] resourceIds = new int[] {R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,
                                        R.id.btn9,R.id.btnPoint,R.id.btnDash,
                                            R.id.btnGenerate,R.id.btnValidate,R.id.btnClear,
                                                R.id.btnScore,R.id.btnFinish};
    int kidsRandomNumber, adultsRandomNumber, advancedRandomNumber, geniusRandomNumber;
    String difficulty;
    float randomNumberOne, randomNumberTwo;
    float answerToGeneratedQuestion;
    int randomOperation;
    String questionToBeDisplayed;
    ArrayList<Score> scores;
    AlertDialog.Builder builder;
    StringBuilder userInputBuilder;
    Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnPoint,btnDash, btnGenerate,btnValidate,btnClear,btnScore,btnFinish;
    Button[] buttons = new Button[]{btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
                                     btnPoint,btnDash,
                                      btnGenerate,btnValidate,btnClear,
                                         btnScore,btnFinish};
    LinearLayout mainLinearLayout;
    TextView titleTextView, questionGeneratedTextView, scoreTotalTextView, timerTextView;
    EditText userInputEditText;
    CountDownTimer countTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }
    private void initialize() {
        mainLinearLayout = findViewById(R.id.linearLayoutMainActivityHolder);
        timerTextView = findViewById(R.id.timerTextView);
        titleTextView = findViewById(R.id.mainTitleTextView);
        userInputEditText = findViewById(R.id.userInputEditTextView);
        questionGeneratedTextView = findViewById(R.id.questionGeneratedTextView);
        scoreTotalTextView = findViewById(R.id.scoreTotalTextView);
        Intent intent = getIntent();
        difficulty = intent.getStringExtra(Constant.difficulty);
        if (difficulty.equals(Constant.genius)) {
            timerSetupForGeniuses();
        }
        scores = new ArrayList<>();
        for(int i=0;i<buttons.length;i++)
        {
            buttons[i] = findViewById(resourceIds[i]);
            buttons[i].setOnClickListener(this);
        }
        buttons[13].setClickable(false);
        buttons[13].setBackgroundColor(getResources().getColor(R.color.black));
        buttons[13].setTextColor(getResources().getColor(R.color.white));
    }

    private int generateRandomNumber(String difficultyLevel) {
        Random rn = new Random();
        if (difficultyLevel.equals(Constant.kids)) {
            return rn.nextInt(Constant.kidsRandomNumber) + 1;
        } else if (difficultyLevel.equals(Constant.adults)) {
            return rn.nextInt(Constant.adultsRandomNumber) + 1;
        } else if (difficultyLevel.equals(Constant.advanced)) {
            return rn.nextInt(Constant.advancedRandomNumber) + 1;
        }
        return rn.nextInt(Constant.geniusesRandomNumber) + 1;
    }
    private int generateRandomOperation( String difficultyLevel) {
        Random rn = new Random();
        if (difficultyLevel.equals(Constant.kids)) {
            return rn.nextInt(Constant.operationKidRandomNumber) + 1;
        } else if (difficultyLevel.equals(Constant.adults)) {
            return rn.nextInt(Constant.operationAdultsRandomNumber) + 1;
        }  else if (difficultyLevel.equals(Constant.advanced)) {
            return rn.nextInt(Constant.operationAdvancedRandomNumber) + 1;
        }
            return rn.nextInt(Constant.operationGeniusesRandomNumber) + 1;
    }

    @Override
    public void onClick(View v) {
        userInputBuilder = new StringBuilder();
        userInputBuilder.append(userInputEditText.getText().toString());
        if (v.getId() == R.id.btn0 || v.getId() == R.id.btn1 || v.getId() == R.id.btn2 || v.getId() == R.id.btn3
                || v.getId() == R.id.btn4 || v.getId() == R.id.btn5 || v.getId() == R.id.btn6 || v.getId() == R.id.btn7
                || v.getId() == R.id.btn8 || v.getId() == R.id.btn9|| v.getId() == R.id.btnDash || v.getId() == R.id.btnPoint  ) {
            if (userInputBuilder.toString().equals("") &&  v.getId() == R.id.btnDash) {
                userInputBuilder.append(v.getTag());
                userInputEditText.setText(userInputBuilder);
            } else if (!userInputBuilder.toString().contains(".") &&  v.getId() == R.id.btnPoint) {
                if (userInputBuilder.toString().equals("")) {
                    userInputBuilder.append("0");
                }
                userInputBuilder.append(v.getTag());
                userInputEditText.setText(userInputBuilder);
            } else if (v.getId() != R.id.btnPoint && v.getId() != R.id.btnDash) {
                userInputBuilder.append(v.getTag());
                userInputEditText.setText(userInputBuilder);
            }

        } else if (v.getId() == R.id.btnFinish) {
            countTimer.cancel();
            finish();
        } else if (v.getId() == R.id.btnGenerate) {
            generateQuestion();
        } else if (v.getId() == R.id.btnClear) {
            clearText();
        } else if (v.getId() == R.id.btnScore) {
            moveToScoreBoard();
        } else if (v.getId() == R.id.btnValidate && !userInputEditText.getText().toString().equals("")) {
            questionGeneratedTextView.setText("");
            boolean userAnswerChecker = true;
            buttons[13].setClickable(false);
            buttons[13].setBackgroundColor(getResources().getColor(R.color.black));
            buttons[13].setTextColor(getResources().getColor(R.color.white));
           float userInputFloat = Float.parseFloat(userInputEditText.getText().toString());
            System.out.println("User Input: " + userInputFloat + "AnswerGenerated: " + answerToGeneratedQuestion);
            if (validateAnswer(userInputFloat, answerToGeneratedQuestion)) {
                Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                userAnswerChecker = false;
            }
            System.out.println("Question Display: "+ questionToBeDisplayed + "Right Answer:" +answerToGeneratedQuestion+" User Input was: "+userInputFloat);
            Score score = new Score(questionToBeDisplayed, answerToGeneratedQuestion,userInputFloat,userAnswerChecker);
            scores.add(score);
            clearText();
        }
    }
    private void generateQuestion() {
        buttons[13].setClickable(true);
        buttons[13].setBackgroundColor(getResources().getColor(R.color.holo_green_dark));
        buttons[13].setTextColor(getResources().getColor(R.color.yellow));
        randomNumberOne = generateRandomNumber(difficulty);
        randomNumberTwo = generateRandomNumber(difficulty);
        randomOperation = generateRandomOperation(difficulty);
        System.out.println(randomOperation);
        switch (randomOperation) {
            case 1:
                questionToBeDisplayed = (int) randomNumberOne + " - " + (int)randomNumberTwo;
                answerToGeneratedQuestion = randomNumberOne - randomNumberTwo;
                break;
            case 2:
                questionToBeDisplayed = (int) randomNumberOne + " + " + (int)randomNumberTwo;
                answerToGeneratedQuestion = randomNumberOne + randomNumberTwo;
                break;
            case 3:
                questionToBeDisplayed = (int) randomNumberOne + " * " + (int)randomNumberTwo;
                answerToGeneratedQuestion = randomNumberOne * randomNumberTwo;
                break;
            case 4:
                questionToBeDisplayed = (int) randomNumberOne + " / " + (int)randomNumberTwo;
                answerToGeneratedQuestion = randomNumberOne / randomNumberTwo;
                break;
            case 5:
                questionToBeDisplayed = (int) randomNumberOne + " % " + (int)randomNumberTwo;
                answerToGeneratedQuestion = randomNumberOne % randomNumberTwo;
                break;
        }
        questionGeneratedTextView.setText(questionToBeDisplayed);
    }

    private float calculateTheScore(ArrayList<Score> scores) {
        float totalQuestions = 0;
        float counter = 0;
        for (Score score: scores) {
            totalQuestions++;
            if (score.getScore()) {
                counter++;
            }
        }
        return (int)((counter / totalQuestions) * 100);
    }
    private void clearText(){
        userInputEditText.setText("");
        userInputBuilder = new StringBuilder();
    }
    private boolean validateAnswer(float userInput, float answerGenerated){
        return userInput == answerGenerated;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                assert data != null;
                String username2 = data.getStringExtra("name");
                String scoreTotal =" Score: " +  data.getStringExtra("score");
                String difficultyForGeinuses = data.getStringExtra(Constant.difficulty);
                System.out.println(username2 + " Here " + scoreTotal);
                scoreTotalTextView.setVisibility(View.VISIBLE);
                scoreTotalTextView.setText(scoreTotal);
                if (!username2.equals("")) {
                    titleTextView.setText(username2);
                } else {
                    titleTextView.setText("Math Quiz");
                }
                if (difficultyForGeinuses.equals(Constant.genius)) {
                    createForRestart(username2);
                }
            }
        }
    }
    private void restartTheGame() {
        mainLinearLayout.setVisibility(View.GONE);

    }
    public void timerSetupForGeniuses () {
      countTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerTextView.setVisibility(View.VISIBLE);
                timerTextView.setText(millisUntilFinished / 1000+"s");
                buttons[15].setClickable(false);
            }

            public void onFinish() {
                mainLinearLayout.setVisibility(View.GONE);
                createAlertDialog();
            }
        }.start();
    }
    public void createAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Game Over!");
        alertDialogBuilder.setMessage(" This game is over. Do you want to start a new game or go to scoreboard?");
        alertDialogBuilder.setNegativeButton("Scoreboard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveToScoreBoard();
            }
        }).setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mainLinearLayout.setVisibility(View.VISIBLE);
                timerSetupForGeniuses();
            }
        });
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }

    public void createForRestart( String name) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Welcome Back " + name);
        alertDialogBuilder.setMessage(" This game is over. Do you want to start a new game or go to the Main Menu?");
        alertDialogBuilder.setNegativeButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mainLinearLayout.setVisibility(View.VISIBLE);
                timerTextView.setVisibility(View.VISIBLE);
                timerSetupForGeniuses();
                clearText();
            }
        }).setPositiveButton("Main Menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              finish();
            }
        });
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }

    private void moveToScoreBoard () {
        Bundle bundle = new Bundle();
        bundle.putSerializable("scoresArray",scores);
        Intent intentToScore = new Intent(MainActivity.this,ListViewActivity.class);
        String floatToStringForTransfer = String.valueOf(calculateTheScore(scores));
        intentToScore.putExtra("scoreBundle", bundle);
        intentToScore.putExtra("percentageScore", floatToStringForTransfer);
        intentToScore.putExtra(Constant.difficulty, difficulty);
        StartActivityForResult(intentToScore,1);
    }

    private void StartActivityForResult(Intent intentToScore, int i) {
    }
}
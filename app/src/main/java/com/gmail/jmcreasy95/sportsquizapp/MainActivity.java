package com.gmail.jmcreasy95.sportsquizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private TextView totalQuestionsLabel, questionsLabel;
    private Button answer01, answer02, answer03, answer04;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZCOUNT = 5;

    ArrayList <ArrayList<String>> quizArray = new ArrayList <>();

    String quizData[][] = {
            //{"Team","Correct Answer","Answer 1", "Answer 2", "Answer 3"}
            {"Diamondbacks", "Randy Johnson", "Luiz Gonzalez", "Paul Goldschmidt", "Curt Schilling"},
            {"Braves", "Chipper Jones", "Greg Maddux", "Dale Murphy", "Freddie Freeman"},
            {"Orioles", "Cal Ripken Jr.", "Manny Machado", "Eddie Murray", "Jim Palmer"},
            {"Red Sox", "Roger Clemens", "David Ortiz", "Dustin Pedroia", "Carl Yastremski"},
            {"White Sox", "Frank Thomas", "Mark Buerhle", "Paul Konerko", "Harold Baines"},
            {"Cubs", "Ryne Sandberg", "Sammy Sosa", "Ron Santo", "Greg Maddux"},
            {"Reds", "Johnny Bench", "Joe Morgan", "Pete Rose", "Joey Votto"},
            {"Indians", "Kenny Lofton", "Francisco Lindor", "Cliff Lee", "Ricky Vaughn"},
            {"Rockies", "Todd Helton", "Larry Walker", "Nolan Arenado", "Troy Tulowitzki"},
            {"Tigers", "Lou Whitaker", "Alan Trammell", "Justin Verlander", "Miguel Cabrera"},
            {"Astros", "Jeff Bagwell", "Craig Biggio", "Jose Altuve", "Roy Oswalt"},
            {"Angels", "Mike Trout", "Tim Salmon", "Nolan Ryan", "Garrett Anderson"},
            {"Dodgers", "Clayton Kershaw", "Steve Garvey", "Orel Hershiser", "Matt Kemp"},
            {"Marlins", "Giancarlo Stanton", "Gary Sheffield", "Dontrelle Willis", "Josh Beckett"},
            {"Brewers", "Robin Yount", "Paul Molitor", "Ryan Braun", "Cecil Cooper"},
            {"Twins", "Rod Carew", "Joe Mauer", "Bert Blyleven", "Johan Santana"},
            {"Yankees", "Derek Jeter", "Mariano Rivera", "Andy Pettite", "Jorge Posada"},
            {"Mets", "Tom Seaver", "David Wright", "Jose Reyes", "Dwight Gooden"},
            {"Athletics", "Rickey Henderson", "Dennis Eckersley", "Reggie Jackson", "Vida Blue"},
            {"Phillies", "Mike Schmidt", "Jimmy Rollins", "Steve Carlton", "Ryan Howard"},
            {"Pirates", "Barry Bonds", "Andrew McCutchen", "Willie Stargell", "Jason Kendall"},
            {"Padres", "Tony Gwynn", "Trevor Hoffman", "Ken Caminitti", "Dave Winfield"},
            {"Giants", "Barry Bonds", "Buster Posey", "Madison Bumgardner", "Jeff Kent"},
            {"Mariners", "Ken Griffey Jr.", "Ichiro Suzuki", "Edgar Martinez", "Randy Johnson"},
            {"Cardinals", "Albert Pujols", "Ozzie Smith", "Yadier Molina", "Chris Carpenter"},
            {"Rays", "Evan Longoria", "Ben Zobrist", "David Price", "James Shields"},
            {"Rangers", "Ivan Rodriguez", "Juan Gonzalez", "Rafael Palmeiro", "Joey Gallo"},
            {"Blue Jays", "Dave Stieb", "Jose Bautista", "Joe Carter", "Roberto Alomar"},
            {"Nationals/Expos", "Gary Carter", "Vlad Guerrero", "Andre Dawson", "Bryce Harper"},
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestionsLabel = findViewById(R.id.totalQuestionsLabel);
        questionsLabel = findViewById(R.id.questionLabel);
        answer01 = findViewById(R.id.answer01);
        answer02 = findViewById(R.id.answer02);
        answer03 = findViewById(R.id.answer03);
        answer04 = findViewById(R.id.answer04);

        int quizCategory = getIntent().getIntExtra("QUIZ_CATEGORY", 0);
        Log.v("CATEGORY", quizCategory + "");

        //Create array

        for (int i = 0; i < quizData.length; i++) {
            ArrayList<String> tempArray = new ArrayList<>();
            tempArray.add(quizData[i][0]); //team
            tempArray.add(quizData[i][1]); //correct answer
            tempArray.add(quizData[i][2]); //wrong option 1
            tempArray.add(quizData[i][3]); //wrong option 2
            tempArray.add(quizData[i][4]); //wrong option 3

            //add temp array to array quiz

            quizArray.add(tempArray);
        }

        showNextQuiz();

    }

    public void showNextQuiz() {
        //update number of quizzes
        totalQuestionsLabel.setText("Q" + quizCount);

        //random number between 0 and 29
        Random random = new Random();
        int randomNumber = random.nextInt(quizArray.size());

        //pick a quiz
        ArrayList<String> quiz = quizArray.get(randomNumber);

        //question and format
        questionsLabel.setText(quiz.get(0));
        rightAnswer = quiz.get(1);

        quiz.remove(0);
        Collections.shuffle(quiz);

        //choices
        answer01.setText(quiz.get(0));
        answer02.setText(quiz.get(1));
        answer03.setText(quiz.get(2));
        answer04.setText(quiz.get(3));

        //remove quiz
        quizArray.remove(randomNumber);

    }

    public void checkAnswer(View view) {

        //Button is pushed
        Button answerButton = findViewById(view.getId());
        String buttonText = answerButton.getText().toString();

        String alertTitle;


        //Correct answer
        if (buttonText.equals(rightAnswer)) {
            alertTitle = "Good job!";
            rightAnswerCount++;
        }

        //Incorrect Answer
        else {
            alertTitle = "Sorry, wrong answer!";
        }

        //Create alert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("The answer is " + rightAnswer);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //should only run when all quizzes are completed
                if (quizCount == QUIZCOUNT)
                {
                    Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    startActivity(intent);
                }
                else {
                    quizCount++;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}



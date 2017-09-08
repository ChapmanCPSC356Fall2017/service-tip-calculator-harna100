package edu.chapman.cpsc356.harna100.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	private EditText et_mealPrice;
	private Button btn_clear;
	private RatingBar rb_ServiceRating;
	private TextView tv_tipPrice;
	private TextView tv_totalPrice;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


	}


	private void calcTip(){

	}

	private double getTipPercentage(){
		return 0;
	}

	private int convertRatingBarScore(){
		return 0;
	}

	private void setTipPrice(double toSet){

	}

	private void setMealPrice(double toSet){

	}

	private String convertStringToCurrency(String toConvert){
		return null;
	}

	private void clear(){
		// clear edit text

		// reset rating bar

		// reset tip price

		// reset total price
	}

}

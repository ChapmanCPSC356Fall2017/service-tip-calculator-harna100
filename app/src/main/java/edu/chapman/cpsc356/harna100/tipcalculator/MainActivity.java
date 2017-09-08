package edu.chapman.cpsc356.harna100.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.NumberFormat;

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

		getReferences();
		setChangeListeners();

		//called to replace currency symbol
		clear();
	}

	private void getReferences(){
		et_mealPrice = (EditText) findViewById(R.id.et_price);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		rb_ServiceRating = (RatingBar) findViewById(R.id.rb_service_score);
		tv_tipPrice = (TextView) findViewById(R.id.tv_tip_price);
		tv_totalPrice = (TextView) findViewById(R.id.tv_total_price);
	}

	private void setChangeListeners(){
		setEditTextMealPriceTextChangeListener();
		setButtonClearOnClickListener();
		setRatingBarServiceOnChangeListener();
	}

	private void setEditTextMealPriceTextChangeListener(){
		et_mealPrice.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				calcTip();
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});
	}

	private void setButtonClearOnClickListener(){
		btn_clear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				clear();
			}
		});
	}

	private void setRatingBarServiceOnChangeListener(){
		rb_ServiceRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
				if(v < 0.5f){
					ratingBar.setRating(0.5f);
				}
				calcTip();
			}
		});
	}

	private void calcTip(){
		double mealPrice = getMealPrice();
		double tipPercentage = getTipPercentage();
		double tipPrice = getTipPrice(mealPrice,tipPercentage);
		double totalPrice = mealPrice + tipPrice;

		setTipPrice(tipPrice);
		setTotalPrice(totalPrice);
	}

	private double getMealPrice(){
		String mealPriceText = et_mealPrice.getText().toString();
		if(mealPriceText.isEmpty()){
			return 0;
		}

		return Double.parseDouble(mealPriceText);
	}

	private double getTipPercentage(){
		int rating = convertRatingBarScore();
		double tipPercentage = 0;
		switch(rating){
			case 1:
			case 2:
			case 3:
				tipPercentage = 0.1;
				break;
			case 4:
			case 5:
				tipPercentage = 0.13;
				break;
			case 6:
			case 7:
				tipPercentage = 0.15;
				break;
			case 8:
			case 9:
				tipPercentage = 0.2;
				break;
			case 10:
				tipPercentage = 0.25;
				break;
		}
		return tipPercentage;
	}

	private int convertRatingBarScore(){
		float ratingBarValue = rb_ServiceRating.getRating();
		int rating = (int) (ratingBarValue*2);
		return rating;
	}

	private double getTipPrice(double mealPrice, double tipPercentage) {
		return mealPrice * tipPercentage;
	}


	private void setTipPrice(double toSet){
		String stringToSet = convertNumberToCurrencyString(toSet);
		tv_tipPrice.setText(stringToSet);
	}

	private void setTotalPrice(double toSet){
		String stringToSet = convertNumberToCurrencyString(toSet);
		tv_totalPrice.setText(stringToSet);
	}

	private String convertNumberToCurrencyString(double toConvert){
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String converted = formatter.format(toConvert);
		return converted;
	}

	private void clear(){
		// clear edit text
		et_mealPrice.setText("");
		// reset rating bar
		rb_ServiceRating.setRating(5);
		// reset tip price
		tv_tipPrice.setText(convertNumberToCurrencyString(0));
		// reset total price
		tv_totalPrice.setText(convertNumberToCurrencyString(0));
	}

}

package edu.chapman.cpsc356.harna100.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

	private EditText et_mealPrice;
	private Button btn_clear;
	private DiscreteSeekBar sb_serviceRating;
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
		sb_serviceRating = (DiscreteSeekBar) findViewById(R.id.sb_service_rating);
		tv_tipPrice = (TextView) findViewById(R.id.tv_tip_price);
		tv_totalPrice = (TextView) findViewById(R.id.tv_total_price);
	}

	private void setChangeListeners(){
		setEditTextMealPriceTextChangeListener();
		setButtonClearOnClickListener();
		setSeekBarServiceOnChangeListener();
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

	private void setSeekBarServiceOnChangeListener(){
		sb_serviceRating.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
			@Override
			public void onProgressChanged(DiscreteSeekBar seekBar, int i, boolean b) {
				if(i == 0){
					seekBar.setProgress(1);
				}
				calcTip();
			}

			@Override
			public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

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
		float seekBarValue= sb_serviceRating.getProgress();
		int rating = (int) seekBarValue;
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
		sb_serviceRating.setProgress(10);
		// reset tip price
		tv_tipPrice.setText(convertNumberToCurrencyString(0));
		// reset total price
		tv_totalPrice.setText(convertNumberToCurrencyString(0));
	}

}

package br.com.sigen.cloudpos.view.component;

import java.math.BigDecimal;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;

public class NumberEditText extends EditText {

	public NumberEditText(Context context) {
		super(context);
		configNumberFormat();
	}

	public NumberEditText(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		configNumberFormat();
	}

	private void configNumberFormat() {
		setKeyListener(DigitsKeyListener.getInstance("0123456789,"));
		addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			private String current = "";

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// if (s != null && !s.toString().isEmpty()
				// && !s.toString().equals(current)) {
				// removeTextChangedListener(this);
				//
				// String replaceable = String.format("[%s,.]", NumberFormat
				// .getCurrencyInstance().getCurrency().getSymbol());
				// String cleanString = s.toString().replaceAll(replaceable,
				// "");
				//
				// BigDecimal parsed = new BigDecimal(cleanString).setScale(2,
				// BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100),
				// BigDecimal.ROUND_FLOOR);
				//
				// NumberFormat numberFormat = NumberFormat
				// .getNumberInstance();
				//
				// numberFormat.setMaximumFractionDigits(2);
				// numberFormat.setMinimumIntegerDigits(1);
				// numberFormat.setGroupingUsed(true);
				//
				// String formated = numberFormat.format(parsed);
				//
				// current = formated;
				// setText(formated);
				// setSelection(formated.length());
				//
				// addTextChangedListener(this);
				// }

				if (!s.toString().matches(
						"(\\d{1,3}(\\.\\d{3})*|(\\d+))(\\,\\d{2})")) {
					String userInput = ""
							+ s.toString().replaceAll("[^\\d]", "");
					if (userInput.length() > 2) {
						Float in = Float.parseFloat(userInput);
						Integer price = Math.round(in); // just to get an
														// Integer
						// float percen = in/100;
						String first, last;
						first = userInput.substring(0, userInput.length() - 2);
						last = userInput.substring(userInput.length() - 2);
						setText(first + "," + last);
						setSelection(getText().length());
					}
				}
			}
		});
	}

	public BigDecimal getNumber() {
		return new BigDecimal(String.valueOf(getText()).replaceAll(",", "."));
	}

	public void clear() {
		setText("");
	}

}

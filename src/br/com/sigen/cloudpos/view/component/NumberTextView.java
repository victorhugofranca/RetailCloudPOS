package br.com.sigen.cloudpos.view.component;

import java.math.BigDecimal;
import java.text.NumberFormat;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class NumberTextView extends TextView {

	public NumberTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setText(BigDecimal number) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(2);

		super.setText(numberFormat.format(number));
	}

}

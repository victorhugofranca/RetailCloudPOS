package br.com.sigen.cloudpos.view.components;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import br.com.sigen.cloudpos.business.ProdutoManager;
import br.com.sigen.cloudpos.entity.Produto;
import br.com.sigen.cloudpos.view.R;

public class SigenTable extends TableLayout {

	private List<SigenTableListener> listeners;

	public SigenTable(Context context) {
		super(context);
		listeners = new ArrayList<SigenTableListener>();
	}

	public void removeRows() {
		removeAllViews();
	}

	public void addListener(SigenTableListener tableListener) {
		listeners.add(tableListener);
	}

	public void setHeader(String[] header) {
		TableRow tableRow = new TableRow(getContext());

		for (int i = 0; i < header.length; i++) {
			String headerText = header[i];
			TextView textView = new TextView(getContext());
			textView.setText(headerText);
			textView.setBackgroundColor(Color.GRAY);
			textView.setTextColor(Color.WHITE);
			textView.setTextSize(20);

			tableRow.addView(textView);
		}

		addView(tableRow);
	}

	public void addRow(Object object) {
		final TableRow tableRow = new TableRow(getContext());

		tableRow.setBackground(getResources().getDrawable(R.drawable.table_row));
		tableRow.setClickable(true);

		configRowContent(object, tableRow);
		tableRow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Produto produto = new Produto();
				produto.setDescricao("coca");
				ProdutoManager.getInstance().find(produto);
				for (Iterator iterator = listeners.iterator(); iterator
						.hasNext();) {
					SigenTableListener listener = (SigenTableListener) iterator
							.next();
					listener.rowClicked(tableRow);
				}
			}
		});

		addView(tableRow);
	}

	private void configRowContent(Object object, TableRow tableRow) {
		Method[] methods = object.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			try {
				if (method.getName().startsWith("get")) {
					Object result = method.invoke(object);
					TextView textView = new TextView(getContext());
					textView.setTextColor(Color.BLACK);
					textView.setText(String.valueOf(result));
					textView.setTextSize(30);
					tableRow.addView(textView);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public void addAllRows(List<?> object) {
		for (Iterator iterator = object.iterator(); iterator.hasNext();) {
			addRow(iterator.next());
		}
	}

	public void removeAllRows() {
		removeViews(1, getChildCount() - 1);
	}

}

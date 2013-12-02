package br.com.sigen.cloudpos.view.pagamento;

import java.math.BigDecimal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import br.com.sigen.cloudpos.business.PagamentoBuilder;
import br.com.sigen.cloudpos.entity.ItemPagamento;
import br.com.sigen.cloudpos.view.R;

public class ActivityPagamento extends Activity {

	private AdapterPagamento pagamentoAdapter;
	private String tipoPagamentoSelecionado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pagamento);

		configComponentesVisuais();
	}

	private void configComponentesVisuais() {

		configTipoPagamentoSelecionado();
		configButtons();
		configItensPagamentosList();

	}

	private void configTipoPagamentoSelecionado() {
		this.tipoPagamentoSelecionado = "Dinheiro";
	}

	private void configItensPagamentosList() {
		TextView saldoPagamentoTextView = (TextView) findViewById(R.id.textSaldoPagamento);
		ListView pagamentosListView = initItensPagamentoList(saldoPagamentoTextView);
		pagamentosListView.setAdapter(pagamentoAdapter);
	}

	private ListView initItensPagamentoList(TextView saldoPagamentoTextView) {
		pagamentoAdapter = new AdapterPagamento(getBaseContext(),
				PagamentoBuilder.createPagamento(), saldoPagamentoTextView);
		ListView pagamentosListView = (ListView) findViewById(R.id.pagamentosListView);
		return pagamentosListView;
	}

	private void configButtons() {
		Button buttonFinalizarPagamentos = (Button) findViewById(R.id.btnFinalizarPagamentos);
		buttonFinalizarPagamentos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Prepare data intent
				Intent data = new Intent();

				// Activity finished ok, return the data
				setResult(RESULT_OK, data);

				finish();
			}
		});

		Button button = (Button) findViewById(R.id.btnConfirmarPagamento);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				TextView txtValor = (TextView) findViewById(R.id.inputValueText);

				ItemPagamento itemPagamento = new ItemPagamento();
				itemPagamento.setTipoPagamento(tipoPagamentoSelecionado);
				itemPagamento.setValor(new BigDecimal(String.valueOf(txtValor
						.getText())));

				pagamentoAdapter.add(itemPagamento);

				txtValor.setText("");
			}
		});

		Button btnDinheiro = (Button) findViewById(R.id.btnDinheiro);
		btnDinheiro.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				configSelectedFormaPagamento(R.id.btnDinheiro);
			}
		});

		Button btnCartaoDinheiro = (Button) findViewById(R.id.btnCartaoDebito);
		btnCartaoDinheiro.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				configSelectedFormaPagamento(R.id.btnCartaoDebito);
			}
		});

		Button btnCartaoCredito = (Button) findViewById(R.id.btnCartaoCredito);
		btnCartaoCredito.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				configSelectedFormaPagamento(R.id.btnCartaoCredito);
			}
		});

		Button btnCheque = (Button) findViewById(R.id.btnCheque);
		btnCheque.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				configSelectedFormaPagamento(R.id.btnCheque);
			}
		});

		configSelectedFormaPagamento(R.id.btnDinheiro);
	}

	private void configSelectedFormaPagamento(int btnId) {
		Button btnDinheiro = (Button) findViewById(R.id.btnDinheiro);
		if (btnId == R.id.btnDinheiro) {
			btnDinheiro.setBackgroundColor(Color.GRAY);
			tipoPagamentoSelecionado = "Dinheiro";
		} else
			btnDinheiro.setBackgroundColor(Color.LTGRAY);

		Button btnCartaoDebito = (Button) findViewById(R.id.btnCartaoDebito);
		if (btnId == R.id.btnCartaoDebito) {
			btnCartaoDebito.setBackgroundColor(Color.GRAY);
			tipoPagamentoSelecionado = "Cartão Débito";
		} else
			btnCartaoDebito.setBackgroundColor(Color.LTGRAY);

		Button btnCartaoCredito = (Button) findViewById(R.id.btnCartaoCredito);
		if (btnId == R.id.btnCartaoCredito) {
			btnCartaoCredito.setBackgroundColor(Color.GRAY);
			tipoPagamentoSelecionado = "Cartão Crédito";
		} else
			btnCartaoCredito.setBackgroundColor(Color.LTGRAY);

		Button btnCheque = (Button) findViewById(R.id.btnCheque);
		if (btnId == R.id.btnCheque) {
			btnCheque.setBackgroundColor(Color.GRAY);
			tipoPagamentoSelecionado = "Cheque";
		} else
			btnCheque.setBackgroundColor(Color.LTGRAY);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pagamento, menu);
		return true;
	}

}

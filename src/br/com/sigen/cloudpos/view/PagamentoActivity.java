package br.com.sigen.cloudpos.view;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import br.com.sigen.cloudpos.entity.ItemPagamento;
import br.com.sigen.cloudpos.entity.Pagamento;

public class PagamentoActivity extends Activity {

	private ItemPagamentoArrayAdapter itemPagamentoArrayAdapter;
	private String tipoPagamentoSelecionado;

	private Pagamento pagamento;
	private BigDecimal saldoPagamentos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pagamento);

		configBusinessObjects();

		configComponentesVisuais();
	}

	private void configBusinessObjects() {
		pagamento = new Pagamento();
		pagamento.setValorTotal(BigDecimal.ZERO);
		saldoPagamentos = BigDecimal.TEN.multiply(BigDecimal.TEN);
	}

	private void configComponentesVisuais() {
		configTipoPagamentoSelecionado();
		configButtons();
		configTextViewValoresTotais();
		configItensPagamentosTable();
	}

	private void configTipoPagamentoSelecionado() {
		this.tipoPagamentoSelecionado = "Dinheiro";
	}

	private void configTextViewValoresTotais() {
		TextView txtSaldoTotalVenda = (TextView) findViewById(R.id.lblSaldoPagamentos);
		txtSaldoTotalVenda.setText(String.valueOf(saldoPagamentos));
	}

	private void configItensPagamentosTable() {
		itemPagamentoArrayAdapter = new ItemPagamentoArrayAdapter(
				getBaseContext(), new ArrayList<ItemPagamento>());

		ListView pagamentosListView = (ListView) findViewById(R.id.pagamentosListView);
		pagamentosListView.setAdapter(itemPagamentoArrayAdapter);
	}

	private void configButtons() {
		Button button = (Button) findViewById(R.id.btnConfirmarPagamento);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				TextView txtValor = (TextView) findViewById(R.id.inputValueText);

				ItemPagamento itemPagamento = new ItemPagamento();
				itemPagamento.setTipoPagamento(tipoPagamentoSelecionado);
				itemPagamento.setValor(new BigDecimal(String.valueOf(txtValor
						.getText())));

				itemPagamentoArrayAdapter.add(itemPagamento);
				pagamento.setValorTotal(pagamento.getValorTotal().add(
						itemPagamento.getValor()));

				TextView txtSaldoTotalVenda = (TextView) findViewById(R.id.lblSaldoPagamentos);
				saldoPagamentos = saldoPagamentos.subtract(itemPagamento
						.getValor());
				txtSaldoTotalVenda.setText(String.valueOf(saldoPagamentos));
				
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
			tipoPagamentoSelecionado = "Cart�o D�bito";
		} else
			btnCartaoDebito.setBackgroundColor(Color.LTGRAY);

		Button btnCartaoCredito = (Button) findViewById(R.id.btnCartaoCredito);
		if (btnId == R.id.btnCartaoCredito) {
			btnCartaoCredito.setBackgroundColor(Color.GRAY);
			tipoPagamentoSelecionado = "Cart�o Cr�dito";
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

package br.com.sigen.cloudpos.view.pagamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.sigen.cloudpos.Messages;
import br.com.sigen.cloudpos.business.PagamentoBuilder;
import br.com.sigen.cloudpos.entity.ItemPagamento;
import br.com.sigen.cloudpos.entity.Pagamento;
import br.com.sigen.cloudpos.entity.Venda;
import br.com.sigen.cloudpos.exception.BusinessException;
import br.com.sigen.cloudpos.view.R;
import br.com.sigen.cloudpos.view.component.NumberEditText;
import br.com.sigen.cloudpos.view.component.NumberTextView;

public class ActivityPagamento extends Activity {

	private AdapterPagamento pagamentoAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pagamento);

		configComponentesVisuais();
	}

	private void configComponentesVisuais() {
		configButtons();
		configItensPagamentosList();
		configLabelsTotais();
	}

	private void configLabelsTotais() {
		Venda venda = getVendaIntent();

		NumberTextView txtTotal = (NumberTextView) findViewById(R.id.txtValorVenda);
		txtTotal.setText(venda.getValorTotal());
		NumberTextView txtSaldo = (NumberTextView) findViewById(R.id.textSaldoPagamento);
		txtSaldo.setText(venda.getValorTotal());
	}

	private Venda getVendaIntent() {
		Intent intent = getIntent();
		Venda venda = (Venda) intent.getExtras().getSerializable("venda");
		return venda;
	}

	private void configItensPagamentosList() {
		NumberTextView saldoPagamentoTextView = (NumberTextView) findViewById(R.id.textSaldoPagamento);
		TextView lblSaldo = (TextView) findViewById(R.id.lblSaldoPagamentos);
		ListView pagamentosListView = initItensPagamentoList(
				saldoPagamentoTextView, lblSaldo);
		pagamentosListView.setAdapter(pagamentoAdapter);
	}

	private ListView initItensPagamentoList(
			NumberTextView saldoPagamentoTextView, TextView lblSaldo) {

		Venda venda = getVendaIntent();

		Pagamento pagamento = PagamentoBuilder.createPagamento();
		pagamento.setValorTotal(venda.getValorTotal());
		pagamento.setSaldoPagamento(venda.getValorTotal());

		pagamentoAdapter = new AdapterPagamento(getBaseContext(), pagamento,
				saldoPagamentoTextView, lblSaldo);
		ListView pagamentosListView = (ListView) findViewById(R.id.pagamentosListView);
		return pagamentosListView;
	}

	private void validarConcluirPagamento() throws BusinessException {
		if (pagamentoAdapter.getPagamento().getSaldoPagamento()
				.compareTo(BigDecimal.ZERO) > 0) {
			throw new BusinessException(Messages.PAGAMENTO_INCOMPLETO);
		}
	}

	private void configButtons() {
		Button btnDinheiro = (Button) findViewById(R.id.btnDinheiro);
		btnDinheiro.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				realizarPagamentoDinheiro();
			}
		});

		Button btnCartaoDinheiro = (Button) findViewById(R.id.btnCartaoDebito);
		btnCartaoDinheiro.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				realizarPagamentoCartaoDebito();
			}
		});

		Button btnCartaoCredito = (Button) findViewById(R.id.btnCartaoCredito);
		btnCartaoCredito.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				realizarPagamentoCartaoCredito();
			}
		});

		Button btnCheque = (Button) findViewById(R.id.btnCheque);
		btnCheque.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				realizarPagamentoCheque();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pagamento, menu);
		MenuItem encerrarVendaMenuItem = menu.getItem(0);
		encerrarVendaMenuItem
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem arg0) {
						try {
							validarConcluirPagamento();
						} catch (BusinessException e) {
							Toast.makeText(getBaseContext(), e.getMessage(),
									Toast.LENGTH_LONG).show();
							return false;
						}

						// Prepare data intent
						Intent data = new Intent();

						// Activity finished ok, return the data
						setResult(RESULT_OK, data);

						finish();
						return true;
					}
				});
		return true;
	}

	private void realizarPagamentoDinheiro() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Pagamento - Dinheiro");

		// Set up the input
		final NumberEditText input = new NumberEditText(this);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ItemPagamento itemPagamento = new ItemPagamento();
				itemPagamento.setTipoPagamento("Dinheiro");
				itemPagamento.setValor(input.getNumber());

				try {
					pagamentoAdapter.addItemPagamento(itemPagamento);
				} catch (BusinessException e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_LONG).show();
				}
			}
		});
		builder.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		builder.show();
	}

	private void realizarPagamentoCheque() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Pagamento - Cheque");

		// Set up the input
		final NumberEditText input = new NumberEditText(this);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ItemPagamento itemPagamento = new ItemPagamento();
				itemPagamento.setTipoPagamento("Cheque");
				itemPagamento.setValor(input.getNumber());

				try {
					pagamentoAdapter.addItemPagamento(itemPagamento);
				} catch (BusinessException e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_LONG).show();
				}
			}
		});
		builder.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		builder.show();
	}

	private void realizarPagamentoCartaoDebito() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Pagamento - Cartão Débito");

		final Spinner redeCartaoSpinner = new Spinner(this);
		List<String> list = new ArrayList<String>();
		list.add("Redecard");
		list.add("Cielo");
		list.add("Hiper");
		list.add("Banese");
		list.add("American Express");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		redeCartaoSpinner.setAdapter(dataAdapter);

		// Set up the input
		final NumberEditText input = new NumberEditText(this);

		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.addView(redeCartaoSpinner);
		linearLayout.addView(input);

		builder.setView(linearLayout);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ItemPagamento itemPagamento = new ItemPagamento();
				itemPagamento.setTipoPagamento("Cartão Débito");
				itemPagamento.setRedeCartao(String.valueOf(redeCartaoSpinner
						.getSelectedItem()));
				itemPagamento.setValor(input.getNumber());

				try {
					pagamentoAdapter.addItemPagamento(itemPagamento);
				} catch (BusinessException e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_LONG).show();
				}
			}
		});
		builder.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		builder.show();
	}

	private void realizarPagamentoCartaoCredito() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Pagamento - Cartão Crédito");

		final Spinner redeCartaoSpinner = new Spinner(this);
		List<String> list = new ArrayList<String>();
		list.add("Redecard");
		list.add("Cielo");
		list.add("Hiper");
		list.add("Banese");
		list.add("American Express");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		redeCartaoSpinner.setAdapter(dataAdapter);

		final NumberPicker numberPicker = new NumberPicker(this);
		numberPicker.setMinValue(1);
		numberPicker.setMaxValue(12);

		// Set up the input
		final NumberEditText input = new NumberEditText(this);

		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.addView(redeCartaoSpinner);
		linearLayout.addView(numberPicker);
		linearLayout.addView(input);

		builder.setView(linearLayout);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ItemPagamento itemPagamento = new ItemPagamento();
				itemPagamento.setTipoPagamento("Cartão Crédito");
				itemPagamento.setRedeCartao(String.valueOf(redeCartaoSpinner
						.getSelectedItem()));
				itemPagamento.setParcelas(numberPicker.getValue());
				itemPagamento.setValor(input.getNumber());

				try {
					pagamentoAdapter.addItemPagamento(itemPagamento);
				} catch (BusinessException e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_LONG).show();
				}
			}
		});
		builder.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		builder.show();
	}

}

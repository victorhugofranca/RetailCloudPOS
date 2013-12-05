package br.com.sigen.cloudpos.view.pos;

import java.math.BigDecimal;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import br.com.sigen.cloudpos.Messages;
import br.com.sigen.cloudpos.business.ProdutoManager;
import br.com.sigen.cloudpos.business.VendaBuilder;
import br.com.sigen.cloudpos.entity.Produto;
import br.com.sigen.cloudpos.exception.BusinessException;
import br.com.sigen.cloudpos.view.R;
import br.com.sigen.cloudpos.view.component.NumberEditText;
import br.com.sigen.cloudpos.view.component.NumberTextView;
import br.com.sigen.cloudpos.view.pagamento.ActivityPagamento;

public class ActivityPosApp extends FragmentActivity {

	// Array Adapters
	private ArrayAdapterProdutos produtosArrayAdapter;
	private AdapterVenda vendaAdapter;

	// Visual Components
	private ListView listViewItensVenda;

	// Context Menu Itens
	private static int CONTEXT_MENU_ITEM_EXCLUIR = 0;
	private static int CONTEXT_MENU_ITEM_DESCONTO = 1;

	// Intent Request Codes
	private static int PAGAMENTO_REQUEST_CODE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pos_app);

		initVisualComponents();
	}

	private void initVisualComponents() {
		configButtons();

		configItensVendaList();

		configProdutosList();

		configSearchProdutosField();
	}

	private void configButtons() {
		Button btnNovaVenda = (Button) findViewById(R.id.btnNovaVenda);
		btnNovaVenda.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				confirmarNovaVenda();
			}
		});

		Button btnDescontoVenda = (Button) findViewById(R.id.btnDescontoVenda);
		btnDescontoVenda.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				realizarDescontoVenda();
			}
		});

		Button btnPagamento = (Button) findViewById(R.id.btnPagamento);
		btnPagamento.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					validarEncerramentoVenda();
				} catch (BusinessException e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_LONG).show();
					return;
				}

				Intent intent = new Intent(getBaseContext(),
						ActivityPagamento.class);
				intent.putExtra("venda", vendaAdapter.getVenda());
				startActivityForResult(intent, PAGAMENTO_REQUEST_CODE);
			}

		});
	}

	private void confirmarNovaVenda() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(Messages.DESEJA_ELIMINAR_VENDA)
				.setCancelable(false)
				.setPositiveButton(Messages.SIM,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								resetVendaAdapter();
							}
						})
				.setNegativeButton(Messages.NAO,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void validarEncerramentoVenda() throws BusinessException {
		if (!vendaAdapter.getVenda().podeEncerrar()) {
			throw new BusinessException(Messages.IMPOSSIVEL_ENCERRAR_VENDA);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PAGAMENTO_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				resetVendaAdapter();
			}
		}
	}

	private void configSearchProdutosField() {
		AutoCompleteTextView pesquisarProdutosField = (AutoCompleteTextView) findViewById(R.id.pesquisarProdutoField);
		pesquisarProdutosField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence constraint, int arg1,
					int arg2, int arg3) {
				produtosArrayAdapter.getFilter().filter(constraint);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
	}

	private void configProdutosList() {
		final ListView listView = (ListView) findViewById(R.id.listViewProdutos);
		List<Produto> produtos = ProdutoManager.getInstance().find(
				new Produto(), getBaseContext());

		produtosArrayAdapter = new ArrayAdapterProdutos(getBaseContext(),
				produtos);

		listView.setAdapter(produtosArrayAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Produto produto = produtosArrayAdapter.getItem(position);
				vendaAdapter.add(produto);
				listViewItensVenda.setSelection(vendaAdapter.getCount() - 1);
			}
		});

	}

	private void configItensVendaList() {
		listViewItensVenda = (ListView) findViewById(R.id.listViewItensVenda);

		resetVendaAdapter();

		registerForContextMenu(listViewItensVenda);
	}

	private void resetVendaAdapter() {
		NumberTextView totalTextView = (NumberTextView) findViewById(R.id.textValorTotal);
		totalTextView.setText(BigDecimal.ZERO);
		NumberTextView descontoTextView = (NumberTextView) findViewById(R.id.txtDesconto);
		descontoTextView.setText(BigDecimal.ZERO);
		vendaAdapter = new AdapterVenda(getBaseContext(),
				VendaBuilder.createVenda(), totalTextView, descontoTextView);
		listViewItensVenda.setAdapter(vendaAdapter);

		AutoCompleteTextView txtPesquisa = (AutoCompleteTextView) findViewById(R.id.pesquisarProdutoField);
		txtPesquisa.setText("");
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenu.ContextMenuInfo menuInfo) {
		if (view.getId() == R.id.listViewItensVenda) {
			menu.add(Menu.NONE, CONTEXT_MENU_ITEM_EXCLUIR,
					CONTEXT_MENU_ITEM_EXCLUIR, "Excluir");
			menu.add(Menu.NONE, CONTEXT_MENU_ITEM_DESCONTO,
					CONTEXT_MENU_ITEM_DESCONTO, "Desconto");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pos_app, menu);
		return true;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		// Get extra info about list item that was long-pressed
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		if (item.getItemId() == CONTEXT_MENU_ITEM_EXCLUIR) {
			removerItemVenda(menuInfo.position);
		} else if (item.getItemId() == CONTEXT_MENU_ITEM_DESCONTO) {
			realizarDescontoItemVenda(menuInfo.position);
		} else {
			return false;
		}
		return true;
	}

	private void removerItemVenda(int position) {
		vendaAdapter.remove(position);
	}

	private void realizarDescontoVenda() {
		try {
			validarDescontoVenda();
		} catch (BusinessException e) {
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG)
					.show();
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Desconto em R$");

		// Set up the input
		final NumberEditText input = new NumberEditText(this);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					vendaAdapter.realizarDesconto(input.getNumber());
				} catch (BusinessException e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_LONG).show();
					realizarDescontoVenda();
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

	private void validarDescontoVenda() throws BusinessException {
		if (!vendaAdapter.getVenda().podeRealizarDesconto(BigDecimal.ZERO)) {
			throw new BusinessException(Messages.DESCONTO_VENDA_ZERO);
		}
	}

	private void realizarDescontoItemVenda(final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Desconto em R$");

		// Set up the input
		final NumberEditText input = new NumberEditText(this);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					vendaAdapter.realizarDescontoItem(position,
							input.getNumber());
				} catch (BusinessException e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_LONG).show();
					realizarDescontoItemVenda(position);
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

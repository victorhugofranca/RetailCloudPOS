package br.com.sigen.cloudpos.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import br.com.sigen.cloudpos.business.ProdutoManager;
import br.com.sigen.cloudpos.entity.ItemVenda;
import br.com.sigen.cloudpos.entity.Produto;
import br.com.sigen.cloudpos.entity.Venda;

public class PosAppActivity extends FragmentActivity {

	private ProdutosArrayAdapter produtosArrayAdapter;
	private ItensVendaArrayAdapter itensVendaArrayAdapter;

	private ListView listViewItensVenda;
	private TextView totalTextView;

	private static int CONTEXT_MENU_ITEM_EXCLUIR = 0;
	private static int CONTEXT_MENU_ITEM_DESCONTO = 1;

	private static int PAGAMENTO_REQUEST_CODE = 0;

	private Venda venda;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pos_app);

		initBusinessObjects();

		initVisualComponents();
	}

	private void initBusinessObjects() {
		venda = new Venda();
		venda.setValorTotal(BigDecimal.ZERO);
	}

	private void initVisualComponents() {
		configTotalTextField();

		configButtons();

		configItensVendaListView();

		configProdutosTable();

		configSearchProdutosField();
	}

	private void configTotalTextField() {
		totalTextView = (TextView) findViewById(R.id.textValorTotal);
		totalTextView.setText(String.valueOf(venda.getValorTotal()));
	}

	private void configButtons() {
		Button btnNovaVenda = (Button) findViewById(R.id.btnNovaVenda);
		btnNovaVenda.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				criarNovaVenda();
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
				Intent intent = new Intent(getBaseContext(),
						PagamentoActivity.class);
				startActivityForResult(intent, PAGAMENTO_REQUEST_CODE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == PAGAMENTO_REQUEST_CODE){
			if(resultCode == RESULT_OK){
				criarNovaVenda();
			}
		}
	}

	private void configSearchProdutosField() {
		AutoCompleteTextView pesquisarProdutosField = findPesquisarProdutosField();
		pesquisarProdutosField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence constraint, int arg1,
					int arg2, int arg3) {
				produtosArrayAdapter.getFilter().filter(constraint);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
	}

	private void configProdutosTable() {
		final ListView listView = (ListView) findViewById(R.id.listViewProdutos);

		List<Produto> produtos = ProdutoManager.getInstance().find(
				new Produto());

		produtosArrayAdapter = new ProdutosArrayAdapter(getBaseContext(),
				produtos);

		listView.setAdapter(produtosArrayAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Produto produto = produtosArrayAdapter.getItem(position);

				ItemVenda itemVenda = new ItemVenda(produto, BigDecimal.ONE);
				addItemVenda(itemVenda);

				listViewItensVenda.setSelection(itensVendaArrayAdapter
						.getCount() - 1);
			}
		});

	}

	private void configItensVendaListView() {
		listViewItensVenda = (ListView) findViewById(R.id.listViewItensVenda);

		itensVendaArrayAdapter = new ItensVendaArrayAdapter(getBaseContext(),
				new ArrayList<ItemVenda>());

		listViewItensVenda.setAdapter(itensVendaArrayAdapter);

		registerForContextMenu(listViewItensVenda);
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

	private AutoCompleteTextView findPesquisarProdutosField() {
		AutoCompleteTextView field = (AutoCompleteTextView) findViewById(R.id.pesquisarProdutoField);
		return field;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pos_app, menu);
		return true;
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_pos_app_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

	private void criarNovaVenda() {
		initBusinessObjects();
		itensVendaArrayAdapter.clear();
		totalTextView.setText(String.valueOf(venda.getValorTotal()));
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

	private void addItemVenda(ItemVenda itemVenda) {
		itensVendaArrayAdapter.insert(itemVenda,
				itensVendaArrayAdapter.getCount());
		venda.setValorTotal(venda.getValorTotal()
				.add(itemVenda.getValorTotal()));
		totalTextView.setText(String.valueOf(venda.getValorTotal()));
	}

	private void removerItemVenda(int position) {
		ItemVenda itemVenda = itensVendaArrayAdapter.getItem(position);
		itensVendaArrayAdapter.remove(itemVenda);
		venda.setValorTotal(venda.getValorTotal().subtract(
				itemVenda.getValorTotal()));
		totalTextView.setText(String.valueOf(venda.getValorTotal()));
	}

	private void realizarDescontoVenda() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Desconto");

		// Set up the input
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				venda.realizarDesconto(new BigDecimal(String.valueOf(input
						.getText())));
				totalTextView.setText(String.valueOf(venda.getValorTotal()));
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

	private void realizarDescontoItemVenda(final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Desconto");

		// Set up the input
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				ItemVenda itemVenda = itensVendaArrayAdapter.getItem(position);
				itemVenda.realizarDesconto(new BigDecimal(String.valueOf(input
						.getText())));
				itensVendaArrayAdapter.notifyDataSetChanged();
				venda.setValorTotal(venda.getValorTotal().subtract(
						new BigDecimal(String.valueOf(input.getText()))));
				totalTextView.setText(String.valueOf(venda.getValorTotal()));
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

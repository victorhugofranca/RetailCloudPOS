package br.com.sigen.cloudpos.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

		configItensVendaTable();

		configProdutosTable();

		configSearchProdutosField();
	}

	private void configTotalTextField() {
		totalTextView = (TextView) findViewById(R.id.textValorTotal);
		totalTextView.setText(String.valueOf(venda.getValorTotal()));
	}

	private void configButtons() {
		Button btnNovaVenda = (Button) findViewById(R.id.button1);
		btnNovaVenda.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				criarNovaVenda();
			}
		});
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
				itensVendaArrayAdapter.insert(itemVenda,
						itensVendaArrayAdapter.getCount());
				venda.setValorTotal(venda.getValorTotal().add(
						itemVenda.getValorTotal()));
				totalTextView.setText(String.valueOf(venda.getValorTotal()));

				listViewItensVenda.setSelection(itensVendaArrayAdapter
						.getCount() - 1);
			}
		});

	}

	private void configItensVendaTable() {
		listViewItensVenda = (ListView) findViewById(R.id.listViewItensVenda);

		itensVendaArrayAdapter = new ItensVendaArrayAdapter(getBaseContext(),
				new ArrayList<ItemVenda>());

		listViewItensVenda.setAdapter(itensVendaArrayAdapter);
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

}

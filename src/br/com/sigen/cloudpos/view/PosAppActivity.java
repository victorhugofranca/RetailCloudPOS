package br.com.sigen.cloudpos.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import br.com.sigen.cloudpos.business.ProdutoManager;
import br.com.sigen.cloudpos.entity.Produto;
import br.com.sigen.cloudpos.entity.Venda;
import br.com.sigen.cloudpos.view.components.SigenTable;
import br.com.sigen.cloudpos.view.components.SigenTableListener;

public class PosAppActivity extends FragmentActivity implements
		ActionBar.OnNavigationListener, SigenTableListener {

	private SigenTable produtosTable;
	private SigenTable itensVendaTable;
	private TextView totalTextView;

	private Venda venda;

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

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
		ActionBar actionBar = configActionBar();
		configListNavigationCallBacks(actionBar);

		configButtons();

		configItensVendaTable();

		configProdutosTable();
		configTotalTextField();

		configSearchProdutosField();
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

	private void configTotalTextField() {
		totalTextView = new TextView(getBaseContext());
		totalTextView.setTextColor(Color.BLACK);
		totalTextView.setTextSize(40);
		totalTextView.setText(String.valueOf(venda.getValorTotal()));
		LinearLayout layout = findTotalBarLayout();
		layout.addView(totalTextView);
	}

	private void configSearchProdutosField() {
		AutoCompleteTextView pesquisarProdutosField = findPesquisarProdutosField();
		pesquisarProdutosField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				Produto filter = new Produto();
				filter.setDescricao(String.valueOf(arg0));
				List<Produto> produtos = ProdutoManager.getInstance().find(
						filter);
				produtosTable.removeAllRows();

				produtosTable.addAllRows(produtos);
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
		// produtosTable = new SigenTable(getBaseContext());
		// produtosTable.addListener(this);
		// produtosTable.setHeader(new String[] { "Cod", "Produto", "Preço" });
		//
		// List<Produto> produtos = ProdutoManager.getInstance().find(
		// new Produto());
		// produtosTable.addAllRows(produtos);
		//
		// LinearLayout layout = findProdutosLayout();
		// layout.addView(produtosTable);

		final ListView listView = (ListView) findViewById(R.id.listViewProdutos);
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
				"OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
				"Android", "iPhone", "WindowsMobile" };
		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				view.animate().setDuration(2000).alpha(0)
						.withEndAction(new Runnable() {
							@Override
							public void run() {
								list.remove(item);
								adapter.notifyDataSetChanged();
								view.setAlpha(1);
							}
						});
			}

		});

	}

	private void configItensVendaTable() {
		itensVendaTable = new SigenTable(getBaseContext());
		itensVendaTable.setHeader(new String[] { "Cod", "Produto", "Preço" });

		LinearLayout layout = findItensVendaLayout();
		layout.addView(itensVendaTable);
	}

	private AutoCompleteTextView findPesquisarProdutosField() {
		AutoCompleteTextView field = (AutoCompleteTextView) findViewById(R.id.pesquisarProdutoField);
		return field;
	}

	private LinearLayout findItensVendaLayout() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.layoutItensVenda);
		return layout;
	}

	// private LinearLayout findProdutosLayout() {
	// LinearLayout layout = (LinearLayout) findViewById(R.id.layoutProdutos);
	// return layout;
	// }

	private LinearLayout findTotalBarLayout() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.layoutTotalBar);
		return layout;
	}

	private void configListNavigationCallBacks(final ActionBar actionBar) {
		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
								getString(R.string.title_venda),
								getString(R.string.title_sistema),
								getString(R.string.title_conf), }), this);
	}

	private ActionBar configActionBar() {
		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		return actionBar;
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pos_app, menu);
		return true;
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
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

	@Override
	public void rowClicked(TableRow tableRow) {

		Produto produto = new Produto();
		String codigo = String.valueOf(((TextView) tableRow.getChildAt(0))
				.getText());
		String descricao = String.valueOf(((TextView) tableRow.getChildAt(1))
				.getText());
		String valorUnitario = String.valueOf(((TextView) tableRow
				.getChildAt(2)).getText());
		produto.setCodigo(codigo);
		produto.setDescricao(descricao);
		produto.setValorUnitario(new BigDecimal(valorUnitario));

		itensVendaTable.addRow(produto);
		ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView2);
		scrollView.fullScroll(HorizontalScrollView.FOCUS_DOWN);

		venda.setValorTotal(venda.getValorTotal().add(
				produto.getValorUnitario()));

		totalTextView.setText(String.valueOf(venda.getValorTotal()));
	}

	private void criarNovaVenda() {
		initBusinessObjects();
		itensVendaTable.removeAllRows();
		totalTextView.setText(String.valueOf(venda.getValorTotal()));
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}
}

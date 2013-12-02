package br.com.sigen.cloudpos.view.pos;

import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import br.com.sigen.cloudpos.business.ProdutoManager;
import br.com.sigen.cloudpos.entity.Produto;
import br.com.sigen.cloudpos.view.R;

public class ArrayAdapterProdutos extends ArrayAdapter<Produto> implements
		Filterable {

	private Context context;
	private List<Produto> values;

	public ArrayAdapterProdutos(Context context, List<Produto> values) {
		super(context, R.layout.produto_row_layout, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.produto_row_layout, parent,
				false);
		TextView textView = (TextView) rowView.findViewById(R.id.lblDescricao);
		textView.setText(values.get(position).getDescricao());

		TextView textView2 = (TextView) rowView.findViewById(R.id.lblTipoPagamento);
		textView2.setText(values.get(position).getUnidadeMedida());

		TextView textView3 = (TextView) rowView
				.findViewById(R.id.lblValorFormaPagamento);
		textView3.setText(String.valueOf(values.get(position)
				.getValorUnitario()));

		return rowView;
	}

	@Override
	public int getCount() {
		return values.size();
	}
	
	@Override
	public Produto getItem(int position) {
		return values.get(position);
	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				if (results.count == 0)
					notifyDataSetInvalidated();
				else {
					values = (List<Produto>) results.values;
					notifyDataSetChanged();
				}
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();

				Produto filtro = new Produto();
				filtro.setDescricao(String.valueOf(constraint));
				List<Produto> produtos = ProdutoManager.getInstance().find(
						filtro);

				for (Iterator iterator = produtos.iterator(); iterator
						.hasNext();) {
					Produto produto = (Produto) iterator.next();
					System.out.println(produto.getDescricao());
				}

				results.count = produtos.size();
				results.values = produtos;

				return results;
			}
		};

		return filter;

	}

}

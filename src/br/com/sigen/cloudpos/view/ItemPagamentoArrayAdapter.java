package br.com.sigen.cloudpos.view;

import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.sigen.cloudpos.entity.ItemPagamento;

public class ItemPagamentoArrayAdapter extends ArrayAdapter<ItemPagamento> {

	private Context context;
	private List<ItemPagamento> values;

	public ItemPagamentoArrayAdapter(Context context, List<ItemPagamento> values) {
		super(context, R.layout.pagamento_row_layout, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public void add(ItemPagamento newItemPagamento) {

		for (Iterator<ItemPagamento> iterator = values.iterator(); iterator
				.hasNext();) {
			ItemPagamento itemPagamento = iterator.next();
			if (itemPagamento.getTipoPagamento().equals(
					newItemPagamento.getTipoPagamento())) {
				itemPagamento.setValor(itemPagamento.getValor().add(
						newItemPagamento.getValor()));
				notifyDataSetChanged();
			}
			return;
		}
		super.add(newItemPagamento);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.pagamento_row_layout, parent,
				false);
		TextView textView = (TextView) rowView
				.findViewById(R.id.lblTipoPagamento);
		textView.setText(values.get(position).getTipoPagamento());

		TextView textView2 = (TextView) rowView
				.findViewById(R.id.lblValorFormaPagamento);
		textView2.setText(String.valueOf(values.get(position).getValor()));

		return rowView;
	}
}

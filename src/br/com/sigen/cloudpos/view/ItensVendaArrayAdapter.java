package br.com.sigen.cloudpos.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.sigen.cloudpos.entity.ItemVenda;

public class ItensVendaArrayAdapter extends ArrayAdapter<ItemVenda> {

	private Context context;
	private List<ItemVenda> values;

	public ItensVendaArrayAdapter(Context context, List<ItemVenda> values) {
		super(context, R.layout.item_venda_row_layout, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.item_venda_row_layout, parent,
				false);
		TextView textView = (TextView) rowView.findViewById(R.id.lblDescricao);
		textView.setText(values.get(position).getProduto().getDescricao());

		TextView textView2 = (TextView) rowView.findViewById(R.id.lblTipoPagamento);
		textView2.setText(values.get(position).getProduto().getUnidadeMedida());

		TextView textView3 = (TextView) rowView
				.findViewById(R.id.lblValorFormaPagamento);
		textView3.setText(String.valueOf(values.get(position).getProduto()
				.getValorUnitario()));

		return rowView;
	}
}

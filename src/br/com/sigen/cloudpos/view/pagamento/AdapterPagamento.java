package br.com.sigen.cloudpos.view.pagamento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.sigen.cloudpos.entity.ItemPagamento;
import br.com.sigen.cloudpos.entity.Pagamento;
import br.com.sigen.cloudpos.view.R;

public class AdapterPagamento extends ArrayAdapter<ItemPagamento> {

	private Context context;
	private Pagamento pagamento;
	private TextView txtSaldoPagamento;

	public AdapterPagamento(Context context, Pagamento pagamento,
			TextView txtSaldoPagamento) {

		super(context, R.layout.pagamento_row_layout, pagamento
				.getItensPagamento());

		this.context = context;

		this.pagamento = pagamento;
		this.txtSaldoPagamento = txtSaldoPagamento;
		txtSaldoPagamento
				.setText(String.valueOf(pagamento.getSaldoPagamento()));

	}

	@Override
	public void add(ItemPagamento newItemPagamento) {
		pagamento.addItemPagamento(newItemPagamento);
		notifyDataSetChanged();
	}

	@Override
	public void notifyDataSetChanged() {
		txtSaldoPagamento
				.setText(String.valueOf(pagamento.getSaldoPagamento()));
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.pagamento_row_layout, parent,
				false);
		TextView textView = (TextView) rowView
				.findViewById(R.id.lblTipoPagamento);
		textView.setText(pagamento.getItensPagamento().get(position)
				.getTipoPagamento());

		TextView textView2 = (TextView) rowView
				.findViewById(R.id.lblValorFormaPagamento);
		textView2.setText(String.valueOf(pagamento.getItensPagamento()
				.get(position).getValor()));

		return rowView;
	}
}

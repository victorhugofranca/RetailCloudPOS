package br.com.sigen.cloudpos.view.pagamento;

import java.math.BigDecimal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.sigen.cloudpos.entity.ItemPagamento;
import br.com.sigen.cloudpos.entity.Pagamento;
import br.com.sigen.cloudpos.exception.BusinessException;
import br.com.sigen.cloudpos.view.R;
import br.com.sigen.cloudpos.view.component.NumberTextView;

public class AdapterPagamento extends ArrayAdapter<ItemPagamento> {

	private Context context;
	private Pagamento pagamento;
	private NumberTextView txtSaldoPagamento;
	private TextView lblSaldo;

	public AdapterPagamento(Context context, Pagamento pagamento,
			NumberTextView txtSaldoPagamento, TextView lblSaldo) {

		super(context, R.layout.pagamento_row_layout, pagamento
				.getItensPagamento());

		this.context = context;

		this.pagamento = pagamento;
		this.txtSaldoPagamento = txtSaldoPagamento;
		this.lblSaldo = lblSaldo;
	}

	public void addItemPagamento(ItemPagamento newItemPagamento)
			throws BusinessException {
		pagamento.addItemPagamento(newItemPagamento);
		notifyDataSetChanged();
	}

	@Override
	public void notifyDataSetChanged() {
		txtSaldoPagamento.setText(pagamento.getSaldoPagamento().abs());
		if (pagamento.getSaldoPagamento().compareTo(BigDecimal.ZERO) < 0) {
			lblSaldo.setText("Troco: R$ ");
		}
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

		TextView txtRedeCartao = (TextView) rowView
				.findViewById(R.id.lblRedeCartao);

		String redeParcelas = pagamento.getItensPagamento().get(position)
				.getRedeCartao();
		Integer parcelas = pagamento.getItensPagamento().get(position).getParcelas();
		if (parcelas != null
				&& parcelas > 0) {
			redeParcelas += " (" + parcelas + "x)"; 
		}
		txtRedeCartao.setText(redeParcelas);

		NumberTextView textView2 = (NumberTextView) rowView
				.findViewById(R.id.lblValorFormaPagamento);
		textView2.setText(pagamento.getItensPagamento().get(position)
				.getValor());

		return rowView;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}
}

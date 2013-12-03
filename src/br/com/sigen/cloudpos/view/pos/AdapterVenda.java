package br.com.sigen.cloudpos.view.pos;

import java.math.BigDecimal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.sigen.cloudpos.entity.ItemVenda;
import br.com.sigen.cloudpos.entity.Produto;
import br.com.sigen.cloudpos.entity.Venda;
import br.com.sigen.cloudpos.exception.BusinessException;
import br.com.sigen.cloudpos.view.R;

public class AdapterVenda extends ArrayAdapter<ItemVenda> {

	private Context context;
	private Venda venda;
	private TextView textViewTotalizador;

	public AdapterVenda(Context context, Venda venda,
			TextView textViewTotalizador) {

		super(context, R.layout.item_venda_row_layout, venda.getItensVenda());
		this.context = context;

		this.venda = venda;

		this.textViewTotalizador = textViewTotalizador;
		textViewTotalizador.setText(String.valueOf(venda.getValorTotal()));

	}

	public Venda getVenda() {
		return this.venda;
	}

	@Override
	public void clear() {
		venda.getItensVenda().clear();
		venda.setValorTotal(BigDecimal.ZERO);
		notifyDataSetChanged();
	}

	public void realizarDesconto(BigDecimal valorDesconto)
			throws BusinessException {
		venda.realizarDesconto(valorDesconto);
		notifyDataSetChanged();
	}

	public void realizarDescontoItem(int position, BigDecimal valorDesconto)
			throws BusinessException {
		ItemVenda itemVenda = venda.getItensVenda().get(position);
		itemVenda.realizarDesconto(valorDesconto);
		venda.atualizarItemVenda(position, itemVenda, valorDesconto);
		notifyDataSetChanged();
	}

	@Override
	public void add(ItemVenda itemVenda) {
		venda.addItemVenda(itemVenda);
		notifyDataSetChanged();
	}

	public void add(Produto produto) {
		ItemVenda itemVenda = new ItemVenda(produto, BigDecimal.ONE);
		venda.addItemVenda(itemVenda);
		notifyDataSetChanged();
	}

	@Override
	public void insert(ItemVenda itemVenda, int index) {
		venda.addItemVenda(itemVenda);
		notifyDataSetChanged();
	}

	public void remove(int position) {
		ItemVenda itemVenda = getItem(position);
		venda.removeItemVenda(itemVenda);
		notifyDataSetChanged();
	}

	@Override
	public void notifyDataSetChanged() {
		textViewTotalizador.setText(String.valueOf(venda.getValorTotal()));
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.item_venda_row_layout, parent,
				false);
		TextView textView = (TextView) rowView.findViewById(R.id.lblDescricao);
		textView.setText(venda.getItensVenda().get(position).getProduto()
				.getDescricao());

		TextView textView2 = (TextView) rowView
				.findViewById(R.id.lblTipoPagamento);
		textView2.setText(venda.getItensVenda().get(position).getProduto()
				.getUnidadeMedida());

		TextView textView3 = (TextView) rowView
				.findViewById(R.id.lblValorFormaPagamento);
		textView3.setText(String.valueOf(venda.getItensVenda().get(position)
				.getValorUnitario()));

		return rowView;
	}
}

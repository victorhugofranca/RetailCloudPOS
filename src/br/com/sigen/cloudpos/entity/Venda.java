package br.com.sigen.cloudpos.entity;

import java.math.BigDecimal;
import java.util.List;

public class Venda {

	private BigDecimal valorTotal;
	private List<ItemVenda> itensVenda;

	public void atualizarItemVenda(int position, ItemVenda itemVenda,
			BigDecimal valorDesconto) {
		setValorTotal(valorTotal.subtract(valorDesconto));
		itensVenda.set(position, itemVenda);
	}

	public void realizarDesconto(BigDecimal valorDesconto) {
		setValorTotal(getValorTotal().subtract(valorDesconto));
	}

	public void addItemVenda(ItemVenda itemVenda) {
		itensVenda.add(itemVenda);
		valorTotal = valorTotal.add(itemVenda.getValorTotal());
	}

	public void removeItemVenda(ItemVenda itemVenda) {
		itensVenda.remove(itemVenda);
		valorTotal = valorTotal.subtract(itemVenda.getValorTotal());
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

}

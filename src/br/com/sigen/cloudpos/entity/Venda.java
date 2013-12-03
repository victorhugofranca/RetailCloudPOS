package br.com.sigen.cloudpos.entity;

import java.math.BigDecimal;
import java.util.List;

import br.com.sigen.cloudpos.Messages;
import br.com.sigen.cloudpos.exception.BusinessException;

public class Venda {

	private BigDecimal valorTotal;
	private List<ItemVenda> itensVenda;

	public Boolean podeRealizarDesconto(BigDecimal desconto) {
		return valorTotal.compareTo(desconto) > 0 ? true : false;
	}

	public Boolean podeEncerrar() {
		return itensVenda.isEmpty() ? false : true;
	}

	public void atualizarItemVenda(int position, ItemVenda itemVenda,
			BigDecimal valorDesconto) {
		setValorTotal(valorTotal.subtract(valorDesconto));
		itensVenda.set(position, itemVenda);
	}

	public void realizarDesconto(BigDecimal valorDesconto)
			throws BusinessException {

		if (!podeRealizarDesconto(valorDesconto))
			throw new BusinessException(Messages.IMPOSSIVEL_REALIZAR_DESCONTO_VENDA);

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

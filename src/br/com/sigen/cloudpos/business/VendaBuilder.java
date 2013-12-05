package br.com.sigen.cloudpos.business;

import java.math.BigDecimal;
import java.util.ArrayList;

import br.com.sigen.cloudpos.entity.ItemVenda;
import br.com.sigen.cloudpos.entity.Venda;

public class VendaBuilder {

	public static Venda createVenda() {
		Venda venda = new Venda();
		venda.setItensVenda(new ArrayList<ItemVenda>());
		venda.setValorTotal(BigDecimal.ZERO);
		venda.setValorDescontos(BigDecimal.ZERO);
		return venda;
	}

}

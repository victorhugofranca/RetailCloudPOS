package br.com.sigen.cloudpos.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.sigen.cloudpos.entity.Produto;

public class ProdutoManager {

	private static ProdutoManager instance;

	private ProdutoManager() {
	}

	public static ProdutoManager getInstance() {
		if (instance == null)
			instance = new ProdutoManager();

		return instance;
	}

	public List<Produto> find(Produto filter) {
		List<Produto> persistedList = new ArrayList<Produto>();

		persistedList.add(new Produto("001", "Coca-cola 330ml", new BigDecimal(
				"3.30")));
		persistedList.add(new Produto("001", "Guaran‡ Ant‡rcica 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Sprite 330ml", new BigDecimal(
				"3.30")));
		persistedList.add(new Produto("001", "Fanta Laranja 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Fanta Uva 330ml", new BigDecimal(
				"3.30")));
		persistedList.add(new Produto("001", "Soda Lim‹o 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Kuat 330ml", new BigDecimal(
				"3.30")));
		persistedList.add(new Produto("001", "Coca-cola zero 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Schin-cola 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Schin-cola 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Schin-cola 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Schin-cola 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Schin-cola 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Schin-cola 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Schin-cola 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Schin-cola 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Schin-cola 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Schin-cola 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Schin-cola 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Schin-cola 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Schin-cola 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Schin-cola 330ml",
				new BigDecimal("3.30")));
		persistedList.add(new Produto("001", "Schin-cola 330ml",
				new BigDecimal("3.30")));

		List<Produto> returnList = new ArrayList<Produto>();
		if (filter.getDescricao() == null || filter.getDescricao().equals("")) {
			returnList.addAll(persistedList);
		} else {
			returnList = containsInList(filter, persistedList);
		}
		return returnList;
	}

	private List<Produto> containsInList(Produto produto, List<Produto> produtos) {
		List<Produto> resultList = new ArrayList<Produto>();

		for (Iterator<Produto> iterator = produtos.iterator(); iterator
				.hasNext();) {
			Produto produtoNaLista = iterator.next();
			if (produtoNaLista.getDescricao().toLowerCase()
					.startsWith(produto.getDescricao().toLowerCase()))
				resultList.add(produtoNaLista);
		}

		return resultList;
	}

}

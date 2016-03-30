package br.com.ocupelago.dao;

import br.com.ocupelago.entidades.Avaliacao;

public class AvaliacaoDAO extends BaseDao<Avaliacao>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Avaliacao> getClasse() {
		return Avaliacao.class;
	}

}

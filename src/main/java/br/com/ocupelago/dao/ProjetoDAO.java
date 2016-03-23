package br.com.ocupelago.dao;

import br.com.ocupelago.entidades.Projeto;

public class ProjetoDAO extends BaseDao<Projeto>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Projeto> getClasse() {
		return Projeto.class;
	}

}

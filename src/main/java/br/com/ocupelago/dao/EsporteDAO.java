package br.com.ocupelago.dao;

import br.com.ocupelago.entidades.Esporte;

public class EsporteDAO extends BaseDao<Esporte> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Esporte> getClasse() {
		return Esporte.class;
	}

}

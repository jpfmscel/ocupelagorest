package br.com.ocupelago.dao;

import br.com.ocupelago.entidades.Noticia;

public class NoticiaDAO extends BaseDao<Noticia>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Noticia> getClasse() {
		return Noticia.class;
	}

}

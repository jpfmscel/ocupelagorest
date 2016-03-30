package br.com.ocupelago.dao;

import br.com.ocupelago.entidades.Imagem;

public class ImagemDAO extends BaseDao<Imagem>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Imagem> getClasse() {
		return Imagem.class;
	}

}

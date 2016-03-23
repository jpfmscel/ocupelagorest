package br.com.ocupelago.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import br.com.ocupelago.entidades.Usuario;

public class UsuarioDAO extends BaseDao<Usuario> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Usuario> getClasse() {
		return Usuario.class;
	}

	public Usuario logarUsuario(String email, String senha) {
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + Usuario.class.getSimpleName()
				+ " x where 1=1");
		sb.append(" and senha = '" + senha + "' and email ='" + email + "'");
		Usuario u = null;
		try {
			u = (Usuario) getEntityManager().createQuery(sb.toString())
					.getSingleResult();
		} catch (NoResultException e) {
		}
		return u;
	}

	public Usuario buscarUsuario(String email) {
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + Usuario.class.getSimpleName()
				+ " x where 1=1");
		sb.append(" and email = '" + email + "'");
		Usuario u = null;
		try {
			u = (Usuario) getEntityManager().createQuery(sb.toString())
					.getSingleResult();
		} catch (NoResultException e) {
		}
		return u;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findAll() {
		StringBuffer sb = new StringBuffer();
		sb.append("Select x from " + Usuario.class.getSimpleName()
				+ " x where 1=1");
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			usuarios.addAll((List<Usuario>) getEntityManager().createQuery(
					sb.toString()).getResultList());
		} catch (NoResultException e) {
		}
		return usuarios;
	}

}

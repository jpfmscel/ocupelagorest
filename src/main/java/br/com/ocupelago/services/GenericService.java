package br.com.ocupelago.services;

import static spark.Spark.*;
import br.com.ocupelago.dao.AlertaDAO;
import br.com.ocupelago.dao.EsporteDAO;
import br.com.ocupelago.dao.LocalDAO;
import br.com.ocupelago.dao.NoticiaDAO;
import br.com.ocupelago.dao.ProjetoDAO;
import br.com.ocupelago.dao.UsuarioDAO;
import br.com.ocupelago.entidades.Alerta;
import br.com.ocupelago.entidades.Usuario;
import br.com.ocupelago.entidades.restful.AlertaREST;
import br.com.ocupelago.entidades.restful.UsuarioREST;

import com.google.gson.Gson;

public class GenericService {
	private static Gson gson = new Gson();
	private static UsuarioDAO usuarioDao = new UsuarioDAO();
	private static AlertaDAO alertaDao = new AlertaDAO();
	private static LocalDAO localDao = new LocalDAO();
	private static ProjetoDAO projetoDao = new ProjetoDAO();
	private static NoticiaDAO noticiaDao = new NoticiaDAO();
	private static EsporteDAO esporteDao = new EsporteDAO();

	public static void main(String[] args) {

		
//		secure("/Users/jpfms/KeyStore.jks", "changeit", null, null);
		secure("C:/Users/Amon/Dropbos/Dropbox/OcupeOLago/software/application/java/rest/KeyStore.jks", "changeit", null, null);

		post("/login", "application/json", (request, response) -> {
			usuarioDao.setEntityManager(null);
			UsuarioREST u = gson.fromJson(request.body(), UsuarioREST.class);
			Usuario us = usuarioDao.buscarUsuario(u.getEmail());
			if(us==null){
				return "Usuário não encontrado!";
			}
			us.setSenha("");
			return gson.toJson(us);
		});
		
		get("/login", "application/json", (request, response) -> {
			usuarioDao.setEntityManager(null);
			UsuarioREST u = gson.fromJson(request.body(), UsuarioREST.class);
			Usuario us = usuarioDao.buscarUsuario(u.getEmail());
			if(us==null){
				return "Usuário não encontrado!";
			}
			us.setSenha("");
			return gson.toJson(us);
		});

		post("/getalertasinit", "application/json", (request, response) -> {
			alertaDao.setEntityManager(null);
			return gson.toJson(alertaDao.getListaInicial());
		});
		
		get("/getalertasinit", "application/json", (request, response) -> {
			alertaDao.setEntityManager(null);
			return gson.toJson(alertaDao.getListaInicial());
		});

		post("/getlocais", "application/json", (request, response) -> {
			localDao.setEntityManager(null);
			return gson.toJson(localDao.findAll());
		});

		get("/getlocais", "application/json", (request, response) -> {
			localDao.setEntityManager(null);
			return gson.toJson(localDao.findAll());
		});
		
		post("/getnoticias", "application/json", (request, response) -> {
			noticiaDao.setEntityManager(null);
			return gson.toJson(noticiaDao.findAll());
		});
		
		get("/getnoticias", "application/json", (request, response) -> {
			noticiaDao.setEntityManager(null);
			return gson.toJson(noticiaDao.findAll());
		});

		post("/getesportes", "application/json", (request, response) -> {
			esporteDao.setEntityManager(null);
			return gson.toJson(esporteDao.findAll());
		});
		
		get("/getesportes", "application/json", (request, response) -> {
			esporteDao.setEntityManager(null);
			return gson.toJson(esporteDao.findAll());
		});

		post("/getprojetos", "application/json", (request, response) -> {
			projetoDao.setEntityManager(null);
			return gson.toJson(projetoDao.findAll());
		});

		get("/getprojetos", "application/json", (request, response) -> {
			projetoDao.setEntityManager(null);
			return gson.toJson(projetoDao.findAll());
		});
		
		post("/addalerta", "application/json", (request, response) -> {

			alertaDao.setEntityManager(null);
			AlertaREST a = gson.fromJson(request.body(), AlertaREST.class);

			Alerta ale = new Alerta();
			ale.setDescricao(a.getDescricao());
			ale.setTitulo(a.getTitulo());

			ale.setLatitude(Double.parseDouble(a.getLatitude()));
			ale.setLongitude(Double.parseDouble(a.getLongitude()));

			alertaDao.iniciarTransacao();
			alertaDao.inserir(ale);
			alertaDao.comitarTransacao();

			return "OK";
		});
		
		get("/addalerta", "application/json", (request, response) -> {

			alertaDao.setEntityManager(null);
			AlertaREST a = gson.fromJson(request.body(), AlertaREST.class);

			Alerta ale = new Alerta();
			ale.setDescricao(a.getDescricao());
			ale.setTitulo(a.getTitulo());

			ale.setLatitude(Double.parseDouble(a.getLatitude()));
			ale.setLongitude(Double.parseDouble(a.getLongitude()));

			alertaDao.iniciarTransacao();
			alertaDao.inserir(ale);
			alertaDao.comitarTransacao();

			return "OK";
		});

	}

}

package br.com.ocupelago.services;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.secure;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import spark.Request;
import br.com.ocupelago.dao.AlertaDAO;
import br.com.ocupelago.dao.AvaliacaoDAO;
import br.com.ocupelago.dao.BaseDao;
import br.com.ocupelago.dao.EsporteDAO;
import br.com.ocupelago.dao.LocalDAO;
import br.com.ocupelago.dao.NoticiaDAO;
import br.com.ocupelago.dao.ProjetoDAO;
import br.com.ocupelago.dao.UsuarioDAO;
import br.com.ocupelago.entidades.Alerta;
import br.com.ocupelago.entidades.Avaliacao;
import br.com.ocupelago.entidades.Esporte;
import br.com.ocupelago.entidades.Local;
import br.com.ocupelago.entidades.Noticia;
import br.com.ocupelago.entidades.Projeto;
import br.com.ocupelago.entidades.Usuario;

import com.google.gson.Gson;

public class GenericService {

	private static Gson gson = new Gson();

	private static UsuarioDAO usuarioDao = new UsuarioDAO();
	private static AlertaDAO alertaDao = new AlertaDAO();
	private static LocalDAO localDao = new LocalDAO();
	private static ProjetoDAO projetoDao = new ProjetoDAO();
	private static NoticiaDAO noticiaDao = new NoticiaDAO();
	private static EsporteDAO esporteDao = new EsporteDAO();
	private static AvaliacaoDAO avaliacaoDao = new AvaliacaoDAO();

	private static List<Alerta> listaAlerta;
	private static List<Local> listaLocal;
	private static List<Projeto> listaProjeto;
	private static List<Noticia> listaNoticia;
	private static List<Esporte> listaEsporte;

	private static Date dataAlerta = new Date();
	private static Date dataLocal = new Date();
	private static Date dataProjeto = new Date();
	private static Date dataNoticia = new Date();
	private static Date dataEsporte = new Date();

	public static void main(String[] args) {

		secure("/Users/jpfms/KeyStore.jks", "changeit", null, null);
		// secure("C:/Users/Amon/Dropbos/Dropbox/OcupeOLago/software/application/java/rest/KeyStore.jks",
		// "changeit", null, null);

		post("/login", "application/json", (request, response) -> {
			Usuario us = loginUsuario(request);
			us.setSenha("");
			return gson.toJson(us);
		});

		get("/login", "application/json", (request, response) -> {
			Usuario us = loginUsuario(request);
			us.setSenha("");
			return gson.toJson(us);
		});

		post("/getalertas", "application/json", (request, response) -> {
			return getAlertas();
		});

		get("/getalertas", "application/json", (request, response) -> {
			return getAlertas();
		});

		post("/getlocais", "application/json", (request, response) -> {
			return getLocais();
		});

		get("/getlocais", "application/json", (request, response) -> {
			return getLocais();
		});

		post("/getnoticias", "application/json", (request, response) -> {
			return getNoticias();
		});

		get("/getnoticias", "application/json", (request, response) -> {
			return getNoticias();
		});

		post("/getesportes", "application/json", (request, response) -> {
			return getEsportes();
		});

		get("/getesportes", "application/json", (request, response) -> {
			return getEsportes();
		});

		post("/getprojetos", "application/json", (request, response) -> {
			return getProjetos();
		});

		get("/getprojetos", "application/json", (request, response) -> {
			return getProjetos();
		});

		post("/addalerta", "application/json", (request, response) -> {
			return addAlerta(request);
		});

		get("/addalerta", "application/json", (request, response) -> {
			return addAlerta(request);
		});

		post("/addavaliacao", "application/json", (request, response) -> {
			return addAvaliacao(request);
		});

		get("/addavaliacao", "application/json", (request, response) -> {
			return addAvaliacao(request);
		});
		
	}

	private static Object addAvaliacao(Request request) {
		avaliacaoDao.setEntityManager(null);
		Avaliacao a = gson.fromJson(request.body(), Avaliacao.class);
		a.setCriadoEm(new Date());
		a.setAtivo(true);
		
		avaliacaoDao.iniciarTransacao();
		avaliacaoDao.inserir(a);
		avaliacaoDao.comitarTransacao();
		return "OK";
	}

	private static Usuario loginUsuario(Request request) {
		usuarioDao.setEntityManager(null);
		Usuario u = gson.fromJson(request.body(), Usuario.class);
		Usuario us = usuarioDao.buscarUsuario(u.getEmail());
		return us;
	}

	private static Object addAlerta(Request request) {
		alertaDao.setEntityManager(null);
		Alerta a = gson.fromJson(request.body(), Alerta.class);
		a.setDataCriado(new Date());
		a.setAtivo(true);
		
		alertaDao.iniciarTransacao();
		alertaDao.inserir(a);
		alertaDao.comitarTransacao();
		return "OK";
	}

	private static Object getProjetos() {
		if (!(isSameMinute(dataProjeto) && !getListaProjeto().isEmpty())) {
			atualizarLista(projetoDao, dataProjeto);
		}
		return gson.toJson(getListaProjeto());
	}

	private static Object getAlertas() {
		if (!(isSameMinute(dataAlerta) && !getListaAlerta().isEmpty())) {
			atualizarLista(alertaDao, dataAlerta);
		}
		return gson.toJson(getListaAlerta());
	}

	private static Object getLocais() {
		if (!(isSameMinute(dataLocal) && !getListaLocal().isEmpty())) {
			atualizarLista(localDao, dataLocal);
		}
		return gson.toJson(getListaLocal());
	}

	private static Object getNoticias() {
		if (!(isSameMinute(dataNoticia) && !getListaNoticia().isEmpty())) {
			atualizarLista(noticiaDao, dataNoticia);
		}
		return gson.toJson(getListaNoticia());
	}

	private static Object getEsportes() {
		if (!(isSameMinute(dataEsporte) && !getListaEsporte().isEmpty())) {
			atualizarLista(esporteDao, dataEsporte);
		}
		return gson.toJson(getListaEsporte());
	}

	@SuppressWarnings("unchecked")
	public static <T> void atualizarLista(BaseDao<T> b, Date d) {
		b.setEntityManager(null);
		if (b instanceof EsporteDAO) {
			setListaEsporte((List<Esporte>) b.findAll());
		} else if (b instanceof LocalDAO) {
			setListaLocal((List<Local>) b.findAll());
		} else if (b instanceof NoticiaDAO) {
			setListaNoticia((List<Noticia>) b.findAll());
		} else if (b instanceof ProjetoDAO) {
			setListaProjeto((List<Projeto>) b.findAll());
		} else if (b instanceof AlertaDAO) {
			setListaAlerta((List<Alerta>) b.findAll());
		}
		d = new Date();
	}

	@SuppressWarnings("deprecation")
	public static boolean isSameMinute(Date d1) {
		Date d2 = new Date();
		boolean retorno = false;
		if (d2.getDay() == d1.getDay() && d2.getMonth() == d1.getMonth() && d2.getYear() == d1.getYear()) {
			if (d2.getHours() == d1.getHours()) {
				retorno = (d2.getMinutes() == d1.getMinutes());
			}
		}
		return retorno;
	}

	public static List<Alerta> getListaAlerta() {
		if (listaAlerta == null) {
			listaAlerta = new ArrayList<>();
		}
		return listaAlerta;
	}

	public static void setListaAlerta(List<Alerta> listaAlerta) {
		GenericService.listaAlerta = listaAlerta;
	}

	public static List<Local> getListaLocal() {
		if (listaLocal == null) {
			listaLocal = new ArrayList<>();
		}
		return listaLocal;
	}

	public static void setListaLocal(List<Local> listaLocal) {
		GenericService.listaLocal = listaLocal;
	}

	public static List<Projeto> getListaProjeto() {
		if (listaProjeto == null) {
			listaProjeto = new ArrayList<>();
		}
		return listaProjeto;
	}

	public static void setListaProjeto(List<Projeto> listaProjeto) {
		GenericService.listaProjeto = listaProjeto;
	}

	public static List<Noticia> getListaNoticia() {
		if (listaNoticia == null) {
			listaNoticia = new ArrayList<>();
		}

		return listaNoticia;
	}

	public static void setListaNoticia(List<Noticia> listaNoticia) {
		GenericService.listaNoticia = listaNoticia;
	}

	public static List<Esporte> getListaEsporte() {
		if (listaEsporte == null) {
			listaEsporte = new ArrayList<>();
		}

		return listaEsporte;
	}

	public static void setListaEsporte(List<Esporte> listaEsporte) {
		GenericService.listaEsporte = listaEsporte;
	}

}

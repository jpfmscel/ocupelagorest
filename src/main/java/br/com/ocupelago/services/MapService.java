package br.com.ocupelago.services;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.secure;
import br.com.ocupelago.dao.AlertaDAO;
import br.com.ocupelago.entidades.Alerta;
import br.com.ocupelago.entidades.restful.AlertaREST;
import br.com.ocupelago.transformer.JsonTransformer;

import com.google.gson.Gson;


public class MapService {

	private static Gson gson = new Gson();
	private static AlertaDAO aleDao = new AlertaDAO();

	public static void main(String[] args) {
		secure("/Users/jpfms/KeyStore.jks", "changeit", null, null);
		
		get("/addmap", "application/json", (request, response) -> {
			return request.body();
		}, new JsonTransformer());

		post("/addmap", "application/json", (request, response) -> {
			AlertaREST a = gson.fromJson(request.body(), AlertaREST.class);

			System.out.println(request.ip() +" - "+ request.body() );
			
			Alerta ale = new Alerta();
			ale.setDescricao(a.getDescricao());
			ale.setTitulo(a.getTitulo());

			ale.setLatitude(Double.parseDouble(a.getLatitude()));
			ale.setLongitude(Double.parseDouble(a.getLongitude()));

			aleDao.iniciarTransacao();
			aleDao.inserir(ale);
			aleDao.comitarTransacao();

			StringBuffer sb = new StringBuffer();
			for (Alerta aler : aleDao.getListaInicial()) {
				sb.append(aler.toString() + " \n");
			}
			
			return sb.toString();
		});
	}

}

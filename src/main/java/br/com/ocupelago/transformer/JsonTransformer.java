package br.com.ocupelago.transformer;

import spark.ResponseTransformer;
import br.com.ocupelago.entidades.restful.AlertaREST;

import com.google.gson.Gson;

public class JsonTransformer implements ResponseTransformer {

	private Gson gson = new Gson();
	 
	@Override
	public String render(Object model) {
		return gson.fromJson((String) model, AlertaREST.class).toString();
	}

}

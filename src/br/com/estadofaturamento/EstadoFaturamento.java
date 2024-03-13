package br.com.estadofaturamento;

import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class EstadoFaturamento {

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("###.##");
		List<Faturamento> listFaturamento = getDadosJson();
		listFaturamento.stream().forEach(f -> System.out.println(f.getEstado() + " - " + f.getValor()));
		Double totalFaturamento = 0.0;
		for (Faturamento faturamento : listFaturamento) {
			totalFaturamento += faturamento.getValor();
		}
		System.out.println("\nTotal Faturamento: " + totalFaturamento + "\n");
		for (Faturamento faturamento : listFaturamento) {
			Double porcentagem = (faturamento.getValor()/totalFaturamento) * 100;
			System.out.println(faturamento.getEstado() + " - "  + df.format(porcentagem) + "%");
		}
	}
	
	public static List<Faturamento> getDadosJson() {
		List<Faturamento> listFaturamento = new ArrayList<>();
		try {
			FileReader reader = new FileReader("dados.json");
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(reader);
			JSONArray jsonArray = (JSONArray) obj;
			Iterator<JSONObject> iterator = jsonArray.iterator();
			while(iterator.hasNext()) {
				JSONObject jsonObject = iterator.next();
				Faturamento fat = new Faturamento();
				fat.setEstado(jsonObject.get("estado").toString());
				fat.setValor(Double.parseDouble(jsonObject.get("valor").toString()));
				listFaturamento.add(fat);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return listFaturamento;
	}
}

package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entidades.Produto;

public class Program {

	public static void main(String[] args) throws ParseException {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Produto> lista = new ArrayList<>();

		System.out.print("Entre com o caminho do arquivo: ");
		String origemDoArquivoStr = sc.nextLine();

		File origemArquivo = new File(origemDoArquivoStr);
		String origemPastaStr = origemArquivo.getParent();
		
		boolean successo = new File(origemPastaStr + "\\saida").mkdir();
		
		String pastaDestinoStr = origemPastaStr + "\\saida\\relatorio.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(origemDoArquivoStr))) {

			String itemCsv = br.readLine();
			while (itemCsv != null) {

				String[] campos = itemCsv.split(";");
				String nome = campos[0];
				double preco = Double.parseDouble(campos[1]);
				int quantidade = Integer.parseInt(campos[2]);

				lista.add(new Produto(nome, preco, quantidade));

				itemCsv = br.readLine();
			}

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(pastaDestinoStr))) {

				for (Produto item : lista) {
					bw.write(item.getNome() + "," + String.format("%.2f", item.total()));
					bw.newLine();
				}

				System.out.println(pastaDestinoStr + " CRIADO!");
				
			} catch (IOException e) {
				System.out.println("Erro criando arquivo: " + e.getMessage());
			}

		} catch (IOException e) {
			System.out.println("Erro lendo aquivo: " + e.getMessage());
		}

		sc.close();
	}
}
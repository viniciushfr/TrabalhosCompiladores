/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoglc;

import formas_normais.Chomsky;
import formas_normais.Greibach;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author vinic
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> gramatica = new ArrayList();
        Simplificacao simplificar = new Simplificacao();
        Chomsky chomsky = new Chomsky();
        Greibach greibach = new Greibach();
        // colocar o diretorio da pasta dos arquivos glc
        String diretorio = "C:\\Users\\vinic\\Documents\\guilherme_vinicius\\TrabalhoTeoria\\Exemplos";

        File file = new File(diretorio);
        File Arquivos[] = file.listFiles();

        boolean Continuar = true;
        while (Continuar) {
            System.out.println("Arquivos encontrados no diretorio:");

            for (int i = 0; i < Arquivos.length; i++) {
                File arquivos = Arquivos[i];
                System.out.println(i + "->" + arquivos.getName());

            }
            System.out.print("Usar: ");
            Scanner teclado = new Scanner(System.in);
            String nomeArquivo = teclado.nextLine();
            gramatica = simplificar.derivacoes(diretorio + "\\" + nomeArquivo);
            System.out.println("Gramatica encontrada: " + gramatica);
            System.out.println("Opcoes: [1]Simplificar, [2]Aplicar FNC, [3]Aplicar FNG, [4]Recursao esquerda");
            System.out.print("Executar : ");
            int Opcao = teclado.nextInt();
            switch (Opcao) {
                case 1:
                    System.out.println("Gramatica Simplificada:  " + simplificar.simplificar(gramatica));
                    break;
                case 2:
                    System.out.println("Forma Normal de Chomsky: " + chomsky.aplicarChomsky(gramatica));
                    break;
                case 3:
                    System.out.println("Forma Normal de Greybach: " + greibach.asplicarGreibach(gramatica));
                    break;
                case 4:
                    gramatica = simplificar.simplificar(gramatica);

                    gramatica = greibach.segundaEtapa(gramatica);

                     gramatica = greibach.terceiraEtapa(gramatica);
                    System.out.println("Recursao a esquerda: " + gramatica);
                    break;
                default:
                    System.out.println("Opção não disponivel");
                    break;
            }
            System.out.print("Realizar outra operacao?[sim],[nao]:   ");
            teclado.nextLine();
            String continuar = teclado.nextLine();
            if (!(continuar.equals("sim"))) {
                System.out.println("vlw flw");
                Continuar = false;
            }
        }

    }
}

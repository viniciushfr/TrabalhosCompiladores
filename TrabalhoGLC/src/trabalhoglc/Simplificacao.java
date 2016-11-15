package trabalhoglc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Simplificacao {

    // aki ele só divide uma linha em várias, exemplo: A:aB|bB|cB é transformado para A:aA A:bB e A:cB

    public ArrayList<String> derivacoes(String diretorioArquivo) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(diretorioArquivo));
        ArrayList<String> derivacao = new ArrayList<>();
        while (sc.hasNextLine()) {
            String linha = sc.nextLine();
            char v = linha.charAt(0);
            int comeco = 2;
            for (int i = 0; i <= linha.length() - 1; i++) {
                //System.out.println(i+" "+linha.length());
                if (linha.charAt(i) == '|' || i == linha.length() - 1) {
                    if (i == linha.length() - 1) {
                        derivacao.add(v + ":" + linha.substring(comeco, i + 1));
                    } else {
                        derivacao.add(v + ":" + linha.substring(comeco, i));
                    }
                    comeco = i + 1;
                }
            }
        }
        return derivacao;
    }

    public ArrayList<String> excluirVazio(ArrayList<String> gramatica) {
        String gramaticaNova = "";
        String vazio = "";
        char primeiro = gramatica.get(0).charAt(0);
        // variavel vazio recebe todas as variaveis que derivam em vazio diretamente
        for (String c : gramatica) {
            if (c.charAt(2) == '&') {
                vazio += c.substring(0, 1);
            }
        }
        // vazio recebe todas as variaveis que derivam em vazio indiretamente
        for (Iterator<String> it = gramatica.iterator(); it.hasNext();) {
            String linha = it.next();
            for (int i = 0; i < vazio.length(); i++) {
                if (linha.charAt(2) == vazio.charAt(i) && linha.length() <= 3 && verificar(linha, vazio)) {
                    vazio += linha.charAt(0);
                    it = gramatica.iterator();
                }
            }
        }
        // remove todas as linhas que contém &
        for (int i = 0; i < gramatica.size(); i++) {
            if (gramatica.get(i).charAt(2) == '&' && gramatica.get(i).charAt(0) != primeiro) {
                gramatica.remove(i);
                i--;
            }
        }
        //variavel gramaticaNova recebe as derivações sem a variavel que deriva em vazio, exemplo: oq era A:aB sendo q B:& fica somente A:a
        // OBS: aquela derivação que tiver mais de uma variavel que deriva em vazio recebe um "2" no final
        for (String linha : gramatica) {
            boolean gravar = true;
            for (int j = 2, cont = 0; j < linha.length(); j++) {
                for (int i = 0; i < vazio.length(); i++) {
                    if (linha.charAt(j) == vazio.charAt(i) && linha.length() > 3) {
                        cont++;
                        String aux = linha.substring(2);
                        if (cont > 1 && gravar) {
                            aux = linha.charAt(0) + ":" + aux.replaceFirst(linha.substring(j, j + 1), "") + "2";
                            gravar = false;
                        } else {
                            aux = linha.charAt(0) + ":" + aux.replaceFirst(linha.substring(j, j + 1), "");
                        }
                        gramaticaNova += aux + "|";
                    }
                }
            }
        }
        // acrescenta gramaticaNova para a ArrayList principal gramatica
        for (int i = 0, comeco = 0; i < gramaticaNova.length(); i++) {
            if (gramaticaNova.charAt(i) == '|') {
                gramatica.add(gramaticaNova.substring(comeco, i));
                comeco = i + 1;
            }
        }
        //percorre o arraylist gramatica para ver se tem alguma linha igual a outra, houve testes que isso ocorreu pelo fato
        // de utilizar replaceFirst, caso ocorra como  o exemplo 5 onde F:AxCDyA sendo A:&, ficará dois F:xCDyA, aki ele deleta a segunda
        // derivação(F:xCDyA) e retira o outro A
        for (int i = 0; i < gramatica.size(); i++) {
            for (int j = i + 1; j < gramatica.size(); j++) {
                if (gramatica.get(i).equals(gramatica.get(j))) {
                    gramatica.remove(j);
                    for (int y = 2; y < gramatica.get(i).length(); y++) {
                        for (int x = 0; x < vazio.length(); x++) {
                            if (gramatica.get(i).charAt(y) == vazio.charAt(x)) {
                                String aux = gramatica.get(i).substring(2);
                                aux = gramatica.get(i).charAt(0) + ":" + aux.replaceFirst(gramatica.get(i).substring(y, y + 1), "");
//                      System.out.println(aux);
                                gramatica.add(aux);
                            }
                        }
                    }
                }
            }
        }
        //onde tiver o 2 no final da linha quer dizer q é necessário retirar todas as variaveis que derivam em vazio
        // exemplo:  A:aBCD sendo B:& e D:& nesse for ele exclui o B e o D da derivação ficando A:aC e insere no ArratList gramatica
        for (int i = 0; i < gramatica.size(); i++) {
            boolean gravar = true;
            if (gramatica.get(i).charAt(gramatica.get(i).length() - 1) == '2') {
                String adiciona = gramatica.get(i).replace("2", "");
                gramatica.remove(i);
                gramatica.add(i, adiciona);
                for (int x = 2; x < gramatica.get(i).length(); x++) {
                    for (int j = 0; j < vazio.length(); j++) {
                        if (gramatica.get(i).charAt(x) == vazio.charAt(j)) {
                            String aux = "";
                            if (gravar) {
                                aux = gramatica.get(i);
                                gravar = false;
                            }
                            aux = aux.replaceAll(gramatica.get(i).substring(x, x + 1), "");
                            if (minusculo(aux)) {
                                gramatica.add(aux);
                            }
//                                System.out.println(aux);
                        }
                    }
                }
            }
        }
//        System.out.println("Variaveis que Derivam em Vazio: "+vazio);
        return gramatica;
    }

    public boolean minusculo(String linha) {
        for (int i = 2; i < linha.length(); i++) {
            if (Character.isLowerCase(linha.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean verificar(String linha, String vazio) {
        for (int i = 0; i < vazio.length(); i++) {
            if (linha.charAt(0) == vazio.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> substituirVariaveis(ArrayList<String> gramatica) {
        ArrayList<String> producoes = new ArrayList<>();
        String variaveis = "";
        for (String linha : gramatica) {
            if (Character.isUpperCase(linha.charAt(2)) && linha.length() <= 3) {
                producoes.add(linha);
            }
            variaveis += linha.charAt(2);
        }
        for (int i = 0; i < producoes.size(); i++) {
            for (int j = i+1; j < producoes.size() - 1; j++) {
                if (producoes.get(i).charAt(2) == producoes.get(j).charAt(0)) {
//                    String aux = producoes.get(i).charAt(0) + ":" + producoes.get(j).charAt(2);
                    String aux = producoes.get(j);
                    producoes.set(j,producoes.get(i));
                    producoes.set(i,aux);
//                    if(!producoes.contains(aux)){
//                        producoes.add(aux);
//                    }
//                    producoes.remove(i);
                }
            }
        }
        for (String prod : producoes) {
            for (int i = 0; i < gramatica.size(); i++) {
                if (prod == gramatica.get(i)) {
                    String aux = gramatica.get(i);
                    gramatica.remove(i);
                    for (int j = 0; j < gramatica.size(); j++) {
                        if (gramatica.get(j).charAt(0) == prod.charAt(2)) {
//                            System.out.println(prod.substring(0,1) + " " + gramatica.get(j).substring(0,1));
                            aux = gramatica.get(j).replaceFirst(gramatica.get(j).substring(0, 1), prod.substring(0, 1));
//                            System.out.println(aux);
//                            System.out.println(gramatica.get(j)+ " " + prod + " " + aux);
                            gramatica.add(aux);
//                            System.out.println(aux);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < gramatica.size(); i++) {
            String aux = gramatica.get(i);
            for (int j = i + 1; j < gramatica.size(); j++) {
                if (gramatica.get(i).equals(gramatica.get(j))) {
                    gramatica.remove(j);
                }
            }
        }
//        System.out.println(producoes);
        return gramatica;
    }

    public ArrayList<String> excluirInuteis(ArrayList<String> gramatica) {
        String variaveis = "";
        String naoExclui = gramatica.get(0).substring(0, 1);
        String inuteis = "";
        for (String c : gramatica) {
            boolean gravar = true;
            for (int i = 0; i < variaveis.length(); i++) {
                if (c.charAt(0) == variaveis.charAt(i)) {
                    gravar = false;
                }
            }
            if (gravar) {
                variaveis += c.substring(0, 1);
            }
        }
        for (int i = 0; i < naoExclui.length(); i++) {
            for (int j = 0; j < gramatica.size(); j++) {
                if (naoExclui.charAt(i) == gramatica.get(j).charAt(0)) {
                    for (int x = 2; x < gramatica.get(j).length(); x++) {
                        if (Character.isUpperCase(gramatica.get(j).charAt(x))) {
                            if (!naoExclui.contains(gramatica.get(j).substring(x, x + 1))) {
                                naoExclui += gramatica.get(j).substring(x, x + 1);
                            }
                        }
                    }
                }
            }
        }
//        System.out.println(variaveis);
//        System.out.println(naoExclui);
        for(int i=0; i<naoExclui.length(); i++)
                if(!variaveis.contains(naoExclui.substring(i,i+1)))
                    inuteis += naoExclui.substring(i,i+1);                       
//        System.out.println(inuteis);
        for(int i=0; i<gramatica.size(); i++){
            for(int j=0; j< inuteis.length(); j++)
                if(gramatica.get(i).contains(inuteis.substring(j,j+1))){
                    gramatica.remove(i);
                    i--;
                }
        }
        for (int i = 0; i < naoExclui.length(); i++) {
            variaveis = variaveis.replace(naoExclui.substring(i, i + 1), " ");
        }
        variaveis = variaveis.replace(" ", "");
        for (int i = 0; i < gramatica.size(); i++) {
            for (int j = 0; j < variaveis.length(); j++) {
                if (gramatica.get(i).charAt(0) == variaveis.charAt(j)) {
                    gramatica.remove(i);
                    i--;
                }
            }
        }

 
//        System.out.println(variaveis);
        for (String c : gramatica) {
//            System.out.println(c);
        }
        return gramatica;

    }

    public ArrayList simplificar(ArrayList<String> gramatica) {
        gramatica = excluirVazio(gramatica);
        gramatica = substituirVariaveis(gramatica);
        gramatica = excluirInuteis(gramatica);

        return gramatica;
    }
    
}

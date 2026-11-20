import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

public class BestiarioGalactico {

    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    // Representa uma criatura do bestiario
    static class Criatura {
        long registro;
        String nome;
        String especie;
        String planeta;
        String habilidade;

        Criatura(long registro, String nome, String especie,
                 String planeta, String habilidade) {
            this.registro = registro;
            this.nome = nome;
            this.especie = especie;
            this.planeta = planeta;
            this.habilidade = habilidade;
        }

        void exibir() {
            System.out.println(registro + " | " + nome + " | " + especie + " | " + planeta + " | " + habilidade);
        }
    }

    // Armazena os resultados de cada algoritmo
    static class Estatisticas {
        long comparacoes = 0;
        long trocas = 0;
        long deslocamentos = 0;
        long tempo = 0;
    }

    // Adiciona uma criatura no vetor
    static Criatura[] adicionarCriatura(Criatura[] dados, Criatura nova) {
        Criatura[] novoVetor = new Criatura[dados.length + 1];

        for (int i = 0; i < dados.length; i++) {
            novoVetor[i] = dados[i];
        }

        novoVetor[dados.length] = nova;
        return novoVetor;
    }

    // Carrega as criaturas do arquivo de texto
    static Criatura[] carregarDados() throws Exception {
        Criatura[] dados = new Criatura[0];
        File arquivo = new File("criaturas.txt");
        Scanner leitor = new Scanner(arquivo, "UTF-8");

        while (leitor.hasNextLine()) {
            String linha = leitor.nextLine().trim();

            if (!linha.isEmpty()) {
                String[] partes = linha.split(";");

                long registro = Long.parseLong(partes[0].trim());
                String nome = partes[1];
                String especie = partes[2];
                String planeta = partes[3];
                String habilidade = partes[4];

                Criatura nova = new Criatura(registro, nome, especie, planeta, habilidade);
                dados = adicionarCriatura(dados, nova);
            }
        }

        leitor.close();
        return dados;
    }

    // Salva uma nova criatura no arquivo de texto
    static void salvarCriatura(Criatura c) throws Exception {
        FileWriter fw = new FileWriter("criaturas.txt", true);
        fw.write("\n" + c.registro + ";" + c.nome + ";" + c.especie + ";" + c.planeta + ";" + c.habilidade);
        fw.close();
    }

    // Cadastro de uma nova criatura
    static Criatura cadastrar(Criatura[] dados, int quantidade) {

        System.out.print("Numero de registro (8 digitos): ");
        long registro = sc.nextLong();
        sc.nextLine();

        if (registro < 10000000 || registro > 99999999) {
            System.out.println("O registro precisa ter 8 digitos.");
            return null;
        }

        for (Criatura c : dados) {
            if (c != null && c.registro == registro) {
                System.out.println("Esse registro ja existe!");
                return null;
            }
        }

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Especie: ");
        String especie = sc.nextLine();

        System.out.print("Planeta: ");
        String planeta = sc.nextLine();

        System.out.print("Habilidade: ");
        String habilidade = sc.nextLine();

        System.out.println("Criatura cadastrada com sucesso!");

        return new Criatura(registro, nome, especie, planeta, habilidade);
    }

    // Lista todas as criaturas
    static void listar(Criatura[] dados) {
        System.out.println("\n===== CRIATURAS CADASTRADAS =====");

        for (Criatura c : dados) {
            if (c != null) {
                c.exibir();
            }
        }
    }

    // Bubble Sort
    static Estatisticas bubbleSort(Criatura[] dados, boolean narrar) {
        Estatisticas e = new Estatisticas();
        for (int i = 0; i < dados.length - 1; i++) {

            if (narrar) {
                System.out.println("\n★ BUBBLE SORT - PASSAGEM " + (i + 1));
            }
            boolean trocou = false;

            for (int j = 0; j < dados.length - 1 - i; j++) {
                e.comparacoes++;
                if (dados[j].registro > dados[j + 1].registro) {
                    if (narrar) {
                        System.out.println(dados[j].nome + " (" + dados[j].registro + ") > " + dados[j + 1].nome + " (" + dados[j + 1].registro + ")? SIM -> Trocam de posicao!");
                    }
                    Criatura temp = dados[j];
                    dados[j] = dados[j + 1];
                    dados[j + 1] = temp;

                    e.trocas++;
                    trocou = true;

                } else if (narrar) {
                    System.out.println("Nao trocam de posicao.");
                }
            }
            if (narrar) {
                System.out.println("Comparacoes: " + e.comparacoes + " | Trocas: " + e.trocas);
            }
            if (!trocou) {
                break;
            }
        }
        return e;
    }

    // Insertion Sort
    static Estatisticas insertionSort(Criatura[] dados, boolean narrar) {

        Estatisticas e = new Estatisticas();

        for (int i = 1; i < dados.length; i++) {

            Criatura atual = dados[i];
            int j = i - 1;

            if (narrar) {
                System.out.println("\n★ INSERTION SORT");
                System.out.println("Organizando: " + atual.nome);
            }
            while (j >= 0) {
                e.comparacoes++;
                if (dados[j].registro > atual.registro) {
                    dados[j + 1] = dados[j];
                    e.deslocamentos++;
                    if (narrar) {
                        System.out.println(dados[j].nome + " deslocado para a direita.");
                    }
                    j--;
                } else {
                    break;
                }
            }
            dados[j + 1] = atual;
            if (narrar) {
                System.out.println("Posicao encontrada para " + atual.nome);
            }
        }
        return e;
    }

    // Quick Sort usando uma pilha para controlar as particoes
    static Estatisticas quickSort(Criatura[] dados, boolean narrar) {

        Estatisticas e = new Estatisticas();

        int n = dados.length;

        if (n < 2) {
            return e;
        }

        int[] pilha = new int[2 * n];
        int topo = -1;

        pilha[++topo] = 0;
        pilha[++topo] = n - 1;

        while (topo >= 0) {

            int fim = pilha[topo--];
            int inicio = pilha[topo--];

            long pivo = dados[fim].registro;

            int i = inicio - 1;

            for (int j = inicio; j < fim; j++) {

                e.comparacoes++;

                if (dados[j].registro <= pivo) {

                    i++;

                    if (i != j){
                        Criatura temp = dados[i];
                        dados[i] = dados[j];
                        dados[j] = temp;
                        e.trocas++;

                        if (narrar) {
                            System.out.println("Troca: " + dados[i].nome + " <-> " + dados[j].nome
                            );
                        }
                    }
                }
            }

            int posicaoPivo = i + 1;

            if (posicaoPivo != fim) {
                Criatura temp = dados[posicaoPivo];
                dados[posicaoPivo] = dados[fim];
                dados[fim] = temp;
                e.trocas++;

                if (narrar) {
                    System.out.println("Pivo " + pivo + " colocado na posicao correta.");
                }
            }
            if (posicaoPivo - 1 > inicio) {
                pilha[++topo] = inicio;
                pilha[++topo] = posicaoPivo - 1;
            }
            if (posicaoPivo + 1 < fim) {
                pilha[++topo] = posicaoPivo + 1;
                pilha[++topo] = fim;
            }
        }
        return e;
    }

    // Seleciona o algoritmo
    static Estatisticas ordenar(Criatura[] dados, int algoritmo, boolean narrar) {

        if (algoritmo == 1) {
            return bubbleSort(dados, narrar);
        }
        if (algoritmo == 2) {
            return insertionSort(dados, narrar);
        }
        return quickSort(dados, narrar);
    }

    // Modo passo a passo
    static void modoPasso(Criatura[] dados) {

        int limite = 10;
        if (dados.length < 10) {
            limite = dados.length;
        }

        System.out.print("Quantas criaturas usar no passo a passo (1 a " + limite + ")? ");
        int qtd = sc.nextInt();

        if (qtd < 1 || qtd > limite) {
            System.out.println("Quantidade invalida. Escolha entre 1 e " + limite + ".");
            return;
        }

        System.out.println("\nEscolha o algoritmo:");
        System.out.println("1 - Bubble Sort");
        System.out.println("2 - Insertion Sort");
        System.out.println("3 - Quick Sort");

        int opcao = sc.nextInt();

        if (opcao < 1 || opcao > 3) {
            System.out.println("Opcao invalida.");
            return;
        }

        Criatura[] copia = new Criatura[qtd];
        for (int i = 0; i < qtd; i++) {
            copia[i] = dados[i];
        }

        Estatisticas e = ordenar(copia, opcao, true);
        System.out.println("\n===== RESULTADO FINAL =====");
        listar(copia);

        exibirEstatisticas(e);
    }
    
    // Mostra os resultados
    static void exibirEstatisticas(Estatisticas e) {
        System.out.println("\nComparacoes: " + e.comparacoes);
        System.out.println("Trocas: " + e.trocas);
        System.out.println("Deslocamentos: " + e.deslocamentos);
    }

    // Gera criaturas para a corrida
    static Criatura[] gerarCriaturas(int quantidade) {
        String[] nomes = {
                "Zibo", "Lumi", "Gloop", "Quark", "Pixel",
                "Nina", "Astro", "Bolota", "Cometa", "Pudim"
        };

        String[] especies = {
                "ZORBLAX", "LUNARI", "GELATINO",
                "PLIMOR", "METEORIX"
        };

        String[] planetas = {
                "Marte", "Venus", "Jupiter", "Netuno", "Plutao"
        };

        String[] habilidades = {
                "Voo", "Telepatia", "Invisibilidade",
                "Regeneracao", "Superforca"
        };

        Criatura[] dados = new Criatura[quantidade];

        for (int i = 0; i < quantidade; i++) {
            dados[i] = new Criatura(
                    10000000L + i,
                    nomes[random.nextInt(nomes.length)] + " " + i,
                    especies[random.nextInt(especies.length)],
                    planetas[random.nextInt(planetas.length)],
                    habilidades[random.nextInt(habilidades.length)]
            );
        }
        return dados;
    }

    // Embaralha os registros
    static void embaralhar(Criatura[] dados) {

        for (int i = dados.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Criatura temp = dados[i];
            dados[i] = dados[j];
            dados[j] = temp;
        }
    }

    // Inverte a ordem dos registros
    static void inverter(Criatura[] dados) {

        int inicio = 0;
        int fim = dados.length - 1;

        while (inicio < fim) {
            Criatura temp = dados[inicio];
            dados[inicio] = dados[fim];
            dados[fim] = temp;

            inicio++;
            fim--;
        }
    }

    // Executa um teste e mede o tempo
    static void executarTeste(String nome, Criatura[] original, int algoritmo) {

        Criatura[] copia = original.clone();
        long inicio = System.nanoTime();
        Estatisticas e = ordenar(copia, algoritmo, false);
        long fim = System.nanoTime();
        e.tempo = fim - inicio;

        double tempoMs = e.tempo / 1_000_000.0;

        System.out.printf("%-15s | %12d | %12.4f | %12d | %12d%n", nome, original.length, tempoMs, e.comparacoes, e.trocas + e.deslocamentos);
    }

    // Grande Corrida
    static void grandeCorrida() {
        int[] quantidades = {50, 1000, 10000};
        String[] algoritmos = {
                "Bubble Sort", "Insertion Sort", "Quick Sort"
        };

        System.out.println("\n===== A GRANDE CORRIDA =====");
        System.out.println("Algoritmo | Registros | Tempo(ms) | Comparacoes | Movimentacoes");

        for (int n : quantidades) {

            Criatura[] aleatorio = gerarCriaturas(n);
            Criatura[] ordenado = aleatorio.clone();
            Criatura[] inverso = aleatorio.clone();

            // Os registros sao criados em ordem crescente
            // antes do embaralhamento.
            embaralhar(aleatorio);

            // Reconstrói a ordem crescente usando os registros
            // originais sequenciais.
            ordenado = gerarCriaturas(n);

            // A ordem inversa e criada a partir da ordem crescente
            inverso = ordenado.clone();
            inverter(inverso);

            System.out.println("\nQuantidade: " + n);
            System.out.println("\n--- ALEATORIO ---");

            for (int a = 1; a <= 3; a++) {
                executarTeste(algoritmos[a - 1], aleatorio, a);
            }

            System.out.println("\n--- JA ORDENADO ---");

            for (int a = 1; a <= 3; a++) {
                executarTeste(algoritmos[a - 1], ordenado, a);
            }

            System.out.println("\n--- ORDEM INVERSA ---");

            for (int a = 1; a <= 3; a++) {
                executarTeste(algoritmos[a - 1], inverso, a);
            }
        }
    }

    // Menu principal
    public static void main(String[] args) throws Exception {

        Criatura[] dados = carregarDados();

        int quantidade = dados.length;
        int opcao;

        do {
            System.out.println("\n==================================");
            System.out.println("       BESTIARIO GALACTICO");
            System.out.println("==================================");
            System.out.println("1 - Cadastrar criatura");
            System.out.println("2 - Listar criaturas");
            System.out.println("3 - Gerar catalogo oficial");
            System.out.println("4 - Modo passo a passo");
            System.out.println("5 - A Grande Corrida");
            System.out.println("0 - Encerrar");
            System.out.println("==================================");
            System.out.print("Escolha uma opcao: ");

            opcao = sc.nextInt();

            switch (opcao) {

                case 1:
                    Criatura nova = cadastrar(dados, quantidade);

                    if (nova != null) {
                        dados = adicionarCriatura(dados, nova);
                        salvarCriatura(nova);
                        quantidade++;
                    }
                    break;

                case 2:
                    listar(dados);
                    break;

                case 3:
                    System.out.println("\nEscolha o algoritmo:");
                    System.out.println("1 - Bubble Sort");
                    System.out.println("2 - Insertion Sort");
                    System.out.println("3 - Quick Sort");

                    int algoritmo = sc.nextInt();

                    if (algoritmo < 1 || algoritmo > 3) {
                        System.out.println("Opcao invalida.");
                        break;
                    }
                    Criatura[] copia = dados.clone();
                    Estatisticas resultado =
                            ordenar(copia, algoritmo, false);

                    System.out.println("\nCATALOGO OFICIAL ORDENADO:");

                    listar(copia);
                    exibirEstatisticas(resultado);
                    break;
                case 4:
                    modoPasso(dados);
                    break;
                case 5:
                    grandeCorrida();
                    break;
                case 0:
                    System.out.println(
                            "Missao encerrada. Ate a proxima, cadete!"
                    );
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
        sc.close();
    }
}
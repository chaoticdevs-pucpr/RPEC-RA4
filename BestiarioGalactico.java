
import java.util.Random;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

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

    // Cria as 50 criaturas iniciais
    static Criatura[] criarDadosIniciais() {
        Criatura[] dados = new Criatura[50];

        // 20 criaturas adaptadas do apendice do projeto
        dados[0] = new Criatura(50318842, "Zibo Zumbido", "ZORBLAX", "Marte", "Voo supersônico");
        dados[1] = new Criatura(21004571, "Quark Quarkinho", "PLIMOR", "Marte", "Invisibilidade");
        dados[2] = new Criatura(38820019, "Lumi Lunar", "LUNARI", "Jupiter", "Controle da luz");
        dados[3] = new Criatura(12093355, "Gloop Gelatina", "GELATINO", "Plutao", "Regeneracao");
        dados[4] = new Criatura(77410028, "Cometa Clotilde", "COMETIX", "Venus", "Supervelocidade");
        dados[5] = new Criatura(45001276, "Pixel Pulsar", "PIXELON", "Mercurio", "Manipulacao digital");
        dados[6] = new Criatura(63390114, "Vovo Vortex", "VORTEX", "Vega", "Controle gravitacional");
        dados[7] = new Criatura(29981530, "Plim Plutonico", "PLUTONIANO", "Plutao", "Teletransporte");
        dados[8] = new Criatura(84120967, "Fafa Foguete", "FOGUETIX", "Venus", "Propulsao");
        dados[9] = new Criatura(16672043, "Nebulosa Nina", "NEBULON", "Netuno", "Criacao de nevoa");
        dados[10] = new Criatura(57033218, "Orbi Orbital", "ORBITRON", "Saturno", "Manipulacao de orbitas");
        dados[11] = new Criatura(33450981, "Tico Telescopio", "TELESCOPIX", "Marte", "Visao telescopica");
        dados[12] = new Criatura(90215574, "Jujuba Jupiteriana", "JUJUBIX", "Jupiter", "Criacao de doces");
        dados[13] = new Criatura(41127730, "Kiko Kepler", "KEPLERIANO", "Kepler", "Leitura espacial");
        dados[14] = new Criatura(72208815, "Zuzu Zigue-Zague", "ZIGZAGON", "Orion", "Movimento dimensional");
        dados[15] = new Criatura(25560349, "Mimi Meteoro", "METEORIX", "Urano", "Controle de meteoros");
        dados[16] = new Criatura(68873002, "Astro Astrolabio", "ASTROLUX", "Sirius", "Orientacao espacial");
        dados[17] = new Criatura(19940278, "Bolota Buraco-Negro", "NEGRON", "Andromeda", "Absorcao");
        dados[18] = new Criatura(54417763, "Tchuca Titanica", "TITANIX", "Saturno", "Forca colossal");
        dados[19] = new Criatura(80031496, "Gugu Galaxia", "GALACTON", "Vega", "Manipulacao sonora");

        // 30 criaturas novas criadas para o projeto
        String[] nomes = {
            "Bilu", "Pipoca", "Zeca", "Malu", "Tutu",
            "Biscoito", "Lulu", "Fofuxo", "Nino", "Bolinha",
            "Pudim", "Teco", "Dudu", "Lilo", "Mimo",
            "Cacau", "Tata", "Fifi", "Pingo", "Nana",
            "Bubu", "Kiko", "Molejo", "Tico", "Pitu",
            "Zaza", "Lala", "Pompom", "Bambam", "Tutuco"
        };

        String[] especies = {
            "ALIENIX", "COSMIX", "NEBULON", "ASTRON",
            "GALACTON", "METEORIX"
        };

        String[] planetas = {
            "Marte", "Venus", "Jupiter", "Saturno",
            "Netuno", "Plutao"
        };

        String[] habilidades = {
            "Telepatia", "Superforca", "Invisibilidade",
            "Controle do fogo", "Teletransporte", "Voo"
        };

        for (int i = 0; i < 30; i++) {
            dados[i + 20] = new Criatura(70000000L + i, nomes[i] + " Espacial", especies[i % especies.length], planetas[i % planetas.length], habilidades[i % habilidades.length]);
        }

        return dados;
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

        if (dados.length > 10) {
            System.out.println("O modo passo a passo aceita ate 10 criaturas.");
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

        Criatura[] copia = dados.clone();
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

    // Gera criaturas para a Grande Corrida
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

        System.out.printf("%-15s | %12d | %12d | %12d | %12d%n", nome, original.length, e.tempo / 1_000_000, e.comparacoes, e.trocas + e.deslocamentos);
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
    public static void main(String[] args) {

        Criatura[] dados = criarDadosIniciais();

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
                        Criatura[] novoVetor =
                            new Criatura[quantidade + 1];

                        for (int i = 0; i < quantidade; i++) {
                            novoVetor[i] = dados[i];
                        }
                        novoVetor[quantidade] = nova;
                        dados = novoVetor;
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

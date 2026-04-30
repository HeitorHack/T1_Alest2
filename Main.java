import java.util.*;

class Order {
    int preco;
    int quantidade;

    public Order(int preco, int quantidade) {
        this.preco = preco;
        this.quantidade = quantidade;
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== Sistema de Compra e Venda ===");
        System.out.println("Formato das operacoes: C/V /quantidade/ preco");
        System.out.println("Exemplo:");
        System.out.println("C 10 100  -> Compra 10 acoes por preco 100");
        System.out.println("V 5 90    -> Venda 5 acoes por preco 90");
        System.out.print("\nQuantas operacoes deseja inserir? ");

        int n = sc.nextInt();

        Comparator<Order> compCompra = new Comparator<Order>() {
            public int compare(Order a, Order b) {
                return b.preco - a.preco;
            }
        };

        Comparator<Order> compVenda = new Comparator<Order>() {
            public int compare(Order a, Order b) {
                return a.preco - b.preco;
            }
        };

        PriorityQueue<Order> compras = new PriorityQueue<>(compCompra);
        PriorityQueue<Order> vendas = new PriorityQueue<>(compVenda);

        long lucro = 0;
        long total = 0;

        System.out.println("\nDigite as operacoes:");

        for (int i = 0; i < n; i++) {

            System.out.print("Operacao " + (i + 1) + ": ");

            char tipo = sc.next().charAt(0);
            int qtd = sc.nextInt();
            int preco = sc.nextInt();

            if (tipo == 'C') {

                while (!vendas.isEmpty() && qtd > 0) {

                    Order v = vendas.peek();

                    if (v.preco > preco) break;

                    vendas.poll();

                    int q = Math.min(qtd, v.quantidade);

                    lucro += (preco - v.preco) * q;
                    total += q;

                    qtd -= q;
                    v.quantidade -= q;

                    if (v.quantidade > 0) {
                        vendas.add(v);
                    }
                }

                if (qtd > 0) {
                    compras.add(new Order(preco, qtd));
                }

            } else if (tipo == 'V') {

                while (!compras.isEmpty() && qtd > 0) {

                    Order c = compras.peek();

                    if (c.preco < preco) break;

                    compras.poll();

                    int q = Math.min(qtd, c.quantidade);

                    lucro += (c.preco - preco) * q;
                    total += q;

                    qtd -= q;
                    c.quantidade -= q;

                    if (c.quantidade > 0) {
                        compras.add(c);
                    }
                }

                if (qtd > 0) {
                    vendas.add(new Order(preco, qtd));
                }

            } else {
                System.out.println("Tipo invalido! Use C ou V.");
                i--; // repete a operação
            }
        }

        System.out.println("\n=== Resultado Final ===");
        System.out.println("Lucro: " + lucro);
        System.out.println("Acoes negociadas: " + total);
        System.out.println("Compras pendentes: " + compras.size());
        System.out.println("Vendas pendentes: " + vendas.size());

        sc.close();
    }
}
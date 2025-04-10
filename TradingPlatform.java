import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class TradingPlatform {
    private Market market;
    private Portfolio portfolio;

    public TradingPlatform() {
        market = new Market();
        portfolio = new Portfolio();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println("\nEnter a command (view, buy, sell, portfolio, exit):");
            command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "view":
                    market.displayMarket();
                    break;
                case "buy":
                    System.out.println("Enter stock symbol and quantity (e.g., AAPL 10):");
                    String[] buyInput = scanner.nextLine().split(" ");
                    String buySymbol = buyInput[0].toUpperCase();
                    int buyQuantity = Integer.parseInt(buyInput[1]);
                    Stock stockToBuy = market.getStock(buySymbol);
                    if (stockToBuy != null) {
                        portfolio.buyStock(buySymbol, buyQuantity);
                        System.out.printf("Bought %d shares of %s.%n", buyQuantity, stockToBuy.getName());
                    } else {
                        System.out.println("Stock not found.");
                    }
                    break;
                case "sell":
                    System.out.println("Enter stock symbol and quantity (e.g., AAPL 5):");
                    String[] sellInput = scanner.nextLine().split(" ");
                    String sellSymbol = sellInput[0].toUpperCase();
                    int sellQuantity = Integer.parseInt(sellInput[1]);
                    portfolio.sellStock(sellSymbol, sellQuantity);
                    break;
                case "portfolio":
                    portfolio.displayPortfolio();
                    break;
                case "exit":
                    System.out.println("Exiting the trading platform.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid command.");
            }
        }
    }

    public static void main(String[] args) {
        TradingPlatform platform = new TradingPlatform();
        platform.start();
    }
class Stock {
    private String name;
    private double price;

    public Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Market {
    private Map<String, Stock> stocks;

    public Market() {
        stocks = new HashMap<>();
        // Adding some initial stocks
        stocks.put("AAPL", new Stock("Apple Inc.", 150.00));
        stocks.put("GOOGL", new Stock("Alphabet Inc.", 2800.00));
        stocks.put("AMZN", new Stock("Amazon.com Inc.", 3400.00));
    }

    public Stock getStock(String symbol) {
        return stocks.get(symbol);
    }

    public void displayMarket() {
        System.out.println("Available Stocks:");
        for (Stock stock : stocks.values()) {
            System.out.printf("%s: $%.2f%n", stock.getName(), stock.getPrice());
        }
    }
}

class Portfolio {
    private Map<String, Integer> holdings;

    public Portfolio() {
        holdings = new HashMap<>();
    }

    public void buyStock(String symbol, int quantity) {
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
    }

    public void sellStock(String symbol, int quantity) {
        if (holdings.containsKey(symbol) && holdings.get(symbol) >= quantity) {
            holdings.put(symbol, holdings.get(symbol) - quantity);
            if (holdings.get(symbol) == 0) {
                holdings.remove(symbol);
            }
        } else {
            System.out.println("Not enough shares to sell.");
        }
    }

    public void displayPortfolio() {
        System.out.println("Your Portfolio:");
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            System.out.printf("Stock: %s, Quantity: %d%n", entry.getKey(), entry.getValue());
        }
    }
}
}

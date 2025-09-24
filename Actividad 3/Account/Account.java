package cuenta;

public class Account {
    
    private String id;
    private String name;
    private int balance = 0;
  
    public Account(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Account(String id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    // Agregar monto
    public int credit(int amount) {
        balance += amount;
        return balance;
    }

    // Retirar monto 
    public int debit(int amount) {
        if (amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Amount exceeded balance");
        }
        return balance;
    }

    // Transferir dinero a otra cuenta 
    public int transferTo(Account another, int amount) {
        if (amount <= balance) {
            this.balance -= amount;
            another.credit(amount);
        } else {
            System.out.println("Amount exceeded balance");
        }
        return balance;
    }
    
    @Override
    public String toString() {
        return "Cuenta con id:" + id + " del titular " + name + ",tiene un balance de" + balance +"" ;
    }
}

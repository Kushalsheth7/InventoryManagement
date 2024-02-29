import java.util.*;

class InventoryManagement {
    static LinkedList inventory;
    static Stack stock;
    static Queue orders;

    static int id;
    String name;
    int quantity;
    double price;

    public InventoryManagement(LinkedList inventory, Stack stock, Queue orders, String name, int quantity,
            double price) {
        this.inventory = inventory;
        this.stock = stock;
        this.orders = orders;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public InventoryManagement(String name, int quantity, double price) {
        this.id++;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public static int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String toString() {
        return "InventoryManagement [id=" + id + ",name=" + name + ", quantity=" + quantity + ", price=" + price + "]";
    }

    public static void main(String[] args) {
        inventory = new LinkedList();
        stock = new Stack();
        orders = new Queue();

        login();
    }

    static void login() {

        System.out.println();
        System.out.println("********* Inventory Management System **********");
        System.out.println();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username ");
        String username = sc.nextLine();
        if (username.equalsIgnoreCase("Manager")) {
            System.out.println("Enter password ");
            String password = sc.nextLine();
            if (password.equalsIgnoreCase("zxcvb")) {
                Manager.manager();
            } else {
                System.out.println("Password and username doesn't match please enter again ");
                login();
            }
        } else if (username.equalsIgnoreCase("Customer")) {
            Customer.customer();
        } else {
            System.out.println("Enter correct username :");
            login();
        }
    }
}

// Customer.java

class Customer extends InventoryManagement {
    static LinkedList inventory;
    static Stack stock;
    static Queue orders;

    public Customer(LinkedList inventory, Stack stock, Queue orders, String name, int quantity, double price) {
        super(inventory, stock, orders, name, quantity, price);
    }

    static void customer() {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. Create New Order (Queue)");
            System.out.println("2. Show Inventory");
            System.out.println("3. Back to login ");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter the name of the item to order: ");
                    String orderItemName = sc.nextLine();
                    System.out.print("Enter the quantity to order: ");
                    int orderQuantity = sc.nextInt();
                    InventoryManagement orderItem = inventory.search(orderItemName);
                    int id = InventoryManagement.getId();
                    if (orderItem != null) {
                        orderItem.quantity += orderQuantity;
                        orders.enqueue(new InventoryManagement(orderItemName, orderQuantity, orderItem.price));
                        System.out.println("Order created: " + orderItem.name);
                    } else {
                        System.out.println("Item not found in inventory.");
                    }
                    break;
                case 2:
                    System.out.println("\nInventory:");
                    try {
                        Manager.orders.display();
                    } catch (Exception e) {
                        System.out.println("Empty");
                    }
                    break;
                case 3:
                    InventoryManagement.login();
                    break;
                case 4:
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}

// Manager.java

class Manager extends InventoryManagement {
    static LinkedList inventory;
    static Stack stock;
    static Queue orders;

    public Manager(LinkedList inventory, Stack stock, Queue orders, String name, int quantity, double price) {
        super(inventory, stock, orders, name, quantity, price);
        // TODO Auto-generated constructor stub
    }

    static void manager() {

        Scanner sc = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("\nManager Menu:");
                System.out.println("1. Add Item to Inventory");
                System.out.println("2. Remove Item from Inventory");
                System.out.println("3. Search for an Item (Linked List)");
                System.out.println("4. Show Inventory");
                System.out.println("5. Product which is in low stock");
                System.out.println("6. Back to login ");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter product id: ");
                        int id = sc.nextInt();
                        System.out.print("Enter the name of the item to add: ");
                        sc.nextLine();
                        String addItemName = sc.nextLine();
                        System.out.print("Enter the quantity: ");
                        int itemQuantity = sc.nextInt();

                        System.out.print("Enter the price: ");
                        double itemPrice = sc.nextDouble();
                        InventoryManagement quantity = new InventoryManagement(addItemName, itemQuantity,
                                itemPrice);
                        orders.enqueueInOrder(quantity);
                        InventoryManagement newItem = new InventoryManagement(addItemName, itemQuantity, itemPrice);
                        inventory.add(newItem);
                        orders.enqueue(newItem);
                        break;
                    case 2:
                        System.out.print("Enter the name of the item to remove: ");
                        String removeItemName = sc.nextLine();
                        InventoryManagement removedItem = inventory.search(removeItemName);

                        if (removedItem != null) {
                            inventory.remove(removeItemName);
                            System.out.println("Removed: " + removedItem.name);
                        } else {
                            System.out.println("Item not found in inventory.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter the name of the item to search for: ");
                        String itemSearchName = sc.nextLine();
                        InventoryManagement foundItem = inventory.search(itemSearchName);

                        if (foundItem != null) {
                            System.out.println("Item found:");
                            System.out.println(
                                    "Item: " + foundItem.name + ", Quantity: " + foundItem.quantity + ", Price: "
                                            + foundItem.price);
                        } else {
                            System.out.println("Item not found in inventory.");
                        }
                        break;
                    case 4:
                        System.out.println("\nInventory:");
                        inventory.display();
                        break;
                    case 5:
                        System.out.print("Product which should be ordered is : ");
                        System.out.print(orders.dequeueQuantity());
                        break;
                    case 6:
                        InventoryManagement.login();
                        break;
                    case 7:
                        sc.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        } catch (InputMismatchException a) {
            System.out.println(a);
            manager();
        }
    }
}

class Node {
    InventoryManagement data;
    Node link;

    public Node(InventoryManagement item) {
        this.data = item;
        this.link = null;
    }
}

class LinkedList {
    Node first;

    public void add(InventoryManagement item) {
        Node newNode = new Node(item);
        newNode.link = first;
        first = newNode;
    }

    public void display() {

        Node temp = first;
        while (temp != null) {
            System.out.println(temp.data.toString());
            temp = temp.link;
        }
    }

    public void remove(String itemName) {
        if (first == null) {
            return;
        }

        if (first.data.name.equalsIgnoreCase(itemName)) {
            first = first.link;
            return;
        }

        Node temp = first;
        while (temp.link != null) {
            if (temp.link.data.name.equalsIgnoreCase(itemName)) {
                temp.link = temp.link.link;
                return;
            }
            temp = temp.link;
        }
    }

    public InventoryManagement search(String itemName) {
        Node temp = first;
        while (temp != null) {
            if (temp.data.name.equalsIgnoreCase(itemName)) {
                return temp.data;
            }
            temp = temp.link;
        }
        return null;
    }
}

class Queue {
    Node front, rear;

    public void enqueue(InventoryManagement item) {
        Node newNode = new Node(item);
        if (front == null) {
            front = rear = newNode;
        } else {
            rear.link = newNode;
            rear = newNode;
        }
    }

    public InventoryManagement dequeue() {
        if (front == null) {
            System.out.println("Queue is empty.");
            return null;
        }
        InventoryManagement item = front.data;
        front = front.link;
        if (front == null) {
            rear = null;
        }
        return item;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public void display() {
        if (front == null) {
            System.out.println("Nothing to show in inventory (Empty)");
            return;
        }
        Node temp = front;
        while (temp != null) {
            System.out.println(temp.data.toString());
            temp = temp.link;
        }
    }

    public void enqueueInOrder(InventoryManagement Quan) {
        Node newNode = new Node(Quan);

        if (front == null || Quan.quantity <= front.data.quantity) {
            newNode.link = front;
            front = newNode;
        } else {
            Node temp = front;
            while (temp.link != null && Quan.quantity > temp.link.data.quantity) {
                temp = temp.link;
            }
            newNode.link = temp.link;
            temp.link = newNode;
        }
        if (rear == null) {
            rear = newNode;
        }
    }

    public String dequeueQuantity() {
        if (front == null) {
            System.out.println("Queue is empty.");
            return null;
        }
        String quantity = front.data.toString();
        front = front.link;
        if (front == null) {
            rear = null;
        }

        return quantity.toString();
    }
}

class Stack {
    Node top;

    public void push(InventoryManagement item) {
        Node newNode = new Node(item);
        newNode.link = top;
        top = newNode;
    }

    public InventoryManagement pop() {
        if (top == null) {
            System.out.println("Stack is empty.");
            return null;
        }
        InventoryManagement item = top.data;
        top = top.link;
        return item;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void display() {
        Node current = top;
        while (current != null) {
            System.out.println(current.data.toString());
            current = current.link;
        }
    }
}
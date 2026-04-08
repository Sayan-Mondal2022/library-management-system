class Book{

}

class BookLocation{
    private int shelf_id;
    private String section, branch;
}

class BookItem{
    private int barcode, isbn, shelf_id;
    private String status;
}

class BookLoan{
    private int loan_id, barcode, user_id;
    private String issue_date, due_date, return_date;
}

class User{
    private String userName, phoneNo, email;
    private String address;
    private int user_id;
}

class Account{
    private int account_id, user_id;
    private String type, passwordHash;
}

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }
}

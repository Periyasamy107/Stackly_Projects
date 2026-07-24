public class Customer {

    private int customerId;
    private String customerName;
    private String mobileNumber;

    public Customer() {}

    public Customer(int customerId, String customerName, String mobileNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void displayCustomer() {
        System.out.println("Customer ID : " + customerId);
        System.out.println("Customer Name : " + customerName);
        System.out.println("Mobile Number : " + mobileNumber);
    }

    @Override
    public String toString() {
        return "Customer {" +
                "CustomerID = " + customerId +
                ", CustomerName = " + customerName +
                ", Mobile Number = " + mobileNumber + " }";
    }

}

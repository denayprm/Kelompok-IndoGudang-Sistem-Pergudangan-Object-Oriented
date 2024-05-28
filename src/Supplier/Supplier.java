package Supplier;

public class Supplier extends DataSupplier {
    private String idSupplier;

    public Supplier(String idSupplier, String namaPT, String noTelp, String alamatPT) {
        super(namaPT, noTelp, alamatPT);
        this.idSupplier = "SP" + idSupplier;
    }

    public String getIdSupplier() {
        return idSupplier;
    }

    @Override
    public String showData() {
        return "============ Data Supplier ==========\n" +
                "ID Supplier \t: " +idSupplier+ "\n" +
                super.showData() +"\n"+
                "=====================================";
    }
}

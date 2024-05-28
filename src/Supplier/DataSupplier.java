package Supplier;

public class DataSupplier {
    protected String namaPT;
    protected String noTelp;
    protected String alamatPT;
    public DataSupplier(String namaPT, String noTelp, String alamatPT) {
        this.namaPT = namaPT;
        this.noTelp = "021-" + noTelp;
        this.alamatPT = alamatPT;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public String showData() {
        return "Nama PT \t\t: " +namaPT+ "\n" +
                "No. Telp \t\t: " +noTelp+ "\n" +
                "Alamat \t\t\t: " +alamatPT;
    };

}

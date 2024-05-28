package Barang;

public class Elektronik extends Barang {
    private final String kategori = "Elektronik";
    public Elektronik(String kodeBarang, String namaBarang, int stok) {
        super(kodeBarang, namaBarang, stok);
        uniqueCode();
    }
    public String getKategori() {
        return kategori;
    }
    @Override
    public void uniqueCode() {
        this.kodeBarang = "EL" + getKodeBarang();
    }

    @Override
    public void kurangiStok(int jumlah) {
        if (jumlah > getStok()) {
            System.out.println("Jumlah yang dikeluarkan melebihi " +
                    "stok.");
        } else {
            this.stok -= jumlah;
            System.out.println("Stok telah dikurangi.\n");
            System.out.println(infoBarang());
        }
    }

    @Override
    public String infoBarang() {
        return "============ Data Barang ============\n" +
                "Kode Barang \t: " +getKodeBarang()+ "\n" +
                "Nama Barang \t: " +getNamaBarang()+ "\n" +
                "Stok Barang \t: " +getStok()+ "\n" +
                "Kategori Barang : " +getKategori()+ "\n" +
                "=====================================";
    }
}

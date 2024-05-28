package Barang;

public abstract class Barang {
    protected String kodeBarang;
    private String namaBarang;
    protected int stok;

    public Barang(String kodeBarang, String namaBarang, int stok) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.stok = stok;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public int getStok() {
        return stok;
    }


    public abstract void uniqueCode();
    public abstract void kurangiStok(int jumlah);
    public abstract String infoBarang();
}

package Warehouse;

import Barang.Barang;

import java.util.HashMap;
import java.util.Map;

public class Gudang {
    Map<String, Inventory> dataInventory;
    private String kodeGudang;
    public Gudang() {
        this.dataInventory = new HashMap<>();
    }
    public void setKodeGudang(String kodeGudang) {
        this.kodeGudang = "AREA" + kodeGudang;
    }
    public String getKodeGudang() {
        return kodeGudang;
    }

    public void simpanBarang(Inventory inventory) {
        dataInventory.put(getKodeGudang(), inventory);
    }

    public void kurangiBarang(String kodeBarang, int jumlah) {
        Barang barang = cariBarang(kodeBarang);
        if (barang != null) {
            if (barang.getStok() < jumlah) {
                System.out.println("Jumlah yang dikeluarkan melebihi stok.");
            } else {
                barang.kurangiStok(jumlah);
                System.out.println("Stok barang berhasil dikurangi sebanyak " + jumlah);
            }
        } else {
            System.out.println("Barang dengan kode " + kodeBarang +
                    " tidak ditemukan.");
        }
    }

    public void keluarkanBarang(Barang barang) {
        try {
            for (String kodeGudang : dataInventory.keySet()) {
                Inventory inventory = dataInventory.get(kodeGudang);
                if (inventory.cariBarang(barang.getKodeBarang()) != null) {
                    dataInventory.remove(kodeGudang);
                    System.out.println("Barang dengan kode " + barang.getKodeBarang() +
                            " berhasil dikeluarkan dari gudang.");
                    return;
                }
            }
            System.out.println("Barang dengan kode " + barang.getKodeBarang() +
                    " tidak ditemukan di gudang.");
        } catch (Exception e) {
            System.out.println("Exception: " +e);
        }
    }

    public Barang cariBarang(String kodeBarang) {
        for (String kodeGudang : dataInventory.keySet()) {
            Inventory inventory = dataInventory.get(kodeGudang);
            Barang barang = inventory.cariBarang(kodeBarang);
            if (barang != null) {
                return barang;
            }
        }
        return null;
    }

    public void dataBarang() {
        for (String kodeGudang : dataInventory.keySet()) {
            System.out.println("\nKode Gudang : " +kodeGudang);
            Inventory inventory = dataInventory.get(kodeGudang);
            inventory.tampilkanData();
        }
    }

    public void dataSupplier() {
        for (String kodeGudang : dataInventory.keySet()) {
            Inventory inventory = dataInventory.get(kodeGudang);
            inventory.dataSupplier();
        }
    }
}

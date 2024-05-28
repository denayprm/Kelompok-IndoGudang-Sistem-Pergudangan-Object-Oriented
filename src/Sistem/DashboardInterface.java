package Sistem;

import Admin.Admin;
import Warehouse.Gudang;

public interface DashboardInterface {
    boolean login(Admin admin);
    void penerimaanBarang(Gudang gudang);
    void pengeluaranBarang(Gudang gudang);
    void dataBarang(Gudang gudang);
    void dataSupplier(Gudang gudang);
    void logout();
}

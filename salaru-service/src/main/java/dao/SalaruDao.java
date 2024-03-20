package dao;

import entity.Salaru;
import service.DiscountService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SalaruDao {
    private static final SalaruDao INSTANCE = new SalaruDao();
    private final List<Salaru> salaruList = new ArrayList<>();
    private final DiscountService discountService = new DiscountService();
    private AtomicInteger key = new AtomicInteger();

    public SalaruDao() {
        saveSalaru(Salaru.builder().name("Engine repair").price(220.0).build());
        saveSalaru(Salaru.builder().name("Brake repair").price(100.0).build());
        saveSalaru(Salaru.builder().name("Gearbox repair").price(200.0).build());
        saveSalaru(Salaru.builder().name("Wheel repair").price(56.0).build());
        saveSalaru(Salaru.builder().name("Electrical repair").price(150.0).build());
        saveSalaru(Salaru.builder().name("Chassis repair").price(80.0).build());
    }

    public void saveSalaru(Salaru salaru) {
        salaru.setId(key.incrementAndGet());
        salaruList.add(salaru);
    }

    public Salaru getSalaruById(int id) {
        Salaru salaru = salaruList.stream()
                .filter(salarus -> salarus.getId() == id)
                .findFirst()
                .orElse(null);
        if(salaru != null) setSalaruPriceWithDiscount(salaru);
        return salaru;
    }

    public List<Salaru> getAllSalaru() {
        List<Salaru> getAll = new ArrayList<>(salaruList);
        getAll.forEach(this::setSalaruPriceWithDiscount);
        return getAll;
    }

    public void update(int id, Salaru updateSal) {
        Salaru salaru = getSalaruById(id);
        if (updateSal != null && salaru != null) {
            salaru.setName(updateSal.getName());
            salaru.setPrice(updateSal.getPrice());
            salaru.setId(key.incrementAndGet());
            delete(id);
        }
    }

    public Salaru delete(int id) {
        Iterator<Salaru> iterator = salaruList.iterator();
        while (iterator.hasNext()) {
            Salaru salaru = iterator.next();
            if (salaru.getId() == id) {
                iterator.remove();
                return salaru;
            }
        }
        System.out.println("product not found");
        return null;
    }

    public static SalaruDao getInstance() {
        return INSTANCE;
    }
    private void setSalaruPriceWithDiscount(Salaru salaru) {
        salaru.setPrice(salaru.getPrice() - (salaru.getPrice() * getPriceWithDiscount()));
    }
    private double getPriceWithDiscount() {
        return discountService.getPriceWithDiscount();
    }
}

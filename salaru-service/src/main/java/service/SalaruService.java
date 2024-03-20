package service;

import dao.SalaruDao;
import entity.Salaru;

import java.util.List;

public class SalaruService {
    private final SalaruDao salaruDao =SalaruDao.getInstance();

    public void saveSalaru(Salaru salaru){
        salaruDao.saveSalaru(salaru);
    }
    public Salaru getSalaruById(int id){
        return salaruDao.getSalaruById(id);
    }
    public List<Salaru> getAllSalaru(){
        return salaruDao.getAllSalaru();
    }
    public void update(int id, Salaru updateSal){
        salaruDao.update(id, updateSal);
    }
    public Salaru delete(int id){
        return salaruDao.delete(id);
    }
}

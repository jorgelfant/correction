package io.rtx.sales;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SalesService {

    @Autowired
    private SalesRepository repo;

    public SalesEntity add(SalesInput sales) {
        SalesEntity entity = new SalesEntity();
        updateEntity(entity, sales);
        return repo.save(entity);
    }

    public boolean delete(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<SalesEntity> findByCountry(String country) {
        return repo.findByCountry(country);
    }

    //*******************************************************************************************************************
    public List<SalesEntity> findByDate(LocalDate start, LocalDate end) {
        return repo.findByDateBetween(start, end);
    }
    //*******************************************************************************************************************

    public Optional<SalesEntity> findById(long id) {
        return repo.findById(id);
    }

    public List<SalesEntity> getAll() {
        return repo.findAll();
    }

    public List<String> listCountry() {
        return repo.listCountry();
    }

    public Optional<SalesEntity> update(long id, SalesInput sales) {
        Optional<SalesEntity> opt = repo.findById(id);

        if (opt.isPresent()) {
            SalesEntity entity = opt.get();
            updateEntity(entity, sales);
        }
        return opt;
    }

    private void updateEntity(SalesEntity entity, SalesInput input) {
        entity.setCountry(input.getCountry());
        entity.setDate(input.getDate());
        entity.setProduct(input.getProduct());
        entity.setProfit(input.getProfit());
        entity.setValue(input.getValue());
    }


    // ************************* 2.2 nouvelle fonctionnalité ***********************************************************
    public List<SalesEntity> findByProduct(String product) {
        List<SalesEntity> list = new ArrayList<>();
        for (SalesEntity salesEntity : this.getAll()) {
            if (salesEntity.getProduct().equals(product)) {
                list.add(salesEntity);
            }
        }
        return list;
    }

    // ************************* 2.3 nouvelle fonctionnalité ***********************************************************
    public List<String> listProduct() {
        return repo.listProduct();
    }

    // ************************* 2.5 nouvelle fonctionnalité ***********************************************************
    public Optional<SalesEntity> partialUpdate(long id, SalesPartialInput input) {
        Optional<SalesEntity> opt = repo.findById(id);

        if (opt.isPresent()) {
            SalesEntity entity = opt.get();

            if (input.getCountry() != null && !input.getCountry().isEmpty()) {
                entity.setCountry(input.getCountry());
            }
            if (input.getDate() != null) {
                entity.setDate(input.getDate());
            }
            if (input.getProduct() != null && !input.getCountry().isEmpty()) {
                entity.setProduct(input.getProduct());
            }
            if (input.getProfit() != null) {
                entity.setProfit(input.getProfit());
            }
            if (input.getValue() != null) {
                entity.setValue(input.getValue());
            }
        }
        return opt;
    }

    // ************************* 2.6 nouvelle fonctionnalité ***********************************************************
    public List<List<SalesEntity>> search(String pays, String product, LocalDate start, LocalDate end) {
        /*J'aurais pu aussi faire un map et utiliser juste un parametre dans la methode qui retournerait
          seulement la liste en fonction de la key saisie qui pourrait etre pays , product etc

        public List<SalesEntity> search(String critere, String pays){ //pays, France par exemple
             Map<String, String> map  = new HashMap<>();

             map.put("pays", findByProduct(String product));
             map.put("product", findByCountry(String country));

             return map.get("critere")


        }
        */
        List<List<SalesEntity>> list = new ArrayList<>();
        list.add(findByCountry(pays));
        list.add(findByProduct(product));
        if (end == null) {
            list.add(findByDate(start, LocalDate.now()));
        } else {
            list.add(findByDate(start, end));
        }
        return list;
    }
}

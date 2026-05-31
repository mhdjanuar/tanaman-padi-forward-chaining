package application.dao;

import application.models.GejalaModel;
import java.util.List;

/**
 *
 * @author mhdja
 */
public interface GejalaDao {

    List<GejalaModel> findAll();

    GejalaModel findById(int id);

    int create(GejalaModel gejala);

    int update(GejalaModel gejala);

    int deleteGejala(int id);
}
package application.dao;

import application.models.PenyakitModel;
import java.util.List;

public interface PenyakitDao {

    List<PenyakitModel> findAll();

    PenyakitModel findById(int id);

    int create(PenyakitModel disease);

    int update(PenyakitModel disease);

    int delete(int id);
}
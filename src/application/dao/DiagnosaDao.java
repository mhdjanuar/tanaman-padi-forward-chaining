package application.dao;

import application.models.DiagnosaModel;
import java.util.List;

public interface DiagnosaDao {

    List<DiagnosaModel> diagnose(List<Integer> symptomIds);

}
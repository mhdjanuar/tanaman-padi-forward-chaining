package application.daoimpl;

import application.dao.DiagnosaDao;
import application.models.DiagnosaModel;
import application.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class DiagnosaDaoImpl implements DiagnosaDao {

    private Connection dbConnection = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;
    private String query;

    public DiagnosaDaoImpl() {
        dbConnection = DatabaseUtil.getInstance().getConnection();
    }

    @Override
    public List<DiagnosaModel> diagnose(
        List<Integer> symptomIds) {

        List<DiagnosaModel> results =
                new ArrayList<>();

        try {

            StringBuilder placeholders =
                    new StringBuilder();

            for (int i = 0; i < symptomIds.size(); i++) {

                placeholders.append("?");

                if (i < symptomIds.size() - 1) {
                    placeholders.append(",");
                }
            }

            query =
                "SELECT " +
                "d.name, " +
                "d.description, " +
                "d.solution, " +
                "COUNT(rd.id) AS matched_symptoms, " +
                "(SELECT COUNT(*) " +
                " FROM rule_details rd2 " +
                " WHERE rd2.rule_id = r.id) " +
                "AS total_symptoms " +
                "FROM rules r " +
                "JOIN diseases d " +
                "ON d.id = r.disease_id " +
                "JOIN rule_details rd " +
                "ON rd.rule_id = r.id " +
                "WHERE rd.symptom_id IN (" +
                placeholders +
                ") " +
                "GROUP BY d.name, " +
                "d.description, " +
                "d.solution, " +
                "r.id " +
                "ORDER BY matched_symptoms DESC";

            pstmt = dbConnection.prepareStatement(query);

            for (int i = 0; i < symptomIds.size(); i++) {

                pstmt.setInt(
                    i + 1,
                    symptomIds.get(i)
                );
            }

            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {

                DiagnosaModel data =
                        new DiagnosaModel();

                data.setDiseaseName(
                    resultSet.getString("name")
                );

                data.setDescription(
                    resultSet.getString("description")
                );

                data.setSolution(
                    resultSet.getString("solution")
                );

                data.setMatchedSymptoms(
                    resultSet.getInt("matched_symptoms")
                );

                data.setTotalSymptoms(
                    resultSet.getInt("total_symptoms")
                );

                results.add(data);
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {

            closeStatement();
        }

        return results;
    }

    private void closeStatement() {

        try {

            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
            }

            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
}
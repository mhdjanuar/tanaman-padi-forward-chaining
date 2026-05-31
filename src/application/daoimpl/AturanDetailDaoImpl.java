/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.daoimpl;

import application.dao.AturanDetailDao;
import application.models.AturanDetailModel;
import application.utils.DatabaseUtil;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mhdja
 */
public class AturanDetailDaoImpl implements AturanDetailDao {
    
    private Connection dbConnection = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;
    private String query;
    
    public AturanDetailDaoImpl() {
        dbConnection = DatabaseUtil.getInstance().getConnection();
    }
    
    private void closeStatement() {
        try {
            if(pstmt != null){
                pstmt.close();
                pstmt = null;
            }
            if(resultSet != null){
                resultSet.close();
                resultSet = null;
            }   
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int create(AturanDetailModel detail) {
        int result = 0;

        try {
            query =
                "INSERT INTO rule_details " +
                "(rule_id, symptom_id) " +
                "VALUES (?, ?)";

            pstmt = dbConnection.prepareStatement(query);

            pstmt.setInt(1, detail.getRuleId());
            pstmt.setInt(2, detail.getSymptomId());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }

        return result;
    }

    @Override
    public int deleteByRuleId(int ruleId) {
        int result = 0;

        try {

            query =
                "DELETE FROM rule_details " +
                "WHERE rule_id = ?";

            pstmt = dbConnection.prepareStatement(query);

            pstmt.setInt(1, ruleId);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }

        return result;
    }

    @Override
    public List<AturanDetailModel> findByRuleId(int ruleId) {
         List<AturanDetailModel> listData = new ArrayList<>();

        try {

            query =
                "SELECT rd.*, " +
                "s.code, " +
                "s.name " +
                "FROM rule_details rd " +
                "JOIN symptoms s " +
                "ON s.id = rd.symptom_id " +
                "WHERE rd.rule_id = ?";

            pstmt = dbConnection.prepareStatement(query);

            pstmt.setInt(1, ruleId);

            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {

                AturanDetailModel detail =
                    new AturanDetailModel();

                detail.setId(
                    resultSet.getInt("id")
                );

                detail.setRuleId(
                    resultSet.getInt("rule_id")
                );

                detail.setSymptomId(
                    resultSet.getInt("symptom_id")
                );

                detail.setSymptomCode(
                    resultSet.getString("code")
                );

                detail.setSymptomQuestion(
                    resultSet.getString("name")
                );

                listData.add(detail);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }

        return listData;
    }
    
}

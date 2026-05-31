/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.daoimpl;

import application.dao.AturanDao;
import application.models.AturanModel;
import application.utils.DatabaseUtil;
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
public class AturanDaoImpl implements AturanDao {
    
    private Connection dbConnection = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;
    private String query;
    
    public AturanDaoImpl() {
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
    public List<AturanModel> findAll() {
        List<AturanModel> listData = new ArrayList<>();

    try {

        query =
            "SELECT r.id, r.rule_name, r.disease_id, " +
            "d.name AS disease_name " +
            "FROM rules r " +
            "JOIN diseases d ON d.id = r.disease_id";

        pstmt = dbConnection.prepareStatement(query);

        resultSet = pstmt.executeQuery();

        while(resultSet.next()) {

            AturanModel rule = new AturanModel();

            rule.setId(
                resultSet.getInt("id")
            );

            rule.setRuleName(
                resultSet.getString("rule_name")
            );

            rule.setDiseaseId(
                resultSet.getInt("disease_id")
            );

            rule.setDiseaseName(
                resultSet.getString("disease_name")
            );

            listData.add(rule);
        }

    } catch (SQLException e) {
        throw new RuntimeException(e);
    } finally {
        closeStatement();
    }

    return listData;
    }

    @Override
    public AturanModel findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int create(AturanModel rule) {
        try {
            query =
                "INSERT INTO rules " +
                "(rule_name, disease_id) " +
                "VALUES (?, ?)";

            pstmt = dbConnection.prepareStatement(
                query,
                Statement.RETURN_GENERATED_KEYS
            );

            pstmt.setString(
                1,
                rule.getRuleName()
            );

            pstmt.setInt(
                2,
                rule.getDiseaseId()
            );

            int result = pstmt.executeUpdate();

            resultSet = pstmt.getGeneratedKeys();

            if(resultSet.next()) {
                return resultSet.getInt(1);
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }
    }

    @Override
    public int update(AturanModel rule) {
        int result = 0;

        try {

            query =
                "UPDATE rules " +
                "SET rule_name = ?, " +
                "disease_id = ? " +
                "WHERE id = ?";

            pstmt = dbConnection.prepareStatement(query);

            pstmt.setString(
                1,
                rule.getRuleName()
            );

            pstmt.setInt(
                2,
                rule.getDiseaseId()
            );

            pstmt.setInt(
                3,
                rule.getId()
            );

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }

        return result;
    }

    @Override
    public int delete(int id) {
        int result = 0;

        try {

            query =
                "DELETE FROM rules " +
                "WHERE id = ?";

            pstmt = dbConnection.prepareStatement(query);

            pstmt.setInt(1, id);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }

        return result;
    }
    
}

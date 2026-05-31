/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.daoimpl;

import application.dao.PenyakitDao;
import application.models.PenyakitModel;
import application.utils.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mhdja
 */
public class PenyakitDaoImpl implements PenyakitDao {
    private Connection dbConnection = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;
    private String query;
    
    public PenyakitDaoImpl() {
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
    public List<PenyakitModel> findAll() {
        List<PenyakitModel> listDataAll = new ArrayList<>();

        try {

            query = "SELECT * FROM diseases";

            pstmt = dbConnection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {

                PenyakitModel disease = new PenyakitModel();

                disease.setId(resultSet.getInt("id"));
                disease.setCode(resultSet.getString("code"));
                disease.setName(resultSet.getString("name"));
                disease.setDescription(resultSet.getString("description"));
                disease.setSolution(resultSet.getString("solution"));

                listDataAll.add(disease);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }

        return listDataAll;
    }

    @Override
    public PenyakitModel findById(int id) {
        PenyakitModel disease = null;

        try {

            query = "SELECT * FROM diseases WHERE id = ?";

            pstmt = dbConnection.prepareStatement(query);
            pstmt.setInt(1, id);

            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {

                disease = new PenyakitModel();

                disease.setId(resultSet.getInt("id"));
                disease.setCode(resultSet.getString("code"));
                disease.setName(resultSet.getString("name"));
                disease.setDescription(resultSet.getString("description"));
                disease.setSolution(resultSet.getString("solution"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }

        return disease;
    }

    @Override
    public int create(PenyakitModel disease) {
         try {
            query = "INSERT INTO diseases "
                  + "(code, name, description, solution) "
                  + "VALUES (?, ?, ?, ?)";

            pstmt = dbConnection.prepareStatement(
                    query,
                    Statement.RETURN_GENERATED_KEYS
            );

            pstmt.setString(1, disease.getCode());
            pstmt.setString(2, disease.getName());
            pstmt.setString(3, disease.getDescription());
            pstmt.setString(4, disease.getSolution());

            int result = pstmt.executeUpdate();

            resultSet = pstmt.getGeneratedKeys();

            if (resultSet.next()) {
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
    public int update(PenyakitModel disease) {
        int result = 0;

        try {

            query = "UPDATE diseases "
                  + "SET code = ?, "
                  + "name = ?, "
                  + "description = ?, "
                  + "solution = ? "
                  + "WHERE id = ?";

            pstmt = dbConnection.prepareStatement(query);

            pstmt.setString(1, disease.getCode());
            pstmt.setString(2, disease.getName());
            pstmt.setString(3, disease.getDescription());
            pstmt.setString(4, disease.getSolution());
            pstmt.setInt(5, disease.getId());

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

            query = "DELETE FROM diseases WHERE id = ?";

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

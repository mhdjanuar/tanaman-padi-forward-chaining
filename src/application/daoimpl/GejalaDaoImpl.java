/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.daoimpl;

import application.dao.GejalaDao;
import application.models.GejalaModel;
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
public class GejalaDaoImpl implements GejalaDao  {
    
    private Connection dbConnection = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;
    private String query;
    
    public GejalaDaoImpl() {
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
    public List<GejalaModel> findAll() {
        List<GejalaModel> listDataAll = new ArrayList<>();

        try {
            query = "SELECT * FROM symptoms";
            pstmt = dbConnection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                GejalaModel gejala = new GejalaModel();

                gejala.setId(resultSet.getInt("id"));
                gejala.setCode(resultSet.getString("code"));
                gejala.setName(resultSet.getString("name"));
                gejala.setDescription(resultSet.getString("description"));

                listDataAll.add(gejala);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }

        return listDataAll;
    }

    @Override
    public GejalaModel findById(int id) {
        GejalaModel gejala = null;

        try {
            query = "SELECT * FROM symptoms WHERE id = ?";

            pstmt = dbConnection.prepareStatement(query);
            pstmt.setInt(1, id);

            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                gejala = new GejalaModel();

                gejala.setId(resultSet.getInt("id"));
                gejala.setCode(resultSet.getString("code"));
                gejala.setQuestion(resultSet.getString("question"));
                gejala.setDescription(resultSet.getString("description"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }

        return gejala;
    }

    @Override
    public int create(GejalaModel gejala) {
        try {
            query = "INSERT INTO symptoms (code, name, description) VALUES (?, ?, ?)";

            pstmt = dbConnection.prepareStatement(
                    query,
                    Statement.RETURN_GENERATED_KEYS
            );

            pstmt.setString(1, gejala.getCode());
            pstmt.setString(2, gejala.getName());
            pstmt.setString(3, gejala.getDescription());

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
    public int update(GejalaModel gejala) {
        int result = 0;

        try {
            query = "UPDATE symptoms SET code = ?, name = ?, description = ? WHERE id = ?";

            pstmt = dbConnection.prepareStatement(query);

            pstmt.setString(1, gejala.getCode());
            pstmt.setString(2, gejala.getName());
            pstmt.setString(3, gejala.getDescription());
            pstmt.setInt(4, gejala.getId());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement();
        }

        return result;
    }

    @Override
    public int deleteGejala(int id) {
        int result = 0;

        try {
            query = "DELETE FROM symptoms WHERE id = ?";

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

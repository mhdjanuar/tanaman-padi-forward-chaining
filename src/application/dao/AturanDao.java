/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package application.dao;

import application.models.AturanModel;
import java.util.List;

/**
 *
 * @author mhdja
 */
public interface AturanDao {
    List<AturanModel> findAll();

    AturanModel findById(int id);

    int create(AturanModel rule);

    int update(AturanModel rule);

    int delete(int id);
}

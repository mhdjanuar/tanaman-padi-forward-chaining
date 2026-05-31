/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package application.dao;

import application.models.AturanDetailModel;
import java.util.List;

/**
 *
 * @author mhdja
 */
public interface AturanDetailDao {
    int create(AturanDetailModel detail);

    int deleteByRuleId(int ruleId);

    List<AturanDetailModel> findByRuleId(int ruleId);
}

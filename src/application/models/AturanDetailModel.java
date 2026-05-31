/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.models;

/**
 *
 * @author mhdja
 */
public class AturanDetailModel {
    private int id;
    private int ruleId;
    private int symptomId;

    // Untuk tampil di tabel
    private String symptomCode;
    private String symptomQuestion;

    public AturanDetailModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public int getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(int symptomId) {
        this.symptomId = symptomId;
    }

    public String getSymptomCode() {
        return symptomCode;
    }

    public void setSymptomCode(String symptomCode) {
        this.symptomCode = symptomCode;
    }

    public String getSymptomQuestion() {
        return symptomQuestion;
    }

    public void setSymptomQuestion(String symptomQuestion) {
        this.symptomQuestion = symptomQuestion;
    }
}

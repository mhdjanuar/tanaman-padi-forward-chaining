package application.models;

public class AturanModel {

    private int id;
    private String ruleName;
    private int diseaseId;

    // untuk ditampilkan di JTable
    private String diseaseName;

    public AturanModel() {
    }

    public AturanModel(int id, String ruleName, int diseaseId) {
        this.id = id;
        this.ruleName = ruleName;
        this.diseaseId = diseaseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }


    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }


    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }
}
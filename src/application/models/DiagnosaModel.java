package application.models;

public class DiagnosaModel {

    private String diseaseName;
    private String description;
    private String solution;

    private int matchedSymptoms;
    private int totalSymptoms;

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public int getMatchedSymptoms() {
        return matchedSymptoms;
    }

    public void setMatchedSymptoms(int matchedSymptoms) {
        this.matchedSymptoms = matchedSymptoms;
    }

    public int getTotalSymptoms() {
        return totalSymptoms;
    }

    public void setTotalSymptoms(int totalSymptoms) {
        this.totalSymptoms = totalSymptoms;
    }

    public double getPercentage() {

        if (totalSymptoms == 0) {
            return 0;
        }

        return ((double) matchedSymptoms / totalSymptoms) * 100;
    }
}
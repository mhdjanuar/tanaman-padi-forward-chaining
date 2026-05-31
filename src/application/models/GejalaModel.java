package application.models;

/**
 *
 * @author mhdja
 */
public class GejalaModel {

    private int id;
    private String code;
    private String question;
    private String description;
    private String name;

    public GejalaModel() {
    }

    public GejalaModel(int id, String code, String question, String description) {
        this.id = id;
        this.code = code;
        this.question = question;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
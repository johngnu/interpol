package rest.client;

/**
 *
 * @author desktop
 */
public class Notice {

    private String forename;
    private String date_of_birth;
    private String entity_id;
    private String name;
    private String[] nationalities;

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getNationalities() {
        return nationalities;
    }

    public void setNationalities(String[] nationalities) {
        this.nationalities = nationalities;
    }

    
}

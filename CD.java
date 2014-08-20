import java.io.Serializable;

public class CD implements Serializable {
    private String title;
    private String artist;

    private Person person;

    public CD(String aTitle) {
	this.title = aTitle;
    }

    public String getArtist() {
	return artist;
    }

    public void setArtist(String aName) {
	this.artist = aName;
    }

    public String getTitle() {
	return title;
    }

    public void setPerson(Person aPerson) {
	this.person = aPerson;
    }

    public Person getPerson() {
	return person;
    }

    public String toString() {
	return title;
    }
}

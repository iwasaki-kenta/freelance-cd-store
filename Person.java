import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private int cdLimit;

    public Person() {}

    public String getName() {
	return name;
    }

    public void setName(String aName) {
	this.name = aName;
    }

    public int getCDLimit() {
	return cdLimit;
    }

    public void setLimit(int maxNumber) {
	this.cdLimit = maxNumber;
    }

    public String toString() {
	return name;
    }
}
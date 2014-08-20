import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

public class CDStore implements Serializable {
    String name;
    ArrayList<CD> discsArray;
    ArrayList<Person> peopleArray;

    public CDStore(String name) {
	this.name = name;

	discsArray = new ArrayList<CD>();
	peopleArray = new ArrayList<Person>();
    }

    public String getName() {
	return name;
    }

    public ArrayList<CD> getDiscs() {
	return discsArray;
    }

    public ArrayList<Person> getPeople() {
	return peopleArray;
    }

    public void addDisc(CD aCD) {
	discsArray.add(aCD);
    }

    public void removeDisc(CD aCD) {
	discsArray.remove(aCD);
    }

    public void addPerson(Person aPerson) {
	peopleArray.add(aPerson);
    }

    public void removePerson(Person aPerson) {
	peopleArray.remove(aPerson);
    }

    public boolean takeOut(CD aCD, Person aPerson) {
	if (aCD != null && aPerson != null) {
	    if (aCD.getPerson() != null) {
		return false;
	    } else {
		if (getDiscsForPerson(aPerson).size() > aPerson.getCDLimit()) {
		    return false;
		}
		aCD.setPerson(aPerson);
		return true;
	    }
	} else {
	    return false;
	}
    }

    public boolean takeIn(CD aCD) {
	if (aCD != null) {
	    if (aCD.getPerson() != null) {
		aCD.setPerson(null);
		return true;
	    } else {
		return false;
	    }
	} else {
	    return false;
	}
    }

    public ArrayList<CD> getDiscsForPerson(Person aPerson) {
	ArrayList<CD> cds = new ArrayList<CD>();
	for (CD cd : discsArray) {
	    if (cd.getPerson() != null && cd.getPerson().equals(aPerson)) {
		cds.add(cd);
	    }
	}
	return cds;
    }

    public ArrayList<CD> getAvailableDiscs() {
	ArrayList<CD> cds = new ArrayList<CD>();
	for (CD cd : discsArray) {
	    if (cd.getPerson() == null) {
		cds.add(cd);
	    }
	}
	return cds;
    }

    public ArrayList<CD> getUnavailableDiscs() {
	ArrayList<CD> cds = new ArrayList<CD>();
	for (CD cd : discsArray) {
	    if (cd.getPerson() != null) {
		cds.add(cd);
	    }
	}
	return cds;
    }

    public String toString() {
	return name;
    }

    public String getStatus() {
	if (discsArray.size() != 0 && getAvailableDiscs().size() > 0) {
	    return "The shop is currently in stock.";
	} else {
	    return "The shop is currently out of stock.";
	}
    }

    public void printStatus() {
	System.out.println(getStatus());
    }

    public void saveCDStoreToSerialFile(String fileName) {
	try {
	    ObjectOutputStream oos = new ObjectOutputStream(
		    new BufferedOutputStream(new FileOutputStream(fileName)));
	    try {
		oos.writeObject(this);
	    } finally {
		oos.close();
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    public static CDStore loadCDStoreFromSerialFile(String fileName) {
	CDStore store = null;
	try {
	    ObjectInputStream os = new ObjectInputStream(
		    new BufferedInputStream(new FileInputStream(fileName)));
	    try {
		Object obj = os.readObject();
		if (obj instanceof CDStore) {
		    store = (CDStore) obj;
		}
	    } finally {
		os.close();
	    }
	} catch (Exception ex) {
	    System.out.println("File not found");
	}
	return store;
    }

    public void savePeopleArray(String fileName) {
	try {
	    FileWriter fw = new FileWriter(fileName);
	    BufferedWriter bw = new BufferedWriter(fw);
	    PrintWriter out = new PrintWriter(bw);
	    out.println("name,CDlimit");
	    for (int i = 0; i < peopleArray.size(); i++) {
		Person p = (Person) peopleArray.get(i);
		String aName = p.getName();
		String aLimit = String.valueOf(p.getCDLimit());
		out.println(aName + "," + aLimit);
	    }
	    out.close();
	    bw.close();
	    fw.close();
	} catch (Exception e) {
	    String errMessage = e.getMessage();
	    System.out.println("Error: " + errMessage);
	}
    }

    public void saveDiscsArray(String fileName) {
	try {
	    FileWriter fw = new FileWriter(fileName);
	    BufferedWriter bw = new BufferedWriter(fw);
	    PrintWriter out = new PrintWriter(bw);
	    out.println("title,artist,person");
	    for (int i = 0; i < discsArray.size(); i++) {
		CD cd = (CD) discsArray.get(i);
		String aTitle = cd.getTitle();
		String anArtist = cd.getArtist();
		Person aPerson = cd.getPerson();
		out.println(aTitle + "," + anArtist + "," + aPerson.getName());
	    }
	    out.close();
	    bw.close();
	    fw.close();
	} catch (Exception e) {
	    String errMessage = e.getMessage();
	    System.out.println("Error: " + errMessage);
	}
    }

    public void loadDiscsArray(String fileName) {
	try {
	    FileReader fr = new FileReader(fileName);
	    BufferedReader br = new BufferedReader(fr);
	    br.readLine();

	    String line = "";
	    while ((line = br.readLine()) != null) {
		String[] data = line.split(",");
		CD cd = new CD(data[0]);
		cd.setArtist(data[1]);

		for (Person p : peopleArray) {
		    if (p.getName().equals(data[2])) {
			cd.setPerson(p);
		    }
		}
		discsArray.add(cd);
	    }
	    fr.close();
	    br.close();
	} catch (Exception e) {
	    String errMessage = e.getMessage();
	    System.out.println("Error: " + errMessage);
	}
    }

    public void loadPeopleArray(String fileName) {
	try {
	    FileReader fr = new FileReader(fileName);
	    BufferedReader br = new BufferedReader(fr);
	    br.readLine();

	    String line = "";
	    while ((line = br.readLine()) != null) {
		String[] data = line.split(",");
		Person p = new Person();
		p.setName(data[0]);
		p.setLimit(Integer.valueOf(data[1]));
		peopleArray.add(p);
	    }
	    fr.close();
	    br.close();
	} catch (Exception e) {
	    String errMessage = e.getMessage();
	    System.out.println("Error: " + errMessage);
	}
    }
}

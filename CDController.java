public class CDController {
    public static void main(String[] args) {
	/** START OF TEST 1 **/
	CDStore cdStore = new CDStore("Multimedia Inc.");
	CD disc1 = new CD("Blues Clues");
	CD disc2 = new CD("Twilight");
	CD disc3 = new CD("2012");
	
	disc1.setArtist("Nickelodeon");
	disc2.setArtist("Mark Morgan");
	disc3.setArtist("Roland Emmerich");
	
	Person person1 = new Person();
	Person person2 = new Person();
	Person person3 = new Person();
	
	person1.setName("Nickel Joe");
	person2.setName("Timmy Doe");
	person3.setName("Smith Wesley");
	
	cdStore.addDisc(disc1);
	cdStore.addDisc(disc2);
	cdStore.addDisc(disc3);
	
	System.out.println(cdStore.getStatus());
	
	cdStore.takeOut(disc1, person1);
	
	System.out.println(cdStore.getStatus());
	
	cdStore.takeIn(disc1);
	
	System.out.println(cdStore.getStatus());
	
	cdStore.takeOut(disc2, person3);
	
	System.out.println(cdStore.getStatus());
	/** END OF TEST 1 **/
	
	/** START OF TEST 2 **/
	
	cdStore.saveCDStoreToSerialFile("testLibrary1.obj");
	
	CDStore testLibrary2 = CDStore.loadCDStoreFromSerialFile("testLibrary1.obj");
	System.out.println(testLibrary2);
	
	/** END OF TEST 2 **/
    }
}
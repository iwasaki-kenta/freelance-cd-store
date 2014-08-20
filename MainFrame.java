import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class MainFrame extends JFrame {
    CDStore store = new CDStore("CD Library");
    
    JPanel contentPane;
    JLabel lblStatus;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    MainFrame frame = new MainFrame();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame.
     */
    public MainFrame() {
	setTitle("CD Library");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 508, 194);
	setResizable(false);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);

	JButton btnOpen = new JButton("Open");
	btnOpen.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();

		fc.showOpenDialog(contentPane);
		store = CDStore.loadCDStoreFromSerialFile(fc.getSelectedFile()
			.getAbsolutePath());
		updateStatus();
	    }
	});
	btnOpen.setBounds(93, 11, 94, 23);
	contentPane.add(btnOpen);

	JButton btnNew = new JButton("New");
	btnNew.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		store = new CDStore(JOptionPane.showInputDialog(contentPane,
			"Please enter a new CD Store name."));
		updateStatus();
	    }
	});
	btnNew.setBounds(10, 11, 73, 23);
	contentPane.add(btnNew);

	JButton btnSave = new JButton("Save");
	btnSave.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();

		fc.showSaveDialog(contentPane);
		store.saveCDStoreToSerialFile(fc.getSelectedFile()
			.getAbsolutePath());
		updateStatus();
	    }
	});
	btnSave.setBounds(197, 11, 73, 23);
	contentPane.add(btnSave);

	JSeparator separator = new JSeparator();
	separator.setBounds(10, 45, 472, 4);
	contentPane.add(separator);

	JButton btnAddPerson = new JButton("Add Person");
	btnAddPerson.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		Person p = new Person();
		p.setName(JOptionPane.showInputDialog(contentPane,
			"Please enter the person's name."));
		try {
		    p.setLimit(Integer.valueOf(JOptionPane.showInputDialog(
			    contentPane, "Please enter the person's CD limit.")));
		} catch (Exception ex) {
		    JOptionPane.showMessageDialog(contentPane,
			    "The CD Limit you entered is not a number.");
		    return;
		}
		if (p.getName() == null || p.getCDLimit() == 0) {
		    JOptionPane.showMessageDialog(contentPane,
			    "You have entered invalid details of the person.");
		} else {
		    store.addPerson(p);
		}
		updateStatus();
	    }
	});
	btnAddPerson.setBounds(10, 60, 106, 23);
	contentPane.add(btnAddPerson);

	JButton btnRemovePerson = new JButton("Remove Person");
	btnRemovePerson.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		String name = JOptionPane.showInputDialog(contentPane,
			"Please enter the person's name.");
		if (name == null) {
		    JOptionPane.showMessageDialog(contentPane,
			    "You have entered no name.");
		} else {
		    Person person = null;
		    for (Person p : store.getPeople()) {
			if (p.getName().equalsIgnoreCase(name)) {
			    person = p;
			}
		    }
		    if (person == null) {
			JOptionPane.showMessageDialog(contentPane,
				"That person cannot be found.");
		    } else {
			for (CD cd : store.getDiscsForPerson(person)) {
			    store.takeOut(cd, person);
			}
			store.removePerson(person);
		    }
		}
		updateStatus();
	    }
	});
	btnRemovePerson.setBounds(353, 63, 129, 23);
	contentPane.add(btnRemovePerson);

	JButton btnAddCD = new JButton("Add CD");
	btnAddCD.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		CD cd = new CD(JOptionPane.showInputDialog(contentPane,
			"Please enter the CD's title."));
		cd.setArtist(JOptionPane.showInputDialog(contentPane,
			"Please enter the CD's artist."));
		if (cd.getTitle() == null || cd.getArtist() == null) {
		    JOptionPane.showMessageDialog(contentPane,
			    "You have entered invalid details for the CD.");
		} else {
		    store.addDisc(cd);
		}
		updateStatus();
	    }
	});
	btnAddCD.setBounds(10, 94, 106, 23);
	contentPane.add(btnAddCD);

	JButton btnRemoveCD = new JButton("Remove CD");
	btnRemoveCD.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		String title = JOptionPane.showInputDialog(contentPane,
			"Please enter the CD's title.");
		if (title == null) {
		    JOptionPane.showMessageDialog(contentPane,
			    "You have entered no title.");
		} else {
		    CD cd = null;
		    for (CD disc : store.getDiscs()) {
			if (disc.getTitle().equalsIgnoreCase(title)) {
			    cd = disc;
			}
		    }
		    if (cd == null) {
			JOptionPane.showMessageDialog(contentPane,
				"That CD cannot be found.");
		    } else {
			store.removeDisc(cd);
		    }
		}
		updateStatus();
	    }
	});
	btnRemoveCD.setBounds(353, 94, 129, 23);
	contentPane.add(btnRemoveCD);

	JButton btnTakeOut = new JButton("Take Out");
	btnTakeOut.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		String name = JOptionPane.showInputDialog(contentPane,
			"Please enter the person's name.");
		String title = JOptionPane.showInputDialog(contentPane,
			"Please enter the CD's title.");
		Person person = null;
		for (Person p : store.getPeople()) {
		    if (p.getName().equalsIgnoreCase(name)) {
			person = p;
		    }
		}
		CD cd = null;
		for (CD disc : store.getDiscs()) {
		    if (disc.getTitle().equalsIgnoreCase(title)) {
			cd = disc;
		    }
		}

		if (store.takeOut(cd, person)) {
		    JOptionPane.showMessageDialog(contentPane,
			    "The CD has been taken out.");
		} else {
		    JOptionPane
			    .showMessageDialog(
				    contentPane,
				    "Either the CD has already been taken out, or the information you've entered is invalid");
		}
		updateStatus();
	    }
	});
	btnTakeOut.setBounds(280, 11, 89, 23);
	contentPane.add(btnTakeOut);

	JButton btnTakeIn = new JButton("Take In");
	btnTakeIn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		String title = JOptionPane.showInputDialog(contentPane,
			"Please enter the CD's title.");
		CD cd = null;
		for (CD disc : store.getDiscs()) {
		    if (disc.getTitle().equalsIgnoreCase(title)) {
			cd = disc;
		    }
		}

		if (store.takeIn(cd)) {
		    JOptionPane.showMessageDialog(contentPane,
			    "The CD has been taken in.");
		} else {
		    JOptionPane
			    .showMessageDialog(contentPane,
				    "The CD either has already been taken in, or could not be found.");
		}
		updateStatus();
	    }
	});
	btnTakeIn.setBounds(379, 11, 103, 23);
	contentPane.add(btnTakeIn);
	
	lblStatus = new JLabel("Status:");
	lblStatus.setBounds(26, 141, 456, 14);
	contentPane.add(lblStatus);
	
	updateStatus();
    }
    
    private void updateStatus() {
	lblStatus.setText("Status: ".concat(store.getStatus()));
    }
}

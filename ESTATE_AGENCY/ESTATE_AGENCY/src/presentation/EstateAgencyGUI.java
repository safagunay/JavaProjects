package presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import domain.EstateAgency;
import domain.House;
import domain.Search;


public class EstateAgencyGUI implements ActionListener {
	
	EstateAgency estateAgency;
	
	JFrame mainFrame;
	
	JPanel mainFramePanel,menuPanel,
	orderHousePanel,addHousePanel,removeHousePanel,
	searchHousePanel;
	
	JRadioButton jrbnAscendingPrice,jrbnDescendingPrice;
	
	JButton jbnAddHouse,jbnRemoveHouse,jbnSearch,jbnRemoveFilters,jbnExit;
	
	JTextField jtfID;
	JTextField jtfPrice;
	JTextField jtfSize;
	JTextField jtfRooms;
	JTextField jtfBathRooms;
	JTextField jtfRemoveID;
	JCheckBox jcbAirConditioner;
	JTextField jtfminPrice;
	JTextField jtfmaxPrice ;
	JTextField jtfRoomFilter ;
	JTextField jtfminSize; 
	JTextField jtfmaxSize;
	
	JScrollPane tablePanel;
	JTable houseTable;
	String[] columnNames = {"ID", "PRICE", "SIZE", "ROOMS", "BRMS", "AIRCN"};
	
	
	public EstateAgencyGUI(EstateAgency estateAgency) {
		
		this.estateAgency = estateAgency;
		mainFrame = new JFrame("Estate Agency");
		mainFramePanel = new JPanel();
		mainFrame.setContentPane(mainFramePanel);
	}

	public void createAndShow() {
		
		mainFramePanel.setLayout(new GridLayout(0,2));
		
		createComponents();
		
		mainFrame.pack();
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}
	
	private void createComponents() {
		
		setTablePanel();
		setMenuPanel();
		
		mainFramePanel.add(tablePanel);
		mainFramePanel.add(menuPanel);
		
	}
	private void setTablePanel() {
		
		houseTable = new JTable();
		tablePanel = new JScrollPane(houseTable);
		updateTable();
	}
	
	private Object[] createRow(House house) {
		
    	Object[] houseRow = new Object[6];
    	
    	houseRow[0] = house.getID();
    	houseRow[1] = house.getPrice();
    	houseRow[2] = house.getSize();
    	houseRow[3] = house.getRooms();
    	houseRow[4] = house.getBathrooms();
    	houseRow[5] = house.isAirConditioner();
    	
    	return houseRow;
	}
	
	private void setMenuPanel() {
		
		menuPanel = new JPanel(new GridLayout(5, 1));
		
		setOrderHousePanel();
		setAddHousePanel();
		setRemoveHousePanel();
		setSearchPanel();
		jbnExit = new JButton("Exit");
		jbnExit.addActionListener(this);
		
		menuPanel.add(orderHousePanel);
		menuPanel.add(addHousePanel);
		menuPanel.add(removeHousePanel);
		menuPanel.add(searchHousePanel);
		menuPanel.add(jbnExit);
	}

	private void setSearchPanel() {
		
		searchHousePanel = new JPanel();
		searchHousePanel.setLayout(new GridBagLayout());
		searchHousePanel.setBorder(BorderFactory.createTitledBorder("Filters"));
		
		JLabel jlbMin = new JLabel("Min");
		JLabel jlbMax = new JLabel("Max");
		JLabel jlbPrice = new JLabel("Price:");
		JLabel jlbRooms = new JLabel("Rooms:");
		JLabel jlbSize = new JLabel("Size");
		
		jtfminPrice = new JTextField(5);
		jtfmaxPrice = new JTextField(5);
		jtfRoomFilter = new JTextField(5);
		jtfminSize = new JTextField(5);
		jtfmaxSize = new JTextField(5);
		
		jbnSearch = new JButton("Search");
		jbnRemoveFilters = new JButton("Remove Filters");
		
		GridBagConstraints gc = new GridBagConstraints();
		
		//First Column ///
		gc.anchor=GridBagConstraints.LINE_END;
		gc.gridx=0;
		
		gc.gridy=1;
		searchHousePanel.add(jlbPrice, gc);
		
		gc.gridy=2;
		searchHousePanel.add(jlbSize, gc);
		
		gc.gridy=3;
		searchHousePanel.add(jlbRooms,gc);
		
		gc.anchor=GridBagConstraints.LINE_START;
		gc.weighty=5;
		gc.gridx=1;
		gc.gridy=4;
		searchHousePanel.add(jbnRemoveFilters,gc);
		
		//Second Column//
		gc.anchor=GridBagConstraints.LINE_START;
		gc.weighty=0.5;
		gc.gridx=1;
		
		gc.gridy=0;
		searchHousePanel.add(jlbMin, gc);
		
		gc.gridy=1;
		searchHousePanel.add(jtfminPrice, gc);
		
		gc.gridy=2;
		searchHousePanel.add(jtfminSize, gc);
		
		gc.gridy=3;
		searchHousePanel.add(jtfRoomFilter,gc);
		
		//ThirdColumn
		gc.anchor=GridBagConstraints.LINE_START;
		gc.gridx=2;
		
		gc.gridy=0;
		searchHousePanel.add(jlbMax, gc);
		
		gc.gridy=1;
		searchHousePanel.add(jtfmaxPrice, gc);
		
		gc.gridy=2;
		searchHousePanel.add(jtfmaxSize, gc);
		
		gc.gridy=3;
		searchHousePanel.add(jbnSearch, gc);
		
		jbnSearch.addActionListener(this);
		jbnRemoveFilters.addActionListener(this);
		
	}

	private void setRemoveHousePanel() {
		
		removeHousePanel =  new JPanel();
		removeHousePanel.setLayout(new GridBagLayout());
		removeHousePanel.setBorder(BorderFactory.createTitledBorder("Remove House"));
		
		jbnRemoveHouse = new JButton("Remove House");
		JLabel jlbID = new JLabel("ID:");
		jtfRemoveID = new JTextField(10);
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx=0.5;
		gc.weighty=0.5;
		
		gc.anchor=GridBagConstraints.LINE_END;
		gc.gridx=0;
		gc.gridy=0;
		removeHousePanel.add(jlbID,gc);
		
		gc.anchor=GridBagConstraints.LINE_START;
		gc.gridx=1;
		removeHousePanel.add(jtfRemoveID,gc);
		
		gc.anchor=GridBagConstraints.FIRST_LINE_START;
		gc.weighty=10;
		gc.gridx=1;
		gc.gridy=1;
		removeHousePanel.add(jbnRemoveHouse,gc);
		
		jbnRemoveHouse.addActionListener(this);
		
	}

	private void setAddHousePanel() {
		
		addHousePanel = new JPanel();
		addHousePanel.setLayout(new GridBagLayout());
		addHousePanel.setBorder(BorderFactory.createTitledBorder("Add new House"));
		
		JLabel jlbID = new JLabel("ID:");
		JLabel jlbPrice = new JLabel("Price:");
		JLabel jlbSize = new JLabel("Size");
		JLabel jlbRooms = new JLabel("Rooms");
		JLabel jlbBathRooms = new JLabel("Bath Rooms:");
		JLabel jlbAirConditioner = new JLabel("Air conditioner:");
		
		jtfID = new JTextField(10);
		jtfPrice = new JTextField(10);
		jtfSize = new JTextField(10);
		jtfRooms = new JTextField(10);
		jtfBathRooms = new JTextField(10);
		jcbAirConditioner = new JCheckBox();
		jbnAddHouse = new JButton("Add");
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx=0.5;
		gc.weighty=0.5;
		
		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx=0;
		gc.gridy=0;
		addHousePanel.add(jlbID,gc);
		
		gc.gridy=1;
		addHousePanel.add(jlbPrice, gc);
		
		gc.gridy=2;
		addHousePanel.add(jlbSize, gc);
		
		gc.gridy=3;
		addHousePanel.add(jlbRooms, gc);	
		
		gc.gridy=4;
		addHousePanel.add(jlbBathRooms,gc);
		
		gc.gridy=5;
		addHousePanel.add(jlbAirConditioner,gc);
		
		//Second Column
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx=1;
		gc.gridy=0;
		addHousePanel.add(jtfID,gc);
		
		gc.gridy=1;
		addHousePanel.add(jtfPrice,gc);
		
		gc.gridy=2;
		addHousePanel.add(jtfSize,gc);
		
		gc.gridy=3;
		addHousePanel.add(jtfRooms,gc);
		
		gc.gridy=4;
		addHousePanel.add(jtfBathRooms,gc);
		
		gc.gridy=5;
		addHousePanel.add(jcbAirConditioner,gc);
		
		gc.gridx=2;
		addHousePanel.add(jbnAddHouse, gc);
		
		jbnAddHouse.addActionListener(this);
		
	}

	private void setOrderHousePanel() {
		
		orderHousePanel = new JPanel(new GridBagLayout());
		orderHousePanel.setBorder(BorderFactory.createTitledBorder("Order Houses"));
		
		jrbnAscendingPrice = new JRadioButton("Ascending price");
		jrbnDescendingPrice = new JRadioButton("Descending price");
		
		ButtonGroup radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(jrbnAscendingPrice);
		radioButtonGroup.add(jrbnDescendingPrice);
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.anchor=GridBagConstraints.FIRST_LINE_START;
		gc.weightx=0.5;
		gc.weighty=0.5;
		gc.gridx = 0;
		gc.gridy = 0;
		orderHousePanel.add(jrbnAscendingPrice,gc);
		
		gc.weighty=10;
		gc.gridx=0;
		gc.gridy=1;
		orderHousePanel.add(jrbnDescendingPrice,gc);
		
		jrbnAscendingPrice.setSelected(true);
	
		jrbnAscendingPrice.addActionListener(this);
		jrbnDescendingPrice.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		
		if(action.getSource()==jrbnAscendingPrice) 
			estateAgency.switchOrder(EstateAgency.ASCENDING_PRICE_ORDER);
			
		if(action.getSource()==jrbnDescendingPrice) 
			estateAgency.switchOrder(EstateAgency.DESCENDING_PRICE_ORDER);
		
		if(action.getSource()==jbnAddHouse) {
			
			String id = jtfID.getText();
			String price = jtfPrice.getText();
			String size = jtfSize.getText();
			String rooms = jtfRooms.getText();
			String bathRooms = jtfBathRooms.getText();
			String airConditioner;
			boolean airCond = jcbAirConditioner.isSelected();
			if(airCond)
				airConditioner="yes";
			else
				airConditioner="no";
			
			House newHouse = new House(id, price, size, rooms, bathRooms, airConditioner);
			if(!estateAgency.addHouseFromGUI(newHouse))
				JOptionPane.showMessageDialog(null,"ID already exists.");
			else 
				JOptionPane.showMessageDialog(null,"Successfuly added");
		}
		
		if(action.getSource()==jbnRemoveHouse) {
			
			String id = jtfRemoveID.getText();
			if(!estateAgency.removeHouse(id)) 
				JOptionPane.showMessageDialog(null, "ID doesn't exists.");
			else
				JOptionPane.showMessageDialog(null,"Successfuly removed.");
		}
		
		if(action.getSource()==jbnSearch) {
			
			String lowerPrice = jtfminPrice.getText();
			String upperPrice = jtfmaxPrice.getText();
			String lowerSize =  jtfminSize.getText();
			String upperSize =  jtfmaxSize.getText();
			String roomFilter = jtfRoomFilter.getText();
			
			try {
				estateAgency.filterSearchResults(new Search(lowerPrice,upperPrice,lowerSize,upperSize,
						roomFilter));
			}catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null,e.getMessage());
				clearSearchPanel();
			}
		}
		if(action.getSource()==jbnRemoveFilters) {
			estateAgency.filterSearchResults(null);
			clearSearchPanel();
		}
		if(action.getSource()==jbnExit) {
			estateAgency.saveToFile();
			System.exit(0);
		}
	}
	private void clearSearchPanel() {
		jtfminPrice.setText("");
		jtfmaxPrice.setText("");
		jtfminSize.setText("");
		jtfmaxSize.setText("");
		jtfRoomFilter.setText("");
	}
	public EstateAgency getEstateAgency() {
		return estateAgency;
	}
	public void setEstateAgency(EstateAgency estateAgency) {
		this.estateAgency = estateAgency;
	}
	public void updateTable() {
		DefaultTableModel houseTableModel = new DefaultTableModel(columnNames, 0);
		houseTable.setModel(houseTableModel);
		for(House house:estateAgency.getSearchResultList())
			houseTableModel.addRow(createRow(house));
	}
}

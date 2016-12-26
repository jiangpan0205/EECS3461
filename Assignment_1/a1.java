import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.LayerUI;

public class a1
{
	private static final String LOOK_AND_FEEL_LINUX = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
	
	public static void main(String[] args) throws IOException
	{		
		a1.setLookAndFeel();
		KioskFrame frame = new KioskFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Parking Permit Kiosk");
		frame.setResizable(false);
		frame.pack();

		// put the frame in the middle of the display
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
		frame.setVisible(true);
	}
	
	private static void setLookAndFeel()
	{
		try {
			UIManager.setLookAndFeel(a1.LOOK_AND_FEEL_LINUX);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}

class KioskFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private static final int NUM_OF_DIGITS_FOR_STUDENT_ID = 9;

	//Dimensions of the GUI components
	private static final Dimension KEYPAD_BUTTON_SIZE_REGULAR = new Dimension(60, 60);
	private static final Dimension NUMBER_KEYPAD_NUMBER_BUTTON_SIZE = new Dimension(80, 80);
	private static final Dimension CONFIRM_BUTTON_SIZE = new Dimension(96, 54);
	private static final Dimension BACK_BUTTON_SIZE = new Dimension(96, 54);
	private static final Dimension EMAIL_UPPER_PANEL_BUTTON_SIZE = new Dimension(139, 48);
	private static final Dimension OPEN_YEAR_SELECTION_DIALOG_BUTTON_SIZE = new Dimension(96, 48);
	private static final Dimension OPEN_MONTH_SELECTION_DIALOG_BUTTON_SIZE = new Dimension(96, 48);
	private static final Dimension DAY_SELECTION_BUTTON_SIZE = new Dimension(54, 54);
	private static final Dimension DAY_NAME_LABEL_SIZE = new Dimension(60, 48);
	private static final Dimension CAPS_LOCK_BUTTON_SIZE = new Dimension(80, 60);
	private static final Dimension DELETE_BUTTON_SIZE = new Dimension(80, 60);
	private static final Dimension PIN_INPUT_FIELD_SIZE= new Dimension(100, 48);
	private static final Dimension NUMBER_KEYPAD_DELETE_BUTTON_SIZE = new Dimension(160, 80);
	private static final Dimension CHARACTER_BUTTON_SIZE = new Dimension(60, 60);
	private static final Dimension SELECTED_DAY_DISPLAY_SIZE = new Dimension(40, 40);
	private static final Dimension EDIT_BUTTON_SIZE = new Dimension(77, 48);

	
	//Custom colors
	private static final Color MAIN_BACKGROUND_COLOR = new Color(247, 247, 247);
	private static final Color INPUT_FIELD_COLOR = new Color(255, 255, 255);
	private static final Color CONFIRM_BUTTON_COLOR = new Color(155, 204, 255);
	
	
	//Button colors
	private static final Color COMMON_BUTTON_COLOR = new Color(250, 245, 250);
	private static final Color KEYPAD_BUTTON_COLOR = new Color(250, 245, 250);
	
	//Fonts
	private static final Font NUMBER_KEYPAD_BUTTON_FONT = new Font("Helvetica Neue", Font.PLAIN, 24);
	private static final Font COMMON_BUTTON_FONT = new Font("Helvetica Neue", Font.PLAIN, 16);
	private static final Font INPUT_FIELD_TEXT_FONT = new Font("Helvetica Neue", Font.PLAIN, 40);
	private static final Font CALENDAR_BUTTON_FONT = new Font("Helvetica Neue", Font.PLAIN, 20);
	private static final Font NUMBER_KEYPAD_DELETE_BUTTON_FONT = new Font("Helvetica Neue", Font.PLAIN, 19);
	private static final Font FULL_KEYPAD_FONT = new Font("Helvetica Neue", Font.PLAIN, 22);
	
	//Path to image files
	private static final String DELETE_BUTTON_IMAGE_PATH = "delete_button_image.png";
	private static final String LOGO_IMAGE_PATH = "logo.png";
	
	private static final String CAPSLOCK_ON_IMAGE_PATH = "capsOn_image.png";
	private static final String CAPSLOCK_OFF_IMAGE_PATH = "capsOff_image.png";
	private static final ImageIcon CAPSLOCK_ON_ICON = new ImageIcon(KioskFrame.CAPSLOCK_ON_IMAGE_PATH);
	private static final ImageIcon CAPSLOCK_OFF_ICON = new ImageIcon(KioskFrame.CAPSLOCK_OFF_IMAGE_PATH);
	
	//Font properties
	private static final int WARNING_FONT_SIZE = 6;
	private static final int INSTRUCTION_TEXT_FONT_SIZE = 7;
	private static final String INSTRUCTION_TEXT_FONT_FACE = "Helvetica Neue";
	private static final String WARNING_FONT_COLOR = "#FF0000";
	private static final String INSTRUCTION_FONT_COLOR = "#000000";
	private static final String BUTTON_FONT_FACE = "Helvetica Neue";
	private static final int CHARACTER_BUTTON_FONT_SIZE = 22;
	private static final String EXPIRY_DATE_FONT_COLOR = "#000000";

	//Welcome screen
	private JPanel welcomePanel;
	private static final String WELCOME_SCREEN_FONT_COLOR = "#000000";
	private static final String WELCOME_SCREEN_FONT_FACE = "Helvetica Neue";
	private static final int WELCOME_SCREEN_FONT_SIZE = 8;
	private static final String WELCOME_SCREEN_TEXT = "<b>WELCOME TO YORK UNIVERSITY PARKING PERMIT KIOSK</b><br><br>Touch anywhere to start";
	
	
	//Parking fine
	JTextPane payParkingFineTextDisplay;
	JPanel payParkingFineButtonContainer;
	private static final String PARKING_FINE_PAYMENT_QUESTION = "Our record shows you have unpaid parking fine.<br>You must pay the fine before proceeding to next step.<br> Would you like to pay it now?";
	private static final String PARKING_FINE_PAYMENT_ACCEPT_TEXT = "Thank you very much for your payment. Your outstanding fine is cleared! ";
	private static final String PARKING_FINE_PAYMENT_DENIED_TEXT = "";
	private static final Dimension PARKING_FINE_BUTTON_SIZE = new Dimension(84, 48);
	private static final Font PARKING_FINE_BUTTON_FONT = new Font("Helvetica Neue", Font.PLAIN, 18);
	
	//Permit printing
	private static final String PERMIT_PRINT_TEXT = "Your permit is being printed.<br>Please take your permit and display it on the dashboard.<br> Thank you for using our service. Have a nice day!";
	private static final String PERMIT_PRINT_FONT_FACE = "Arial";
	private static final String PERMIT_PRINT_FONT_SIZE = "7";
	private static final String PERMIT_PRINT_FONT_COLOR = "#000000";
	private static final String PRINTED_PERMIT_FONT_COLOR = "#D53E1C";
	private static JDialog printedPermit;
	
	
	//Common panels
	private JPanel mainScreenDisplayPanel;
	private JTextPane instructionDisplay;
	private JPanel panelWithInstructionDisplay;
	private JPanel keypadPanel;
	private JPanel panelWithKeypad;
	private JPanel fullKeypadPanel;
	private JPanel mainInputFieldPanel;
	private JPanel subCardLayoutPanel;
	private JPanel inputPanelWithBackAndConfirmButton;
	private JPanel commonButtonContainer;
	
	//Dialogs
	private JDialog yearSelectionDialog;
	private JDialog monthSelectionDialog;
	private JDialog payParkingFineDialog;
	
	//Input fields
	private JTextField inputTextField;
	private JPasswordField PINInputField;

	//Common buttons
	private JButton confirmButton;
	private JButton backButton;
	private JButton capsLockButton;
	private JButton logoutButton;
	
	//Attributes for student information input step
	private static ArrayList<String> studentInfo = new ArrayList<String>();
	
	//keypad
	Keypad numberKeypad;
	Keypad keypadRowOne;
	Keypad keypadRowTwo;
	Keypad keypadRowThree;
	private static boolean IS_CAPS_LOCK_ON = false;

	//Attributes for Insurance Company selection step
	private JPanel insuranceCompanySelectionPanel;
	private JPanel insuranceCompanyHelpPanel;
	private int numberOfCompanies;
	private String companySelected;
	
	//Attributes for Expiry Date selection step
	private JPanel expiryDateSelectionPanel;
	private static final Font MONTH_SELECTION_BUTTON_FONT = new Font("Helvetica Neue", Font.PLAIN, 18);
	private static final String SELECTED_DATE_FONT_COLOR = "#000000";
	private static final double PARKING_RATE = 3.5;
	private  JButton yearButton;
	private JButton monthButton;
	private ArrayList<JButton> dateButtons;
	private String yearSelected;
	private String monthSelected;
	private String daySelected;
	private JPanel weekAndDateDisplay;
	private JLabel selectedDayDisplay;
	private int currentYear;
	private int currentMonth;
	private int currentDay;
	private ArrayList<JButton> yearSelectionButtons;
	private ArrayList<JButton> monthSelectionButtons;
	private static double price; 
	enum Month
	{
		January("January", "Jan"),
		February("February", "Feb"),
		March("March", "Mar"),
		April("April", "Apr"),
		May("May", "May"),
		June("June", "Jun"),
		July("July", "Jul"),
		August("August", "Aug"),
		September("September", "Sep"),
		October("October", "Oct"),
		November("November", "Nov"),
		December("December", "Dec");
		
		private String fullName;
		private String shortName;
		
		private Month(String fullName, String shortName)
		{
			this.fullName = fullName;
			this.shortName = shortName;
		}
		
		private String getFullName()
		{
			return this.fullName;
		}
		
		public String getShortName()
		{
			return this.shortName;
		}
		
	}
	
	//Attributes for email panel
	private static final String DEFAULT_EMAIL = "Not Available";
	private static final Font EMAIL_INPUT_UPPERPANEL_FONT = new Font("Helvetica Neue", Font.PLAIN, 16);
	JPanel emailInputUpperPanel;
	JPanel emailInputRightPanel;
	private static String emailAddress = KioskFrame.DEFAULT_EMAIL;
	
	//Attributes for database management
	private StudentDatabaseManager studentDatabaseManager;
	
	//Attributes for Purchase summary panel
	private static final Font EDIT_BUTTON_FONT = new Font("Helvetica Neue", Font.PLAIN, 18);
	private static final String PURCHASE_SUMMARY_FONT_COLOR = "#000000";
	private JPanel purchaseSummaryPanel;
	
	
	//Attributes for printing permit panel
	private JPanel permitPrintPanel;
	private Timer permitPrintTimer;
	private Timer switchPanelTimer;
	
	//Attributes for Logout
	private static final Dimension LOGOUT_DIALOG_BUTTON_SIZE = new Dimension(200, 60);
	private static final String LOGOUT_DIALOG_TEXT = "Log out to home screen will erase all the information you have entered.";
	private static JDialog logoutConfirmDialog;
	private static final Font LOGOUT_DIALOG_BUTTON_FONT = new Font("Helvetica Neue", Font.PLAIN, 18);
	
	//Other attributes
	private int counter;
	private static String vehiclePlateNumber;

	//Session timeout due to idle
	private  Timer IDLE_COUNTDOWN_TIMER;
	private static final int ALLOWED_IDLE_TIME_IN_MILLISECONDS = 180000;
//	private static final int ALLOWED_IDLE_TIME_IN_MILLISECONDS = 5000;
	private  Timer finalCountDownTimer;
	private JDialog sessionTimeoutWarningDialog;
	private Timer updateCountDownTimer;
	private int finalTimeRemainCounter = 10;
	private JLabel countDownDisplay;
	private static final Font COUNT_DOWN_DISPLAY_FONT = new Font("Arial", Font.PLAIN, 28);
	private static final Dimension STAY_IN_SESSION_BUTTON_SIZE = new Dimension(77, 48);
	private static final Font STAY_IN_SESSION_BUTTON_FONT = new Font("Arial", Font.PLAIN, 18);
	
	//Information of each step
	private Step currentStep;
	enum Step
	{
		WELCOME_SCREEN("", "", "", false),
		INPUT_INSURANCE_COMPANY_INFO("PLEASE SELECT YOUR VEHICLE'S INSURANCE COMPANY", "NoInputField", "NoKeypad", true),
		INPUT_POLICY_NUMBER("PLEASE ENTER YOUR POLICY NUMBER ASSOCIATED WITH <br>", "regularInputTextField", "fullKeypad", false),
		INPUT_STUDENT_NUMBER("PLEASE ENTER YOUR STUDENT ID", "regularInputTextField",  "numberKeypad", true),
		INPUT_PIN("PLEASE ENTER YOUR PASSWORD", "inputPINPanel", "numberKeypad", true),
		INPUT_VEHICLE_PLATE_NUMBER("PLEASE ENTER YOUR VEHICLE'S PLATE NUMBER", "regularInputTextField",  "fullKeypad", false),
		INPUT_EXPIRY_DATE("PLEASE CHOOSE THE EXPIRY DATE FOR YOUR PARKING PERMIT", "NoInputField", "NoKeypad", false),
		INPUT_EMAIL_ADDRESS("PLEASE ENTER YOUR EMAIL ADDRESS (OPTIONAL)<br><font size = 6>Your email address will only be used to receive news and receipt.</font>", "regularInputTextField", "fullKeypad", false),
		CONFIRM_PURCHASE_SUMMARY("PLEASE CONFIRM THE FOLLOWING INFORMATION", "NoInputField", "NoKeypad", false),
		PRINT_PARKING_PERMIT("", "NoInputTextField", "NoKeypad", false),
		EDIT_VEHICLE_PLATE_NUMBER("EDIT VEHICLE'S PLATE NUMBER", "regularInputTextField", "fullKeypad", false),
		EDIT_EMAIL_ADDRESS("EDIT EMAIL ADDRESS", "regularInputTextField", "fullKeypad", false),
		EDIT_EXPIRY_DATE("EDIT EXPIRY DATE", "NoInputField", "NoKeypad", false);
		
		private String instruction;
		private String inputFieldType;
		private String keypadType;
		private boolean requireValidation;
		
		private Step(String instruction, String inputFieldType, String keypadType, boolean requireValidation)
		{
			this.instruction = instruction;
			this.inputFieldType = inputFieldType;
			this.keypadType = keypadType;
			this.requireValidation = requireValidation;
		}
		public String getInstruction()
		{
			return this.instruction;
		}
		
		public String getInputFieldType()
		{
			return this.inputFieldType;
		}
		
		public String getKeypadType()
		{
			return this.keypadType;
		}
		
		public boolean requireValidation()
		{
			return this.requireValidation;
		}
	}
	
	//Information of insurance companies
	enum  InsuranceCompany
	{
		CO_OPERATORS_INSURANCE("CO-OPERATORS", "www.cooperators.ca"),
		METROPOLITAN_LIFE_INSURANCE("METROPOLITAN LIFE", "www.metlife.com"),
		BANG_EM_UP_INSURANCE("BANG EM UP", "website address"),
		STATE_FARM_UNDERWRITERS("STATEFARM", "www.statefarm.ca"),
		ALLSTATE_INTERNATIONAL("ALLSTATE", "www.allstate.ca"),
		TIGHTWADS_INCORPORATED("TIGHTWADS", "website address"),
		GOTTCHA_INSURANCE("GOTTCHA", "website address"),
		NOT_FOUND("NONE OF ABOVE", "no phone number");
		
		private String name;
		private String webAddress;
		
		private InsuranceCompany(String name, String webAddress)
		{
			this.name = name;
			this.webAddress = webAddress;
		}
		
		public String getName()
		{
			return this.name;
		}
		
		public String getWebAddress()
		{
			return this.webAddress;
		}
	}
	
	
	enum ButtonStatus
	{
		ENABLED,
		DISABLED;
	}
	
	// ------------
	// constructor
	// ------------
	
	public KioskFrame() throws IOException 
	{
		InsuranceCompanyDatabaseManager insuranceCompanyInfoManager = new InsuranceCompanyDatabaseManager("companies.txt");
		this.numberOfCompanies = insuranceCompanyInfoManager.getNumberOfLinesFromFile();
		studentDatabaseManager = new StudentDatabaseManager("students.txt");		
		
		//Set the first step of the application to current step
		this.setCurrentStep(Step.values()[0]);
		this.setCounterValue(0);

		//Build and set up panel content for each step
		this.buildWelcomeScreen();
		this.buildInsuranceCompanySelectonAndHelpPanel();
		this.buildExpiryDatePicker();
		this.buildPermitPrintPanel();
		this.buildPanelWithKeypad();
		
		//build the main screen display of the kiosk
		this.buildMainScreenDisplay();
		this.setContentPane(this.mainScreenDisplayPanel);
	}	

	// -----------------------------------------------------------------
	// implement methods for building panels for the required steps
	// -----------------------------------------------------------------
	
	/** Create the home screen for the kiosk.  */
	public void buildWelcomeScreen()
	{
		JTextPane welcomePane = new JTextPane();
		welcomePane.setEditable(false);
		welcomePane.addMouseListener(new WelcomePanelListener());
		UIDefaults defaults = new UIDefaults();
		defaults.put("TextPane[Enabled].backgroundPainter", MAIN_BACKGROUND_COLOR);
		welcomePane.putClientProperty("Nimbus.Overrides", defaults);
		welcomePane.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
		welcomePane.setBackground(MAIN_BACKGROUND_COLOR);
		welcomePane.setContentType("text/html");
		welcomePane.setText(this.setHTMLTextProperties(KioskFrame.WELCOME_SCREEN_TEXT, KioskFrame.WELCOME_SCREEN_FONT_SIZE, KioskFrame.WELCOME_SCREEN_FONT_FACE, "<center>", KioskFrame.WELCOME_SCREEN_FONT_COLOR));
		
		this.welcomePanel = new JPanel(new GridBagLayout());
		this.welcomePanel.setBackground(KioskFrame.MAIN_BACKGROUND_COLOR);
		this.welcomePanel.addMouseListener(new WelcomePanelListener());
		this.welcomePanel.add(welcomePane);
	}
	
	/** Clicking the welcome panel will enter purchase process, and start count down timer for inactivity. */
	private class WelcomePanelListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			KioskFrame.this.goToNextStepFromCurrentStep();
			KioskFrame.this.startIdleCountDownTimer();
		}
	}
	
	/** Create the panel for selecting insurance companies. */
	public void buildInsuranceCompanySelectonAndHelpPanel()
	{
		InsuranceCompanyButtonActionListener insuranceCompanyButtonActionListener = new InsuranceCompanyButtonActionListener();
		insuranceCompanySelectionPanel = this.buildInsuranceCompanySelectionPanelWithListener(insuranceCompanyButtonActionListener);
		insuranceCompanyHelpPanel = this.buildInsuranceCompanyHelpPanel();
	}
	
	/** Create a date picker for selecting the expiry date of the permit. */
	public void buildExpiryDatePicker()
	{
		this.currentYear = Calendar.getInstance().get(Calendar.YEAR);
		this.currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		this.currentDay =  Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		this.yearSelected = String.valueOf(currentYear);
		this.monthSelected = Month.values()[this.currentMonth].getFullName();
		this.daySelected = String.valueOf(this.currentDay);
		this.expiryDateSelectionPanel = this.buildExpiryDateSelectionPanelBasedOnCurrentYearAndMonth(currentYear, currentMonth);
	}
	
	/**Build container for commonly used control button such as back, logout, and confirm buttons*/
	public void buildCommonButtonPanel()
	{
		this.confirmButton = this.buildConfirmButton();
		this.backButton = this.buildBackButton();
		this.logoutButton = this.buildLogoutButton();
	
		this.commonButtonContainer= new JPanel(new FlowLayout());
		this.commonButtonContainer.setOpaque(false);
		this.resetCommonButtonContainer();
	}
	
	public void buildSubCardLayoutPanel()
	{
		subCardLayoutPanel = new JPanel(new CardLayout());
		subCardLayoutPanel.setOpaque(false);
		subCardLayoutPanel.add(insuranceCompanySelectionPanel, "insuranceCompanySelectionPanel");
		subCardLayoutPanel.add(insuranceCompanyHelpPanel, "insuranceCompanyHelpPanel");
		subCardLayoutPanel.add(panelWithKeypad, "panelWithKeypad");
		subCardLayoutPanel.add(expiryDateSelectionPanel, "expiryDateSelectionPanel");
	}
	
	public void buildInputPanelWithCommonControlButtons()
	{
		inputPanelWithBackAndConfirmButton = new JPanel(new BorderLayout());
		inputPanelWithBackAndConfirmButton.setOpaque(false);
		inputPanelWithBackAndConfirmButton.add(subCardLayoutPanel, BorderLayout.CENTER);
		inputPanelWithBackAndConfirmButton.add(commonButtonContainer, BorderLayout.PAGE_END);
		inputPanelWithBackAndConfirmButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	}
	
	/** Create a panel that contains the instruction display, as well as panel with back and confirm buttons. */
	public void buildPanelWithInstructionDisplay()
	{
		panelWithInstructionDisplay = new JPanel(new BorderLayout());
		panelWithInstructionDisplay.setBackground(KioskFrame.MAIN_BACKGROUND_COLOR);
		panelWithInstructionDisplay.setBorder(BorderFactory.createEmptyBorder(60, 24, 24, 24));
		panelWithInstructionDisplay.add(instructionDisplay, BorderLayout.PAGE_START);
		panelWithInstructionDisplay.add(inputPanelWithBackAndConfirmButton,  BorderLayout.CENTER);
	}
	
	// -----------------------------------------------------------------
	// implement methods for building main screen 
	// -----------------------------------------------------------------
	
	public void buildMainScreenDisplay()
	{
		this.buildCommonButtonPanel();
		this.buildSubCardLayoutPanel();
		this.buildInputPanelWithCommonControlButtons();
		instructionDisplay = this.buildInstructionDisplay(Step.values()[0].getInstruction());
		this.buildPanelWithInstructionDisplay();
		this.buildMainCardLayoutPanel();
	}
	
	public void buildMainCardLayoutPanel()
	{
		mainScreenDisplayPanel = new JPanel(new CardLayout());
		mainScreenDisplayPanel.setOpaque(false);
		mainScreenDisplayPanel.add(this.welcomePanel, "welcomePanel");
		mainScreenDisplayPanel.add(this.panelWithInstructionDisplay, "panelWithInstructionDisplay");
		mainScreenDisplayPanel.add(this.permitPrintPanel, "permitPrintPanel");
	}
	
	// -------------------------------------------------------------------------------------
	// implement methods for timer that counts down for inactivity during purchasing process
	// -------------------------------------------------------------------------------------
	
	public void startIdleCountDownTimer()
	{
		this.IDLE_COUNTDOWN_TIMER = new Timer(KioskFrame.ALLOWED_IDLE_TIME_IN_MILLISECONDS, new IdleLogoutAction());
		this.IDLE_COUNTDOWN_TIMER.start();
	}
	
	public void resetIdleCountDownTimer()
	{
		this.IDLE_COUNTDOWN_TIMER.restart();
	}
	
	public JPanel buildSessionTimeoutWarningPanel()
	{
		JLabel startText = new JLabel("<html><center><font size=5> Your will be logged out to home screen in </font>");
		this.countDownDisplay = new JLabel(this.finalTimeRemainCounter + " seconds");
		this.countDownDisplay.setFont(KioskFrame.COUNT_DOWN_DISPLAY_FONT);
		this.countDownDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel endText = new JLabel("<html><center><font size=5>due to inactivity.</font>");
		
		JPanel textWrapper = new JPanel(new GridLayout(3, 1));
		textWrapper.add(startText);
		textWrapper.add(this.countDownDisplay);
		textWrapper.add(endText);
		textWrapper.setOpaque(false);
		textWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		
		JButton stayButton = new JButton("Stay");
		stayButton.addActionListener(new StayInSessionActionListener());
		stayButton.setPreferredSize(KioskFrame.STAY_IN_SESSION_BUTTON_SIZE);
		stayButton.setFont(KioskFrame.STAY_IN_SESSION_BUTTON_FONT);
		stayButton.setBackground(KioskFrame.KEYPAD_BUTTON_COLOR);

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.add(textWrapper, this.createGridBagConstraints(0, 0, 10));
		panel.add(stayButton, this.createGridBagConstraints(2, 1, 6));
		panel.setBackground(KioskFrame.MAIN_BACKGROUND_COLOR);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 32, 20, 32));
		return panel;
	}
	
	private class IdleLogoutAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.IDLE_COUNTDOWN_TIMER.stop();
			KioskFrame.this.startFinalCountDownTimer();
			KioskFrame.this.updateCountDownTime();
			JPanel sessionTimeoutWarningPanel = KioskFrame.this.buildSessionTimeoutWarningPanel();
			KioskFrame.this.sessionTimeoutWarningDialog = KioskFrame.this.buildDialogWithCustomPanel(sessionTimeoutWarningPanel);
			KioskFrame.this.sessionTimeoutWarningDialog.pack(); 
			KioskFrame.this.sessionTimeoutWarningDialog.setLocationRelativeTo(keypadPanel);
			KioskFrame.this.sessionTimeoutWarningDialog.setVisible(true);
		}
	}
	
	public void startFinalCountDownTimer()
	{
		System.out.println("final count down start");
		this.finalCountDownTimer = new Timer(12000, new SessionTimeoutActionListener());
		this.finalCountDownTimer.start();
	}
	
	private class SessionTimeoutActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.sessionTimeoutWarningDialog.dispose();
			KioskFrame.this.finalCountDownTimer.stop();
			KioskFrame.this.updateCountDownTimer.stop();
			KioskFrame.this.finalTimeRemainCounter = 10;
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.currentStep = Step.WELCOME_SCREEN;
			KioskFrame.this.updateScreenViewBasedOnCurrentStep(currentStep);
		}
	}
	
	private class StayInSessionActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.playButtonClickNormalSound();
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.sessionTimeoutWarningDialog.dispose();
			KioskFrame.this.finalCountDownTimer.stop();
			KioskFrame.this.updateCountDownTimer.stop();
			KioskFrame.this.finalTimeRemainCounter = 10;
		}
	}
	
	public void updateCountDownTime()
	{
		System.out.println("update countdown");
		this.updateCountDownTimer = new Timer(1000, new UpdateTimeActionListener());
		this.updateCountDownTimer.setRepeats(true);
		this.updateCountDownTimer.start();
	}
	
	private class UpdateTimeActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.countDownDisplay.setText(String.valueOf(KioskFrame.this.finalTimeRemainCounter) + " seconds");
			if (KioskFrame.this.finalTimeRemainCounter == 0)
			{
				KioskFrame.this.updateCountDownTimer.stop();
				KioskFrame.this.finalTimeRemainCounter = 10;
			}
			else
			{
				KioskFrame.this.finalTimeRemainCounter--;
			}
		}
	}
	
	/** Clear all user entered information and those stored in the program. Reset expiry date picker to reflect current
	 *  date.
	 * */
	public void resetUserInputWhenLogout()
	{
		this.resetInputField();
		this.resetPINInputField();
		KioskFrame.vehiclePlateNumber = "";
		KioskFrame.emailAddress = "";
		if (!KioskFrame.studentInfo.isEmpty())
		{
			KioskFrame.studentInfo.clear();
		}
		this.currentYear = Calendar.getInstance().get(Calendar.YEAR);
		this.currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		this.currentDay =  Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		this.yearSelected = String.valueOf(currentYear);
		this.monthSelected = Month.values()[this.currentMonth].getFullName();
		this.daySelected = String.valueOf(this.currentDay);
		System.out.println("selected month is " + this.monthSelected);
		this.yearButton.setText(this.yearSelected);
		this.monthButton.setText(Month.valueOf(this.monthSelected).getShortName());
		this.updateSelectedDayDisplay();
		this.updateWeekAndDateDisplayBasedOnYearAndMonthName(this.yearSelected, this.monthSelected);
	}
	
	// -------------------------------------------
	// implement ActionListener for confirm button
	// -------------------------------------------
	
	private class confirmButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent actionEvent)
		{
			KioskFrame.this.resetIdleCountDownTimer();
			
			if (currentStep == Step.INPUT_STUDENT_NUMBER)
			{
				KioskFrame.this.performActionBasedOnInputStudentNumber();
			}
			else if (currentStep == Step.INPUT_PIN)
			{
				KioskFrame.this.performActionBasedOnInputPIN();
			}
			else if (currentStep == Step.INPUT_EMAIL_ADDRESS || currentStep == Step.EDIT_EMAIL_ADDRESS)
			{
				KioskFrame.this.updateEmailAddress();
			} 
			else
			{
				KioskFrame.this.playButtonClickNormalSound();
				if (currentStep == Step.INPUT_VEHICLE_PLATE_NUMBER || currentStep == Step.EDIT_VEHICLE_PLATE_NUMBER) 
				{
					KioskFrame.vehiclePlateNumber = KioskFrame.this.inputTextField.getText();
				}
				else if (currentStep == Step.INPUT_EXPIRY_DATE || currentStep == Step.EDIT_EXPIRY_DATE )
				{
					KioskFrame.this.setSelectedDateButtonFocus();
				}
				
				KioskFrame.this.goToNextStepFromCurrentStep();
			}
			if (currentStep != Step.INPUT_EMAIL_ADDRESS && currentStep != Step.INPUT_EXPIRY_DATE && currentStep != Step.CONFIRM_PURCHASE_SUMMARY)
			{
				KioskFrame.this.setConfirmButtonStatus(ButtonStatus.DISABLED);
			}
		}
	}
	
	public void performActionBasedOnInputStudentNumber()
	{
		String userInput = KioskFrame.this.inputTextField.getText();
		if (isStudentNumberValid(userInput)) //Student number is valid
		{	
			KioskFrame.this.playButtonClickNormalSound();
			//Retrieve the users information based on the student number.
			KioskFrame.studentInfo = KioskFrame.this.studentDatabaseManager.getStudentInfoBasedOnStudentNumber(userInput);
			System.out.println("student info is " + studentInfo);
			
			//Go to next step, switch the view in the main input screen, change instruction display output, reset counter.
			KioskFrame.this.goToNextStepFromCurrentStep();
			KioskFrame.this.numberKeypad.enableAllButtons();
		}
		else //Student number is not valid
		{
			Toolkit.getDefaultToolkit().beep();
			KioskFrame.this.addWarningToInstruction("The student number entered is not found in our database. Please try again or contact xxx-xxx-xxx.");
			KioskFrame.this.numberKeypad.disableAllButtons();
		}
	}
	
	public void performActionBasedOnInputPIN()
	{
		char[] passwordInput = KioskFrame.this.PINInputField.getPassword();
		if (isPINValid(passwordInput) && !hasUnpaidFine()) 
		{
			KioskFrame.this.playButtonClickNormalSound();
			KioskFrame.this.goToNextStepFromCurrentStep();
		}
		else if (isPINValid(passwordInput) && hasUnpaidFine()) 
		{
			//Ask if the user wants to pay the fine now
			KioskFrame.this.popUpFinePaymentDialog();
		}
		else
		{
			//PIN not valid. 
			KioskFrame.this.addWarningToInstruction("The PIN entered does not match the student number " + KioskFrame.studentInfo.get(0) + ".");
		}
	}
	
	// -------------------------------------------
	// implement ActionListener for back button
	// -------------------------------------------
	
	private class BackButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.playButtonClickNormalSound();
			KioskFrame.this.goToPreviousStepFromCurrentStep();
			KioskFrame.this.PINInputField.setText("");
			KioskFrame.this.numberKeypad.enableAllButtons();
			if(currentStep == Step.INPUT_VEHICLE_PLATE_NUMBER)
			{
				KioskFrame.this.inputTextField.setText(KioskFrame.vehiclePlateNumber);
			}
			if (currentStep != Step.INPUT_EMAIL_ADDRESS && currentStep != Step.INPUT_EXPIRY_DATE && currentStep != Step.INPUT_VEHICLE_PLATE_NUMBER)
			{
				KioskFrame.this.setConfirmButtonStatus(ButtonStatus.DISABLED);
			}
			else
			{
				KioskFrame.this.setConfirmButtonStatus(ButtonStatus.ENABLED);
			}
			if (!KioskFrame.this.confirmButton.isVisible())
			{
				KioskFrame.this.confirmButton.setVisible(true);
			}
		}
	}
	
	// -------------------------------------------
	// implement ActionListener for caps lock button
	// -------------------------------------------
	
	private class CapsLockButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.playButtonClickNormalSound();
			KioskFrame.this.setCapsLockButtonStatus();
		}
	}
	
	// -----------------------------------------------------------------------------------------------
	// implement ActionListener for delete button
	// The DeleteButtonListener offers two delete methods: single click delete and long press delete.
	// -----------------------------------------------------------------------------------------------
	
	/** Deleting the input text starting from the end of the text. Single click deletes one character. 
	 * 	Long press will delete characters one by one continuously, and clear the input field once the number 
	 * 	of continuously deleted characters reaches the specified threshold. 
	 *  */
	private class DeleteButtonListener extends MouseAdapter
	{
		private static final int LONG_PRESS_THRESHOLD_IN_MILLISECONDS = 500;
		private static final int REPEAT_INTERVAL_IN_MILLISECONDS = 100;
		private static final int DELETED_CHAR_COUNT_THRESHOLD = 20;
		private int deletedCharCounter = 0;
		
		ActionListener longPressListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				longPressTimer.stop();
				deleteRepeatTimer.setRepeats(true);
				deleteRepeatTimer.start();
			}
		};
		
		ActionListener deleteRepeatListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				KioskFrame.this.playButtonClickNormalSound();
				KioskFrame.this.deleteText();
				DeleteButtonListener.this.deletedCharCounter++;
				if (deletedCharCounter == DeleteButtonListener.DELETED_CHAR_COUNT_THRESHOLD)
				{
					KioskFrame.this.inputTextField.setText("");
					KioskFrame.this.PINInputField.setText("");
					KioskFrame.this.setConfirmButtonStatus(ButtonStatus.DISABLED);
					deleteRepeatTimer.stop();
				}
				else if (currentStep != Step.INPUT_PIN && KioskFrame.this.inputTextField.getText().equals(""))
				{
					deleteRepeatTimer.stop();
				}
				else if (currentStep == Step.INPUT_PIN && KioskFrame.this.convertCharArrayToString(KioskFrame.this.PINInputField.getPassword()).equals(""))
				{
					deleteRepeatTimer.stop();
				}
			}
		};
			
		Timer longPressTimer = new Timer(DeleteButtonListener.LONG_PRESS_THRESHOLD_IN_MILLISECONDS, longPressListener);
		Timer deleteRepeatTimer = new Timer(DeleteButtonListener.REPEAT_INTERVAL_IN_MILLISECONDS, deleteRepeatListener);
		
		@Override
		public void mousePressed(MouseEvent e)
		{
			KioskFrame.this.playButtonClickNormalSound();
			KioskFrame.this.deleteText();
			KioskFrame.this.resetIdleCountDownTimer();
			longPressTimer.start();
		}
		
		@Override
		public void mouseReleased(MouseEvent e)
		{
			KioskFrame.this.resetIdleCountDownTimer();
			this.longPressTimer.stop();
			this.deleteRepeatTimer.stop();
			this.deletedCharCounter = 0;
		}
	}
	
	/** Delete text in the textField or passwordField. Disables confirm button if no text presents in the input 
	 *  field.
	 * */
	public void deleteText()
	{
		if (currentStep == Step.INPUT_PIN)
			{
				KioskFrame.this.setConfirmButtonStatus(ButtonStatus.ENABLED);
				KioskFrame.this.deleteTextInPasswordField();
			}
			else
			{
				KioskFrame.this.deleteTextInRegularInputField();
			}
	}
	
	/** Delete text in the password input field. Disables confirm button when the field is empty. */
	public void deleteTextInPasswordField()
	{
		String text = KioskFrame.this.convertCharArrayToString(KioskFrame.this.PINInputField.getPassword());
		if (text.length() > 0)
		{
			if (text.length() == 1)
			{
				KioskFrame.this.PINInputField.setText("");
			}
			else
			{
				text = text.substring(0, text.length() - 1);
				KioskFrame.this.PINInputField.setText(text);
			}
		}
		
		String newText = KioskFrame.this.convertCharArrayToString(KioskFrame.this.PINInputField.getPassword());
		//Disable confirm button if there is no text in the input field.
		if (newText.length() == 0)
		{
			KioskFrame.this.setConfirmButtonStatus(ButtonStatus.DISABLED);
		}
	}
	
	/** Delete text in the regular input field. Disables confirm button when the field is empty. */
	public void deleteTextInRegularInputField()
	{
		String text = KioskFrame.this.inputTextField.getText();
		if (text.length() > 0)
		{
			if (text.length() == 1)
			{
				KioskFrame.this.inputTextField.setText("");
			}
			else
			{
				text = text.substring(0, text.length() - 1);
				KioskFrame.this.inputTextField.setText(text);
			}
			if (currentStep == Step.INPUT_STUDENT_NUMBER)
			{
				KioskFrame.this.counter--;
    			if (counter != NUM_OF_DIGITS_FOR_STUDENT_ID)
    			{
    				KioskFrame.this.setConfirmButtonStatus(ButtonStatus.DISABLED);
    				KioskFrame.this.numberKeypad.enableAllButtons();
    			}
			}
		}
		
		String newText = KioskFrame.this.inputTextField.getText();
		//Disable confirm button if there is no text in the input field.
		if (newText.length() == 0 && currentStep != Step.INPUT_EMAIL_ADDRESS && currentStep != Step.EDIT_EMAIL_ADDRESS)
		{
			KioskFrame.this.setConfirmButtonStatus(ButtonStatus.DISABLED);
		}
		if (currentStep == Step.INPUT_EMAIL_ADDRESS || currentStep == Step.EDIT_EMAIL_ADDRESS)
		{
			KioskFrame.this.setConfirmButtonStatus(ButtonStatus.ENABLED);
		}
	}
	
	// -------------------------------------------
	// implement ActionListener for keypad buttons
	// -------------------------------------------
	
	/** ActionListener for keypad buttons */
	private class KeypadButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent actionEvent){
        	
        	KioskFrame.this.resetIdleCountDownTimer();
        	KioskFrame.this.playButtonClickNormalSound();
        	
        	if (currentStep == Step.INPUT_STUDENT_NUMBER)
        	{
        		//input digits does not exceed the required digit number.
        		if (counter < NUM_OF_DIGITS_FOR_STUDENT_ID)
        		{
        			KioskFrame.this.inputTextField.setText(KioskFrame.this.inputTextField.getText() + ((JButton)actionEvent.getSource()).getText());
        			counter++;
        			if (counter == NUM_OF_DIGITS_FOR_STUDENT_ID)
        			{
        				KioskFrame.this.setConfirmButtonStatus(ButtonStatus.ENABLED);
        				KioskFrame.this.numberKeypad.disableAllButtons();
        				KioskFrame.this.confirmButton.requestFocus();
        			}
        		}
        	}
        	else if (currentStep == Step.INPUT_PIN)
        	{
        		String input = KioskFrame.this.convertCharArrayToString(PINInputField.getPassword()) + ((JButton)actionEvent.getSource()).getText();
        		PINInputField.setText(input);
        		KioskFrame.this.setConfirmButtonStatus(ButtonStatus.ENABLED);
        	}
        	else
        	{
        		//Add char to the input field, also enable confirm button.
        		KioskFrame.this.inputTextField.setText(KioskFrame.this.inputTextField.getText() + ((JButton)actionEvent.getSource()).getText());
        		KioskFrame.this.setConfirmButtonStatus(ButtonStatus.ENABLED);
        	}
        }
    }
	
	// ----------------------------------------------------------------
	// implement ActionListener for insurance company selection buttons
	// ----------------------------------------------------------------
	
	/** ActionListener for insurance company selection buttons */
	private class InsuranceCompanyButtonActionListener implements ActionListener{
		public void actionPerformed(ActionEvent actionEvent){
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.playButtonClickNormalSound();
			KioskFrame.this.companySelected = ((JButton)actionEvent.getSource()).getText();
			if (!companySelected.equals(InsuranceCompany.NOT_FOUND.getName()))
			{
				KioskFrame.this.goToNextStepFromCurrentStep();
				KioskFrame.this.resetCommonButtonContainer();
				KioskFrame.this.setConfirmButtonStatus(ButtonStatus.DISABLED);
			}
			else
			{
				//Go to the page telling the user to register with a company in order to purchase parking permit
				String instruction = "Please contact and have your vehicle insured with one of the companies<br>listed below in order to purchase parking permit. Thank you!";
				KioskFrame.this.updateInstruction(instruction);
				CardLayout cl = (CardLayout)(subCardLayoutPanel.getLayout());
				cl.show(subCardLayoutPanel, "insuranceCompanyHelpPanel");
				KioskFrame.this.hideConfirmButton();
			}
		}
	}
	
	// -----------------------------------------
	// Set up GridBagLayout constraints
	// -----------------------------------------
	
	public GridBagConstraints buildGridBagConstraintsForMainInputFieldPanel()
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 12;
		
		return constraints;
	}
	
	public GridBagConstraints buildGridBagConstraintsForKeypadPanel()
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth = 12;
		return constraints;
	}
	public GridBagConstraints buildGridBagConstraintsForConfirmButton()
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 14;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		return constraints;
	}
	
	public GridBagConstraints buildGridBagConstraintsForBackButton()
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		return constraints;
	}
	
	
	public GridBagConstraints buildGridBagConstraintsForBottomPanel()
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 16;
		return constraints;
	}
	
	public GridBagConstraints buildGridBagConstraintsForInsuranceCompanySelectionPanel()
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 4;
		constraints.gridwidth = 4;
		return constraints;
	}
	
	public String setHTMLTextProperties(String text, int size, String family, String alignment, String color)
	{
		return alignment + "<font face=" + family + " size=" + size + " color=" + color + ">" + text + "</font>";
	}
	
	// --------------------------------------------------------
	// implement methods for changing steps and updating views
	// --------------------------------------------------------
	
	/** Set the current step
	 * @param step the current step
	 *  */
	public void setCurrentStep(Step step)
	{
		this.currentStep = step;
	}
	
	/** Get the next step that follows the current step in a collection of steps
	 * @param currentStep the current step
	 * */
	public Step getNextStep(Step currentStep)
	{
		if (currentStep == Step.PRINT_PARKING_PERMIT)
		{
			return currentStep;
		}
		else
		{
			int currentStepIndex = currentStep.ordinal();
			return Step.values()[currentStepIndex + 1];
		}
	}
	
	public Step getPreviousStep(Step currentStep)
	{
		if (currentStep == Step.INPUT_INSURANCE_COMPANY_INFO)
		{
			return currentStep;
		}
		else
		{
			int currentStepIndex = currentStep.ordinal();
			return Step.values()[currentStepIndex - 1];
		}
	}
	
	/** Set the value of the counter */
	public void setCounterValue(int value)
	{
		this.counter = value;
	}
	
	/** Reset text input field. */
	public void resetInputField()
	{
		this.inputTextField.setText("");
	}
	
	/** Reset password input field. */
	public void resetPINInputField()
	{
		this.PINInputField.setText("");
	}
	
	/** Set the status of the confirm button to be enable or disabled. */
	public void setConfirmButtonStatus(ButtonStatus status)
	{
		if (status == ButtonStatus.ENABLED)
		{
			this.confirmButton.setEnabled(true);
		}
		else
		{
			this.confirmButton.setEnabled(false);
		}
	}
	
	/** Go to the next step from current step. */
	public void goToNextStepFromCurrentStep()
	{
		if (currentStep == Step.EDIT_EMAIL_ADDRESS || currentStep == Step.EDIT_EXPIRY_DATE || currentStep == Step.EDIT_VEHICLE_PLATE_NUMBER)
		{
			this.currentStep = Step.CONFIRM_PURCHASE_SUMMARY;
			this.purchaseSummaryPanel.removeAll();
			KioskFrame.this.buildPurchaseSummaryPanel(KioskFrame.this.purchaseSummaryPanel);
		}
		else
		{
			this.currentStep = KioskFrame.this.getNextStep(currentStep);
			if (currentStep.ordinal() == Step.CONFIRM_PURCHASE_SUMMARY.ordinal() - 1) //The next step is confirmation of purchase summary
			{
				//Build purchase summary panel based on user input information
				KioskFrame.this.purchaseSummaryPanel = new JPanel();
				purchaseSummaryPanel.setOpaque(false);
				KioskFrame.this.buildPurchaseSummaryPanel(KioskFrame.this.purchaseSummaryPanel);
				subCardLayoutPanel.add(purchaseSummaryPanel, "purchaseSummaryPanel");
				KioskFrame.this.hideBackButton();
			}
			if (this.currentStep == Step.INPUT_VEHICLE_PLATE_NUMBER)
			{
				this.setCapsLockOn();
				
			}
			else
			{
				this.setCapsLockOff();
			}
		}
		
		this.updateScreenViewBasedOnCurrentStep(currentStep);
		this.setCounterValue(0);
		this.resetInputField();
		this.resetPINInputField();
	}
	
	/** Go to the previous step from current step. */
	public void goToPreviousStepFromCurrentStep()
	{
		this.currentStep = KioskFrame.this.getPreviousStep(currentStep);
		this.updateScreenViewBasedOnCurrentStep(currentStep);
		this.setCounterValue(0);
		this.resetInputField();
		this.resetPINInputField();
	}
	
	public void hideBackButton()
	{
		this.commonButtonContainer.removeAll();
		this.commonButtonContainer.revalidate();
		this.commonButtonContainer.add(Box.createRigidArea(new Dimension(KioskFrame.BACK_BUTTON_SIZE.width * 5, 0)));
		this.commonButtonContainer.add(this.logoutButton);
		this.commonButtonContainer.add(Box.createRigidArea(new Dimension(KioskFrame.BACK_BUTTON_SIZE.width * 4, 0)));
		this.commonButtonContainer.add(this.confirmButton);
		this.commonButtonContainer.revalidate();
		this.repaint();
	}
	
	public void resetCommonButtonContainer()
	{
		this.commonButtonContainer.removeAll();
		this.commonButtonContainer.revalidate();
		this.commonButtonContainer.add(this.backButton);
		this.commonButtonContainer.add(Box.createRigidArea(new Dimension(KioskFrame.BACK_BUTTON_SIZE.width * 4, 0)));
		this.commonButtonContainer.add(this.logoutButton);
		this.commonButtonContainer.add(Box.createRigidArea(new Dimension(KioskFrame.BACK_BUTTON_SIZE.width * 4, 0)));
		this.commonButtonContainer.add(this.confirmButton);
		this.commonButtonContainer.revalidate();
		this.repaint();
	}
	
	public void hideConfirmButton()
	{
		this.commonButtonContainer.removeAll();
		this.commonButtonContainer.revalidate();
		this.commonButtonContainer.add(this.backButton);
		this.commonButtonContainer.add(Box.createRigidArea(new Dimension(KioskFrame.BACK_BUTTON_SIZE.width * 4, 0)));
		this.commonButtonContainer.add(this.logoutButton);
		this.commonButtonContainer.add(Box.createRigidArea(new Dimension(KioskFrame.BACK_BUTTON_SIZE.width * 5, 0)));
		this.commonButtonContainer.revalidate();
		this.repaint();
	}
	
	public void hideBackAndConfirmButton()
	{
		this.commonButtonContainer.removeAll();
		this.commonButtonContainer.revalidate();
		this.commonButtonContainer.add(Box.createRigidArea(new Dimension(KioskFrame.BACK_BUTTON_SIZE.width * 5, 0)));
		this.commonButtonContainer.add(this.logoutButton);
		this.commonButtonContainer.add(Box.createRigidArea(new Dimension(KioskFrame.BACK_BUTTON_SIZE.width * 5, 0)));
		this.commonButtonContainer.revalidate();
		this.repaint();
	}
	
	// -----------------------------------------
	// implement methods for instruction display
	// -----------------------------------------
	
	/** Create the display that shows instruction */
	public JTextPane buildInstructionDisplay(String instructionText)
	{
		JTextPane instructionDisplay = new JTextPane();
		
		UIDefaults defaults = new UIDefaults();
		defaults.put("TextPane[Enabled].backgroundPainter", MAIN_BACKGROUND_COLOR);
		instructionDisplay.putClientProperty("Nimbus.Overrides", defaults);
		instructionDisplay.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
		instructionDisplay.setBackground(MAIN_BACKGROUND_COLOR);
		  
		instructionDisplay.setEditable(false);
		instructionDisplay.setPreferredSize(new Dimension(200, 100));
		instructionDisplay.setContentType("text/html");
		instructionText = this.setHTMLTextProperties(instructionText, KioskFrame.INSTRUCTION_TEXT_FONT_SIZE, KioskFrame.INSTRUCTION_TEXT_FONT_FACE, "<center>", KioskFrame.INSTRUCTION_FONT_COLOR);
		instructionDisplay.setText(instructionText);

		return instructionDisplay;
	}
	
	public JLabel createLogoLabel()
	{
		JLabel logo = new JLabel();
		ImageIcon logoIcon = new ImageIcon(KioskFrame.LOGO_IMAGE_PATH);
		logo.setIcon(logoIcon);
		return logo;
	}
	
	/** Update the instruction text based on current step */
	public void updateInstructionBasedOnCurrentStep(Step currentStep)
	{
		String instruction = currentStep.getInstruction();
		if (currentStep == Step.INPUT_POLICY_NUMBER)
		{
			instruction = instruction + "<i>" + this.companySelected + "</i>";
		}
		instruction = this.setHTMLTextProperties(instruction, KioskFrame.INSTRUCTION_TEXT_FONT_SIZE, KioskFrame.INSTRUCTION_TEXT_FONT_FACE, "<center>", KioskFrame.INSTRUCTION_FONT_COLOR);
		this.instructionDisplay.setText(instruction);
	}
	
	/** Update the instruction display's text. */
	public void updateInstruction(String text)
	{
		text = this.setHTMLTextProperties(text, KioskFrame.INSTRUCTION_TEXT_FONT_SIZE, KioskFrame.INSTRUCTION_TEXT_FONT_FACE, "<center>", KioskFrame.INSTRUCTION_FONT_COLOR);
		this.instructionDisplay.setText(text);
	}
	
	// ---------------------------------------------------
	// implement methods for panels requiring keypad input
	// ---------------------------------------------------
	
	/** Create a panel that has keypad input function. The panel contains an input field and a keypad. */
	public void buildPanelWithKeypad()
	{
		//Set up main input field panel
		mainInputFieldPanel = this.buildMainInputFieldPanel();	
		
		//Set up keypad panel
		KeypadButtonListener keypadButtonListener = new KeypadButtonListener();
		
		JPanel numberKeypadPanel = this.buildNumberKeypadPanelwithActionListener(keypadButtonListener);
		numberKeypadPanel.setBorder(BorderFactory.createEmptyBorder(10, 240, 10, 240));
		this.fullKeypadPanel = new JPanel(new BorderLayout());

		//Add upper panel and right panel for email address input
		this.emailInputUpperPanel = this.buildEmailAddressKeypadUpperPanel();
		this.emailInputRightPanel = this.buildEmailCharacterKeypadRightPanel();
		
		this.emailInputUpperPanel.setVisible(false);
		this.emailInputRightPanel.setVisible(false);
		
		this.fullKeypadPanel.add(this.emailInputUpperPanel, BorderLayout.PAGE_START);
		fullKeypadPanel.add(this.buildQWERTYKeypadPanelwithActionListener(keypadButtonListener), BorderLayout.CENTER);
		this.fullKeypadPanel.add(this.emailInputRightPanel, BorderLayout.LINE_END);
		fullKeypadPanel.setOpaque(false);
		this.keypadPanel = this.buildKeypadPanelWithCardLayout(numberKeypadPanel, fullKeypadPanel);
		this.keypadPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
		this.keypadPanel.setOpaque(false);
		
		//Add components to the main panel with keypad
		this.panelWithKeypad = new JPanel(new GridBagLayout());
		panelWithKeypad.add(mainInputFieldPanel, this.buildGridBagConstraintsForMainInputFieldPanel());
		panelWithKeypad.add(keypadPanel, this.buildGridBagConstraintsForKeypadPanel());
		
		panelWithKeypad.setOpaque(false);
	}
	
	// -----------------------------------------------------------------
	// implement methods for building components of the main input field
	// -----------------------------------------------------------------
	
	public JPasswordField buildPINInputField()
	{
		JPasswordField panel = new JPasswordField(4);
		panel.setFont(KioskFrame.INPUT_FIELD_TEXT_FONT);
		panel.setPreferredSize(KioskFrame.PIN_INPUT_FIELD_SIZE);
		return panel;
	}
	
//	public JPanel buildInputPasswordPanel() {
//		
//	}
	public JTextField buildRegularInputField()
	{
		JTextField panel = new JTextField();
		panel.setBackground(KioskFrame.INPUT_FIELD_COLOR);
		return panel;
	}
	
	// --------------------------------------------
	// implement methods for main input field panel
	// --------------------------------------------
	
	/** The main input field panel consists of password input field and regular text input field. */
	public JPanel buildMainInputFieldPanel()
	{
		JPanel panel = new JPanel(new CardLayout());
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

		//Set up password entry field
        PINInputField = this.buildPINInputField();
        panel.add(PINInputField, "inputPINPanel");
        
		//Set up regular text input field
		inputTextField = this.buildRegularInputField();
		inputTextField.setText("");
		inputTextField.setFont(new Font("Helvetica Neue", Font.PLAIN, 28));
		panel.add(inputTextField, "regularInputTextField");
		
		return panel;
	}
	
	// ---------------------------------------------------------------------------
	// implement methods for changing the current panel view displayed to the user
	// ---------------------------------------------------------------------------
	
	/** Show relevant input field based on current step. 
	 * @param currentStep current step the user is at
	 * */
	public void changeMainInputFieldBasedOnCurrentStep(Step currentStep)
	{
		CardLayout clMainInputField = (CardLayout)(mainInputFieldPanel.getLayout());
		clMainInputField.show(mainInputFieldPanel, currentStep.getInputFieldType());
	}
	
	public void updateSubCardLayoutBasedOnCurrentStep(Step currentStep)
	{
		CardLayout cl = (CardLayout)(subCardLayoutPanel.getLayout());
		if (currentStep == Step.INPUT_INSURANCE_COMPANY_INFO)
		{
			cl.show(subCardLayoutPanel, "insuranceCompanySelectionPanel");
		}
		else if (currentStep == Step.INPUT_EXPIRY_DATE || currentStep == Step.EDIT_EXPIRY_DATE)
		{
			cl.show(subCardLayoutPanel, "expiryDateSelectionPanel");
		}
		else if (currentStep == Step.CONFIRM_PURCHASE_SUMMARY)
		{
			cl.show(subCardLayoutPanel, "purchaseSummaryPanel");
		}
		else 
		{
			cl.show(subCardLayoutPanel, "panelWithKeypad");
		}
	}
	
	public void updateScreenViewBasedOnCurrentStep(Step currentStep)
	{
		System.out.println("current step is " + currentStep);
		if (currentStep == Step.INPUT_EMAIL_ADDRESS || currentStep == Step.EDIT_EMAIL_ADDRESS)
		{
			this.setDisplayEmailAddressButtonPanel(true);
		}
		else
		{
			if (this.emailInputUpperPanel.isVisible())
			{
				this.setDisplayEmailAddressButtonPanel(false);
			}
		}
		if (this.currentStep == Step.CONFIRM_PURCHASE_SUMMARY)
		{
			this.updateConfirmButtonText("Purchase");
		}
		else
		{
			this.updateConfirmButtonText("Confirm");
		}
		if (currentStep == Step.INPUT_INSURANCE_COMPANY_INFO)
		{
			this.hideBackAndConfirmButton();
		}

		this.updateMainCardLayoutPanelBasedOnCurrentStep();
		this.updateInstructionBasedOnCurrentStep(currentStep);
		this.updateSubCardLayoutBasedOnCurrentStep(currentStep);
		this.changeMainInputFieldBasedOnCurrentStep(currentStep);
		this.changeKeypadPanelBasedOnCurrentStep(currentStep);
		
		if (currentStep == Step.PRINT_PARKING_PERMIT)
		{
			this.printPermitCountDown();
			this.backToMainScreenCountDown();
		}
		else if (currentStep == Step.WELCOME_SCREEN)
		{
			this.IDLE_COUNTDOWN_TIMER.stop();
			this.resetUserInputWhenLogout();
		}
	}
	
	public void updateMainCardLayoutPanelBasedOnCurrentStep()
	{
		CardLayout cl = (CardLayout)(mainScreenDisplayPanel.getLayout());
		if (currentStep == Step.WELCOME_SCREEN)
		{
			cl.show(mainScreenDisplayPanel, "welcomePanel");
		}
		else if (currentStep == Step.PRINT_PARKING_PERMIT)
		{
			cl.show(mainScreenDisplayPanel, "permitPrintPanel");
		}
		else
		{
			cl.show(mainScreenDisplayPanel, "panelWithInstructionDisplay");
		}
	}

	// ----------------------------------------------------------------
	// implement methods for building insurance company selection panel
	// ----------------------------------------------------------------
	
	public JPanel buildInsuranceCompanySelectionPanelWithListener(InsuranceCompanyButtonActionListener listener)
	{
		ArrayList<JButton> buttons = this.createButtonsBasedOnNumberOfInsuranceCompanies(this.numberOfCompanies);
		this.addCompanyNamesToButtons(buttons);
		this.setInsuranceCompanySelectionButtonsProperties(buttons);
		JPanel panel = new JPanel(new GridLayout(0, 2, 60, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 170, 10, 170));
		for (JButton button : buttons)
		{
			button.addActionListener(listener);
			panel.add(button);
		}
		
		panel.setOpaque(false);
		
		return panel;
	}
	
	public ArrayList<JButton> createButtonsBasedOnNumberOfInsuranceCompanies(int numberOfCompanies)
	{
		ArrayList<JButton> companies = new ArrayList<JButton>();
		for (int i = 0; i < numberOfCompanies; i++)
		{
			JButton button = new JButton();
			companies.add(button);
		}
		return companies;
	}
	
	public void addCompanyNamesToButtons(ArrayList<JButton> buttons)
	{
		for (int i = 0; i < this.numberOfCompanies; i++)
		{
			String companyName = InsuranceCompany.values()[i].getName();
			buttons.get(i).setText(companyName);
		}
	}
	
	public void setInsuranceCompanySelectionButtonsProperties(ArrayList<JButton> buttons)
	{
		for (JButton button : buttons)
		{
			button.setFont(new Font("Arial", Font.PLAIN, 20));
			button.setBackground(KioskFrame.MAIN_BACKGROUND_COLOR);
			button.setPreferredSize(new Dimension(36, 60));
		}
	}
	
	public JPanel buildInsuranceCompanyHelpPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7, 0));
		panel.setBorder(BorderFactory.createEmptyBorder(8, 240, 0, 240));
		panel.setOpaque(false);
		for (InsuranceCompany company : InsuranceCompany.values())
		{
			if (company != InsuranceCompany.NOT_FOUND)
			{
				String name = company.getName();
				String webAddress = company.getWebAddress();
				
				String outputText = String.format("<br><center><font face=" + "Helvetica Neue" + " size=5>%s</font><br><font face=\"Helvetica Neue\" size=6><b>%s</b></font></center>", name, webAddress);
				
				JTextPane pane = new JTextPane();
				pane.setEditable(false);
				pane.setOpaque(false);
				pane.setPreferredSize(new Dimension(200, 48));
				pane.setContentType("text/html");
				pane.setText(outputText);
				pane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				panel.add(pane);
			}
		}
		return panel;
	}
	
	// ----------------------------------------------------------------------------------------------
	// implement methods for building Expiry Date selection panel
	// Users can choose year, month, and date in this swing component.
	// The days in this date picker is generated dynamically based on the selected year and month.
	// ----------------------------------------------------------------------------------------------
	
	/** Create a JPanel that allow users to select year, month, and day.
	 *  @param currentYear the current year
	 *  @param currentMonth the current month
	 *  */
	public JPanel buildExpiryDateSelectionPanelBasedOnCurrentYearAndMonth(int currentYear, int currentMonth)
	{
		this.calculatePrice();
		JLabel startText = new JLabel("<html>" + this.setHTMLTextProperties("Your permit will expire on ", 6, KioskFrame.INSTRUCTION_TEXT_FONT_FACE, "<left>", KioskFrame.EXPIRY_DATE_FONT_COLOR));
		this.yearButton = this.buildOpenYearSelectionButton();
		this.monthButton = this.buildOpenMonthSelectionPanelButton();
		this.selectedDayDisplay = buildSelectedDayDisplay();
		JLabel endText = new JLabel("<html>" + this.setHTMLTextProperties(" ,at 11:59 pm.", 6, KioskFrame.INSTRUCTION_TEXT_FONT_FACE, "<left>", KioskFrame.EXPIRY_DATE_FONT_COLOR));
		
		JPanel yearMonthDayContainer = new JPanel(new FlowLayout());
		yearMonthDayContainer.setOpaque(false);
		yearMonthDayContainer.add(startText);
		yearMonthDayContainer.add(this.yearButton);
		yearMonthDayContainer.add(this.monthButton);
		yearMonthDayContainer.add(this.selectedDayDisplay);
		yearMonthDayContainer.add(endText);
		
		this.weekAndDateDisplay = this.buildWeekAndDateDisplayBasedOnYearAndMonth(currentYear, currentMonth);
		

		JPanel container = new JPanel(new FlowLayout());
		container.setOpaque(false);
		container.setPreferredSize(new Dimension(KioskFrame.DAY_SELECTION_BUTTON_SIZE.width * 7, KioskFrame.DAY_SELECTION_BUTTON_SIZE.height * 7));
		container.add(this.weekAndDateDisplay);
		container.setOpaque(false);

		JPanel dateSelectionWrapper = new JPanel();
		dateSelectionWrapper.setLayout(new BoxLayout(dateSelectionWrapper, BoxLayout.Y_AXIS));
		dateSelectionWrapper.add(yearMonthDayContainer);
		dateSelectionWrapper.add(container);
		
		dateSelectionWrapper.setOpaque(false);
		
		return dateSelectionWrapper;
	}
	
	/** A button. When clicked, triggers year selection action. */
	public JButton buildOpenYearSelectionButton()
	{
		String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		JButton button = new JButton();
		button.setBackground(KioskFrame.COMMON_BUTTON_COLOR);
		button.setPreferredSize(KioskFrame.OPEN_YEAR_SELECTION_DIALOG_BUTTON_SIZE);
		button.setText(currentYear);
		button.setFont(KioskFrame.CALENDAR_BUTTON_FONT);
		button.addActionListener(new OpenYearSelectionPanelActionListener());
		return button;
	}
	
	/** A JPanel that displays all available years for selection.
	 *  @return A panel that displays all available years for selection
	 *  */
	public JPanel buildYearSelectionButtonPanel()
	{
		JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
		{
			this.yearSelectionButtons = this.createButtonsForYearSelectionWithListener(this.currentYear, new yearSelectionButtonListener());
			for (JButton button : this.yearSelectionButtons)
			{
				button.setBackground(KioskFrame.KEYPAD_BUTTON_COLOR);
				button.setFont(KioskFrame.MONTH_SELECTION_BUTTON_FONT);
				panel.add(button);
			}
		}
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		panel.setBackground(KioskFrame.MAIN_BACKGROUND_COLOR);
		return panel;
	}
	
	/** Create a list of buttons that have the year number as their labels.
	 *  @param listener the ActionListener to be added to the buttons
	 *  @return A collection of buttons. Each button is labeled with the year starting from the current year. Each button is associated with an action listener.
	 *  */
	public ArrayList<JButton> createButtonsForYearSelectionWithListener(int currentYear, ActionListener listener)
	{
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		int availableYears = 6;
		for (int i = 0; i < availableYears; i++)
		{
			JButton button = new JButton("" + (currentYear + i));
			button.addActionListener(listener);
			button.setPreferredSize(new Dimension(160, 80));
			buttons.add(button);
		}
		return buttons;
	}
	
	/** A button. When clicked, open month selection dialog. */
	public JButton buildOpenMonthSelectionPanelButton()
	{
		String[] allMonths = new DateFormatSymbols().getMonths();
		int currentMonthIndex = Calendar.getInstance().get(Calendar.MONTH);
		JButton button = new JButton();
		button.setBackground(KioskFrame.COMMON_BUTTON_COLOR);
		button.setPreferredSize(KioskFrame.OPEN_MONTH_SELECTION_DIALOG_BUTTON_SIZE);
		button.setText(Month.valueOf(allMonths[currentMonthIndex]).getShortName());
		button.setFont(KioskFrame.CALENDAR_BUTTON_FONT);
		button.addActionListener(new OpenMonthSelectionPanelActionListener());
		return button;
	}
	
	/** A panel that displays all available months for selection.
	 */
	public JPanel buildMonthSelectionButtonPanel()
	{
		JPanel panel = new JPanel(new GridLayout(3, 4));
		{
			this.monthSelectionButtons = this.createButtonsForMonthSelectionWithListener(new monthSelectionButtonListener());
			for (JButton button : this.monthSelectionButtons)
			{
				this.disableUnSelectableMonths(button);
				button.setBackground(KioskFrame.KEYPAD_BUTTON_COLOR);
				button.setFont(KioskFrame.MONTH_SELECTION_BUTTON_FONT);
				button.setPreferredSize(new Dimension(160, 80));
				panel.add(button);
			}
		}
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		panel.setBackground(KioskFrame.MAIN_BACKGROUND_COLOR);
		return panel;
	}
	
	public void disableUnSelectableMonths(JButton button)
	{
		if (Integer.parseInt(this.yearSelected) == this.currentYear)
		{
			if (Month.valueOf(button.getText()).ordinal() < this.currentMonth)
			{
				button.setEnabled(false);
			}
		}
	}
	
	/** Create a list of JButtons objects that have the name of the month as their labels. 
	 * @return A collection of twelve buttons. Each button is labeled with the month name. Each button is associated with an action listener.
	 */
	public ArrayList<JButton> createButtonsForMonthSelectionWithListener(ActionListener listener)
	{
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		int totalMonths = Month.values().length;
		for (int i = 0; i < totalMonths; i++)
		{
			JButton button = new JButton(Month.values()[i].getFullName());
			button.addActionListener(listener);
			button.setPreferredSize(new Dimension(120, 60));
			buttons.add(button);
		}
		
		return buttons;
	}
	
	/** Create a dialog with the custom panel.
	 * @param panel a JPanel object to be displayed in the dialog
	 * @return a dialog that displays the passed panel
	 *  */
	public JDialog buildDialogWithCustomPanel(JPanel panel)
	{
		JDialog dialog = new JDialog(new JFrame(), "", true);
		dialog.setContentPane(panel);
		return dialog;
	}
	
	private class OpenYearSelectionPanelActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.playButtonClickNormalSound();
			JPanel panel = KioskFrame.this.buildYearSelectionButtonPanel();
			KioskFrame.this.yearSelectionDialog = KioskFrame.this.buildDialogWithCustomPanel(panel);
			KioskFrame.this.yearSelectionDialog.pack(); 
			KioskFrame.this.selectedYearButtonRequestFocus();
			KioskFrame.this.yearSelectionDialog.setLocationRelativeTo(keypadPanel);
			KioskFrame.this.yearSelectionDialog.setVisible(true);
		}
	}
	
	public void selectedYearButtonRequestFocus()
	{
		for (JButton button : KioskFrame.this.yearSelectionButtons)
		{
			if (button.getText().equals(KioskFrame.this.yearSelected))
			{
				button.requestFocus();
			}
		}
	}
	
	public void jcomponentRequestFocus(JComponent jcomponent)
	{
		jcomponent.requestFocus();
	}
	
	private class OpenMonthSelectionPanelActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.playButtonClickNormalSound();
			JPanel panel = KioskFrame.this.buildMonthSelectionButtonPanel();
			KioskFrame.this.monthSelectionDialog = KioskFrame.this.buildDialogWithCustomPanel(panel);
			KioskFrame.this.monthSelectionDialog.pack();
			KioskFrame.this.selectedMonthButtonRequestFocus();
			KioskFrame.this.monthSelectionDialog.setLocationRelativeTo(keypadPanel);
			KioskFrame.this.monthSelectionDialog.setVisible(true);
		}
	}
	
	/** The button whose name matches the selected month will request focus. */
	public void selectedMonthButtonRequestFocus()
	{
		for (JButton button : KioskFrame.this.monthSelectionButtons)
		{
			if (button.getText().equals(KioskFrame.this.monthSelected))
			{
				button.requestFocus();
			}
		}
	}
	
	private class yearSelectionButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.playButtonClickNormalSound();
			KioskFrame.this.yearSelectionDialog.dispose();
			KioskFrame.this.yearSelected = ((JButton) e.getSource()).getText();
			KioskFrame.this.yearButton.setText(KioskFrame.this.yearSelected);
			KioskFrame.this.updateWeekAndDateDisplayBasedOnYearAndMonthName(KioskFrame.this.yearSelected, KioskFrame.this.monthSelected);
			KioskFrame.this.calculatePrice();
		}
	}
	
	private class monthSelectionButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.playButtonClickNormalSound();
			KioskFrame.this.monthSelectionDialog.dispose();
			KioskFrame.this.monthSelected = ((JButton) e.getSource()).getText();
			KioskFrame.this.monthButton.setText(Month.valueOf(KioskFrame.this.monthSelected).getShortName());
			KioskFrame.this.updateWeekAndDateDisplayBasedOnYearAndMonthName(KioskFrame.this.yearSelected, KioskFrame.this.monthSelected);
			KioskFrame.this.calculatePrice();
		}
	}
	
	private class DaySelectionButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.playButtonClickNormalSound();
			KioskFrame.this.daySelected = ((JButton) e.getSource()).getText();
			KioskFrame.this.calculatePrice();
			KioskFrame.this.updateSelectedDayDisplay();
		}
	}
	
	public JLabel buildSelectedDayDisplay()
	{
		JLabel label = new JLabel();
		label.setBorder(BorderFactory.createEtchedBorder());
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setPreferredSize(KioskFrame.SELECTED_DAY_DISPLAY_SIZE);
		label.setText(String.format("<html><center><font size=6 color=%s>%s</font></center>", KioskFrame.SELECTED_DATE_FONT_COLOR, this.daySelected));
		return label;
	}
	
	public void updateSelectedDayDisplay()
	{
		this.selectedDayDisplay.setText(String.format("<html><center><font size=6 color=%s>%s</font></center>", KioskFrame.SELECTED_DATE_FONT_COLOR, this.daySelected));
	}
	
	public GridBagConstraints gridBagConstraintsForYearSeletionOpenButton()
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		return constraints;
	}
	
	public GridBagConstraints gridBagConstraintsForMonthSeletionOpenButton()
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		return constraints;
	}
	
	public GridBagConstraints gridBagConstraintsForDateSeletionPanel()
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 7;
		return constraints;
	}
	
	public ArrayList<String> generateAvailableYears(int numberOfYearsAvailableFromNow)
	{
		ArrayList<String> availableYears = new ArrayList<String>();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = 0; i < numberOfYearsAvailableFromNow; i++)
		{
			availableYears.add("" + currentYear);
		}
		return availableYears;
	}
	
	/** Create a panel that displays the week and days in a given month of a given year. 
	 * @param year year
	 * @param month month
	 * @return A panel that displays the names of the week and the dates in a given year and month.
	 * */
	public JPanel buildWeekAndDateDisplayBasedOnYearAndMonth(int year, int month)
	{	
		JPanel calendarPanel = new JPanel(new GridBagLayout());
		calendarPanel.setBackground(KioskFrame.MAIN_BACKGROUND_COLOR);
		this.buildCalendarInPanelWithForYearAndMonth(calendarPanel, year, month);
		return calendarPanel;
	}
	
	/** Given a panel, year, and month, build a calendar for the given year and month inside the panel. 
	 *  @param calendarPanel a panel used to contain the calendar
	 *  @param year year
	 *  @param month month
	 *  */
	public void buildCalendarInPanelWithForYearAndMonth(JPanel calendarPanel, int year, int month)
	{
		calendarPanel.removeAll();
		calendarPanel.revalidate();
		int day = 1;
		Calendar calendar = new GregorianCalendar(year, month, day);
		int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println("day in month " + month + " is " + daysInMonth);
		this.dateButtons = this.createButtonsBasedOnNumberOfDays(daysInMonth);
		ArrayList<JLabel> namesOfDaysLabels = this.createNamesOfDaysLabels();
		int gridxValue = 0;
		int gridyValue = 0;
		for (JLabel nameOfDay : namesOfDaysLabels)
		{
			if (gridxValue < 7)
			{
				calendarPanel.add(nameOfDay, this.setGridBagConstraints(gridxValue, gridyValue));
			}
			gridxValue++;
		}
		
		gridyValue++;
		gridxValue = this.getNamesOfDaysList().indexOf(this.getNameOfTheFirstDayOfTheYearAndMonth(year, month));
		System.out.println("gridxValue is " + gridxValue);
		
		for (JButton dateButton : this.dateButtons)
		{	
			if (gridxValue < 7)
			{
				calendarPanel.add(dateButton, this.setGridBagConstraints(gridxValue, gridyValue));	
			}
			else
			{
				gridxValue = 0;
				gridyValue++;
				calendarPanel.add(dateButton, this.setGridBagConstraints(gridxValue, gridyValue));
			}
			gridxValue++;
		}
		calendarPanel.revalidate();
		calendarPanel.repaint();
	}
	
	public void setSelectedDateButtonFocus()
	{
		for (JButton dateButton : this.dateButtons)
		{
			if (dateButton.getText().equals(this.daySelected))
			{
				dateButton.requestFocus();
			}
		}
	}
	
	/** Update the week and days based on the given year and month.
	 * @param yearName year
	 * @param monthName name of the month
	 *  */
	public void updateWeekAndDateDisplayBasedOnYearAndMonthName(String yearName, String monthName)
	{
		this.weekAndDateDisplay.removeAll();
		int year = Integer.parseInt(yearName);
		this.buildCalendarInPanelWithForYearAndMonth(this.weekAndDateDisplay, year, Month.valueOf(monthName).ordinal());
		this.weekAndDateDisplay.revalidate();
		this.weekAndDateDisplay.repaint();
	}
	
	/** Create a collection of buttons labeled with the date of each day starting from 1.
	 * @return a collection of button
	 *  */
	public ArrayList<JButton> createButtonsBasedOnNumberOfDays(int days)
	{
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		for (int i = 0; i < days; i++)
		{
			JButton button = new JButton("" + (i + 1));
			button.setBackground(KioskFrame.KEYPAD_BUTTON_COLOR);
			button.setFont(KioskFrame.CALENDAR_BUTTON_FONT);
			button.setPreferredSize(KioskFrame.DAY_SELECTION_BUTTON_SIZE);
			this.setCurrentDayInMonthButtonForegroundColor(button, Color.RED);
			button.addActionListener(new DaySelectionButtonListener());
			this.disableUnselectableDay(button);
			buttons.add(button);
		}
		return buttons;
	}
	
	public void disableUnselectableDay(JButton button)
	{
		if (Integer.parseInt(this.yearSelected) == this.currentYear && Month.valueOf(this.monthSelected).ordinal() == this.currentMonth)
		{
			if (Integer.parseInt(button.getText()) < this.currentDay)
			{
				button.setEnabled(false);
			}
		}
		else if (Integer.parseInt(this.yearSelected) == this.currentYear && Month.valueOf(this.monthSelected).ordinal() < this.currentMonth)
		{
			button.setEnabled(false);
		}
	}
	
	/** Get the current day in current month.
	 * @return current day in current month
	 *  */
	public int getCurrentDay()
	{
		int currentDay;
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("d");
		currentDay = Integer.parseInt(sdf.format(date));
		return currentDay;
	}
	
	public void setSelectedDayInMonthButtonColor(JButton button, Color color)
	{
		button.setBackground(color);
	}
	
	public void setCurrentDayInMonthButtonForegroundColor(JButton button, Color color)
	{
		if (Integer.parseInt(button.getText()) == this.getCurrentDay())
		{
			button.setForeground(color);
		}
	}
	
	/** Create a collection of labels for the names of the days of a seven-day week */
	public ArrayList<JLabel> createNamesOfDaysLabels()
	{
		ArrayList<String> namesOfDaysList = this.getNamesOfDaysList();
		ArrayList<JLabel> weekLabels = new ArrayList<JLabel>();
		for (String nameOfDay : namesOfDaysList )
		{
			JLabel weekNameLabel = new JLabel();
			weekNameLabel.setText(String.format("<html><font size=5 color=%s>%s</font>", KioskFrame.EXPIRY_DATE_FONT_COLOR, nameOfDay));
			weekNameLabel.setFont(CALENDAR_BUTTON_FONT);
			weekNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			weekNameLabel.setPreferredSize(KioskFrame.DAY_NAME_LABEL_SIZE);
			weekLabels.add(weekNameLabel);
		}
		return weekLabels;
	}
	
	/** Get the name of the first day in the given month of the given year. 
	 * @param year year
	 * @param month month
	 * @return the name of the first day in the given month of the given year
	 * */
	public String getNameOfTheFirstDayOfTheYearAndMonth(int year, int month)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		DateFormat dateFormat = new SimpleDateFormat("E");
		return dateFormat.format(cal.getTime());
	}
	
	public GridBagConstraints setGridBagConstraints(int gridxValue, int gridyValue)
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridxValue;
		constraints.gridy = gridyValue;
		return constraints;
	}
	
	/** Get a collection of names of the days.
	 * @return a collection of names of the days
	 *  */
	public ArrayList<String> getNamesOfDaysList()
	{
		String[] namesOfDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		ArrayList<String> namesOfDaysList = new ArrayList<String>();
		{
			for (int i = 0; i < namesOfDays.length; i++)
			{
				namesOfDaysList.add(namesOfDays[i]);
			}
		}
		return namesOfDaysList;
	}
	
	public void calculatePrice()
	{
		int yearSelected = Integer.parseInt(KioskFrame.this.yearSelected);
		int monthSelected = Month.valueOf(KioskFrame.this.monthSelected).ordinal();
		int daySelected = Integer.parseInt(KioskFrame.this.daySelected);
		KioskFrame.price = this.calculatePriceBasedOnExpiryDate(yearSelected,monthSelected, daySelected);
	}
	
	public double calculatePriceBasedOnExpiryDate(int year, int month, int date)
	{
		double price;
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(this.currentYear, this.currentMonth, this.currentDay);
		Date startDate = calendar1.getTime();
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(year, month, date);
		Date expiryDate = calendar2.getTime();
		
		int daysBetween = Math.round((expiryDate.getTime() - startDate.getTime()) / 86400000) + 1; //Current day is also included
		
		price = KioskFrame.PARKING_RATE * daysBetween;
		System.out.println("price is " + price);
		return price;
	}
	
	// -------------------------------------------
	// implement methods for email address input
	// -------------------------------------------
	
	public JPanel buildEmailAddressKeypadUpperPanel()
	{
		String[] buttonLabels = {"@yorku.ca", "@gmail.com", "@yahoo.com", ".ca", ".com"};
		Keypad upperPanelButtons = new Keypad(buttonLabels);
		this.setEmailInputUpperPanelButtonProperties(upperPanelButtons.getAllButtons());
		this.addActionListenerToButtons(upperPanelButtons.getAllButtons(), new KeypadButtonListener());
		
		JPanel upperPanel = new JPanel(new FlowLayout());
		this.addButtonsToPanel(upperPanelButtons.getAllButtons(), upperPanel);
		
		upperPanel.setOpaque(false);
		
		return upperPanel;
	}
	
	public void setEmailInputUpperPanelButtonProperties(ArrayList<JButton> buttons)
	{
		for (JButton button : buttons)
		{
			button.setBackground(KioskFrame.COMMON_BUTTON_COLOR);
			button.setPreferredSize(KioskFrame.EMAIL_UPPER_PANEL_BUTTON_SIZE);
			button.setFont(KioskFrame.EMAIL_INPUT_UPPERPANEL_FONT);
		}
	}
	
	/***/
	public JPanel buildEmailCharacterKeypadRightPanel() {
		String[] buttonLabels = {"@", "_", "-", "."};
		Keypad rightPanelButtons = new Keypad(buttonLabels);
		this.setEmailInputRightPanelButonProperties(rightPanelButtons.getAllButtons());
		this.addActionListenerToButtons(rightPanelButtons.getAllButtons(), new KeypadButtonListener());
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridBagLayout());
		for (JButton button : rightPanelButtons.getAllButtons())
		{
			JPanel buttonWrapper = new JPanel();
			buttonWrapper.setOpaque(false);
			buttonWrapper.add(button);
			rightPanel.add(buttonWrapper, this.createGridBagConstraints(0, rightPanelButtons.getAllButtons().indexOf(button), 1));
		}
		
		rightPanel.setOpaque(false);
		
		return rightPanel;
	}
	
	/** Set the properties of the right panel for inputing email address. */
	public void setEmailInputRightPanelButonProperties(ArrayList<JButton> buttons) {
		
		for (JButton button : buttons) {
			button.setBackground(KioskFrame.COMMON_BUTTON_COLOR);
			button.setFont(KioskFrame.FULL_KEYPAD_FONT);
			button.setPreferredSize(KioskFrame.KEYPAD_BUTTON_SIZE_REGULAR);
		}
	}
	
	/** Set whether the email input panels are visible or not. */
	public void setDisplayEmailAddressButtonPanel(boolean visible)
	{
		this.emailInputRightPanel.setVisible(visible);
		this.emailInputUpperPanel.setVisible(visible);
		this.revalidate();
		this.repaint();
	}
	
	/** Check if the input email matches the required patterns. */
	public boolean validateEmailInputFormat(String input)
	{
		Pattern pattern = Pattern.compile(".+@.+\\..+");
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
	
	// -------------------------------------------------
	// implement methods for confirm summary of purchase
	// -------------------------------------------------
	
	public void buildPurchaseSummaryPanel(JPanel purchaseSummaryPanel)
	{
		purchaseSummaryPanel.removeAll();
		
		JLabel studentNumber = new JLabel(String.format("<html><font size=6 color=%s><b>Student number:</b> %s</font>", KioskFrame.PURCHASE_SUMMARY_FONT_COLOR, KioskFrame.studentInfo.get(0)), JLabel.LEFT);
		studentNumber.setOpaque(false);
		JPanel studentNumberWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
		studentNumberWrapper.setOpaque(false);
		studentNumberWrapper.add(studentNumber);
		
		JLabel insuranceCompany = new JLabel(String.format("<html><font size=6 color=%s><b>Insurance company:</b> %s</font>", KioskFrame.PURCHASE_SUMMARY_FONT_COLOR, this.companySelected));
		insuranceCompany.setOpaque(false);
		JPanel insuranceCompanyWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
		insuranceCompanyWrapper.setOpaque(false);
		insuranceCompanyWrapper.add(insuranceCompany);
		
		JLabel plateNumber = new JLabel(String.format("<html><font size=6 color=%s><b>Plate number:</b> %s</font>", KioskFrame.PURCHASE_SUMMARY_FONT_COLOR, KioskFrame.vehiclePlateNumber));
		plateNumber.setOpaque(false);
		JButton plateNumberEditButton = this.buildPlateNumberEditButton();
		JPanel plateNumberWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
		plateNumberWrapper.setOpaque(false);
		plateNumberWrapper.add(plateNumber);
		
		JLabel emailAddress = new JLabel(String.format("<html><font size=6 color=%s><b>Email address:</b> %s</font>", KioskFrame.PURCHASE_SUMMARY_FONT_COLOR, KioskFrame.emailAddress));
		emailAddress.setOpaque(false);
		JButton emailAddressEditButton = this.buildEmailAddressEditButton();
		JPanel emailAddressWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
		emailAddressWrapper.setOpaque(false);
		emailAddressWrapper.add(emailAddress);
		
		JLabel expiryDate = new JLabel(String.format("<html><font size=6 color=%s><b>Expiry date:</b> %s %s %s, at 11:59 pm</font>", KioskFrame.PURCHASE_SUMMARY_FONT_COLOR, this.yearSelected, this.monthSelected, this.daySelected));
		expiryDate.setOpaque(false);
		JButton expiryDateEditButton = this.buildExpiryDateEditButton();
		JPanel expiryDateWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
		expiryDateWrapper.setOpaque(false);
		expiryDateWrapper.add(expiryDate);

		JLabel cost = new JLabel(String.format("<html><font size=6 color=%s><b>Price:</b> $%s", "#FF0000", KioskFrame.price));
		cost.setOpaque(false);
		JPanel costWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
		costWrapper.setOpaque(false);
		costWrapper.add(cost);
		
		JPanel leftPanel = new JPanel(new GridLayout(6, 1, 0, 5));
		leftPanel.setOpaque(false);
		leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		
		leftPanel.add(studentNumberWrapper);
		leftPanel.add(insuranceCompanyWrapper);
		leftPanel.add(plateNumberWrapper);
		leftPanel.add(emailAddressWrapper);
		leftPanel.add(expiryDateWrapper);
		leftPanel.add(costWrapper);
		
		JPanel rightPanel = new JPanel(new GridLayout(6, 1, 0, 5));
		rightPanel.setOpaque(false);
		
		rightPanel.add(Box.createRigidArea(new Dimension(0,0)));
		rightPanel.add(Box.createRigidArea(new Dimension(0,0)));
		rightPanel.add(plateNumberEditButton);
		rightPanel.add(emailAddressEditButton);
		rightPanel.add(expiryDateEditButton);
		rightPanel.add(Box.createRigidArea(new Dimension(0,0)));
		
		JPanel panelWithEditButtonWrapper = new JPanel(new BorderLayout());
		panelWithEditButtonWrapper.setOpaque(false);
		panelWithEditButtonWrapper.setAlignmentY(Component.CENTER_ALIGNMENT);
		panelWithEditButtonWrapper.add(leftPanel, BorderLayout.CENTER);
		panelWithEditButtonWrapper.add(rightPanel, BorderLayout.LINE_END);
		
		purchaseSummaryPanel.setLayout(new GridBagLayout());
		purchaseSummaryPanel.add(panelWithEditButtonWrapper);
		
//		this.drawDashedLine(purchaseSummaryPanel.getGraphics(), (int)plateNumberEditButton.getLocation().getX(), (int)plateNumberEditButton.getLocation().getY(), (int)plateNumber.getLocation().getX(), (int)plateNumber.getLocation().getY());
	}
	
	public void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2)
	{
		Graphics2D g2d= (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(x1, y1, x2, y2);
		
		g2d.dispose();
	}
	
	public JButton buildEmailAddressEditButton()
	{
		JButton emailAddressEditButton = new JButton();
		Icon editIcon = new ImageIcon("Ic_menu_pencil.png");
		emailAddressEditButton.setIcon(editIcon);
		emailAddressEditButton.setName("emailAddressEdit");
		emailAddressEditButton.setPreferredSize(KioskFrame.EDIT_BUTTON_SIZE);
		emailAddressEditButton.setFont(KioskFrame.EDIT_BUTTON_FONT);
		emailAddressEditButton.setBackground(KioskFrame.KEYPAD_BUTTON_COLOR);
		//emailAddressEditButton.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		emailAddressEditButton.addActionListener(new EditInfoButtonActionListener());
		emailAddressEditButton.setOpaque(false);
		emailAddressEditButton.setContentAreaFilled(false);
		emailAddressEditButton.setBorderPainted(false);
		return emailAddressEditButton; 
	}
	
	public JButton buildPlateNumberEditButton()
	{
		JButton plateNumberEditButton = new JButton();
		Icon editIcon = new ImageIcon("Ic_menu_pencil.png");
		plateNumberEditButton.setIcon(editIcon);
		plateNumberEditButton.setName("plateNumberEdit");
		plateNumberEditButton.setPreferredSize(KioskFrame.EDIT_BUTTON_SIZE);
		plateNumberEditButton.setFont(KioskFrame.EDIT_BUTTON_FONT);
		plateNumberEditButton.setBackground(KioskFrame.KEYPAD_BUTTON_COLOR);
		plateNumberEditButton.addActionListener(new EditInfoButtonActionListener());
		plateNumberEditButton.setOpaque(false);
		plateNumberEditButton.setContentAreaFilled(false);
		plateNumberEditButton.setBorderPainted(false);
		return plateNumberEditButton;
	}
	//xiezhe
	public JButton buildExpiryDateEditButton()
	{
		JButton expiryDateEditButton = new JButton();
		Icon editIcon = new ImageIcon("Ic_menu_pencil.png");
		expiryDateEditButton.setIcon(editIcon);
		expiryDateEditButton.setName("expiryDateEdit");
		expiryDateEditButton.setPreferredSize(KioskFrame.EDIT_BUTTON_SIZE);
		expiryDateEditButton.setFont(KioskFrame.EDIT_BUTTON_FONT);
		expiryDateEditButton.setBackground(KioskFrame.KEYPAD_BUTTON_COLOR);
		expiryDateEditButton.addActionListener(new EditInfoButtonActionListener());
		expiryDateEditButton.setOpaque(false);
		expiryDateEditButton.setContentAreaFilled(false);
		expiryDateEditButton.setBorderPainted(false);
		return expiryDateEditButton;
	}
	
	/** ActionListener for the edit buttons. */
	private class EditInfoButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.playButtonClickNormalSound();
			String name = ((JButton) e.getSource()).getName();
			if (name.equals("plateNumberEdit"))
			{
				KioskFrame.this.currentStep = Step.EDIT_VEHICLE_PLATE_NUMBER;
				KioskFrame.this.updateScreenViewBasedOnCurrentStep(currentStep);
				KioskFrame.this.inputTextField.setText(KioskFrame.vehiclePlateNumber);
				KioskFrame.this.setConfirmButtonStatus(ButtonStatus.ENABLED);
			}
			else if (name.equals("emailAddressEdit"))
			{
				KioskFrame.this.currentStep = Step.EDIT_EMAIL_ADDRESS;
				KioskFrame.this.updateScreenViewBasedOnCurrentStep(currentStep);
				KioskFrame.this.inputTextField.setText(KioskFrame.emailAddress);
				if (KioskFrame.emailAddress.equals(KioskFrame.DEFAULT_EMAIL))
				{
					KioskFrame.this.inputTextField.setText("");
				}
				KioskFrame.this.setConfirmButtonStatus(ButtonStatus.ENABLED);
			}
			else if (name.equals("expiryDateEdit"))
			{
				KioskFrame.this.currentStep = Step.EDIT_EXPIRY_DATE;
				KioskFrame.this.updateScreenViewBasedOnCurrentStep(currentStep);
				KioskFrame.this.updateWeekAndDateDisplayBasedOnYearAndMonthName(KioskFrame.this.yearSelected, KioskFrame.this.monthSelected);
				KioskFrame.this.setSelectedDateButtonFocus();
				KioskFrame.this.setConfirmButtonStatus(ButtonStatus.ENABLED);
			}
		}
	}
	
	// -----------------------------------------------
	// implement methods for email address input step
	// -----------------------------------------------
	public void updateEmailAddress()
	{
		if (KioskFrame.this.inputTextField.getText().length() != 0)
		{
			if (KioskFrame.this.validateEmailInputFormat(KioskFrame.this.inputTextField.getText()))
			{
				KioskFrame.emailAddress = KioskFrame.this.inputTextField.getText();
				KioskFrame.this.goToNextStepFromCurrentStep();
			}
			else
			{
				KioskFrame.this.addWarningToInstruction("The format of the email address is not correct.");
				KioskFrame.this.setConfirmButtonStatus(ButtonStatus.DISABLED);
			}
		}
		else
		{
			KioskFrame.emailAddress = KioskFrame.DEFAULT_EMAIL;
			KioskFrame.this.goToNextStepFromCurrentStep();
		}
	}
	
	// ---------------------------------------------
	// implement methods for printing parking permit
	// ---------------------------------------------
	
	/** Create a panel that display text after the user has confirmed purchase and started printing the permit. */
	public void buildPermitPrintPanel()
	{
		JTextPane infoDisplay = new JTextPane();
		infoDisplay.setOpaque(false);
		UIDefaults defaults = new UIDefaults();
		defaults.put("TextPane[Enabled].backgroundPainter", MAIN_BACKGROUND_COLOR);
		infoDisplay.putClientProperty("Nimbus.Overrides", defaults);
		infoDisplay.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
		infoDisplay.setBackground(MAIN_BACKGROUND_COLOR);
		infoDisplay.setContentType("text/html");
		infoDisplay.setText(String.format("<html><center><font face=%s size=%s color=%s>%s</font></center>", KioskFrame.PERMIT_PRINT_FONT_FACE, KioskFrame.PERMIT_PRINT_FONT_SIZE, KioskFrame.PERMIT_PRINT_FONT_COLOR, KioskFrame.PERMIT_PRINT_TEXT));
		
		//Icon by PJ
		JLabel iconLogo = new JLabel();
		Icon icon = new ImageIcon("Untitled-1.png");
		iconLogo.setIcon(icon);
		
		this.permitPrintPanel = new JPanel(new GridBagLayout());
		permitPrintPanel.setBackground(KioskFrame.MAIN_BACKGROUND_COLOR);
		permitPrintPanel.add(iconLogo, this.createGridBagConstraints(0, 0, 1));
		permitPrintPanel.add(infoDisplay, this.createGridBagConstraints(0, 1, 0));	
	}
	
	public JPanel permitPrintPopupPanel()
	{
		JLabel permitTitle = new JLabel(String.format("<html><centr><font face=\"%s\" size=%s color = %s>STUDENT PARKING PERMIT</font></center>", KioskFrame.INSTRUCTION_TEXT_FONT_FACE, 6, KioskFrame.INSTRUCTION_FONT_COLOR));
		permitTitle.setOpaque(false);
		permitTitle.setHorizontalAlignment(SwingConstants.CENTER);
		permitTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		
		JLabel plateNumberLabel = new JLabel(String.format("<html><font face=\"%s\" size=%s color = %s>PLATE NO: <b>%s</b></font>", KioskFrame.INSTRUCTION_TEXT_FONT_FACE, 6, KioskFrame.INSTRUCTION_FONT_COLOR, KioskFrame.vehiclePlateNumber));
		plateNumberLabel.setOpaque(false);
		plateNumberLabel.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
		
		JLabel expiryDateLabel = new JLabel(String.format("<html><center><font face=\"%s\" size=%s color = %s><b>EXPIRY DATE: %s %s %s</b></font></center>", KioskFrame.INSTRUCTION_TEXT_FONT_FACE, 6 ,KioskFrame.PRINTED_PERMIT_FONT_COLOR, this.yearSelected, this.monthSelected, this.daySelected));
		expiryDateLabel.setOpaque(false);
	
		JPanel infoWrapper = new JPanel(new GridLayout(2, 1));
		infoWrapper.setOpaque(false);
		infoWrapper.add(plateNumberLabel);
		infoWrapper.add(expiryDateLabel);
		
		JPanel permitPanel = new JPanel(new GridLayout(2, 1));
		permitPanel.setBackground(Color.WHITE);
		permitPanel.setBorder(BorderFactory.createEmptyBorder(26, 60, 40, 60));
		permitPanel.add(permitTitle);
		permitPanel.add(infoWrapper);
		
		return permitPanel;
	}
	
	public void printPermitCountDown()
	{
		this.permitPrintTimer = new Timer(5000, new PrintPermitAction());
		this.permitPrintTimer.start();
	}
	
	public void backToMainScreenCountDown()
	{
		this.switchPanelTimer = new Timer(10000, new BackToMainScreenActoin());
		this.switchPanelTimer.start();
	}
	
	private class PrintPermitAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.permitPrintTimer.stop();
			JPanel permit = KioskFrame.this.permitPrintPopupPanel();
			KioskFrame.printedPermit = KioskFrame.this.buildDialogWithCustomPanel(permit);
			KioskFrame.printedPermit.pack();
			KioskFrame.printedPermit.setLocationRelativeTo(keypadPanel);
			KioskFrame.printedPermit.setVisible(true);
		}
	}
	
	private class BackToMainScreenActoin implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.switchPanelTimer.stop();
			KioskFrame.this.currentStep = Step.WELCOME_SCREEN;
			KioskFrame.this.updateScreenViewBasedOnCurrentStep(currentStep);
			KioskFrame.this.resetCommonButtonContainer();
		}
	}
	
//	public void playPermitPrintSound()
//	{
//		
//	}
	
	// ----------------------------------------
	// implement methods for building keypads
	// ----------------------------------------
	
	/** Create a number keypad object */
	public Keypad createNumberKeypad()
	{
		String numberKeys[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
		return (new Keypad(numberKeys));
	}
	
	/** Set the properties of the number buttons*/
	public void setNumberButtonsProperty(ArrayList<JButton> buttons)
	{
		for (JButton button : buttons)
		{
			button.setFont(KioskFrame.NUMBER_KEYPAD_BUTTON_FONT);
			button.setPreferredSize(KioskFrame.NUMBER_KEYPAD_NUMBER_BUTTON_SIZE);
			button.setBackground(KioskFrame.KEYPAD_BUTTON_COLOR);
		}
	}
	public JPanel createPanelForNumberKeypad()
	{
	JPanel numberKeypadPanel = new JPanel();
	numberKeypadPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	numberKeypadPanel.setLayout(new GridBagLayout());

	numberKeypadPanel.add(this.numberKeypad.getAllButtons().get(0), this.createGridBagConstraints(0, 0, 1));
	numberKeypadPanel.add(this.numberKeypad.getAllButtons().get(1), this.createGridBagConstraints(1, 0, 1));
	numberKeypadPanel.add(this.numberKeypad.getAllButtons().get(2), this.createGridBagConstraints(2, 0, 1));
	numberKeypadPanel.add(this.numberKeypad.getAllButtons().get(3), this.createGridBagConstraints(0, 1, 1));
	numberKeypadPanel.add(this.numberKeypad.getAllButtons().get(4), this.createGridBagConstraints(1, 1, 1));
	numberKeypadPanel.add(this.numberKeypad.getAllButtons().get(5), this.createGridBagConstraints(2, 1, 1));
	numberKeypadPanel.add(this.numberKeypad.getAllButtons().get(6), this.createGridBagConstraints(0, 2, 1));
	numberKeypadPanel.add(this.numberKeypad.getAllButtons().get(7), this.createGridBagConstraints(1, 2, 1));
	numberKeypadPanel.add(this.numberKeypad.getAllButtons().get(8), this.createGridBagConstraints(2, 2, 1));
	numberKeypadPanel.add(this.numberKeypad.getAllButtons().get(9), this.createGridBagConstraints(0, 3, 1));

	return numberKeypadPanel;
	}

	/** Create a panel that contains buttons for number input. */
	public JPanel buildNumberKeypadPanelwithActionListener(KeypadButtonListener keypadButtonListener)
	{
		this.numberKeypad = createNumberKeypad();
		JPanel numberKeypadPanel = createPanelForNumberKeypad();
		setNumberButtonsProperty(numberKeypad.getAllButtons());
		addActionListenerToButtons(numberKeypad.getAllButtons(), keypadButtonListener);
	
		//Add delete button
		JButton deleteButton = new JButton("Delete");
		deleteButton.setFont(KioskFrame.NUMBER_KEYPAD_DELETE_BUTTON_FONT);
		deleteButton.setBackground(KioskFrame.COMMON_BUTTON_COLOR);
		
		deleteButton.setPreferredSize(KioskFrame.NUMBER_KEYPAD_DELETE_BUTTON_SIZE);
		deleteButton.addMouseListener(new DeleteButtonListener());
		numberKeypadPanel.add(deleteButton, this.createGridBagConstraints(1, 3, 2));
	
		numberKeypadPanel.setOpaque(false);
	
		return numberKeypadPanel;
	}
	
	//Create QWERTY keypad
	public Keypad createQWERTYkeypadRowOne()
	{
		String characterKeys[] = {"q", "w", "e", "r", "t", "y", "u", "i", "o", "p"};
		return (new Keypad(characterKeys));
	}
	
	public Keypad createQWERTYkeypadRowTwo()
	{
		String characterKeys[] = {"a", "s", "d", "f", "g", "h", "j", "k", "l"};
		return (new Keypad(characterKeys));
	}
	
	public Keypad createQWERTYkeypadRowThree()
	{
		String characterKeys[] = {"z", "x", "c", "v", "b", "n", "m"};
		return (new Keypad(characterKeys));
	}
	
	
	public JPanel buildQWERTYKeypadPanelwithActionListener(KeypadButtonListener keypadButtonListener)
	{
		Keypad numberKeypad = this.createNumberKeypad();
		JPanel numberRowPanel = new JPanel();
		numberRowPanel.setOpaque(false);
		this.addButtonsToPanel(numberKeypad.getAllButtons(), numberRowPanel);
		
		this.keypadRowOne = createQWERTYkeypadRowOne();
		JPanel rowOnePanel = new JPanel();
		rowOnePanel.setOpaque(false);
		this.addButtonsToPanel(keypadRowOne.getAllButtons(), rowOnePanel);
		
		this.keypadRowTwo = createQWERTYkeypadRowTwo();
		JPanel rowTwoPanel = new JPanel();
		rowTwoPanel.setOpaque(false);
		this.addButtonsToPanel(keypadRowTwo.getAllButtons(), rowTwoPanel);
		
		
		//Add delete button
		JButton changeCaseButton = this.buildCapsLockButton();
		JButton deleteButton = this.buildDeleteButton();
		
		//Create the third row of characters
		this.keypadRowThree = createQWERTYkeypadRowThree();
		JPanel rowThreePanel = new JPanel();
		rowThreePanel.setOpaque(false);
		rowThreePanel.add(changeCaseButton);
		rowThreePanel.add(Box.createRigidArea(new Dimension(8, 0)));
		this.addButtonsToPanel(keypadRowThree.getAllButtons(), rowThreePanel);
		rowThreePanel.add(Box.createRigidArea(new Dimension(8, 0)));
		rowThreePanel.add(deleteButton);
		
		JPanel QWERTYKeypadPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints numberRowConstraints = this.createGridBagConstraints(0, 0, 24);
		QWERTYKeypadPanel.add(numberRowPanel, numberRowConstraints);
		GridBagConstraints rowOneConstraints = this.createGridBagConstraints(0, 1, 24);
		QWERTYKeypadPanel.add(rowOnePanel, rowOneConstraints);
		GridBagConstraints rowTwoConstraints = this.createGridBagConstraints(4, 2, 18);
		QWERTYKeypadPanel.add(rowTwoPanel, rowTwoConstraints);
		GridBagConstraints rowThreeConstraints = this.createGridBagConstraints(0, 3, 24);
		QWERTYKeypadPanel.add(rowThreePanel, rowThreeConstraints);
		
		setCharacterButtonsProperty(numberKeypad.getAllButtons());
		addActionListenerToButtons(numberKeypad.getAllButtons(), keypadButtonListener);
		setCharacterButtonsProperty(keypadRowOne.getAllButtons());
		addActionListenerToButtons(keypadRowOne.getAllButtons(), keypadButtonListener);
		setCharacterButtonsProperty(keypadRowTwo.getAllButtons());
		addActionListenerToButtons(keypadRowTwo.getAllButtons(), keypadButtonListener);
		setCharacterButtonsProperty(keypadRowThree.getAllButtons());
		addActionListenerToButtons(keypadRowThree.getAllButtons(), keypadButtonListener);
		
//		QWERTYKeypadPanel.setBackground(Color.LIGHT_GRAY);
		QWERTYKeypadPanel.setOpaque(false);
		
		return QWERTYKeypadPanel;
	}
	
	public void setQWERTYKeypadButtonProperties()
	{
		this.setCharacterButtonsProperty(this.numberKeypad.getAllButtons());
		this.setCharacterButtonsProperty(this.keypadRowOne.getAllButtons());
		this.setCharacterButtonsProperty(this.keypadRowTwo.getAllButtons());
		this.setCharacterButtonsProperty(this.keypadRowThree.getAllButtons());
	}
	
	public GridBagConstraints createGridBagConstraints(int x, int y, int width)
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = width;
		constraints.weightx = 1;
		return constraints;
	}
	
	public GridBagConstraints createGridBagConstraints(int x, int y, int width, int anchor)
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = width;
		constraints.anchor = anchor;
		return constraints;
	}
	
//	/** Create a character keypad object given the characters as keys. */
//	public Keypad createCharacterKeypad()
//	{
//		String characterKeys[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "_", "-", ".", "@", ".com" };
//		return (new Keypad(characterKeys));
//	}
	
	public void setCharacterButtonsProperty(ArrayList<JButton> buttons)
	{
		for (JButton button : buttons)
		{
			button.setPreferredSize(KioskFrame.CHARACTER_BUTTON_SIZE);
			button.setBackground(KioskFrame.KEYPAD_BUTTON_COLOR);
			button.setFont(new Font(KioskFrame.BUTTON_FONT_FACE, Font.PLAIN, KioskFrame.CHARACTER_BUTTON_FONT_SIZE));
		}
	}
	
	public JPanel createPanelForCharacterKeypad()
	{
		JPanel characterKeypadPanel = new JPanel();
		characterKeypadPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		characterKeypadPanel.setLayout(new GridLayout(0, 6));
		return characterKeypadPanel;
	}
	
	public JPanel addKeypadToPanel(Keypad keypad, JPanel panel)
	{
		addButtonsToPanel(keypad.getAllButtons(), panel);
		return panel;
	}
	
	public JPanel buildFullKeypadPanel(JPanel numberKeypadPanel, JPanel characterKeypadPanel)
	{
		JPanel fullKeypadPanel = new JPanel();
		fullKeypadPanel.add(characterKeypadPanel);
		fullKeypadPanel.add(numberKeypadPanel);
		return fullKeypadPanel;
	}
	
	public JPanel buildKeypadPanelWithCardLayout(JPanel numberKeypadPanel, JPanel fullKeypadPanel)
	{
		JPanel panelCardLayout = new JPanel(new CardLayout());
		panelCardLayout.add(fullKeypadPanel, "fullKeypad");
		panelCardLayout.add(numberKeypadPanel, "numberKeypad");
		
		return panelCardLayout;
	}
	
	public void changeKeypadPanelBasedOnCurrentStep(Step currentStep)
	{
		CardLayout keypadCardLayout = (CardLayout) (this.keypadPanel.getLayout());
		System.out.println("keypad type is " + currentStep.getKeypadType());
		keypadCardLayout.show(keypadPanel, currentStep.getKeypadType());
	}
	
	/** Add JButtons to JPanel 
	 * @param buttons an ArrayList object of JButtons
	 * @param panel a JPanel object
	 * */
	public void addButtonsToPanel(ArrayList<JButton> buttons, JPanel panel)
	{
		for (JButton button : buttons)
		{
			panel.add(button);
		}
	}
	
	public void addActionListenerToButtons(ArrayList<JButton> buttons, ActionListener actionListener)
	{
		for (JButton button : buttons)
		{
			button.addActionListener(actionListener);
		}
	}
	
	// ----------------------------------------
	// implement methods for common buttons
	// ----------------------------------------
	
	public JButton buildBackButton()
	{
		JButton backButton = new JButton("Back");
		backButton.setBackground(KioskFrame.COMMON_BUTTON_COLOR);
		backButton.setFont(KioskFrame.COMMON_BUTTON_FONT);
		backButton.setMinimumSize(KioskFrame.BACK_BUTTON_SIZE);
		backButton.setPreferredSize(KioskFrame.BACK_BUTTON_SIZE);
		backButton.addActionListener(new BackButtonListener());
		return backButton;
	}
	
	// ----------------------------------------
	// implement methods for logout buttons
	// ----------------------------------------
	
	public JButton buildLogoutButton()
	{
		JButton logoutButton = new JButton("logout");
		Icon logoutIcon = new ImageIcon("logout_22.png");
		logoutButton.setIcon(logoutIcon);
		logoutButton.setBackground(KioskFrame.COMMON_BUTTON_COLOR);
		logoutButton.setPreferredSize(KioskFrame.CONFIRM_BUTTON_SIZE);
		logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
		logoutButton.addActionListener(new LogoutButtonListener());
		return logoutButton;
	}
	
	private class LogoutButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.playButtonClickNormalSound();
			JPanel warningPanel = KioskFrame.this.buildLogoutWarningPanel();
			KioskFrame.logoutConfirmDialog = KioskFrame.this.buildDialogWithCustomPanel(warningPanel);
			KioskFrame.logoutConfirmDialog.pack(); 
			KioskFrame.logoutConfirmDialog.setLocationRelativeTo(keypadPanel);
			KioskFrame.logoutConfirmDialog.setVisible(true);
		}
	}
	
	public JPanel buildLogoutWarningPanel()
	{
		JTextPane pane = new JTextPane();
		pane.setEditable(false);
		UIDefaults defaults = new UIDefaults();
		defaults.put("TextPane[Enabled].backgroundPainter", MAIN_BACKGROUND_COLOR);
		pane.putClientProperty("Nimbus.Overrides", defaults);
		pane.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
		pane.setBackground(KioskFrame.MAIN_BACKGROUND_COLOR);
		pane.setContentType("text/html");
		pane.setText(this.setHTMLTextProperties(KioskFrame.LOGOUT_DIALOG_TEXT, KioskFrame.WARNING_FONT_SIZE, KioskFrame.INSTRUCTION_TEXT_FONT_FACE, "<justify>", KioskFrame.INSTRUCTION_FONT_COLOR));
		

		JPanel buttonWrapper = new JPanel();
		buttonWrapper.add(this.buildStayButton());
		buttonWrapper.add(Box.createRigidArea(new Dimension(220, 0)));
		buttonWrapper.add(this.buildOutButton());
		buttonWrapper.setOpaque(false);
		
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.setBackground(KioskFrame.MAIN_BACKGROUND_COLOR);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.add(pane);
		panel.add(buttonWrapper);
		
		return panel;
	}
	
	public JButton buildOutButton()
	{
		JButton button = new JButton("Continue logging out");
		button.setPreferredSize(KioskFrame.LOGOUT_DIALOG_BUTTON_SIZE);
		button.setBackground(KioskFrame.CONFIRM_BUTTON_COLOR);
		button.setFont(KioskFrame.LOGOUT_DIALOG_BUTTON_FONT);
		button.addActionListener(new LogoutOutButtonListener());
		return button;
	}
	
	public JButton buildStayButton()
	{
		JButton button = new JButton("Stay at current step");
		button.setPreferredSize(KioskFrame.LOGOUT_DIALOG_BUTTON_SIZE);
		button.setBackground(KioskFrame.COMMON_BUTTON_COLOR);
		button.setFont(KioskFrame.LOGOUT_DIALOG_BUTTON_FONT);
		button.addActionListener(new LogoutStayButtonListener());
		return button;
	}
	
	private class LogoutOutButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.playButtonClickNormalSound();
			KioskFrame.logoutConfirmDialog.dispose();
			KioskFrame.this.currentStep = Step.WELCOME_SCREEN;
			KioskFrame.this.updateScreenViewBasedOnCurrentStep(currentStep);
			KioskFrame.this.resetCommonButtonContainer();
		}
	}
	
	
	private class LogoutStayButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.playButtonClickNormalSound();
			KioskFrame.logoutConfirmDialog.dispose();
		}
	}
	
	// ----------------------------------------
	// implement methods for confirm buttons
	// ----------------------------------------
	
	public JButton buildConfirmButton()
	{
		JButton confirmButton = new JButton("Confirm");
		confirmButton.setBackground(KioskFrame.CONFIRM_BUTTON_COLOR);
		confirmButton.setPreferredSize(KioskFrame.CONFIRM_BUTTON_SIZE);
		confirmButton.setFont(new Font("Arial", Font.PLAIN, 16));
		confirmButton.addActionListener(new confirmButtonListener());
		return confirmButton;
	}
	
	public void updateConfirmButtonText(String text)
	{
		this.confirmButton.setText(text);
	}
	
	public JButton buildDeleteButton()
	{
		Icon deleteButtonIcon = new ImageIcon(KioskFrame.DELETE_BUTTON_IMAGE_PATH);
		JButton deleteButton = new JButton(deleteButtonIcon);
		
		deleteButton.setBackground(KioskFrame.COMMON_BUTTON_COLOR);
		deleteButton.setPreferredSize(KioskFrame.DELETE_BUTTON_SIZE);
		deleteButton.addMouseListener(new DeleteButtonListener());
		return deleteButton;
	}
	
	public JButton buildCapsLockButton()
	{
		this.capsLockButton = new JButton();
		capsLockButton.setIcon(KioskFrame.CAPSLOCK_OFF_ICON);
		capsLockButton.setBackground(KioskFrame.COMMON_BUTTON_COLOR);
		capsLockButton.setPreferredSize(KioskFrame.CAPS_LOCK_BUTTON_SIZE);
		capsLockButton.addActionListener(new CapsLockButtonActionListener());
		return capsLockButton;
	}
	
	// -----------------------------------------
	// implement methods for caps lock button
	// -----------------------------------------
	
	public void setCapsLockButtonStatus()
	{
		if (KioskFrame.IS_CAPS_LOCK_ON)
		{
			this.setCapsLockOff();
			KioskFrame.IS_CAPS_LOCK_ON = false;
		}
		else
		{
			this.setCapsLockOn();
			KioskFrame.IS_CAPS_LOCK_ON = true;
		}
	}
	
	public void setCapsLockOn()
	{
		for (JButton button : this.keypadRowOne.getAllButtons())
		{
			button.setText(button.getText().toUpperCase());
		}
		for (JButton button : this.keypadRowTwo.getAllButtons())
		{
			button.setText(button.getText().toUpperCase());
		}
		for (JButton button : this.keypadRowThree.getAllButtons())
		{
			button.setText(button.getText().toUpperCase());
		}
		this.capsLockButton.setIcon(KioskFrame.CAPSLOCK_ON_ICON);
	}
	
	public void setCapsLockOff()
	{
		for (JButton button : this.keypadRowOne.getAllButtons())
		{
			button.setText(button.getText().toLowerCase());
		}
		for (JButton button : this.keypadRowTwo.getAllButtons())
		{
			button.setText(button.getText().toLowerCase());
		}
		for (JButton button : this.keypadRowThree.getAllButtons())
		{
			button.setText(button.getText().toLowerCase());
		}
		this.capsLockButton.setIcon(KioskFrame.CAPSLOCK_OFF_ICON);
	}
	
	// --------------------------------------------------
	// implement methods for validating input information
	// --------------------------------------------------
	
	public boolean isStudentNumberValid(String studentNum)
	{
		return this.studentDatabaseManager.isStudentNumberInDatabase(studentNum);
	}
	
	private boolean isPINValid(char[] input) {
	    boolean isCorrect = true;
	    char[] correctPassword = KioskFrame.studentInfo.get(1).toCharArray();

	    if (input.length != correctPassword.length) {
	        isCorrect = false;
	    } else {
	        isCorrect = Arrays.equals (input, correctPassword);
	    }

	    //Zero out the password.
	    Arrays.fill(correctPassword,'0');

	    return isCorrect;
	}
	
	public String convertCharArrayToString(char[] inputCharArray)
	{
		String result = "";
		for (char character : inputCharArray)
		{
			result += character;
		}
		return result;
	}
	
	public boolean hasUnpaidFine()
	{
		boolean result = true;
		if (KioskFrame.studentInfo.get(4).equals("ok"))
		{
			result = false;
		}
		return result;
	}
	
	public void addWarningToInstruction(String warning)
	{
		String currentInstruction = currentStep.getInstruction();
		String warningText = String.format("<br><font size=%s color=%s>%s</font>" , KioskFrame.WARNING_FONT_SIZE, KioskFrame.WARNING_FONT_COLOR, warning);
		String instructionWithWarning = currentInstruction + warningText;
		instructionWithWarning = this.setHTMLTextProperties(instructionWithWarning, KioskFrame.INSTRUCTION_TEXT_FONT_SIZE, KioskFrame.INSTRUCTION_TEXT_FONT_FACE, "<center>", KioskFrame.INSTRUCTION_FONT_COLOR);
		this.instructionDisplay.setText(instructionWithWarning);
	}
	
	public void popUpFinePaymentDialog()
	{
		JPanel panel = KioskFrame.this.buildPayParkingFinePanel();
		panel.setBackground(KioskFrame.MAIN_BACKGROUND_COLOR);
		KioskFrame.this.payParkingFineDialog = KioskFrame.this.buildDialogWithCustomPanel(panel);
		KioskFrame.this.payParkingFineDialog.pack(); 
		KioskFrame.this.payParkingFineDialog.setLocationRelativeTo(keypadPanel);
		KioskFrame.this.payParkingFineDialog.setVisible(true);
	}
	
	public JPanel buildPayParkingFinePanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		this.payParkingFineTextDisplay = new JTextPane();
		this.payParkingFineTextDisplay.setEditable(false);
		UIDefaults defaults = new UIDefaults();
		defaults.put("TextPane[Enabled].backgroundPainter", MAIN_BACKGROUND_COLOR);
		this.payParkingFineTextDisplay.putClientProperty("Nimbus.Overrides", defaults);
		this.payParkingFineTextDisplay.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
		this.payParkingFineTextDisplay.setBackground(KioskFrame.MAIN_BACKGROUND_COLOR);
		this.payParkingFineTextDisplay.setContentType("text/html");
		String text = this.setHTMLTextProperties(KioskFrame.PARKING_FINE_PAYMENT_QUESTION, KioskFrame.WARNING_FONT_SIZE, KioskFrame.INSTRUCTION_TEXT_FONT_FACE, "<justify>", KioskFrame.INSTRUCTION_FONT_COLOR);
		this.payParkingFineTextDisplay.setText(text);
		
		JPanel yesNoButtonContainer = this.buildParkingFinePaymentYesNoButtonPanel();
		
		panel.add(this.payParkingFineTextDisplay);
		panel.add(yesNoButtonContainer);
		return panel;
	}
	
	public JPanel buildParkingFinePaymentYesNoButtonPanel()
	{
		JButton yesButton = new JButton("Yes");
		yesButton.setPreferredSize(KioskFrame.PARKING_FINE_BUTTON_SIZE);
		yesButton.setFont(KioskFrame.PARKING_FINE_BUTTON_FONT);
		yesButton.setBackground(KioskFrame.CONFIRM_BUTTON_COLOR);
		yesButton.addActionListener(new YesButtonActionListener());
		
		JButton noButton = new JButton("No");
		noButton.setPreferredSize(KioskFrame.PARKING_FINE_BUTTON_SIZE);
		noButton.setFont(KioskFrame.PARKING_FINE_BUTTON_FONT);
		noButton.setBackground(KioskFrame.KEYPAD_BUTTON_COLOR);
		noButton.addActionListener(new NoButtonActionListener());
		
		JPanel yesNoButtonContainer = new JPanel(new FlowLayout());
		yesNoButtonContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));
		yesNoButtonContainer.setBackground(KioskFrame.MAIN_BACKGROUND_COLOR);
		
		yesNoButtonContainer.add(noButton);
		yesNoButtonContainer.add(Box.createRigidArea(new Dimension(KioskFrame.PARKING_FINE_BUTTON_SIZE.width * 4, 0)));
		yesNoButtonContainer.add(yesButton);
		return yesNoButtonContainer;
	}
	
	private class YesButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.playButtonClickNormalSound();
			//Change database field to "ok"
			KioskFrame.this.payParkingFineDialog.dispose();
			KioskFrame.this.goToNextStepFromCurrentStep();
			String instructionText = "<font size=6 color=\"#aaaaaa\">" + KioskFrame.PARKING_FINE_PAYMENT_ACCEPT_TEXT + "</font><br>" + KioskFrame.this.currentStep.getInstruction();
			instructionText = KioskFrame.this.setHTMLTextProperties(instructionText, KioskFrame.INSTRUCTION_TEXT_FONT_SIZE, KioskFrame.INSTRUCTION_TEXT_FONT_FACE, "<center>", KioskFrame.INSTRUCTION_FONT_COLOR);
			KioskFrame.this.instructionDisplay.setText(instructionText);
		}
	}
	
	private class NoButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			KioskFrame.this.resetIdleCountDownTimer();
			KioskFrame.this.playButtonClickNormalSound();
			// TODO Auto-generated method stub
			KioskFrame.this.payParkingFineDialog.dispose();
			currentStep = Step.WELCOME_SCREEN;
			KioskFrame.this.updateScreenViewBasedOnCurrentStep(currentStep);
			KioskFrame.this.setCounterValue(0);
			KioskFrame.this.resetInputField();
			KioskFrame.this.resetPINInputField();
		}
	}
	
	/** Play the audio clip when regular button is clicked. */
	public void playButtonClickNormalSound()
	{
		try
		{
			String soundName = "regular_button_click_sound.wav";    
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.setFramePosition(0);
			clip.start();
		}
		catch (Exception e)
		{
			
		}
	}
}

//-----------------------------------
// implements methods for decorating
//-----------------------------------

class BackgroundLayerMask extends LayerUI<JComponent>
{
	int x1;
	int x2;
	int y1;
	int y2;

	private static final long serialVersionUID = 1L;
	@Override 
	public void paint(Graphics g, JComponent c)
	{
		super.paint(g, c);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));

		g2.drawLine(this.x1, this.y1, this.x2, this.y2);
		g2.dispose();
	}
	
	public void setCoordinate(double x1, double y1, double x2, double y2)
	{
		this.x1 = (int) x1;
		this.x2 = (int) x2;
		this.y1 = (int) y1;
		this.y2 = (int)	y2;
	}
}




// -------------------------------
// Define the Keypad class
// -------------------------------

/** A keypad that has a collection of buttons. The number and label of the buttons are defined by the implementer. */
class Keypad
{
	private ArrayList<JButton> allButtons; //A collection that stores all the buttons of this keypad
	
	public Keypad(String[] keys)
	{
		allButtons = new ArrayList<JButton>();
		this.setAllKeys(keys);
	}
	
	private void setAllKeys(String[] keys)
	{
		for (String key : keys)
		{
			allButtons.add(new JButton(key));
		}
	}
	
	/** Return a collection of all the buttons in this keypad object. */
	public ArrayList<JButton> getAllButtons()
	{
		return this.allButtons;
	}
	
	public void disableAllButtons()
	{
		for (JButton button : this.getAllButtons())
		{
			button.setEnabled(false);
		}
	}
	
	public void enableAllButtons()
	{
		for (JButton button : this.getAllButtons())
		{
			button.setEnabled(true);
		}
	}
}

// -------------------------------------------
// implement methods for working with database
// -------------------------------------------

class DatabaseManager
{
	String fileName;
	
	public DatabaseManager(String fileName)
	{
		this.setDatabaseFileName(fileName);
	}
	
	public void setDatabaseFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public ArrayList<String> getAllLinesFromDatabase() throws IOException
	{
		try {
			BufferedReader in = new BufferedReader(new FileReader(this.fileName));
			String line;
			ArrayList<String> lines = new ArrayList<String>();
			while((line = in.readLine()) != null)
			{
				lines.add(line);
			    System.out.println(line);
			}
			in.close();
			return lines;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}
	
	/** Return the number of lines in the database. */
	public int getNumberOfLinesFromFile() throws IOException
	{
		try {
			BufferedReader in = new BufferedReader(new FileReader(this.fileName));
			int numOfLines = 0;
			while((in.readLine()) != null)
			{
			    numOfLines++;
			}
			in.close();
			return numOfLines;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}
}

class InsuranceCompanyDatabaseManager extends DatabaseManager
{
	public String fileName;
	
	public InsuranceCompanyDatabaseManager(String fileName) {
		super(fileName);
	}
	
	/** Return a collection of all the companies' names in the database. */
	public ArrayList<String> getAllCompanyNames() throws IOException
	{
		ArrayList<String> names = super.getAllLinesFromDatabase();
		return names;
	}
}

class StudentDatabaseManager extends DatabaseManager
{

	public StudentDatabaseManager(String fileName) {
		super(fileName);
	}
	
	/** Return whether the input student number is in the database. */
	public boolean isStudentNumberInDatabase(String studentNum)
	{
		boolean result = false;
		try {
			BufferedReader in = new BufferedReader(new FileReader(this.fileName));
			String line;
			while(((line = in.readLine())) != null)
			{
			    String[] lineElements = line.split(",");
			    if (studentNum.equals(lineElements[0].trim()))
			    {
			    	result = true;
			    }
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/** Return a collection of the student's information based on the input student number. */
	public ArrayList<String> getStudentInfoBasedOnStudentNumber(String studentNum)
	{
		String[] studentInfo = {};
		ArrayList<String> studentInfoList = new ArrayList<String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(this.fileName));
			String line;
			while(((line = in.readLine())) != null)
			{
			    String[] lineElements = line.split(",");
			    if (studentNum.equals(lineElements[0].trim()))
			    {
			    	studentInfo = lineElements;
			    }
			}
			in.close();
		
		for (int i = 0; i < studentInfo.length; i ++)
		{
			studentInfoList.add(studentInfo[i].trim());
		}
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return studentInfoList;
	}
}

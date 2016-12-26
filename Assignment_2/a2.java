import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.FocusManager;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ButtonUI;
import javax.swing.undo.AbstractUndoableEdit;




//import ApplicationFrame.DrawMode;

public class a2 {
	
	private static final String LOOK_AND_FEEL_LINUX = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
	
	public static void main(String[] args)
	{
		a2.setLookAndFeel();
		ApplicationFrame paintFrame = new ApplicationFrame();
		paintFrame.setBackground(null);
		paintFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		paintFrame.setTitle("Drawmator");
		paintFrame.setSize(new Dimension(1200, 750));
		//menu.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		// put the frame in the middle of the display
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		paintFrame.setLocation(dim.width / 2 - paintFrame.getSize().width / 2, dim.height / 2 - paintFrame.getSize().height / 2);
		paintFrame.setVisible(true);
		paintFrame.menuBar.requestFocus();
	}
	
	private static void setLookAndFeel()
	{
		try {
			UIManager.setLookAndFeel(a2.LOOK_AND_FEEL_LINUX);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}

/** The PaintFrame is the main container for all the components of this program. */
class ApplicationFrame extends JFrame implements WindowListener
{

	private static final long serialVersionUID = 1L;
	final Toolkit toolkit = Toolkit.getDefaultToolkit();
	final Dimension screenSize = toolkit.getScreenSize();
	
	public enum DrawMode
	{
		RECTANGLE,
		ELLIPSE,
		LINE,
		FREE_DRAW,
		ROUND_RECTANGLE,
		BUCKET_FILL,
		INPUT_TEXT,
		EYE_DROPPER,
		ERASER,
		POLYGON,
		ZOOM_IN,
		ZOOM_OUT;
	}
	
	//Image paths
	private static final String FREE_DRAW_ICON_PATH = "freeDraw.png";
	private static final String RECTANGLE_ICON_PATH = "rectangle.png";
	private static final String ELLIPSE_ICON_PATH = "circle.png";
	private static final String ROUNDRECTANGLE_ICON_PATH = "roundRec.png";
	private static final String POLYGON_ICON_PATH = "poly.png";
	private static final String PAINT_BUCKET_ICON_PATH = "bucket.png";
	private static final String EYE_DROPPER_ICON_PATH = "eyedropper.png";
	private static final String ZOOM_IN_ICON_PATH = "zoom-in.png";
	private static final String ZOOM_OUT_ICON_PATH = "zoom-out.png";
	private static final String TEXT_ICON_PATH = "textIcon.png";
	private static final String LINE_ICON_PATH = "line.png";
	private static final String ERASER_ICON_PATH = "eraser.png";
	
	private static final String FREE_DRAW_ICON_BLACK_PATH = "freeDraw_black.png";
	private static final String RECTANGLE_ICON_BLACK_PATH = "rectangle_black.png";
	private static final String ELLIPSE_ICON_BLACK_PATH = "circle_black.png";
	private static final String ROUNDRECTANGLE_ICON_BLACK_PATH = "roundRec_black.png";
	private static final String POLYGON_ICON_BLACK_PATH = "poly_black.png";
	private static final String PAINT_BUCKET_ICON_BLACK_PATH = "bucket_black.png";
	private static final String EYE_DROPPER_ICON_BLACK_PATH = "eyedropper_black.png";
	private static final String ZOOM_IN_ICON_BLACK_PATH = "zoom-in_black.png";
	private static final String ZOOM_OUT_ICON_BLACK_PATH = "zoom-out_black.png";
	private static final String TEXT_ICON_BLACK_PATH = "textIcon_black.png";
	private static final String LINE_ICON_BLACK_PATH = "line_black.png";
	private static final String ERASER_ICON_BLACK_PATH = "eraser_black.png";
	private static final String FREE_DRAW_AND_ERASER_BLACK_PATH = "draw&erase.png";
	
	//Image Icon
	private static ImageIcon freeDrawIcon = new ImageIcon(FREE_DRAW_ICON_PATH);
	private static ImageIcon freeDrawIconBlack = new ImageIcon(FREE_DRAW_ICON_BLACK_PATH);
	private static ImageIcon recIcon = new ImageIcon(RECTANGLE_ICON_PATH);
	private static ImageIcon recIconBlack = new ImageIcon(RECTANGLE_ICON_BLACK_PATH);
	private static ImageIcon ellipseIcon = new ImageIcon(ELLIPSE_ICON_PATH);
	private static ImageIcon ellipseIconBlack = new ImageIcon(ELLIPSE_ICON_BLACK_PATH);
	private static ImageIcon roundRecIcon = new ImageIcon(ROUNDRECTANGLE_ICON_PATH);
	private static ImageIcon roundRecIconBlack = new ImageIcon(ROUNDRECTANGLE_ICON_BLACK_PATH);
	private static ImageIcon eyeDropperIcon = new ImageIcon(EYE_DROPPER_ICON_PATH);
	private static ImageIcon eyeDropperIconBlack = new ImageIcon(EYE_DROPPER_ICON_BLACK_PATH);
	private static ImageIcon zoomInIcon = new ImageIcon(ZOOM_IN_ICON_PATH);
	private static ImageIcon zoomInIconBlack = new ImageIcon(ZOOM_IN_ICON_BLACK_PATH);
	private static ImageIcon zoomOutIcon = new ImageIcon(ZOOM_OUT_ICON_PATH);
	private static ImageIcon zoomOutIconBlack = new ImageIcon(ZOOM_OUT_ICON_BLACK_PATH);
	private static ImageIcon lineIcon = new ImageIcon(LINE_ICON_PATH);
	private static ImageIcon lineIconBlack = new ImageIcon(LINE_ICON_BLACK_PATH);
	private static ImageIcon eraserIcon = new ImageIcon(ERASER_ICON_PATH);
	private static ImageIcon eraserIconBlack = new ImageIcon(ERASER_ICON_BLACK_PATH);
	private static ImageIcon polyIcon = new ImageIcon(POLYGON_ICON_PATH);
	private static ImageIcon polyIconBlack = new ImageIcon(POLYGON_ICON_BLACK_PATH);
	private static ImageIcon paintBucketIcon = new ImageIcon(PAINT_BUCKET_ICON_PATH);
	private static ImageIcon paintBucketIconBlack = new ImageIcon(PAINT_BUCKET_ICON_BLACK_PATH);
	private static ImageIcon textIcon = new ImageIcon(TEXT_ICON_PATH);
	private static ImageIcon textIconBlack = new ImageIcon(TEXT_ICON_BLACK_PATH);
	private static ImageIcon eraserAndFreeDrawCursorBlack = new ImageIcon(FREE_DRAW_AND_ERASER_BLACK_PATH);
	
	
	//Components
	private JPanel mainPanel;
	public MenuBar menuBar;
	private CustomToolBar toolBar;
	private InformationBar infoBar;
	private JDialog newFileDialog;
	private JPanel paintPanelWrapper;
	private JScrollPane paintPanelScrollPane;
	private JButton colorChooserBtn;
	public JMenuItem undo;
	
	
	//Colors 
	private static final Color BUTTON_SELECTED_COLOR = new Color(83, 88, 95);
	private static final Color MAIN_BACKGROUND_COLOR = new Color(64, 64, 64);
	private static final Color BAR_COLOR = new Color(96, 96, 96);
	
	//Fonts
	private static final Font FONT_OF_SYSTEM = new Font("Calibri", Font.PLAIN, 16);
	
	//Space between two JMenus
	private static final String spaceBetweenMenus = " ";
	
	//PaintPanel status
	public static JMenuItem currentlyPressedMenuItem = null;
	public static boolean changeSaved = true;
	private static final int MAX_PAINT_PANEL_WIDTH = 1400;
	private static final int MAX_PAINT_PANEL_HEIGHT = 750;
	private DrawMode drawMode = DrawMode.FREE_DRAW;
	private static int paintPanelWidth = 500;
	private static int paintPanelHeight = 500;
	private int originalPaintWidth;
	private int originalPaintHeight;
	private static int offsetX = 0;
	private static int offsetY = 0;
	private static final int OFFSET_INCREMENT = 20;
	private static final String DEFAULT_TITLE = "Untitled-";
	private static int numOfUntitledPaint = 1;
	private static PaintPanel currentPaintPanel;
	public static Stroke inkStroke; 
	public static boolean isFilled = false;
	public static boolean isSizeFixed = false;
	public static final int DEFAULT_ZOOM_RATIO = 100;
	public static int currentZoomRatio = 100;
	public static boolean dashedLine = false;
	public static int lineWidth = 1;
	
	
	//FreeDraw mode
	private static final int DEFAULT_BRUSH_WIDTH = 5;
	private static int currentBrushWidth = DEFAULT_BRUSH_WIDTH;
	
	//Draw shape mode
	public static int shapeWidth = 100;
	public static int shapeHeight = 100;
	public static int currentArcWidth = 10;
	public static int currentArcHeight = 10;
	public static int polygonSides = 5;
	
	//Color Chooser
	private static final Color DEFAULT_PAINT_COLOR = Color.RED;
	private static Color chosenPaintColor = DEFAULT_PAINT_COLOR;
	private static Color COLOR_CHOOSER_BORDER_COLOR = new Color(166, 170, 168);
	
	private ArrayList<PaintPanel> paintPanels = new ArrayList<PaintPanel>();
	private HashMap<JDialog, PaintPanel> paintPanelDialogMap = new HashMap<JDialog, PaintPanel>();
	private ArrayList<Image> redoList = new ArrayList<Image>();
	
	//Text on the image
	private static int currentFontSize;
	private static String currentFontFamily;
	private static int currentFontStyle;
	private static Font currentFont;
	//A List used to store user created text 
	private static ArrayList<PaintPanel.OverlayEditor> insertedTextList = new ArrayList<PaintPanel.OverlayEditor>();
	
	public ApplicationFrame()
	{
		// set color of main frame
		this.setBackground(MAIN_BACKGROUND_COLOR);
		//instantiate and set the menu bar
		this.buildMenuBar();
		//instantiate and set the tool bar
		this.buildToolBar();
		
		this.buildInformationBar();
		
		this.buildPaintPanelScrollPane();
		
		this.buildMainPanel();
		
		this.setContentPane(mainPanel);
		this.setMinimumSize(new Dimension(1000, 700));
		this.setResizable(true);
		this.addWindowListener(this);
	}
	
	/*********************************************************************************************************************
	 * Build application components                                                                                              *
	 *********************************************************************************************************************/

	public void buildMainPanel()
	{
		this.mainPanel = new JPanel(new BorderLayout());
		this.mainPanel.add(this.infoBar, BorderLayout.PAGE_START);
		this.mainPanel.add(this.toolBar, BorderLayout.LINE_START);
		this.mainPanel.add(paintPanelScrollPane, BorderLayout.CENTER);
		
		this.mainPanel.setBackground(MAIN_BACKGROUND_COLOR);
	}
	
	public void buildPaintPanelScrollPane()
	{
		paintPanelScrollPane = new JScrollPane();
		paintPanelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		paintPanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		paintPanelScrollPane.getViewport().setOpaque(false);
		paintPanelScrollPane.setBorder(BorderFactory.createEmptyBorder());
	}
	
	/** Create the menu bar and add it to the JFrame. */
	public void buildMenuBar()
	{
		UIManager.put("Menu.font", FONT_OF_SYSTEM);
		this.menuBar = new MenuBar();
		this.setJMenuBar(this.menuBar);
	//	this.setBackground(MAIN_BACKGROUND_COLOR);
	}

	/** Create the tool bar, add ActionListener to its buttons, and add the tool bar to the JFrame. */
	public void buildToolBar()
	{
		this.toolBar = new CustomToolBar();
		this.toolBar.setDefaultToggledButton(this.toolBar.freeDrawBtn);
		this.toolBar.setBackground(BAR_COLOR);
	}
	
	public void buildInformationBar()
	{
		this.infoBar = new InformationBar();
	}
	
	public void createNewFilePanelDialog()
	{
		JPanel newFilePanel = new NewFilePanel();
		//Create the internal JFrame that holds the paint panel
		this.newFileDialog = ApplicationFrame.this.buildDialogWithCustomPanel(newFilePanel, true);
		newFileDialog.pack(); 
		this.centerJDialogInTheScreen(newFileDialog);
		newFileDialog.setVisible(true);
	}
	
	/** Create a paint panel where the user can paint. */
	public void createNewPaintPanel(String title, int width, int height)
	{
		//Create the paint panel
		PaintPanel paintPanel = new PaintPanel(width, height);
		this.originalPaintWidth = width;
		this.originalPaintHeight = height;
		paintPanel.addMouseListener(new PaintPanelMouseListetner());
		paintPanel.setShapeToDraw(drawMode);
		paintPanel.setName(title);
		paintPanel.getStrokeInfo(ApplicationFrame.currentBrushWidth);
		paintPanels.add(paintPanel);
		ApplicationFrame.currentPaintPanel = paintPanel;
		this.addPaintPanelToScrollPane(paintPanel);
		ApplicationFrame.currentZoomRatio = ApplicationFrame.DEFAULT_ZOOM_RATIO;
		this.infoBar.zoomTextField.setText(ApplicationFrame.currentZoomRatio + "");
		this.menuBar.zoomIn.setEnabled(true);
		this.menuBar.zoomOut.setEnabled(true);
		this.menuBar.clearAll.setEnabled(true);
//		this.addPaintPanelInScrollPaneToDialog(title, paintPanelContainer, paintPanel);
//		this.addPaintPanelScrollPaneToMainPanel(title, paintPanelContainer, paintPanel);
	}
	
	/** Add the paint panel to a scroll pane. */
	public void addPaintPanelToScrollPane(PaintPanel paintPanel)
	{
		JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT));
		container.add(paintPanel);
		container.setOpaque(true);
		container.setBackground(MAIN_BACKGROUND_COLOR);
		this.paintPanelScrollPane.setViewportView(container);
	}
	
	public void addPaintPanelScrollPaneToMainPanel(String title, JScrollPane paintPanelContainer, PaintPanel paintPanel)
	{
		this.mainPanel.add(paintPanelContainer, BorderLayout.CENTER);
	}
	
	public void addPaintPanelInScrollPaneToDialog(String title, JScrollPane paintPanelContainer, PaintPanel paintPanel)
	{
		JDialog paintDialog = ApplicationFrame.this.buildDialogWithJScrollPane(paintPanelContainer, false);
		paintDialog.setTitle(title);
		this.paintPanelDialogMap.put(paintDialog, paintPanel);
		paintDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		paintDialog.setAlwaysOnTop(true);
		paintDialog.pack(); 
		this.setPaintPanelDialogLocation(paintPanel, paintDialog);
		paintDialog.setVisible(true);
	}
	
	/*********************************************************************************************************************
	 * Utilities                                                                                            *
	 *********************************************************************************************************************/
	
	/** Create a dialog with the custom panel.
	 * @param panel a JPanel object to be displayed in the dialog
	 * @return a dialog that displays the passed panel
	 *  */
	public JDialog buildDialogWithCustomPanel(JPanel panel, boolean modal)
	{
		JDialog dialog = new JDialog(new JFrame(), "", modal);
		dialog.setContentPane(panel);
		return dialog;
	}
	
	public int calculateZoomRatio()
	{
		Double originalSize = (double)(this.originalPaintWidth * this.originalPaintHeight);
		Double newSize = (double) (ApplicationFrame.currentPaintPanel.paintWidth * ApplicationFrame.currentPaintPanel.paintHeight);
		int ratio = (int) (100 * newSize / originalSize);
		return ratio;
	}
	
	public JDialog buildDialogWithJScrollPane(JScrollPane panel, boolean modal)
	{
		final JDialog dialog = new JDialog(new JFrame(), "", null);
		dialog.setContentPane(panel);
		dialog.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				//TODO If changes have been done to the paint panel and it is not saved. 
				//Ask the users if they want to save the painting.
			}
			
			@Override 
			public void windowClosed(WindowEvent e)
			{
				System.out.println("dialog closed");
				//TODO Remove paint panel dialog and the paint panel from the HashMap and ArrayList if not done so.
			}
		});
		dialog.addWindowFocusListener(new WindowAdapter(){
			@Override 
			public void windowGainedFocus(WindowEvent e)
			{
				ApplicationFrame.currentPaintPanel = paintPanelDialogMap.get(dialog);
				System.out.println("current paint panel is " + ApplicationFrame.currentPaintPanel.getName());
			}
		});
		
		return dialog;
	}
	
	public void centerJDialogInTheScreen(JDialog dialog)
	{
		final int x = (screenSize.width - dialog.getWidth()) / 2;
		final int y = (screenSize.height - dialog.getHeight()) / 2;
		dialog.setLocation(x, y);
	}
	
	public void setPaintPanelDialogLocation(PaintPanel paintPanel, JDialog dialog)
	{
		int index = this.paintPanels.indexOf(paintPanel); //Check which paint panel it is.
		int xOffSet = ApplicationFrame.offsetX + ApplicationFrame.OFFSET_INCREMENT * index;
		int yOffSet = ApplicationFrame.offsetY + ApplicationFrame.OFFSET_INCREMENT * index;
		final int x = (screenSize.width - dialog.getWidth()) / 2 + xOffSet;
		final int y = (screenSize.height - dialog.getHeight()) / 2 + yOffSet;
		dialog.setLocation(x, y);
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
	
	public  JComponent createVerticalSeparator(int width, int height) 
	{
	        JSeparator x = new JSeparator(SwingConstants.VERTICAL);
	        x.setPreferredSize(new Dimension(width,height));
	        return x;
	    }
	  
	public  JComponent createHorizontalSeparator(int width, int height) 
	{
	        JSeparator x = new JSeparator(SwingConstants.HORIZONTAL);
	        x.setPreferredSize(new Dimension(width,height));
	        return x;
	}
	
	class PaintPanelMouseListetner extends MouseAdapter
	{
		@Override
		// If I just set a cursor for the status in the first if statement, the rest status s.t. bucket will be CROSSHAIR_CURSOR too.
		// But If I set a cursor for bucket, it will be the result I expected.
		public void mouseEntered(MouseEvent me) 
		{
			if (ApplicationFrame.this.drawMode == DrawMode.RECTANGLE || ApplicationFrame.this.drawMode == DrawMode.ELLIPSE 
					|| ApplicationFrame.this.drawMode == DrawMode.LINE || ApplicationFrame.this.drawMode == DrawMode.ROUND_RECTANGLE
				)
			{
				ApplicationFrame.currentPaintPanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			} 
			else if (ApplicationFrame.this.drawMode == DrawMode.BUCKET_FILL)
			{
				Image bucketCursorImage = ApplicationFrame.paintBucketIconBlack.getImage();
				int x = bucketCursorImage.getWidth(ApplicationFrame.currentPaintPanel) / 2 + 1;
				int y = bucketCursorImage.getHeight(ApplicationFrame.currentPaintPanel) / 2 + 1;
				Point center = new Point(x, y);
				Toolkit t = Toolkit.getDefaultToolkit();
				Cursor bucketCursor = t.createCustomCursor(bucketCursorImage, center, "bucket fill");
				ApplicationFrame.currentPaintPanel.setCursor(bucketCursor);	
			} 
			else if (ApplicationFrame.this.drawMode == DrawMode.FREE_DRAW)
			{
				Image freeDrawCursorImage = ApplicationFrame.eraserAndFreeDrawCursorBlack.getImage();
				int x = freeDrawCursorImage.getWidth(ApplicationFrame.currentPaintPanel) / 2 + 1;
				int y = freeDrawCursorImage.getHeight(ApplicationFrame.currentPaintPanel) / 2 + 1;
				Point center = new Point(x, y);
				Toolkit t = Toolkit.getDefaultToolkit();
				Cursor freeDrawCursor = t.createCustomCursor(freeDrawCursorImage, center, "free draw");
				ApplicationFrame.currentPaintPanel.setCursor(freeDrawCursor);
			}
			else if (ApplicationFrame.this.drawMode == DrawMode.ZOOM_IN) 
			{
				Image zoomInCursorImage = ApplicationFrame.zoomInIconBlack.getImage();
				int x = zoomInCursorImage.getWidth(ApplicationFrame.currentPaintPanel) / 2 + 1;
				int y = zoomInCursorImage.getHeight(ApplicationFrame.currentPaintPanel) / 2 + 1;
				Point center = new Point(x, y);
				Toolkit t = Toolkit.getDefaultToolkit();
				Cursor zoomInCursor = t.createCustomCursor(zoomInCursorImage, center, "zoom in");
				ApplicationFrame.currentPaintPanel.setCursor(zoomInCursor);
			}
			else if (ApplicationFrame.this.drawMode == DrawMode.ZOOM_OUT) 
			{
				Image zoomOutCursorImage = ApplicationFrame.zoomOutIconBlack.getImage();
				int x = zoomOutCursorImage.getWidth(ApplicationFrame.currentPaintPanel) / 2 + 1;
				int y = zoomOutCursorImage.getHeight(ApplicationFrame.currentPaintPanel) / 2 + 1;
				Point center = new Point(x, y);
				Toolkit t = Toolkit.getDefaultToolkit();
				Cursor zoomOutCursor = t.createCustomCursor(zoomOutCursorImage, center, "zoom out");
				ApplicationFrame.currentPaintPanel.setCursor(zoomOutCursor);
			}
			else if (ApplicationFrame.this.drawMode == DrawMode.ERASER) 
			{
				Image eraserCursorImage = ApplicationFrame.eraserAndFreeDrawCursorBlack.getImage();
				int x = eraserCursorImage.getWidth(ApplicationFrame.currentPaintPanel) / 2 + 1;
				int y = eraserCursorImage.getHeight(ApplicationFrame.currentPaintPanel) / 2 + 1;
				Point center = new Point(x, y);
				Toolkit t = Toolkit.getDefaultToolkit();
				Cursor eraserCursor = t.createCustomCursor(eraserCursorImage, center, "eraser");
				ApplicationFrame.currentPaintPanel.setCursor(eraserCursor);
			}
			else if (ApplicationFrame.this.drawMode == DrawMode.EYE_DROPPER) 
			{
				Image eyeDropperCursorImage = ApplicationFrame.eyeDropperIconBlack.getImage();
				int x = 10;
				int y = eyeDropperCursorImage.getHeight(ApplicationFrame.currentPaintPanel) / 2 + 6;
				Point center = new Point(x, y);
				Toolkit t = Toolkit.getDefaultToolkit();
				Cursor eyeDropperCursor = t.createCustomCursor(eyeDropperCursorImage, center, "eye dropper");
				ApplicationFrame.currentPaintPanel.setCursor(eyeDropperCursor);
			}
			else if (ApplicationFrame.this.drawMode == DrawMode.INPUT_TEXT) 
			{
				ApplicationFrame.currentPaintPanel.setCursor(new Cursor(Cursor.TEXT_CURSOR));
				
			}
			else 
			{
				ApplicationFrame.currentPaintPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
		
	}
	
	public void buildHelpDialog()
	{
		JDialog dialog = this.buildDialogWithCustomPanel(new HelpListPanel(), false);
		dialog.pack(); 
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}
	
	/*********************************************************************************************************************
	 * Help panel inner class                                                                                              *
	 *********************************************************************************************************************/
	
	public class HelpListPanel extends JPanel
	{
		  String label[] = { "Free Draw Tool", "Eraser Tool", "Rectangle Tool", "Round Rectangle Tool", "Polygon Tool", "Ellipse Tool", "Line Tool",
		      "Text Tool", "Eyedropper Tool", "Bucket Fill Tool", "Zoom In Tool", "Zoom Out Tool" };

		  Map<String, String> map = new HashMap<String, String>();
		  JTextArea textArea;
		  
		  JList list;

		  public HelpListPanel() {
			this.setBackground(ApplicationFrame.BAR_COLOR);
			this.setUpMap();
		    this.setLayout(new FlowLayout(FlowLayout.LEFT));
		    JPanel listWrapper = new JPanel(new BorderLayout());
		    list = new JList(label);
		    JScrollPane pane = new JScrollPane(list);
		    pane.setPreferredSize(new Dimension(200, 290));

		    DefaultListSelectionModel m = new DefaultListSelectionModel();
		    m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		    m.setLeadAnchorNotificationEnabled(false);
		    list.setSelectionModel(m);
		    list.setPreferredSize(new Dimension(180, 295));
		    
		    this.textArea = new JTextArea();
		    this.textArea.setWrapStyleWord(true);
		    this.textArea.setLineWrap(true);
		    this.textArea.setFont(new Font("Calibri", Font.PLAIN, 14));
		    textArea.setPreferredSize(new Dimension(200, 300));
//		    listWrapper.add(pane, BorderLayout.NORTH);
		    this.add(list);
		    this.add(textArea);

		    list.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent e) {
		        textArea.setText(map.get(list.getSelectedValue()));
		      }
		    });

		    
		  }
		  
		  public void setUpMap()
		  {
			  map.put("Free Draw Tool", "You can use free draw tool to draw any thing you want. You can set the thickness of the brush using the text input field or the slider bar.");
			  map.put("Eraser Tool", "You can use eraser tool to eraser any thing you want. You can set the thickness of the eraser using the text input field or the slider bar.");
			  map.put("Rectangle Tool", "You can use rectangle tool to draw rectangles.");
			  map.put("Round Rectangle Tool", "You can use round rectangle tool to draw rounded rectangle.");
			  map.put("Polygon Tool", "You can use polygon tool to draw polygons with the specified number of sides ranging from 3 to 100.");
			  map.put("Ellipse Tool", "You can use ellipse tool to draw ellipse.");
			  map.put("Line Tool", "You can use line tool to draw lines. You can set the thickness of the line. You can choose to draw dashed line.");
			  map.put("Text Tool", "You can use text tool to insert text anywhere you want. You can modify the content of the inserted text. You can select the font family, style, and size.");
			  map.put("Eyedropper Tool", "You can use eyedropper tool to pick a color from the paint panel.");
			  map.put("Bucket Fill Tool", "You can use bucket fill tool to fill a bounded area with the same colour.");
			  map.put("Zoom In Tool", "You can use zoom in tool to enlarge the whole image. Zoom in action will not be recorded in the undo/redo list.");
			  map.put("Zoom Out Tool", "You can use zoom out tool to shrink the whole image. Zoom out action will not be recorded in the undo/redo list.");
		  }
	}
	
	/*********************************************************************************************************************
	 * New file panel inner class                                                                                              *
	 *********************************************************************************************************************/
	
	/** NewFilePanel class implements the UI of the panel for creating new files. */
	class NewFilePanel extends JPanel implements ActionListener, KeyListener, DocumentListener
	{
		private static final long serialVersionUID = 1L;
		
		JButton okButton;
		JButton cancelButton;
		JPanel newFilePanel;
		JTextField nameField;
		JTextField widthField;
		JTextField heightField;
		
		public NewFilePanel()
		{
			JPanel nameInput = this.buildNameField();
			JPanel widthInput = this.buildWidthField();
			JPanel heightInput = this.buildHeightField();
			
			JPanel inputFieldWrapper = new JPanel(new GridBagLayout());
			inputFieldWrapper.add(nameInput, createGridBagConstraints(0, 0, 10));
			inputFieldWrapper.add(widthInput, createGridBagConstraints(0, 1, 10));
			inputFieldWrapper.add(heightInput, createGridBagConstraints(0, 2, 10));
			
			this.buildButtons();
			JPanel buttonWrapper = new JPanel();
			buttonWrapper.add(this.cancelButton);
			buttonWrapper.add(this.okButton);
			
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(inputFieldWrapper);
			this.add(buttonWrapper);
			
			nameField.addKeyListener(this);
			widthField.addKeyListener(this);
			heightField.addKeyListener(this);
		}

		public JPanel buildNameField()
		{
			JLabel nameLabel = new JLabel("Name: ");
			
			this.nameField = new JTextField(20);
			//Set the title based on number of untitled paints;
			String title = ApplicationFrame.DEFAULT_TITLE + ApplicationFrame.numOfUntitledPaint;
			this.nameField.setText(title);
			
			JPanel panel = new JPanel(); 
			panel.add(nameLabel);
			panel.add(this.nameField);
			return panel;
		}
		
		public JPanel buildWidthField()
		{
			JLabel widthLabel = new JLabel("Width: ");
			this.widthField = new JTextField(6);
			this.widthField.setText("" + ApplicationFrame.paintPanelWidth);
			JLabel unitLabel = new JLabel("Pixels");
			
			JPanel panel = new JPanel(); 
			panel.add(widthLabel);
			panel.add(this.widthField);
			panel.add(unitLabel);
			return panel;
		}
		
		public JPanel buildHeightField()
		{
			JLabel heightLabel = new JLabel("Height: ");
			this.heightField = new JTextField(6);
			this.heightField.setText("" + ApplicationFrame.paintPanelHeight);
			JLabel unitLabel = new JLabel("Pixels");
			
			JPanel panel = new JPanel(); 
			panel.add(heightLabel);
			panel.add(this.heightField);
			panel.add(unitLabel);
			return panel;
		}
		
		/** Create all the required buttons. */
		public void buildButtons()
		{
			this.okButton = new JButton("OK");
			this.okButton.addActionListener(this);
			this.cancelButton = new JButton("Cancel");
			this.cancelButton.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == this.okButton)
			{
				try
				{
					int width = Integer.parseInt(this.widthField.getText());
					int height = Integer.parseInt(this.heightField.getText());
					
				
					String title = this.nameField.getText();
		
					createNewPaintPanel(title, width, height);
					
					ApplicationFrame.this.menuBar.saveAsFile.setEnabled(true);
					
					ApplicationFrame.paintPanelWidth = width;
					ApplicationFrame.paintPanelHeight = height;
					ApplicationFrame.this.newFileDialog.dispose();
				}
				catch (NumberFormatException ne)
				{
					String message = "Invalid numeric entry. An integer value greater than 0 is required.";
					JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if (e.getSource() == this.cancelButton)
			{
				ApplicationFrame.this.newFileDialog.dispose();
			}
		}

		@Override
		public void keyTyped(KeyEvent e) 
		{
			char c = e.getKeyChar();
			if (e.getSource() == widthField || e.getSource() == heightField) 
			{
				if (!(Character.isDigit(c) || c == KeyEvent.VK_DELETE || c == KeyEvent.VK_BACK_SPACE)) 
				{
					this.getToolkit().beep();
					e.consume();
				}
			}
			if (c == KeyEvent.VK_ENTER) {
				try
				{
					int width = Integer.parseInt(this.widthField.getText());
					int height = Integer.parseInt(this.heightField.getText());
					
				
					String title = this.nameField.getText();
		
					createNewPaintPanel(title, width, height);
					
					ApplicationFrame.this.menuBar.saveAsFile.setEnabled(true);
					
					ApplicationFrame.paintPanelWidth = width;
					ApplicationFrame.paintPanelHeight = height;
					ApplicationFrame.this.newFileDialog.dispose();
				}
				catch (NumberFormatException ne)
				{
					String message = "Invalid numeric entry. An integer value greater than 0 is required.";
					JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if (c == KeyEvent.VK_ESCAPE) {
				ApplicationFrame.this.newFileDialog.dispose();
			}
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
			char c = e.getKeyChar();
			if (e.getSource() == widthField) 
			{
				String s = widthField.getText();
				
				try {
					if (Integer.parseInt(s) > ApplicationFrame.MAX_PAINT_PANEL_WIDTH) 
					{
						this.getToolkit().beep();
						e.consume();
						widthField.setText("" + ApplicationFrame.MAX_PAINT_PANEL_WIDTH);
						JOptionPane.showMessageDialog(this, "The maximum value of width is 1400", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else if (Integer.parseInt(s) < 1)
					{
						this.getToolkit().beep();
						e.consume();
						widthField.setText("1");
						JOptionPane.showMessageDialog(this, "The minimum value of width is 1", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException NullPointerException){
					e.consume();
				}
			}
			if (e.getSource() == heightField) 
			{
				
				String s = heightField.getText();
				
				try {
					if (Integer.parseInt(s) > ApplicationFrame.MAX_PAINT_PANEL_HEIGHT) 
					{
						this.getToolkit().beep();
						e.consume();
						heightField.setText("" + ApplicationFrame.MAX_PAINT_PANEL_HEIGHT);
						JOptionPane.showMessageDialog(this, "The maximum value of height is 750", "Error" ,JOptionPane.ERROR_MESSAGE);
					}
					else if (Integer.parseInt(s) < 1)
					{
						this.getToolkit().beep();
						e.consume();
						heightField.setText("1");
						JOptionPane.showMessageDialog(this, "The minimum value of height is 1", "Error", JOptionPane.ERROR_MESSAGE);
					}

				} catch (NumberFormatException NullPointerException) 
				{
					e.consume();
				}
			}
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			if (e.getDocument() == this.widthField.getDocument())
			{
				
			}
			else if (e.getDocument() == this.heightField.getDocument())
			{
				
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/** Set the selected shapes to all the available paint panels. */
	public void setPaintPanelDrawingShape(DrawMode shapeToDraw)
	{
		for (PaintPanel pp : paintPanels)
		{
			pp.setShapeToDraw(shapeToDraw);
		}
	}
	
	/*********************************************************************************************************************
	 * Menu Bar inner class                                                                                              *
	 *********************************************************************************************************************/
	
	/**
	 * This is a class of menu bar.
	 */
	class MenuBar extends JMenuBar implements ActionListener
	{
		private static final long serialVersionUID = 1L;

		// ----------------------------------
		// Menu Bar and its components
		// ----------------------------------
		public JMenuBar menuBar;
		
		private JMenu editMenu;
		private JMenu helpMenu;
		
		// menu items of Assignment 2 menu
		private JMenuItem aboutAssignment2;
		// menu items of File menu
		private JMenuItem newFile;
		private JMenuItem openFile;
		private JMenuItem saveFile;
		private JMenuItem saveAsFile;
		private JMenuItem exit;
		private JMenuItem print; //TODO
		// menu items of Edit menu
		private JMenuItem redo;
		private JMenuItem cut;
		private JMenuItem copy;
		private JMenuItem paste;
		private JMenuItem zoomIn;
		private JMenuItem zoomOut;
		private JMenuItem clearAll;
		//
		
		
		// ----------------------------------
		// File related attributes
		// ----------------------------------
		private JFileChooser fileChooser;
		private File file;
		private File fileToSave;
		private String extensionToSave;
		private String[] availableExtensions = {"jpg", "jpeg", "png"};

		/** Construct a menubar. */
		public MenuBar() 
		{
			// Assignment Menu 
			creatAssignmentMenu(); 
			// File Menu 
			creatFileMenu();
			// Edit menu
			creatEditMenu();
			// Window menu
//			creatWindowMenu();
			// Help menu
			creatHelpMenu();
			
		}

		public JFileChooser buildSaveFileChooser()
		{
			final JFileChooser myFileChooser = new JFileChooser(new File("."));
			myFileChooser.setAcceptAllFileFilterUsed(false);
		
			myFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG (*.jpg; *.jpeg)", "jpg", "jpeg"));
			myFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG (*.png)", "png"));
			
			myFileChooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener()
			{
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					
					String extension = getFileFilterExtension(evt);
					fileToSave = new File(myFileChooser.getSelectedFile().getName() + "." + extension);
					myFileChooser.setSelectedFile(fileToSave);
				}
			});
			myFileChooser.addPropertyChangeListener(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY, new PropertyChangeListener()
			{
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (evt.getOldValue() != null && evt.getNewValue() == null)
					{
						String fileName = getFileNameTextFieldFromFileChooser(myFileChooser).getText();
						fileToSave = new File(getFileNameWithOutExtension(fileName));
						myFileChooser.setSelectedFile(fileToSave);
					}
				}
			});
			return myFileChooser;
		}
		
		/** Get the text in the file name TextField of JFileChooser. */
		public JTextField getFileNameTextFieldFromFileChooser(Container container)
		{
			for (int i = 0; i < container.getComponentCount(); i++)
			{
				Component child = container.getComponent(i);
				if (child instanceof JTextField)
				{
					return (JTextField) child;
				}
				else if (child instanceof Container)
				{
					JTextField field = getFileNameTextFieldFromFileChooser((Container) child);
					if (field != null)
					{
						return field;
					}
				}
			}
			return null;
		}
		
		public JFileChooser buildOpenFileChooser()
		{
			JFileChooser myFileChooser = new JFileChooser(new File("."));
		
			myFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG (*.jpg; *.jpeg)", "jpg", "jpeg"));
			myFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG (*.png)", "png"));
			return myFileChooser;
		}
		
		public String getFileFilterExtension(PropertyChangeEvent evt)
		{	
		
			return ((FileNameExtensionFilter)evt.getNewValue()).getExtensions()[0].toString();
		}
		
		/*********************************************************************************************************************
		 * Create component of Menu Bar                                                                                      *
		 *********************************************************************************************************************/
		// ----------------------------------
		// Create assignment menu
		// ----------------------------------
		public JMenu creatAssignmentMenu() {
			JMenu appNameMenu = new JMenu(spaceBetweenMenus +"Assignment 2" + spaceBetweenMenus);
			appNameMenu.setMnemonic(KeyEvent.VK_A);
			appNameMenu.setForeground(Color.WHITE);
			this.add(appNameMenu);
			aboutAssignment2 = new JMenuItem("About Assignment_2");
			aboutAssignment2.setMnemonic(KeyEvent.VK_B);
			aboutAssignment2.setFont(FONT_OF_SYSTEM);
			appNameMenu.add(aboutAssignment2);
			return appNameMenu;
		}
		// ----------------------------------
		// Create file menu
		// ----------------------------------
		public JMenu creatFileMenu() {
		
			JMenu fileMenu = new JMenu(spaceBetweenMenus +"File" + spaceBetweenMenus);
			fileMenu.setMnemonic(KeyEvent.VK_F);
			fileMenu.setForeground(Color.WHITE);
			this.add(fileMenu);
			newFile = new JMenuItem("New");
			newFile.setFont(FONT_OF_SYSTEM);
			newFile.setPreferredSize(new Dimension(180, newFile.getPreferredSize().height));
			openFile = new JMenuItem("Open...");
			openFile.setFont(FONT_OF_SYSTEM);
			saveFile = new JMenuItem("Save");
			saveFile.setFont(FONT_OF_SYSTEM);
			saveFile.setEnabled(false);
			saveAsFile = new JMenuItem("Save as...");
			saveAsFile.setFont(FONT_OF_SYSTEM);
			saveAsFile.setEnabled(false);
			exit = new JMenuItem("Exit");
			exit.setFont(FONT_OF_SYSTEM);
		
			//add shortcut
			newFile.setMnemonic(KeyEvent.VK_N);
			newFile.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			openFile.setMnemonic(KeyEvent.VK_O);
			openFile.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			saveFile.setMnemonic(KeyEvent.VK_S);
			saveFile.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			saveAsFile.setMnemonic(KeyEvent.VK_S);
			saveAsFile.setAccelerator(KeyStroke.getKeyStroke("shift ctrl S"));
			exit.setMnemonic(KeyEvent.VK_E);
			exit.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			
			// add items to menu
			fileMenu.add(newFile);
			fileMenu.add(openFile);
			fileMenu.addSeparator();
			fileMenu.add(saveFile);
			fileMenu.add(saveAsFile);
			fileMenu.addSeparator();
			fileMenu.add(exit);
			
			//TODO try to use iterator to add the elements to menu later on.
			
			//add listener 
			aboutAssignment2.addActionListener(this);
			newFile.addActionListener(this);
			openFile.addActionListener(this);
			saveFile.addActionListener(this);
			saveAsFile.addActionListener(this);
			exit.addActionListener(this);
			
			return fileMenu;
		}
		
		// ----------------------------------
		// Create edit menu
		// ----------------------------------
		public JMenu creatEditMenu() {
			this.editMenu = new JMenu(spaceBetweenMenus + "Edit" + spaceBetweenMenus);
			editMenu.setMnemonic(KeyEvent.VK_E);
			this.editMenu.setForeground(Color.WHITE);
			this.addListenerToEditMenu();
			this.add(editMenu);
			undo = new JMenuItem("Undo");
			undo.setFont(FONT_OF_SYSTEM);
			undo.setPreferredSize(new Dimension(180, undo.getPreferredSize().height));
			undo.setEnabled(false);
			redo = new JMenuItem("Redo");
			redo.setFont(FONT_OF_SYSTEM);
			
			cut = new JMenuItem("Cut");
			cut.setFont(FONT_OF_SYSTEM);
			copy = new JMenuItem("Copy");
			copy.setFont(FONT_OF_SYSTEM);
			paste = new JMenuItem("Paste");
			paste.setFont(FONT_OF_SYSTEM);
			
			zoomIn = new JMenuItem("Zoom In");
			zoomIn.setFont(FONT_OF_SYSTEM);
			zoomIn.setEnabled(false);
			
			zoomOut = new JMenuItem("Zoom Out");
			zoomOut.setFont(FONT_OF_SYSTEM);
			zoomOut.setEnabled(false);
			
			clearAll = new JMenuItem("Clear All");
			clearAll.setFont(FONT_OF_SYSTEM);
			clearAll.setEnabled(false);
			
			//add shortcut
			undo.setMnemonic(KeyEvent.VK_U);
			undo.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			redo.setMnemonic(KeyEvent.VK_R);
			redo.setAccelerator(KeyStroke.getKeyStroke("shift ctrl Z"));
			cut.setMnemonic(KeyEvent.VK_C);
			cut.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			copy.setMnemonic(KeyEvent.VK_Y);
			copy.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			paste.setMnemonic(KeyEvent.VK_P);
			paste.setAccelerator(KeyStroke.getKeyStroke('V', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			
			zoomIn.setMnemonic(KeyEvent.VK_I);
			zoomIn.setAccelerator(KeyStroke.getKeyStroke("shift ctrl I"));
			
			zoomOut.setMnemonic(KeyEvent.VK_O);
			zoomOut.setAccelerator(KeyStroke.getKeyStroke("shift ctrl O"));
			
			clearAll.setMnemonic(KeyEvent.VK_E);
			clearAll.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			
			clearAll.addActionListener(this);
			
			
			// add items to menu
			editMenu.add(undo);
			editMenu.add(redo);
//			editMenu.addSeparator();
//			editMenu.add(cut);
//			editMenu.add(copy);
//			editMenu.add(paste);
			editMenu.addSeparator();
			editMenu.add(zoomIn);
			editMenu.add(zoomOut);
			editMenu.addSeparator();
			editMenu.add(clearAll);
			
			//Add listener
			undo.addActionListener(this);
			redo.addActionListener(this);
			zoomIn.addActionListener(this);
			zoomOut.addActionListener(this);
			return editMenu;
		}
		
		public void addListenerToEditMenu()
		{
			this.editMenu.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e) 
				{
					if (ApplicationFrame.currentPaintPanel == null)
					{
						undo.setEnabled(false);
						redo.setEnabled(false);
					}
					else 
					{
						if  (ApplicationFrame.currentPaintPanel.history.size() < 2)
						{
							undo.setEnabled(false);
						}
						else
						{
							undo.setEnabled(true);
						}
						if (ApplicationFrame.this.redoList.size() < 1)
						{
							redo.setEnabled(false);
						}
						else
						{
							redo.setEnabled(true);
						}
					}
				}
			});
		}
		
		// ----------------------------------
		// Create window menu
		// ----------------------------------
		public JMenu creatWindowMenu() {
			
			JMenu windowMenu = new JMenu(spaceBetweenMenus +"Window" + spaceBetweenMenus);
			windowMenu.setMnemonic(KeyEvent.VK_W);
			windowMenu.setForeground(Color.WHITE);
			windowMenu.setFont(FONT_OF_SYSTEM);
			this.add(windowMenu);
			
			return windowMenu;
		}
		// ----------------------------------
		// Create help menu
		// ----------------------------------
		public JMenu creatHelpMenu() {
			
			this.helpMenu = new JMenu(spaceBetweenMenus +"Help" + spaceBetweenMenus);
			helpMenu.setMnemonic(KeyEvent.VK_H);
			helpMenu.setForeground(Color.WHITE);
			helpMenu.setFont(FONT_OF_SYSTEM);
			helpMenu.addMouseListener(new HelpMouseListener());
			this.add(helpMenu);
			
			return helpMenu;
		}
		
		/*********************************************************************************************************************
		 * MenuBar ActionListener                                                                                    *
		 *********************************************************************************************************************/
		class HelpMouseListener extends MouseAdapter
		{
			@Override
			public void mousePressed(MouseEvent e) 
			{
				ApplicationFrame.this.buildHelpDialog();
			}
		}
		
		
		public void openImage()
		{
			JFileChooser fileChooser = this.buildOpenFileChooser();
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
			{
				file = fileChooser.getSelectedFile();
				
				if (ApplicationFrame.currentPaintPanel == null)
				{
					//No paint panel is created yet, so open the image directly
					this.readAndSetUpImage();
				}
				else if (ApplicationFrame.changeSaved == false)
				{
					//Check if the user wants to save the painting before opening the image
					int returnState = this.saveBeforeReplace();
				
					if (returnState == JOptionPane.YES_OPTION)
					{
						this.saveAsImage();
						this.readAndSetUpImage();
					}
					else if (returnState == JOptionPane.NO_OPTION)
					{
						this.readAndSetUpImage();
					}
				}
				else
				{
					this.readAndSetUpImage();
				}
			}
		}
		
		public void readAndSetUpImage()
		{
			ApplicationFrame.this.createNewPaintPanel(file.getName(), ApplicationFrame.paintPanelWidth, ApplicationFrame.paintPanelHeight);
			try
			{
				ApplicationFrame.currentPaintPanel.paintImage = ImageIO.read(file);
				ApplicationFrame.currentPaintPanel.paintWidth = ApplicationFrame.currentPaintPanel.paintImage.getWidth();
				ApplicationFrame.currentPaintPanel.paintHeight = ApplicationFrame.currentPaintPanel.paintImage.getHeight();
				ApplicationFrame.currentPaintPanel.setPreferredSize(new Dimension(ApplicationFrame.currentPaintPanel.paintWidth, ApplicationFrame.currentPaintPanel.paintHeight));
				ApplicationFrame.currentPaintPanel.history.remove(0);
				ApplicationFrame.currentPaintPanel.history.add(ApplicationFrame.currentPaintPanel.paintImage);
				this.saveFile.setEnabled(false);
				this.saveAsFile.setEnabled(true);
			} 
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		
		public int saveBeforeReplace()
		{
			final Object[] options = {"Save", "No", "Cancel"};
			return JOptionPane.showOptionDialog(ApplicationFrame.this, 
			"Save changes before closing?",
			"Close", 
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null, options, options[2]);
		}
		
		public int saveBeforeExit()
		{
			final Object[] options = {"Save", "No", "Cancel"};
			return JOptionPane.showOptionDialog(ApplicationFrame.this, 
			"Save changes before exit?",
			"Exit", 
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null, options, options[2]);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			Object ob = e.getSource();

			if (e.getActionCommand().equals("About Assignment_2") == true) {
				JOptionPane.showMessageDialog(ApplicationFrame.this, "Made by: Yuan Ji\n         Pan Jiang\n         Keren Xu");
			}
			else if (ob == helpMenu)
			{
				System.out.println("test");
				ApplicationFrame.this.buildHelpDialog();
			}
			else if (ob == exit) {
				if (ApplicationFrame.changeSaved == false)
				{
					int returnState = this.saveBeforeExit();
					
					if (returnState == JOptionPane.YES_OPTION)
					{
						this.saveAsImage();
					}
					else if (returnState == JOptionPane.NO_OPTION)
					{
					}
					System.exit(0);
				}
				else
				{
					System.exit(0);
				}
			}
			else if (e.getActionCommand().equals("New"))
			{
				if (ApplicationFrame.changeSaved == false)
				{
					//Check if the user wants to save the painting before opening the image
					int returnState = this.saveBeforeReplace();
				
					if (returnState == JOptionPane.YES_OPTION)
					{
						this.saveAsImage();
						createNewFilePanelDialog();
					}
					else if (returnState == JOptionPane.NO_OPTION)
					{
						createNewFilePanelDialog();
					}
				}
				else
				{
					createNewFilePanelDialog();
				}
				
			}
			else if (ob == undo)
			{
				this.undo();
			}
			else if (ob == redo)
			{
				this.redo();
			}
			else if (ob == clearAll)
			{
				ApplicationFrame.currentPaintPanel.paintImage = new BufferedImage(ApplicationFrame.currentPaintPanel.paintWidth, ApplicationFrame.currentPaintPanel.paintHeight, BufferedImage.TYPE_INT_RGB);
				ApplicationFrame.currentPaintPanel.setUpPaintGraphic();
				ApplicationFrame.currentPaintPanel.history.add(ApplicationFrame.currentPaintPanel.paintImage);
				ApplicationFrame.currentPaintPanel.repaint();
			}
			else if (ob == openFile) 
			{
				this.openImage();
			}
			else if (ob == saveFile) 
			{
				if (fileToSave != null)
				{
					this.saveImage(ApplicationFrame.currentPaintPanel, extensionToSave, fileToSave);
					ApplicationFrame.changeSaved = true;
				}
			}
			else if (ob == saveAsFile) 
			{
				this.saveAsImage();
			}
			else if (ob == zoomIn && ApplicationFrame.currentPaintPanel != null)
			{
				ApplicationFrame.currentlyPressedMenuItem = zoomIn;
				ApplicationFrame.currentPaintPanel.paintImage = new BufferedImage(ApplicationFrame.currentPaintPanel.paintWidth, ApplicationFrame.currentPaintPanel.paintHeight, BufferedImage.TYPE_INT_RGB);
				ApplicationFrame.currentPaintPanel.setUpPaintGraphic();
				ApplicationFrame.currentPaintPanel.zoomIn();
				ApplicationFrame.currentZoomRatio = ApplicationFrame.this.calculateZoomRatio();
				ApplicationFrame.this.infoBar.zoomTextField.setText(ApplicationFrame.currentZoomRatio + "");
			}
			else if (ob == zoomOut && ApplicationFrame.currentPaintPanel != null)
			{
				ApplicationFrame.currentlyPressedMenuItem = zoomOut;
				ApplicationFrame.currentPaintPanel.paintImage = new BufferedImage(ApplicationFrame.currentPaintPanel.paintWidth, ApplicationFrame.currentPaintPanel.paintHeight, BufferedImage.TYPE_INT_RGB);
				ApplicationFrame.currentPaintPanel.setUpPaintGraphic();
				ApplicationFrame.currentPaintPanel.zoomOut();
				ApplicationFrame.currentZoomRatio = ApplicationFrame.this.calculateZoomRatio();
				ApplicationFrame.this.infoBar.zoomTextField.setText(ApplicationFrame.currentZoomRatio + "");
			}
		}
		
		public void saveAsImage()
		{
			JFileChooser fileChooser = this.buildSaveFileChooser();
			String default_extension = ((FileNameExtensionFilter)fileChooser.getFileFilter()).getExtensions()[0].toString();
			
			fileToSave = new File(this.getFileNameWithOutExtension(ApplicationFrame.currentPaintPanel.getName()) + "." + default_extension);
			fileChooser.setSelectedFile(fileToSave);
			
			int returnState = fileChooser.showSaveDialog(ApplicationFrame.this);
			
			if (returnState == JFileChooser.APPROVE_OPTION)
			{
			
				extensionToSave = ((FileNameExtensionFilter)fileChooser.getFileFilter()).getExtensions()[0].toString();
				fileToSave = fileChooser.getSelectedFile();
				
				//Check if the file name has an extension.
				//Append the extension to file name if it does not have it.
				
				String fileName = fileToSave.getName();
				if (!fileName.contains("."))
				{
					fileName = fileName + "." + extensionToSave;
				}
				else 
				{
					if (!availableExtensionsContains(fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())))
					{
						fileName = fileName + "." + extensionToSave;
					}
				}
				ApplicationFrame.currentPaintPanel.setName(fileName);
				fileToSave = new File(fileChooser.getCurrentDirectory() + "/" + fileName);
				if (!fileToSave.exists() || okToReplace(fileToSave))
				{
					this.saveImage(ApplicationFrame.currentPaintPanel, extensionToSave, fileToSave);
					ApplicationFrame.changeSaved = true;
					MenuBar.this.saveFile.setEnabled(true);
				}
			}
		}
		
		public boolean okToReplace(File f)
		{
			final Object[] options = {"Yes", "No", "Cancel"};
			return JOptionPane.showOptionDialog(ApplicationFrame.this, "The file '" 
			+ f.getName() + "'already exists. " + "Replace existing file?", 
			"Warning", 
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.WARNING_MESSAGE,
			null, options, options[2]) == JOptionPane.YES_OPTION;
		}
		
		public int changesShouldBeSavedBeforeClosing()
		{
			final Object[] options = {"Save", "No", "Cancel"};
			return JOptionPane.showOptionDialog(this, 
			"Save changes before closing?",
			"Close", 
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null, options, options[2]);
		}
		
		/** Check if the extension belongs to available extensions. */
		public boolean availableExtensionsContains(String extension)
		{
			boolean result = false;
			for (int i = 0; i < availableExtensions.length; i++)
			{
				if (availableExtensions[i].equals(extension))
				{
					result = result || true;
				}
			}
			return result;
		}
		
		/** Return the file's name by removing the extension if any exits. */
		public String getFileNameWithOutExtension(String fileName)
		{
			String result = fileName;
			if (!fileName.isEmpty() && fileName.contains("."))
			{
				if (this.availableExtensionsContains(fileName.substring(fileName.lastIndexOf(".") + 1)))
				{
					result = fileName.substring(0, fileName.lastIndexOf("."));
				}
			}
			return result;
		}
		
		/** Set the color of the menubar. */
		@Override
		protected void paintComponent(Graphics graphic) {
		
			super.paintComponent(graphic);
			
			Graphics2D g = (Graphics2D) graphic;
			g.setColor(BAR_COLOR);
			g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
			
		}
		
		public void saveImage(PaintPanel currentPaint, String extension, File file)
		{
			try {
				RenderedImage imageToSave = (RenderedImage) currentPaint.history.get(currentPaint.history.size() - 1);
				ImageIO.write(imageToSave, extension, file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		public void undo()
		{
			if (ApplicationFrame.currentPaintPanel != null && ApplicationFrame.currentPaintPanel.history.size() > 1)
			{
				Image removedImage = ApplicationFrame.currentPaintPanel.history.remove(ApplicationFrame.currentPaintPanel.history.size() - 1);
				ApplicationFrame.this.redoList.add(removedImage);

				Image previousImage = ApplicationFrame.currentPaintPanel.history.get(ApplicationFrame.currentPaintPanel.history.size() - 1);
				int paintWidth = ApplicationFrame.currentPaintPanel.paintWidth;
				int paintHeight = ApplicationFrame.currentPaintPanel.paintHeight;
				
				ApplicationFrame.currentPaintPanel.paintImage = new BufferedImage(paintWidth, paintHeight, BufferedImage.TYPE_INT_RGB);
				ApplicationFrame.currentPaintPanel.setUpPaintGraphic();
				
				ApplicationFrame.currentPaintPanel.myGraphics.drawImage(previousImage, 0, 0, paintWidth, paintHeight, null);
				ApplicationFrame.currentPaintPanel.repaint();
				
				this.redo.setEnabled(true);
			}
			else
			{
				undo.setEnabled(false);
				ApplicationFrame.changeSaved = true;
			}
		}
		
		public void redo()
		{
			if (ApplicationFrame.currentPaintPanel != null && ApplicationFrame.this.redoList.size() > 0)
			{
				Image removedImage = ApplicationFrame.this.redoList.remove(ApplicationFrame.this.redoList.size() - 1);
				ApplicationFrame.currentPaintPanel.history.add(removedImage);
				
				Image previousImage = ApplicationFrame.currentPaintPanel.history.get(ApplicationFrame.currentPaintPanel.history.size() - 1);
				int paintWidth = ApplicationFrame.currentPaintPanel.paintWidth;
				int paintHeight = ApplicationFrame.currentPaintPanel.paintHeight;
				
				ApplicationFrame.currentPaintPanel.paintImage = new BufferedImage(paintWidth, paintHeight, BufferedImage.TYPE_INT_RGB);
				ApplicationFrame.currentPaintPanel.setUpPaintGraphic();
				
				ApplicationFrame.currentPaintPanel.myGraphics.drawImage(previousImage, 0, 0, paintWidth, paintHeight, null);
				ApplicationFrame.currentPaintPanel.repaint();
				
				ApplicationFrame.this.undo.setEnabled(true);
			}
			else 
			{
				redo.setEnabled(false);
			}
		}
	}
	
	/*********************************************************************************************************************
	 * Custom toolbar class                                                                                                 *
	 *********************************************************************************************************************/

	class CustomToolBar extends JToolBar implements ChangeListener
	{
		private static final long serialVersionUID = 1L;
		
		JToggleButton freeDrawBtn;
		JToggleButton drawRectBtn;
		JToggleButton drawPolygonBtn;
		JToggleButton drawRoundedCornerRectBtn;
		JToggleButton drawEllipseBtn;
		JToggleButton drawLineBtn;
		JToggleButton insertTextBtn;
		JToggleButton bucketFillBtn;
		JToggleButton eraserBtn;
		JToggleButton zoomInBtn;
		JToggleButton zoomOutBtn;
		JToggleButton eyeDropperBtn;
		
		Map<JToggleButton, ImageIcon[]> buttonIconMap = new HashMap<JToggleButton, ImageIcon[]>();
		
		
		public ArrayList<JToggleButton> toolBarBtns = new ArrayList<JToggleButton>();
		
		/*********************************************************************************************************************
		 * Create Tool Bar                                                                                                   *
		 *********************************************************************************************************************/
		public CustomToolBar() 
		{
			this.setFloatable(false);
			this.setOrientation(JToolBar.VERTICAL);
			this.setUpToolbar();
		}
		
		public void setUpToolbar()
		{
			this.createDrawingModeSelectionButtons();
			
			this.createColorChooserButton();
			
			this.setToolTipsForToolBarButtons();

			this.addButtonsToToolBar();
			
			this.addButtonsToButtonGroup();
			
			this.setButtonProperty();
		}
		
		public void createDrawingModeSelectionButtons()
		{
			this.freeDrawBtn = new JToggleButton();
			this.buttonIconMap.put(this.freeDrawBtn, new ImageIcon[]{ApplicationFrame.freeDrawIcon, ApplicationFrame.freeDrawIconBlack});
			toolBarBtns.add(freeDrawBtn);
			
			this.drawRectBtn = new JToggleButton();
			this.buttonIconMap.put(this.drawRectBtn, new ImageIcon[]{ApplicationFrame.recIcon, ApplicationFrame.recIconBlack});
			toolBarBtns.add(drawRectBtn);
			
			this.drawPolygonBtn = new JToggleButton();
			this.buttonIconMap.put(this.drawPolygonBtn, new ImageIcon[]{ApplicationFrame.polyIcon, ApplicationFrame.polyIconBlack});
			toolBarBtns.add(drawPolygonBtn);
			
			this.drawRoundedCornerRectBtn = new JToggleButton();
			this.buttonIconMap.put(this.drawRoundedCornerRectBtn, new ImageIcon[]{ApplicationFrame.roundRecIcon, ApplicationFrame.roundRecIconBlack});
			toolBarBtns.add(drawRoundedCornerRectBtn);
			
			this.drawEllipseBtn = new JToggleButton();
			this.buttonIconMap.put(this.drawEllipseBtn, new ImageIcon[]{ApplicationFrame.ellipseIcon, ApplicationFrame.ellipseIconBlack});
			toolBarBtns.add(drawEllipseBtn);
			
			this.drawLineBtn = new JToggleButton();
			this.buttonIconMap.put(this.drawLineBtn, new ImageIcon[]{ApplicationFrame.lineIcon, ApplicationFrame.lineIconBlack});
			toolBarBtns.add(drawLineBtn);
			
			this.insertTextBtn = new JToggleButton();
			this.buttonIconMap.put(this.insertTextBtn, new ImageIcon[]{ApplicationFrame.textIcon, ApplicationFrame.textIconBlack});
			toolBarBtns.add(insertTextBtn);
			
			
			this.bucketFillBtn = new JToggleButton();
			this.buttonIconMap.put(this.bucketFillBtn, new ImageIcon[]{ApplicationFrame.paintBucketIcon, ApplicationFrame.paintBucketIconBlack});
			toolBarBtns.add(bucketFillBtn);
			
			this.zoomInBtn = new JToggleButton();
			this.buttonIconMap.put(this.zoomInBtn, new ImageIcon[]{ApplicationFrame.zoomInIcon, ApplicationFrame.zoomInIconBlack});
			toolBarBtns.add(zoomInBtn);
			
			this.zoomOutBtn = new JToggleButton();
			this.buttonIconMap.put(this.zoomOutBtn, new ImageIcon[]{ApplicationFrame.zoomOutIcon, ApplicationFrame.zoomOutIconBlack});
			toolBarBtns.add(zoomOutBtn);
			
			this.eyeDropperBtn = new JToggleButton();
			this.buttonIconMap.put(this.eyeDropperBtn, new ImageIcon[]{ApplicationFrame.eyeDropperIcon, ApplicationFrame.eyeDropperIconBlack});
			toolBarBtns.add(eyeDropperBtn);
			
			this.eraserBtn = new JToggleButton();
			this.buttonIconMap.put(this.eraserBtn, new ImageIcon[]{ApplicationFrame.eraserIcon, ApplicationFrame.eraserIconBlack});
			toolBarBtns.add(eraserBtn);
		}
		
		public void addButtonsToToolBar()
		{
			this.add(freeDrawBtn);
			this.add(eraserBtn);
			this.addSeparator();
			this.add(drawRectBtn);
			this.add(drawRoundedCornerRectBtn);
			this.add(drawPolygonBtn);
			this.add(drawEllipseBtn);
			this.add(drawLineBtn);
			this.addSeparator();
			this.add(insertTextBtn);
			this.addSeparator();
			this.add(eyeDropperBtn);
			this.add(bucketFillBtn);
			this.addSeparator();
			this.add(zoomInBtn);
			this.add(zoomOutBtn);
			this.addSeparator();
			this.add(colorChooserBtn);
		}
		
		public void setToolTipsForToolBarButtons()
		{
			freeDrawBtn.setToolTipText("Free Draw Tool");
			drawRectBtn.setToolTipText("Rectangle Tool");
			drawPolygonBtn.setToolTipText("Polygon Tool");
			drawRoundedCornerRectBtn.setToolTipText("Rounded Corner Rectangle Tool");
			drawEllipseBtn.setToolTipText("Ellipse Tool");
			drawLineBtn.setToolTipText("Line Tool");
			insertTextBtn.setToolTipText("Text Tool");
			bucketFillBtn.setToolTipText("Bucket Fill Tool");
			zoomInBtn.setToolTipText("Zoom In Tool");
			zoomOutBtn.setToolTipText("Zoom Out Tool");
			colorChooserBtn.setToolTipText("Color Tool");
			eraserBtn.setToolTipText("Eraser Tool");
			eyeDropperBtn.setToolTipText("Eyedropper Tool");
		}
		
		public void createColorChooserButton()
		{
			colorChooserBtn = new JButton();
			colorChooserBtn.setPreferredSize(new Dimension(32, 32));
			colorChooserBtn.addActionListener(new ColorChooserListener());
			colorChooserBtn.setBackground(ApplicationFrame.chosenPaintColor);
			colorChooserBtn.setOpaque(true);
			colorChooserBtn.setBorder(BorderFactory.createLineBorder(ApplicationFrame.COLOR_CHOOSER_BORDER_COLOR));
		}
		
		public void addButtonsToButtonGroup()
		{
			ButtonGroup buttonGroup = new ButtonGroup();
			for (JToggleButton button : toolBarBtns)
			{
				buttonGroup.add(button);
			}
		}
		
		public void setDefaultToggledButton(JToggleButton button)
		{
			button.setSelected(true);
		}
		
		public void setButtonProperty() {
			for (JToggleButton button : toolBarBtns) {
				button.setPreferredSize(new Dimension(40, 40));
				button.setIcon(this.buttonIconMap.get(button)[0]);
				button.addItemListener(new ToolBarButtonItemListener());
				button.addMouseListener(new ToolBarButtonListener());
			}
		}
		
		/*********************************************************************************************************************
		 * Listener for tool bar drawing mode selection buttons                                                                                           *
		 *********************************************************************************************************************/
		
		class ToolBarButtonItemListener implements ItemListener
		{
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				JToggleButton button = (JToggleButton)e.getItem();
				
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					button.setIcon(buttonIconMap.get(button)[1]);
				}
				else if (e.getStateChange() == ItemEvent.DESELECTED)
				{
					button.setIcon(buttonIconMap.get(button)[0]);
				}
			}
		}
		
		class ToolBarButtonListener extends MouseAdapter
		{
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e))
				{
					this.setDrawMode(e);
				}
			}
			
			public void setDrawMode(MouseEvent e)
			{
				if (e.getSource() == CustomToolBar.this.drawRectBtn)
				{
					drawMode = DrawMode.RECTANGLE;
				}
				else if (e.getSource() == CustomToolBar.this.drawRoundedCornerRectBtn)
				{
					drawMode = DrawMode.ROUND_RECTANGLE;
				}			
				else if (e.getSource() == CustomToolBar.this.drawEllipseBtn)
				{
					drawMode = DrawMode.ELLIPSE;
				}
				else if (e.getSource() == CustomToolBar.this.drawLineBtn)
				{
					drawMode = DrawMode.LINE;
				}
				else if (e.getSource() == CustomToolBar.this.freeDrawBtn)
				{
					drawMode = DrawMode.FREE_DRAW;
				}
				else if (e.getSource() == CustomToolBar.this.zoomInBtn)
				{
					drawMode = DrawMode.ZOOM_IN;
				}
				else if (e.getSource() == CustomToolBar.this.zoomOutBtn)
				{
					drawMode = DrawMode.ZOOM_OUT;
				}
				else if (e.getSource() == CustomToolBar.this.insertTextBtn)
				{
					drawMode = DrawMode.INPUT_TEXT;
				} 
				else if (e.getSource() == CustomToolBar.this.bucketFillBtn)
				{
					drawMode = DrawMode.BUCKET_FILL;
				} 
				else if (e.getSource() == CustomToolBar.this.eyeDropperBtn)
				{
					drawMode = DrawMode.EYE_DROPPER;
				} 
				else if (e.getSource() == CustomToolBar.this.eraserBtn)
				{
					drawMode = DrawMode.ERASER;
				} 
				else if (e.getSource() == CustomToolBar.this.drawPolygonBtn)
				{
					drawMode = DrawMode.POLYGON;
				} 
				
				if (!paintPanels.isEmpty())
				{
					ApplicationFrame.this.setPaintPanelDrawingShape(drawMode);
				}
				
				ApplicationFrame.this.infoBar.updateInfoBarBasedOnSelectedDrawingMode(drawMode);
				
				System.out.println("set draw mode to " + drawMode);
			}
			
			public void displayToolBarPopupMenu(MouseEvent e)
			{
				PopupMenu ppm = new PopupMenu();
				JToggleButton buttonClicked = (JToggleButton) e.getSource();
				System.out.printf("x is %d, y is %d, width is %d, height is %d", buttonClicked.getX(), buttonClicked.getY(), buttonClicked.getWidth(), buttonClicked.getHeight());
				ppm.show(buttonClicked, buttonClicked.getX() + buttonClicked.getWidth(),  5);
			}
		}
		
		class ColorChooserListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(ApplicationFrame.this, null, ApplicationFrame.chosenPaintColor);
				if (color != null)
				{
					ApplicationFrame.chosenPaintColor = color;
					colorChooserBtn.setBackground(color);
				}
			}
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
//			if (this.toolBarBtns.contains(e.getSource()))
//			{
//				JToggleButton button = (JToggleButton) e.getSource();
//				if (button.isSelected())
//				{
//					button.setBackground(Color.RED);
//				}
////				ButtonModel buttonModel = button.getModel();
////				if (buttonModel.isSelected())
////				{
////					System.out.println("button rollover");
////					button.setBackground(Color.GREEN);
//////					button.setForeground(Color.BLACK);
////				}
//			}
		}
		
	}
	
	class CustomButtonUI extends ButtonUI
	{
		public void paint(Graphics g, JComponent c)
		{
			Graphics2D g2 = (Graphics2D) g;
			
			AbstractButton b = (AbstractButton) c;
			ButtonModel bm = b.getModel();
			
			Color bevelColor;
			
			if (bm.isPressed())
			{
				bevelColor = Color.BLUE;
			}
		}
	}
	
	/*********************************************************************************************************************
	 * InformationBar inner class                                                                                                 *
	 *********************************************************************************************************************/

	/** A InformationBar displaying the information of the current painting activity.
	 *  The content of the InformationBar changes based on the drawing mode selected by the user.
	 *  */
	class InformationBar extends JPanel implements ChangeListener, DocumentListener, KeyListener, ActionListener, ItemListener, FocusListener
	{
		private static final long serialVersionUID = 1L;
		public JTextField brushSizeField;
		public JSlider brushSizeSlider;
		
		public JCheckBox filledCheckBoxForShape;
		public JCheckBox filledCheckBoxForRoundRect;
		public JCheckBox filledCheckBoxForPolygon;
		public JTextField shapeWidthField;
		public JTextField shapeHeightField;
		public JTextField arcWidthField;
		public JTextField arcHeightField;
		public JCheckBox sizeFixedCheckBoxForShape;
		public JCheckBox sizeFixedCheckBoxForRoundRect;
		public JTextField zoomTextField;
		public JComboBox<String> fontComboBox;
		public JComboBox<String> fontSizeComboBox;
		public JComboBox<String> fontStyleComboBox;
		public JTextField polygonSidesField;
		public JCheckBox boldFontStyle;
		public JCheckBox italicFontStyle;
		public JCheckBox dashedLineCheckBox;
		public JTextField lineWidthField;
		
		public String fontFamily;
		public String[] fontFamilyList;
		public String defaultFamily;
		public int defaultFamilyIndex;
		public int fontSize;
		public int fontStyle;
		
		final String[] SIZE = { "10", "14", "18", "22", "26", "32", "38", "48" };
		//final String[] STYLE = {"Plain", "Italic", "Bold"};
		final int DEFAULT_STYLE = Font.PLAIN;
		final int DEFAULT_STYLE_INDEX = 0;
		final int DEFAULT_SIZE = 22;
		final int DEFAULT_SIZE_INDEX = 3;
		
		public InformationBar()
		{
			this.setPreferredSize(new Dimension(500, 38));
			this.setBackground(BAR_COLOR);
			
			this.setLayout(new CardLayout());
			this.add(this.buildInfoBarForFreeDrawing(), "freeDrawing");
			this.add(this.buildinfoBarForShape(), "shapeDrawing");
			this.add(this.buildinfoBarForRoundRectangle(), "roundRectangleDrawing");
			this.add(this.buildinfoBarForZoom(), "zoom");
			this.add(this.buildinforBarForText(), "text");
			this.add(this.buildinfoBarForPolygon(), "polygon");
			this.add(this.buildinforBarForLine(), "line");
			this.add(this.buildEmptyInfoBar(), "empty");
			
			brushSizeField.addKeyListener(this);
		}
		
		public JPanel buildEmptyInfoBar()
		{
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.setOpaque(false);
			return panel;
		}
		
		/** User can change the size of the paint brush. */
		public JPanel buildInfoBarForFreeDrawing()
		{
			JLabel sizeLabel = new JLabel("<html><font size=4 color=#ffffff>size(px):</font>");
			JLabel pxLabel = new JLabel("px");
			this.brushSizeField = new JTextField(2);
			brushSizeField.setText(ApplicationFrame.currentBrushWidth + "");
			brushSizeField.getDocument().addDocumentListener(this);
			
			this.brushSizeSlider = this.buildBrushSizeSlider();
			this.brushSizeSlider.setValue(ApplicationFrame.currentBrushWidth);
			
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.setOpaque(false);
			panel.add(Box.createRigidArea(new Dimension(44, 0)));
			panel.add(sizeLabel);
			panel.add(brushSizeField);
//			panel.add(pxLabel);
			panel.add(this.brushSizeSlider);
			
			return panel;
		}
		
		public JSlider buildBrushSizeSlider()
		{
			JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 1, 100, 38);
			slider.addChangeListener(this);
			return slider;
		}
		
		public JPanel buildinfoBarForShape()
		{
			this.filledCheckBoxForShape = new JCheckBox("<html><font size=4 color=#ffffff>filled</font>");
			filledCheckBoxForShape.addActionListener(this);
			
			sizeFixedCheckBoxForShape = new JCheckBox("<html><font size=4 color=#ffffff>fixed size</font>");
			sizeFixedCheckBoxForShape.addActionListener(this);
			
			JLabel shapeWidthLabel = new JLabel("<html><font size=4 color=#ffffff>width(px):</font>");
			JLabel shapeHeightLabel = new JLabel("<html><font size=4 color=#ffffff>height(px):</font>");
			
			this.shapeWidthField = new JTextField(3);
			this.shapeWidthField.setText(ApplicationFrame.this.shapeWidth + "");
			this.shapeWidthField.addKeyListener(this);
			this.shapeWidthField.getDocument().addDocumentListener(this);
			this.shapeHeightField = new JTextField(3);
			this.shapeHeightField.setText(ApplicationFrame.this.shapeHeight + "");
			this.shapeHeightField.addKeyListener(this);
			this.shapeHeightField.getDocument().addDocumentListener(this);
			
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.setOpaque(false);
			panel.add(Box.createRigidArea(new Dimension(44, 0)));
			panel.add(filledCheckBoxForShape);
			panel.add(ApplicationFrame.this.createVerticalSeparator(2, 32));
			
			panel.add(sizeFixedCheckBoxForShape);
			
			panel.add(shapeWidthLabel);
			panel.add(this.shapeWidthField);
			panel.add(shapeHeightLabel);
			panel.add(this.shapeHeightField);
			
			return panel;
		}
		
		public JPanel buildinfoBarForPolygon()
		{
			this.filledCheckBoxForPolygon = new JCheckBox("<html><font size=4 color=#ffffff>filled</font>");
			filledCheckBoxForPolygon.addActionListener(this);
			
			JLabel sidesLable = new JLabel("<html><font size=4 color=#ffffff>sides: </font>");
			this.polygonSidesField = new JTextField(3);
			this.polygonSidesField.setText(ApplicationFrame.polygonSides + "");
			this.polygonSidesField.addKeyListener(this);
			this.polygonSidesField.addFocusListener(this);
			this.polygonSidesField.getDocument().addDocumentListener(this);
			
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.setOpaque(false);
			panel.add(Box.createRigidArea(new Dimension(44, 0)));
			
			panel.add(filledCheckBoxForPolygon);
			panel.add(ApplicationFrame.this.createVerticalSeparator(2, 32));
			
			panel.add(sidesLable);
			panel.add(this.polygonSidesField);
			
			return panel;
		}
		
		public JPanel buildinfoBarForRoundRectangle()
		{
			this.filledCheckBoxForRoundRect = new JCheckBox("<html><font size=4 color=#ffffff>filled</font>");
			filledCheckBoxForRoundRect.addActionListener(this);
			sizeFixedCheckBoxForRoundRect = new JCheckBox("<html><font size=4 color=#ffffff>fixed size</font>");
			sizeFixedCheckBoxForRoundRect.addActionListener(this);
			
			JLabel shapeWidthLabel = new JLabel("<html><font size=4 color=#ffffff>width(px):</font>");
			JLabel shapeHeightLabel = new JLabel("<html><font size=4 color=#ffffff>height(px):</font>");
			
			this.shapeWidthField = new JTextField(3);
			this.shapeWidthField.setText(ApplicationFrame.this.shapeWidth + "");
			this.shapeWidthField.addKeyListener(this);
			this.shapeWidthField.getDocument().addDocumentListener(this);
			this.shapeHeightField = new JTextField(3);
			this.shapeHeightField.setText(ApplicationFrame.this.shapeHeight + "");
			this.shapeHeightField.addKeyListener(this);
			this.shapeHeightField.getDocument().addDocumentListener(this);
			
			JLabel arcWidthLabel = new JLabel("<html><font size=4 color=#ffffff>arc width(px):</font>");
			JLabel arcHeightLabel = new JLabel("<html><font size=4 color=#ffffff>arc height(px):</font>");
			
			this.arcWidthField = new JTextField(3);
			this.arcWidthField.setText(ApplicationFrame.this.currentArcWidth + "");
			this.arcWidthField.addKeyListener(this);
			this.arcWidthField.getDocument().addDocumentListener(this);
			this.arcHeightField = new JTextField(3);
			this.arcHeightField.setText(ApplicationFrame.this.currentArcHeight + "");
			this.arcHeightField.addKeyListener(this);
			this.arcHeightField.getDocument().addDocumentListener(this);
			
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.setOpaque(false);
			panel.add(Box.createRigidArea(new Dimension(44, 0)));
			panel.add(filledCheckBoxForRoundRect);
			panel.add(ApplicationFrame.this.createVerticalSeparator(2, 32));
			
			panel.add(sizeFixedCheckBoxForRoundRect);
			
			panel.add(shapeWidthLabel);
			panel.add(this.shapeWidthField);
			panel.add(shapeHeightLabel);
			panel.add(this.shapeHeightField);
			
			panel.add(ApplicationFrame.this.createVerticalSeparator(2, 32));
			
			panel.add(arcWidthLabel);
			panel.add(this.arcWidthField);
			panel.add(arcHeightLabel);
			panel.add(this.arcHeightField);
			
			return panel;
		}
		
		public JPanel buildinforBarForLine()
		{
			JLabel lineWidth = new JLabel("<html><font size=4 color=#ffffff>width(px): </font>");
			this.lineWidthField = new JTextField(3);
			this.lineWidthField.setText(ApplicationFrame.lineWidth + "");
			this.lineWidthField.addKeyListener(this);
			this.lineWidthField.getDocument().addDocumentListener(this);
			
			this.dashedLineCheckBox = new JCheckBox("<html><font size=4 color=#ffffff>dashed</font>");
			this.dashedLineCheckBox.addItemListener(this);
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.setOpaque(false);
			panel.add(Box.createRigidArea(new Dimension(44, 0)));
			panel.add(lineWidth);
			panel.add(this.lineWidthField);
			
			panel.add(ApplicationFrame.this.createVerticalSeparator(2, 32));
			
			panel.add(this.dashedLineCheckBox);
			return panel;
		}
		
		public JPanel buildinfoBarForZoom()
		{
			JLabel ratioLabel = new JLabel("<html><font size=4 color=#ffffff>ratio:</font>");
			JLabel percentLabel = new JLabel("<html><font size=4 color=#ffffff>%</font>");
			this.zoomTextField = new JTextField(4);
			this.zoomTextField.setEditable(false);
			this.zoomTextField.setText(ApplicationFrame.currentZoomRatio + "");
			this.zoomTextField.addKeyListener(this);
			this.zoomTextField.getDocument().addDocumentListener(this);
			
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.setOpaque(false);
			panel.add(Box.createRigidArea(new Dimension(44, 0)));
			panel.add(ratioLabel);
			panel.add(this.zoomTextField);
			panel.add(percentLabel);
			
			return panel;
		}
		
		//panjiang
		public JPanel buildinforBarForText()
		{
			this.buildFontFamily();
			JLabel fontLabel = new JLabel("<html><font size=4 color=#ffffff>font:</font>");
			JLabel fontSizeLabel = new JLabel("<html><font size=4 color=#ffffff>font size:</font>");
			this.fontComboBox = new JComboBox<String>(fontFamilyList);
			this.fontComboBox.addActionListener(this);
			this.fontSizeComboBox = new JComboBox<String>(SIZE);
			this.fontSizeComboBox.addActionListener(this);
			boldFontStyle = new JCheckBox("<html><font size=4 color=#ffffff>Bold</font>");
			boldFontStyle.addItemListener(this);
			italicFontStyle = new JCheckBox("<html><font size=4 color=#ffffff>Italic</font>");
			italicFontStyle.setForeground(Color.WHITE);
			italicFontStyle.addItemListener(this);
			
			
			((JLabel) fontSizeComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
			((JTextField) fontSizeComboBox.getEditor().getEditorComponent()).setHorizontalAlignment(JTextField.CENTER);
			fontSizeComboBox.setSelectedIndex(DEFAULT_SIZE_INDEX);
			fontComboBox.setSelectedIndex(defaultFamilyIndex);
			
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.setOpaque(false);
			panel.add(Box.createRigidArea(new Dimension(44, 0)));
			panel.add(fontLabel);
			panel.add(fontComboBox);
			panel.add(ApplicationFrame.this.createVerticalSeparator(2, 32));
			panel.add(fontSizeLabel);
			panel.add(fontSizeComboBox);
			panel.add(ApplicationFrame.this.createVerticalSeparator(2, 32));
			panel.add(boldFontStyle);
			panel.add(italicFontStyle);
			
			return panel;
		}
		
		private void buildFontFamily() 
		{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			fontFamilyList = ge.getAvailableFontFamilyNames();

			defaultFamilyIndex = -1;
			for (int i = 0; i < fontFamilyList.length; i++)
			{
				if (fontFamilyList[i].toLowerCase().equals("serif"))
				{
					defaultFamilyIndex = i;
					break;
				}
			}
			if (defaultFamilyIndex == -1) // not found!
			{
				JOptionPane.showMessageDialog(this, "Default font family ('serif') not found!\n" + "Will use '"
						+ fontFamilyList[0] + "' as default.", "Information Message", JOptionPane.INFORMATION_MESSAGE);
				defaultFamilyIndex = 0;
			}
			defaultFamily = fontFamilyList[defaultFamilyIndex];
			ApplicationFrame.currentFontFamily = defaultFamily;
			ApplicationFrame.currentFontStyle = DEFAULT_STYLE;
			ApplicationFrame.currentFontSize = DEFAULT_SIZE;
			
		}
		
		public void updateInfoBarBasedOnSelectedDrawingMode(DrawMode drawMode)
		{
			CardLayout cl = (CardLayout)(this.getLayout());
			if (drawMode == DrawMode.FREE_DRAW || drawMode == DrawMode.ERASER)
			{
				cl.show(this, "freeDrawing");
			}
			else if (drawMode == DrawMode.ROUND_RECTANGLE)
			{
				cl.show(this, "roundRectangleDrawing");
			}
			else if (drawMode == drawMode.ZOOM_IN || drawMode == drawMode.ZOOM_OUT)
			{
				cl.show(this, "zoom");
			}
			else if (drawMode == drawMode.INPUT_TEXT)
			{
				cl.show(this, "text");
			}
			else if (drawMode == drawMode.POLYGON)
			{
				cl.show(this, "polygon");
			}
			else if (drawMode == drawMode.LINE)
			{
				cl.show(this, "line");
			}
			else if (drawMode == drawMode.EYE_DROPPER || drawMode == drawMode.BUCKET_FILL)
			{
				cl.show(this, "empty");
			}
			else
			{
				cl.show(this, "shapeDrawing");
			}
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			
			if (drawMode == DrawMode.FREE_DRAW || drawMode == DrawMode.ERASER)
			{
				Object source = e.getSource();
				//Update brushSizeField text only when the slider bar is being focused
				if (source == this.brushSizeSlider && source == ApplicationFrame.this.getFocusOwner())
				{
					this.brushSizeField.setText(((JSlider) source).getValue() + "");
					this.updateBrushSize(this.brushSizeSlider.getValue());
				}
			}
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			if ((drawMode == DrawMode.FREE_DRAW || drawMode == DrawMode.ERASER) && this.brushSizeField == ApplicationFrame.this.getFocusOwner())
			{
				String text = this.brushSizeField.getText();
				int value = Integer.parseInt(text);
				this.brushSizeSlider.setValue(value);
				this.updateBrushSize(this.brushSizeSlider.getValue());
			}
			if (e.getDocument() == this.shapeWidthField.getDocument() || e.getDocument() == this.shapeHeightField.getDocument())
			{
				if (!this.shapeWidthField.getText().isEmpty() && !this.shapeHeightField.getText().isEmpty())
				{
					ApplicationFrame.shapeWidth = Integer.parseInt(this.shapeWidthField.getText());
					ApplicationFrame.shapeHeight = Integer.parseInt(this.shapeHeightField.getText());
				}
			}
			else if (e.getDocument() == this.arcWidthField.getDocument() || e.getDocument() == this.arcHeightField.getDocument())
			{
				if (!this.arcWidthField.getText().isEmpty() && !this.arcHeightField.getText().isEmpty())
				{
					ApplicationFrame.currentArcWidth = Integer.parseInt(this.arcWidthField.getText());
					ApplicationFrame.currentArcHeight = Integer.parseInt(this.arcHeightField.getText());
				}
			}
			else if (e.getDocument() == this.polygonSidesField.getDocument())
			{
				if (!this.polygonSidesField.getText().isEmpty())
				{
					ApplicationFrame.polygonSides = Integer.parseInt(this.polygonSidesField.getText());
				}
			}
			else if (e.getDocument() == this.lineWidthField.getDocument())
			{
				if (!this.lineWidthField.getText().isEmpty())
				{
					ApplicationFrame.lineWidth = Integer.parseInt(this.lineWidthField.getText());
				}
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Only allow numeric input above 0 and below the upper limit of the slider
			if ((drawMode == DrawMode.FREE_DRAW || drawMode == DrawMode.ERASER)&& this.brushSizeField == ApplicationFrame.this.getFocusOwner())
			{
				String text = this.brushSizeField.getText();
				if (text.isEmpty())
				{
					this.brushSizeSlider.setValue(1);
				}
				else
				{
					int value = Integer.parseInt(text);
					this.brushSizeSlider.setValue(value);
				}
				this.updateBrushSize(this.brushSizeSlider.getValue());
			}
			if (e.getDocument() == this.shapeWidthField.getDocument() || e.getDocument() == this.shapeHeightField.getDocument())
			{
				if (!this.shapeWidthField.getText().isEmpty() && !this.shapeHeightField.getText().isEmpty())
				{
					ApplicationFrame.shapeWidth = Integer.parseInt(this.shapeWidthField.getText());
					ApplicationFrame.shapeHeight = Integer.parseInt(this.shapeHeightField.getText());
				}
			}
			else if (e.getDocument() == this.arcWidthField.getDocument() || e.getDocument() == this.arcHeightField.getDocument())
			{
				if (!this.arcWidthField.getText().isEmpty() && !this.arcHeightField.getText().isEmpty())
				{
					ApplicationFrame.currentArcWidth = Integer.parseInt(this.arcWidthField.getText());
					ApplicationFrame.currentArcHeight = Integer.parseInt(this.arcHeightField.getText());
				}
			}
			else if (e.getDocument() == this.polygonSidesField.getDocument())
			{
				if (!this.polygonSidesField.getText().isEmpty())
				{
					ApplicationFrame.polygonSides = Integer.parseInt(this.polygonSidesField.getText());
				}
			}
			else if (e.getDocument() == this.lineWidthField.getDocument())
			{
				if (!this.lineWidthField.getText().isEmpty())
				{
					ApplicationFrame.lineWidth = Integer.parseInt(this.lineWidthField.getText());
				}
			}
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub

		}
		
		public void updatePaintPanelBrushWidth(int width)
		{
			for (PaintPanel pp : ApplicationFrame.this.paintPanels)
			{
				pp.getStrokeInfo(width);
			}
		}
		
		public void updateCurrentBrushWidth(int width)
		{
			ApplicationFrame.currentBrushWidth = width;
		}
		
		public void updateBrushSize(int width)
		{
			this.updateCurrentBrushWidth(width);
			this.updatePaintPanelBrushWidth(width);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar();
			if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) 
			{
				this.getToolkit().beep();
				e.consume();
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getSource() == brushSizeField) 
			{
				String s = brushSizeField.getText();
				try {
					if (Integer.parseInt(s) > 100) {
						this.getToolkit().beep();
						e.consume();
						brushSizeField.setText("100");
						JOptionPane.showMessageDialog(mainPanel, "The maximum value allowed is 100!","Error", JOptionPane.ERROR_MESSAGE);	
					}
					else if (Integer.parseInt(s) <= 0) {
						this.getToolkit().beep();
						e.consume();
						brushSizeField.setText("1");
						JOptionPane.showMessageDialog(mainPanel, "The minimum value allowed is 1!","Error", JOptionPane.ERROR_MESSAGE);	
					}
				} catch (NumberFormatException NullPointerException){
					e.consume();
				}
			}
			else if (e.getSource() == lineWidthField) 
			{
				String s = lineWidthField.getText();
				try {
					if (Integer.parseInt(s) > 100) {
						this.getToolkit().beep();
						e.consume();
						lineWidthField.setText("100");
						JOptionPane.showMessageDialog(mainPanel, "The maximum value allowed is 100!","Error", JOptionPane.ERROR_MESSAGE);	
					}
					else if (Integer.parseInt(s) <= 0) {
						this.getToolkit().beep();
						e.consume();
						lineWidthField.setText("1");
						JOptionPane.showMessageDialog(mainPanel, "The minimum value allowed is 1!","Error", JOptionPane.ERROR_MESSAGE);	
					}
				} catch (NumberFormatException NullPointerException){
					e.consume();
				}
			}
			else if (e.getSource() == this.shapeWidthField || e.getSource() == this.shapeHeightField)
			{
				//TODO
			}
			else if (e.getSource() == this.arcWidthField || e.getSource() == this.arcHeightField)
			{
				//TODO
			}
		}

		public void setIsFilledCheckBoxSelected(boolean isSelected)
		{
			this.filledCheckBoxForShape.setSelected(isSelected);
			this.filledCheckBoxForPolygon.setSelected(isSelected);
			this.filledCheckBoxForRoundRect.setSelected(isSelected);
		}
		
		public void setIsSizeFixedCheckBoxSelected(boolean isSelected)
		{
			this.sizeFixedCheckBoxForShape.setSelected(isSelected);
			this.sizeFixedCheckBoxForRoundRect.setSelected(isSelected);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == this.filledCheckBoxForShape)
			{
				ApplicationFrame.isFilled = this.filledCheckBoxForShape.isSelected();
				this.setIsFilledCheckBoxSelected(ApplicationFrame.isFilled);
			}
			else if (e.getSource() == this.filledCheckBoxForRoundRect)
			{
				ApplicationFrame.isFilled = this.filledCheckBoxForRoundRect.isSelected();
				this.setIsFilledCheckBoxSelected(ApplicationFrame.isFilled);
			}
			else if (e.getSource() == this.filledCheckBoxForPolygon)
			{
				ApplicationFrame.isFilled = this.filledCheckBoxForPolygon.isSelected();
				this.setIsFilledCheckBoxSelected(ApplicationFrame.isFilled);
			}
			else if (e.getSource() == this.sizeFixedCheckBoxForShape)
			{
				ApplicationFrame.isSizeFixed = this.sizeFixedCheckBoxForShape.isSelected();
				this.setIsSizeFixedCheckBoxSelected(ApplicationFrame.isSizeFixed);
			}
			else if (e.getSource() == this.sizeFixedCheckBoxForRoundRect)
			{
				ApplicationFrame.isSizeFixed = this.sizeFixedCheckBoxForRoundRect.isSelected();
				this.setIsSizeFixedCheckBoxSelected(ApplicationFrame.isSizeFixed);
			}
			else if (e.getSource() == this.shapeWidthField || e.getSource() == this.shapeHeightField)
			{
				ApplicationFrame.shapeWidth = Integer.parseInt(this.shapeWidthField.getText());
				ApplicationFrame.shapeHeight = Integer.parseInt(this.shapeHeightField.getText());
			}
			else if (e.getSource() == this.fontComboBox)
			{
				ApplicationFrame.currentFontFamily = fontFamilyList[this.fontComboBox.getSelectedIndex()];
				//Check which text is selected by the user, then change its font family
			}
			else if (e.getSource() == this.fontSizeComboBox)
			{
				ApplicationFrame.currentFontSize = Integer.parseInt((String) fontSizeComboBox.getSelectedItem());
				//Check which text is selected by the user, then change its font family
			}
			
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			Object source = e.getSource();

			// 'Style' - check if the font style was change via a checkbox
			if (source == italicFontStyle)
			{
				if (italicFontStyle.isSelected())
				{
					ApplicationFrame.currentFontStyle = ApplicationFrame.currentFontStyle | Font.ITALIC;
				}
				else
				{
					ApplicationFrame.currentFontStyle = ApplicationFrame.currentFontStyle & ~Font.ITALIC;
				}
			} else if (source == boldFontStyle)
			{
				if (boldFontStyle.isSelected())
				{
					ApplicationFrame.currentFontStyle = ApplicationFrame.currentFontStyle | Font.BOLD;
				}
				else
				{
					ApplicationFrame.currentFontStyle = ApplicationFrame.currentFontStyle & ~Font.BOLD;
				}
			}
			else if (source == this.dashedLineCheckBox)
			{
				ApplicationFrame.dashedLine = this.dashedLineCheckBox.isSelected();
			}
			
		}
		
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			JTextField source = (JTextField)e.getSource();
			
			if (e.getSource() == this.polygonSidesField)
			{
				String s = polygonSidesField.getText();
				
				try {
					if (Integer.parseInt(s) > 100) {
						this.getToolkit().beep();
						//e.consume();
						polygonSidesField.setText("100");
						JOptionPane.showMessageDialog(mainPanel, "The maximum number of slides is 100!", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else if (Integer.parseInt(s) < 3) 
					{
						//polygonSidesField.setText("3");
						this.getToolkit().beep();
						//e.consume();
						polygonSidesField.setText("3");
						JOptionPane.showMessageDialog(mainPanel, "The minimum number of slides is 3!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException ne){
					;
				} catch (NullPointerException ne) {
					;
				}
			}
			
		}
	}

	
	
	/*********************************************************************************************************************
	 * Popup Menu inner class                                                                                              *
	 *********************************************************************************************************************/
	
	class PopupMenu extends JPopupMenu
	{
		private JMenuItem item1;
		
		PopupMenu()
		{
			this.createJMenuItems();
			this.add(item1);
		}
		
		public void createJMenuItems()
		{
			item1 = new JMenuItem("Item 1");
		}
	}
	
	/*********************************************************************************************************************
	 * Paint panel inner class                                                                                              *
	 *********************************************************************************************************************/
	
	/*A panel where all the painting happens.*/
	class PaintPanel extends JLayeredPane
	{
		private static final long serialVersionUID = 1L;
		public BufferedImage paintImage;
		public Graphics2D myGraphics;
		
		ArrayList<Shape> shapes = new ArrayList<Shape>(); //An ArrayList that stores drawn shapes
		public ArrayList<Image> history = new ArrayList<Image>(); //Stores all the graphics
		
		private DrawMode drawMode;
		
		private Point[] strokePoints = new Point[2];
		Paint paint;
		Point dragStartPoint; 
		Point dragEndPoint;
		
		public int paintWidth;
		public int paintHeight;
		private final int SIZE_INCREMENT = 50;
		private int xOffset;
		private int yOffset;
		
		public Rectangle clip;
		
		/** Construct a paintpanel.
		 *  @param width the width of the panel
		 *  @param height the height of the panel
		 *  */
		PaintPanel(){}
		
		PaintPanel(int width, int height)
		{
			this.setFocusable(true);
			this.paintWidth = width;
			this.paintHeight = height;
			this.initializeHistory(width, height);
			this.paintImage = new BufferedImage(this.paintWidth, this.paintHeight, BufferedImage.TYPE_INT_RGB);
			this.setUpPaintGraphic();
			this.setPreferredSize(new Dimension(this.paintWidth, this.paintHeight));
			this.setMinimumSize(new Dimension(this.paintWidth, this.paintHeight));
			this.setMaximumSize(new Dimension(this.paintWidth, this.paintHeight));
			this.setUpMouseListener();
			this.setUpMouseMotionListener();
			this.setUpTextInput();
			this.setBackground(Color.WHITE);
			this.xOffset = 0;
			this.yOffset = 0;
		}
		
		public void initializeHistory(int imageWidth, int imageHeight)
		{
			Image unpaintedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB); //Stores an empty image
			Graphics2D emptyGraphics = (Graphics2D) unpaintedImage.getGraphics();
			emptyGraphics.setColor(Color.WHITE);
			emptyGraphics.fillRect(0, 0, imageWidth, imageHeight);
			this.history.add(unpaintedImage);
		}
		
		public void getStrokeInfo(int width)
		{
			ApplicationFrame.inkStroke = new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		}
		
		public void setUpPaintGraphic()
		{
			this.myGraphics = (Graphics2D) this.paintImage.createGraphics();
			this.myGraphics.setColor(Color.WHITE);
			this.myGraphics.fillRect(0, 0, this.paintImage.getWidth(), this.paintImage.getHeight());
		}
		
		public void setShapeToDraw(DrawMode shapeToDraw)
		{
			this.drawMode = shapeToDraw;
		}
		
		public void zoomIn()
		{
			this.updatePreferredSize(1);
		}
		
		public void zoomOut()
		{
			this.updatePreferredSize(-1);
		}
		
		public void updatePreferredSize(int n)
		{
			this.paintWidth = this.getWidth() + n * this.SIZE_INCREMENT;
			this.paintHeight = this.getHeight() + n * this.SIZE_INCREMENT;
			//Resize the paint panel
			this.setPreferredSize(new Dimension(this.paintWidth, this.paintHeight));
			
			//Also resize the BufferedImage
			this.paintImage = new BufferedImage(this.paintWidth, this.paintHeight, BufferedImage.TYPE_INT_RGB);
			this.setUpPaintGraphic();
			
			this.revalidate();
			this.repaint();
		}
		
		public void setUpTextInput()
		{
			InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
		    ActionMap am = getActionMap();
		    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");
		    am.put("cancel", new AbstractAction() {
				private static final long serialVersionUID = 1L;
				@Override
		        public void actionPerformed(ActionEvent e) {
		            Component focusOwner = FocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		            if (focusOwner instanceof OverlayEditor) {
		            	System.out.println("it is being focused");
		                remove(focusOwner);
		                invalidate();
		                repaint();
		            }
		        }

		    });
		}
		
		public void setUpMouseListener()
		{
			this.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e) 
				{
					//Create and set up a new paintImage to be painted by the user.

					PaintPanel.this.paintImage = new BufferedImage(PaintPanel.this.paintWidth, PaintPanel.this.paintHeight, BufferedImage.TYPE_INT_RGB);
					PaintPanel.this.setUpPaintGraphic();
					PaintPanel.this.requestFocus();
					
					System.out.println("draw mode is " + drawMode);
					
					dragStartPoint = new Point(e.getX(), e.getY());
					dragEndPoint = dragStartPoint;
					if  (PaintPanel.this.drawMode == DrawMode.FREE_DRAW || PaintPanel.this.drawMode == DrawMode.ERASER)
					{
						if (!history.isEmpty())
						{
							//Redraw old drawing onto the graphics
							drawOldImage();
						}
						if (PaintPanel.this.drawMode == DrawMode.FREE_DRAW)
						{
							PaintPanel.this.freeDraw(dragStartPoint.x, dragStartPoint.y, dragEndPoint.x, dragEndPoint.y);
						}
						else if (PaintPanel.this.drawMode == DrawMode.ERASER)
						{
							PaintPanel.this.erase(dragStartPoint.x, dragStartPoint.y, dragEndPoint.x, dragEndPoint.y);
						}
						repaint();
					}
					else if (PaintPanel.this.drawMode == DrawMode.ZOOM_IN)
					{
						PaintPanel.this.zoomIn();
						ApplicationFrame.currentZoomRatio = ApplicationFrame.this.calculateZoomRatio();
						ApplicationFrame.this.infoBar.zoomTextField.setText(ApplicationFrame.currentZoomRatio + "");
					}
					else if (PaintPanel.this.drawMode == DrawMode.ZOOM_OUT)
					{
						PaintPanel.this.zoomOut();
						ApplicationFrame.currentZoomRatio = ApplicationFrame.this.calculateZoomRatio();
						ApplicationFrame.this.infoBar.zoomTextField.setText(ApplicationFrame.currentZoomRatio + "");
					}
					else if (PaintPanel.this.drawMode == DrawMode.BUCKET_FILL)
					{
						//Redraw old drawing onto the graphics
						drawOldImage();
						Color targetColor = new Color(paintImage.getRGB(e.getX(), e.getY()));
						PaintPanel.this.floodFill2(paintImage, dragStartPoint, targetColor.getRGB(), chosenPaintColor.getRGB());
					}
					else if (PaintPanel.this.drawMode == DrawMode.EYE_DROPPER)
					{
						//Redraw old drawing onto the graphics
						drawOldImage();
						chosenPaintColor = PaintPanel.this.eyedropper(paintImage, e.getX(), e.getY());
						colorChooserBtn.setBackground(chosenPaintColor);
					}
				}
				
				public void mouseReleased(MouseEvent e)
				{
					
					if (PaintPanel.this.drawMode == DrawMode.ZOOM_IN || PaintPanel.this.drawMode == DrawMode.ZOOM_OUT)
					{
					}
					else if (PaintPanel.this.drawMode == DrawMode.INPUT_TEXT)
					{
					}
					else if (PaintPanel.this.drawMode == DrawMode.EYE_DROPPER)
					{
					}
					else
					{
						if (PaintPanel.this.drawMode == DrawMode.FREE_DRAW || PaintPanel.this.drawMode == DrawMode.ERASER)
						{
							PaintPanel.this.shapes.clear();
						}
						else if (PaintPanel.this.drawMode == DrawMode.BUCKET_FILL)
						{
							repaint();
						}
						else
						{
							drawOldImage();
							PaintPanel.this.dragAndDrawShape(myGraphics, isFilled, chosenPaintColor, isSizeFixed, shapeWidth, shapeHeight);
						}
						
						//Save the paintImage in history
						PaintPanel.this.history.add(PaintPanel.this.paintImage);
						ApplicationFrame.changeSaved = false;
					}
					
					dragStartPoint = null;
					dragEndPoint = null;
					
					if (history.size() > 1)
					{
						ApplicationFrame.this.undo.setEnabled(true);
					}
				}
				
				@Override
		        public void mouseClicked(MouseEvent e) 
				{
					if (PaintPanel.this.drawMode == DrawMode.INPUT_TEXT)
					{
						ApplicationFrame.changeSaved = false;
			            Component focusOwner = FocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
			            if (!(focusOwner instanceof OverlayEditor)) {
			                OverlayEditor field = new OverlayEditor();
			                ApplicationFrame.insertedTextList.add(field);
			                field.setLocation(e.getPoint());
			                add(field);
			                invalidate();
			                repaint();
			                field.requestFocusInWindow();
			            } else {
			                requestFocusInWindow();
			            }
					}
		        }
			});
		}
		
		public void setUpMouseMotionListener()
		{
			this.addMouseMotionListener(new MouseMotionAdapter()
			{
				public void mouseDragged(MouseEvent e)
				{
					dragEndPoint = new Point(e.getX(), e.getY());
					if (PaintPanel.this.drawMode == DrawMode.ZOOM_IN || PaintPanel.this.drawMode == DrawMode.ZOOM_OUT)
					{
					}
					else if (PaintPanel.this.drawMode == DrawMode.INPUT_TEXT || PaintPanel.this.drawMode == DrawMode.BUCKET_FILL || PaintPanel.this.drawMode == DrawMode.EYE_DROPPER)
					{
					}
					else
					{
						if (PaintPanel.this.drawMode == DrawMode.FREE_DRAW || PaintPanel.this.drawMode == DrawMode.ERASER)
						{
							if (!history.isEmpty())
							{
								//Redraw old drawing onto the graphics
								drawOldImage();
							}
							if (PaintPanel.this.drawMode == DrawMode.FREE_DRAW)
							{
								PaintPanel.this.freeDraw(dragStartPoint.x, dragStartPoint.y, dragEndPoint.x, dragEndPoint.y);
							}
							else if (PaintPanel.this.drawMode == DrawMode.ERASER)
							{
								PaintPanel.this.erase(dragStartPoint.x, dragStartPoint.y, dragEndPoint.x, dragEndPoint.y);
							}
							dragStartPoint = dragEndPoint;
						}
						else
						{
							PaintPanel.this.myGraphics.setColor(Color.WHITE);
							PaintPanel.this.myGraphics.fillRect(0, 0, paintImage.getWidth(), paintImage.getHeight());
							
							if (!history.isEmpty())
							{
								//Redraw old drawing onto the graphics
								drawOldImage();
							}
							PaintPanel.this.dragAndDrawShape(myGraphics, isFilled, chosenPaintColor, isSizeFixed, shapeWidth, shapeHeight);
						}
						repaint();
					}
				}
			}
			);
		}       
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			if (this.drawMode == DrawMode.ZOOM_IN || PaintPanel.this.drawMode == DrawMode.ZOOM_OUT || PaintPanel.this.drawMode == DrawMode.INPUT_TEXT || ApplicationFrame.currentlyPressedMenuItem == ApplicationFrame.this.menuBar.zoomIn ||ApplicationFrame.currentlyPressedMenuItem == ApplicationFrame.this.menuBar.zoomOut)
			{
				Image image = this.history.get(history.size() - 1);
				g.drawImage(image, 0, 0, this.paintWidth, this.paintHeight, null);
				ApplicationFrame.currentlyPressedMenuItem = null;
			}
			else
			{
				g.drawImage(this.paintImage, 0, 0, this.paintWidth, this.paintHeight, null);
			}
		}
		
		public void drawOldImage()
		{
			myGraphics.drawImage((BufferedImage) history.get(history.size() - 1), 0, 0, paintWidth, paintHeight, null);
		}
		
		/**Free drawing of lines*/
		public void freeDraw(int x1, int y1, int x2, int y2)
		{
			this.myGraphics.setColor(chosenPaintColor);
			this.myGraphics.setStroke(inkStroke);
			for (Shape oldLine : shapes)
			{
				this.myGraphics.draw(oldLine);
			}
			Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
			this.myGraphics.draw(line);
			this.shapes.add(line);
		}
		
		public void erase(int x1, int y1, int x2, int y2)
		{
			this.myGraphics.setColor(Color.WHITE);
			this.myGraphics.setStroke(inkStroke);
			for (Shape oldLine : shapes)
			{
				this.myGraphics.draw(oldLine);
			}
			Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
			this.myGraphics.draw(line);
			this.shapes.add(line);
		}
		
			
		/**Drag and draw the selected shape.*/
		//TODO
	    public void dragAndDrawShape(Graphics2D g, boolean isFilled, Color color, boolean isSizeFixed, int width, int height) {
	        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	        g.setStroke(new BasicStroke(1));
	        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	        if (dragStartPoint != null && dragEndPoint != null) 
	        {
	        	Shape shape = null;
	        	if (isFilled)
	        	{
	        		g.setColor(Color.BLACK);
	        	}
	        	
	        	if (isSizeFixed && drawMode != DrawMode.LINE && drawMode != DrawMode.POLYGON)
	        	{
	        		shape = this.createFixedSizeShapeBasedOnSelectedShape(dragEndPoint.x - width, dragEndPoint.y - height, width, height);
	        	}
	        	else
	        	{
	        		if (drawMode == DrawMode.LINE )
	        		{
	        			int strokeWidth = ApplicationFrame.lineWidth;
	        			g.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
	        			if (ApplicationFrame.dashedLine == true)
	        			{
	        				float[] dash = new float[]{4, 4};
		        	        g.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));
	        			}
	        		}
	        		shape = this.createShapeBasedOnSelectedShape(dragStartPoint.x, dragStartPoint.y, dragEndPoint.x, dragEndPoint.y);
	        	}
	        	
	        	g.setColor(color);
	            g.draw(shape);
	            
	            if (isFilled)
	        	{
	        		g.setColor(color);
	        		g.fill(shape);
	        	}
	        }
	    }

	    
	    public void dragAndDrawDashedShape(Graphics2D g)
	    {
	    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    	float[] dash = new float[]{4, 4};
	        g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));
	        g.setColor(Color.BLACK);
	        Shape shape = this.createShapeBasedOnSelectedShape(dragStartPoint.x, dragStartPoint.y, dragEndPoint.x, dragEndPoint.y);
	        g.draw(shape);
	    }
	    
	    public Shape createFixedSizeShapeBasedOnSelectedShape(int x1, int y1, int width, int height)
	    {
	    	if (drawMode == DrawMode.RECTANGLE)
	    	{
	    		return new Rectangle2D.Double(x1, y1, width, height);
	    	}
	    	else if  (drawMode == DrawMode.ELLIPSE)
	    	{
	    		return new Ellipse2D.Double(x1, y1, width, height);
	    	}
	    	else if  (drawMode == DrawMode.ROUND_RECTANGLE)
	    	{
	    		return new RoundRectangle2D.Double(x1, y1, width, height, ApplicationFrame.currentArcWidth, ApplicationFrame.currentArcHeight);
	    	}
	    	else if (drawMode == DrawMode.LINE)
	    	{
	    		return null;
	    	}
	    	else
	    	{
	    		return null;
	    	}
	    }
	    
	    /* Create the shape based on the shape selected. */
	    public Shape createShapeBasedOnSelectedShape(int x1, int y1, int x2, int y2)
	    {
	    	if (drawMode == DrawMode.RECTANGLE)
	    	{
	    		return makeRectangle(x1, y1, x2, y2);
	    	}
	    	else if  (drawMode == DrawMode.ELLIPSE)
	    	{
	    		return makeEllipse(x1, y1, x2, y2);
	    	}
	    	else if  (drawMode == DrawMode.ROUND_RECTANGLE)
	    	{
	    		return makeRoundedRectangle(x1, y1, x2, y2, ApplicationFrame.currentArcWidth, ApplicationFrame.currentArcHeight);
	    	}
	    	else if (drawMode == DrawMode.LINE)
	    	{
		    	return makeLine(x1, y1, x2, y2);
	    	}
	    	else if (drawMode == DrawMode.POLYGON)
	    	{
	    		return makePolygon(x1, y1, x2, y2, ApplicationFrame.polygonSides);
	    	}
	    	else
	    	{
	    		return null;
	    	}
	    }
		
		private Rectangle2D.Double makeRectangle(int x1, int y1, int x2, int y2)
		{
			return new Rectangle2D.Double(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
		}
		
		private Ellipse2D.Double makeEllipse(int x1, int y1, int x2, int y2)
		{
			return new Ellipse2D.Double(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
		}
		
		private Line2D.Double makeLine(int x1, int y1, int x2, int y2)
		{
			return new Line2D.Double(x1, y1, x2, y2);
		}
		
		private RoundRectangle2D.Double makeRoundedRectangle(int x1, int y1, int x2, int y2, int arcWidth, int arcHeight)
		{
			return new RoundRectangle2D.Double(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2), arcWidth, arcHeight);
		}
		
		private Polygon makePolygon(int x1, int y1, int x2, int y2, int vertex) 
		{
			System.out.printf("x1 is %d, y1 is %d\n", x1, y1);
				int	side = 2 * Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));
				int	xCtr = x1;
				int	yCtr = y1;
				Polygon	poly = new Polygon();
				double	angle = 0,
					delAngle = 2 * Math.PI / vertex;
				if (vertex % 2 != 0)
				{
					angle = delAngle / 4;
					if (vertex == 3)
					{
						angle = -angle;
					}
				}

				for ( int i = 0; i < vertex; i++, angle += delAngle )
				{
//					System.out.printf("x is %d, y is %d\n", xCtr + ( int )( side * Math.cos( angle ) ), yCtr - ( int )( side * Math.sin( angle ) ) );
				    poly.addPoint( xCtr + ( int )( side * Math.cos( angle ) ),
						   yCtr - ( int )( side * Math.sin( angle ) ) );
				}
				return poly;
		 }
		
		public void floodFill2(BufferedImage image, Point node, int targetColor, int replacementColor) 
		{
		    int width = image.getWidth();
		    int height = image.getHeight();
		    int target = targetColor;
		    int replacement = replacementColor;
		    if (target != replacement) {
		        Queue<Point> queue = new LinkedList<Point>();
		        do {
		            int x = node.x;
		            int y = node.y;
		            while (x > 0 && image.getRGB(x - 1, y) == target) {
		                x--;
		            }
		            boolean spanUp = false;
		            boolean spanDown = false;
		            while (x < width && image.getRGB(x, y) == target) {
		                image.setRGB(x, y, replacement);
		                if (!spanUp && y > 0 && image.getRGB(x, y - 1) == target) {
		                    queue.add(new Point(x, y - 1));
		                    spanUp = true;
		                } else if (spanUp && y > 0
		                        && image.getRGB(x, y - 1) != target) {
		                    spanUp = false;
		                }
		                if (!spanDown && y < height - 1
		                        && image.getRGB(x, y + 1) == target) {
		                    queue.add(new Point(x, y + 1));
		                    spanDown = true;
		                } else if (spanDown && y < height - 1
		                        && image.getRGB(x, y + 1) != target) {
		                    spanDown = false;
		                }
		                x++;
		            }
		        } while ((node = queue.poll()) != null);
		    }
		}
		
		/** Pick a color from the picture at location x and y. */
		public Color eyedropper(BufferedImage image, int x, int y)
		{
			return new Color(image.getRGB(x, y));
		}
		
		public void setClip(Point p1, Point p2)
		{
			clip = new Rectangle();
			clip.setFrameFromDiagonal(p1, p2);
			repaint();
		}
		
		class OverlayEditor extends JTextArea 
		{

			private static final long serialVersionUID = 1L;

			public OverlayEditor() {
	            super(1, 10);
	            setBorder(null);
	            setForeground(ApplicationFrame.this.chosenPaintColor);
	            setOpaque(false);
	            setSize(getPreferredSize());
	            
	            //Obtain the current font information set by the user
	            setFont(new Font(ApplicationFrame.this.currentFontFamily, ApplicationFrame.this.currentFontStyle, ApplicationFrame.this.currentFontSize));
	            //Still need to make it better  pan
	            
	            setBackground(new Color(0, 0, 0, 0));

	            getDocument().addDocumentListener(new DocumentListener() {
	                public void update() {
	                    setSize(getPreferredSize());
	                }

	                @Override
	                public void insertUpdate(DocumentEvent e) {
	                    update();
	                }

	                @Override
	                public void removeUpdate(DocumentEvent e) {
	                    update();
	                }

	                @Override
	                public void changedUpdate(DocumentEvent e) {
	                    update();
	                }

	            });

	            addFocusListener(new FocusListener() {
	                @Override
	                public void focusGained(FocusEvent e) {
	                    setBorder(new LineBorder(Color.WHITE));
	                    repaint();
	                }

	                @Override
	                public void focusLost(FocusEvent e) {
	                    setBorder(null);
	                    repaint();
	                }

	            });
	        }

	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            Graphics2D g2d = (Graphics2D) g.create();
	            if (hasFocus()) {
	                g2d.setColor(new Color(0, 0, 0, 25));
	                g2d.fill(new Rectangle(getWidth(), getHeight()));
	            }
	            g2d.dispose();
	        }

	    }
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO 
		if (ApplicationFrame.changeSaved == false)
		{
			int returnState = this.menuBar.saveBeforeExit();
			
			if (returnState == JOptionPane.YES_OPTION)
			{
				this.menuBar.saveAsImage();
			}
			else if (returnState == JOptionPane.NO_OPTION)
			{
			}
			System.exit(0);
		}
		else
		{
			System.exit(0);
		}
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}


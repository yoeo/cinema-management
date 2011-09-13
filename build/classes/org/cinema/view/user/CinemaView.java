/*
 * CinemaView.java
 */
package org.cinema.view.user;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.cinema.dao.film.FilmsDao;
import org.cinema.dao.film.FilmsInfo;
import org.cinema.dao.seance.SeancesDao;
import org.cinema.dao.seance.SeancesInfo;

/**
 * The application's main frame.
 */
public class CinemaView extends FrameView
{

	private List<FilmsInfo> films;
	private FilmElement[] fElements;

	private JPanel tabSeance = null;
	private FilmDetail fDetail = null;

	public CinemaView(SingleFrameApplication app)
	{
		super(app);

		initComponents();

		// status bar initialization - message timeout, idle icon and busy animation, etc
		ResourceMap resourceMap = getResourceMap();
		int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
		messageTimer = new Timer(messageTimeout, new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				statusMessageLabel.setText("");
			}
		});
		messageTimer.setRepeats(false);
		int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
		for (int i = 0; i < busyIcons.length; i++)
		{
			busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
		}
		busyIconTimer = new Timer(busyAnimationRate, new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
				statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
			}
		});
		idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
		statusAnimationLabel.setIcon(idleIcon);
		progressBar.setVisible(false);

		// connecting action tasks to status bar via TaskMonitor
		TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
		taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener()
		{

			public void propertyChange(java.beans.PropertyChangeEvent evt)
			{
				String propertyName = evt.getPropertyName();
				if ("started".equals(propertyName))
				{
					if (!busyIconTimer.isRunning())
					{
						statusAnimationLabel.setIcon(busyIcons[0]);
						busyIconIndex = 0;
						busyIconTimer.start();
					}
					progressBar.setVisible(true);
					progressBar.setIndeterminate(true);
				}
				else if ("done".equals(propertyName))
				{
					busyIconTimer.stop();
					statusAnimationLabel.setIcon(idleIcon);
					progressBar.setVisible(false);
					progressBar.setValue(0);
				}
				else if ("message".equals(propertyName))
				{
					String text = (String) (evt.getNewValue());
					statusMessageLabel.setText((text == null) ? "" : text);
					messageTimer.restart();
				}
				else if ("progress".equals(propertyName))
				{
					int value = (Integer) (evt.getNewValue());
					progressBar.setVisible(true);
					progressBar.setIndeterminate(false);
					progressBar.setValue(value);
				}
			}
		});

		SwingUtilities.invokeLater(new Runnable() {

			public void run()
			{
				initPanelFilms();
				initPanelSeances();
				initPanelFilmsDetail();
				afficherFilmSemaine();
			}
		});
	}

	@Action
	public void showAboutBox()
	{
		if (aboutBox == null)
		{
			JFrame mainFrame = CinemaApp.getApplication().getMainFrame();
			aboutBox = new CinemaAboutBox(mainFrame);
			aboutBox.setLocationRelativeTo(mainFrame);
		}
		CinemaApp.getApplication().show(aboutBox);
	}

	/*
	 * CINEMA Fonctions ajoutées
	 */
	private void initPanelFilms ()
	{
		int panelMaxY = 2;
		int panelMaxX = 3;
		int decalage = 5;

		int x = decalage;
		int y = decalage;

		fElements = new FilmElement[panelMaxX * panelMaxY];
		FilmElement e = new FilmElement();
		int pwidth = e.getPreferredSize().width;
		int pheight = e.getPreferredSize().height;

		for (int j = 0; j < panelMaxY; j++)
		{
			for (int i = 0; i < panelMaxX; i++)
			{
				e = new FilmElement();

				e.setBounds(new Rectangle(x, y, pwidth, pheight));
				e.setVisible(false);
				tabFilms.add(e);
				fElements[j * panelMaxX + i] = e;

				x += decalage + pwidth;
			}
			x = decalage;
			y += decalage + pheight;
		}
	}

	private void initPanelSeances ()
	{
		tabSeance = new JPanel();
        javax.swing.GroupLayout tabSeanceLayout = new javax.swing.GroupLayout(tabSeance);
        tabSeance.setLayout(tabSeanceLayout);
        tabSeanceLayout.setHorizontalGroup(
            tabSeanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 997, Short.MAX_VALUE)
        );
        tabSeanceLayout.setVerticalGroup(
            tabSeanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 356, Short.MAX_VALUE)
        );
		List<SeancesInfo> listSeance = new SeancesDao().weekSeance();
		int panelMaxY = listSeance.size();
		int decalage = 5;

		int x = decalage;
		int y = decalage;

		SeanceElement e = new SeanceElement();

		int pwidth = e.getPreferredSize().width;
		int pheight = e.getPreferredSize().height;

		for (int j = 0; j < panelMaxY; j++)
		{
			e = new SeanceElement();

			Date d = new Date(System.currentTimeMillis());
			Time t = new Time(System.currentTimeMillis());

			e.setSeance(listSeance.get(j));

			e.setBounds(new Rectangle(x, y, pwidth, pheight));
			e.setVisible(true);
			tabSeance.add(e);

			y += decalage + pheight;
		}
		tabSeance.setPreferredSize(new Dimension(pwidth, y));
		tabSeance.validate();
		tabSeance.repaint();

		scroll.setViewportView(tabSeance);
	}

	private void initPanelFilmsDetail ()
	{
		try
		{
			fDetail = new FilmDetail();
			//fDetail.setPreferredSize(tabFilms.getPreferredSize());
			fDetail.setVisible(true);
		}
		catch (InstantiationException ex)
		{
			Logger.getLogger(CinemaView.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch (IllegalAccessException ex)
		{
			Logger.getLogger(CinemaView.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void afficherFilmSemaine ()
	{
		scroll.setViewportView(tabFilms);
		pages.setVisible(true);
		message.setText("Les films à l'affiche cette semaine (Page " + (pages.getValue() + 1) + ")");

		addFilms(new FilmsDao().weekFilm());
	}

	private void afficherFilm2Mois ()
	{
		scroll.setViewportView(tabFilms);
		pages.setVisible(true);
		message.setText("Les films à l'affiche dans les 2 mois (Page " + (pages.getValue() + 1) + ")");

		addFilms(new FilmsDao().monthFilm());
	}

	private void afficherSeanceSemaine ()
	{
		scroll.setViewportView(tabSeance);
		pages.setVisible(false);
		message.setText("Les séances de la semaine");

		actualiserSeance (new SeancesDao().weekSeance());
	}

	private void actualiserSeance(List<SeancesInfo> weekSeance)
	{
	}

	private void afficherPage()
	{
		int nbrCases = fElements.length;
		int nbrFilms = films.size();

		int min = pages.getValue() * nbrCases;
		int max = Math.min(min + nbrCases, nbrFilms);

		for (int i = 0; i < nbrCases; i++)
		{
			FilmElement e = fElements[i];
			if (i + min < max)
			{
				final FilmsInfo fi = films.get(i + min);
				e.setFilm(fi);
				e.setClick(new Runnable() {

					public void run()
					{
						afficherDetail(fi);
					}
				});
				e.setVisible(true);
			}
			else
			{
				e.setVisible(false);
			}
		}
		tabFilms.validate();
		tabFilms.repaint();
		String txt = message.getText().split("\\(")[0];
		message.setText(txt + "(Page " + (pages.getValue() + 1) + ")");
	}

	private void afficherDetail (FilmsInfo fi)
	{
		fDetail.setFilm(fi);
		pages.setVisible(false);
		scroll.setViewportView(fDetail);
		if (message.getText().contains("semaine"))
		{
			fDetail.setClick(new Runnable() {

				public void run()
				{
					scroll.setViewportView(tabFilms);
					pages.setVisible(true);
					message.setText("Les films à l'affiche cette semaine (Page " + (pages.getValue() + 1) + ")");
				}
			});
		}
		else
		{
			fDetail.setClick(new Runnable() {

				public void run()
				{
					scroll.setViewportView(tabFilms);
					pages.setVisible(true);
					message.setText("Les films à l'affiche cette semaine (Page " + (pages.getValue() + 1) + ")");
				}
			});
		}
		message.setText("Détails du film : " + fi.getTitre());
	}

	private void addFilms(List<FilmsInfo> listeFilm)
	{
		films = listeFilm;
		pages.setMaximum((films.size() - 1) / fElements.length);
		if (pages.getValue() == 0)
		{
			afficherPage();
		}
		else
		{
			pages.setValue(0);
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        panelAction = new javax.swing.JPanel();
        filmsSemaine = new javax.swing.JButton();
        film2mois = new javax.swing.JButton();
        seanceSemaine = new javax.swing.JButton();
        carteAbonne = new javax.swing.JButton();
        scroll = new javax.swing.JScrollPane();
        tabFilms = new javax.swing.JPanel();
        message = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        pages = new javax.swing.JSlider();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.cinema.view.user.CinemaApp.class).getContext().getResourceMap(CinemaView.class);
        panelAction.setBackground(resourceMap.getColor("panelAction.background")); // NOI18N
        panelAction.setName("panelAction"); // NOI18N

        filmsSemaine.setText(resourceMap.getString("filmsSemaine.text")); // NOI18N
        filmsSemaine.setName("filmsSemaine"); // NOI18N
        filmsSemaine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filmsSemaineActionPerformed(evt);
            }
        });

        film2mois.setText(resourceMap.getString("film2mois.text")); // NOI18N
        film2mois.setName("film2mois"); // NOI18N
        film2mois.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                film2moisActionPerformed(evt);
            }
        });

        seanceSemaine.setText(resourceMap.getString("seanceSemaine.text")); // NOI18N
        seanceSemaine.setName("seanceSemaine"); // NOI18N
        seanceSemaine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seanceSemaineActionPerformed(evt);
            }
        });

        carteAbonne.setText(resourceMap.getString("carteAbonne.text")); // NOI18N
        carteAbonne.setAutoscrolls(true);
        carteAbonne.setName("carteAbonne"); // NOI18N
        carteAbonne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carteAbonneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelActionLayout = new javax.swing.GroupLayout(panelAction);
        panelAction.setLayout(panelActionLayout);
        panelActionLayout.setHorizontalGroup(
            panelActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelActionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filmsSemaine)
                .addGap(18, 18, 18)
                .addComponent(film2mois)
                .addGap(18, 18, 18)
                .addComponent(seanceSemaine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 290, Short.MAX_VALUE)
                .addComponent(carteAbonne)
                .addContainerGap())
        );
        panelActionLayout.setVerticalGroup(
            panelActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelActionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filmsSemaine)
                    .addComponent(film2mois)
                    .addComponent(seanceSemaine)
                    .addComponent(carteAbonne))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        scroll.setBackground(resourceMap.getColor("scroll.background")); // NOI18N
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setAutoscrolls(true);
        scroll.setName("scroll"); // NOI18N
        scroll.setPreferredSize(new java.awt.Dimension(1000, 355));

        tabFilms.setBackground(resourceMap.getColor("tabFilms.background")); // NOI18N
        tabFilms.setName("tabFilms"); // NOI18N
        tabFilms.setRequestFocusEnabled(false);

        javax.swing.GroupLayout tabFilmsLayout = new javax.swing.GroupLayout(tabFilms);
        tabFilms.setLayout(tabFilmsLayout);
        tabFilmsLayout.setHorizontalGroup(
            tabFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 996, Short.MAX_VALUE)
        );
        tabFilmsLayout.setVerticalGroup(
            tabFilmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 362, Short.MAX_VALUE)
        );

        scroll.setViewportView(tabFilms);

        message.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        message.setText(resourceMap.getString("message.text")); // NOI18N
        message.setName("message"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        pages.setMaximum(1);
        pages.setValue(0);
        pages.setName("pages"); // NOI18N
        pages.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pagesStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pages, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scroll, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 928, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(message, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelAction, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, 0, 33, Short.MAX_VALUE)
                    .addComponent(message, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(panelAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuBar.setFont(resourceMap.getFont("menuBar.font")); // NOI18N
        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setFont(resourceMap.getFont("fileMenu.font")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.cinema.view.user.CinemaApp.class).getContext().getActionMap(CinemaView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setFont(resourceMap.getFont("exitMenuItem.font")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setFont(resourceMap.getFont("fileMenu.font")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setFont(resourceMap.getFont("aboutMenuItem.font")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 948, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 778, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

	private void seanceSemaineActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_seanceSemaineActionPerformed
	{//GEN-HEADEREND:event_seanceSemaineActionPerformed
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				afficherSeanceSemaine();
			}
		});

	}//GEN-LAST:event_seanceSemaineActionPerformed

	private void filmsSemaineActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_filmsSemaineActionPerformed
	{//GEN-HEADEREND:event_filmsSemaineActionPerformed
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				afficherFilmSemaine();
			}
		});
	}//GEN-LAST:event_filmsSemaineActionPerformed

	private void pagesStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_pagesStateChanged
	{//GEN-HEADEREND:event_pagesStateChanged
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				afficherPage();
			}
		});

	}//GEN-LAST:event_pagesStateChanged

	private void film2moisActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_film2moisActionPerformed
	{//GEN-HEADEREND:event_film2moisActionPerformed
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				afficherFilm2Mois();
			}
		});
	}//GEN-LAST:event_film2moisActionPerformed

	private void carteAbonneActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_carteAbonneActionPerformed
	{//GEN-HEADEREND:event_carteAbonneActionPerformed
	  JFrame achat = new JFrame();
	  achat.getContentPane().add(new achat_carte(achat));
	  achat.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	  achat.pack();
	  achat.setVisible(true);
	}//GEN-LAST:event_carteAbonneActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton carteAbonne;
    private javax.swing.JButton film2mois;
    private javax.swing.JButton filmsSemaine;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel message;
    private javax.swing.JSlider pages;
    private javax.swing.JPanel panelAction;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JButton seanceSemaine;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JPanel tabFilms;
    // End of variables declaration//GEN-END:variables
	private final Timer messageTimer;
	private final Timer busyIconTimer;
	private final Icon idleIcon;
	private final Icon[] busyIcons = new Icon[15];
	private int busyIconIndex = 0;
	private JDialog aboutBox;

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FilmElement.java
 *
 * Created on 18 janv. 2011, 13:54:18
 */
package org.cinema.view.user;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.cinema.dao.film.FilmsInfo;

/**
 *
 * @author ydeo
 */
public class FilmElement extends javax.swing.JPanel
{

	FilmsInfo fi;
	private Runnable montrerFilm;
	/** Creates new form FilmElement */
	FilmElement()
	{
		initComponents();
	}

	public void setFilm (FilmsInfo f)
	{
		fi = f;
		titre.setText(f.getTitre());
		acteur.setText(f.getActeur());
		float pop = f.getPopularite();

		try
		{
			image.setIcon(new ImageIcon(ImageIO.read(new File("./tools/images/" + f.getImage()))
					.getScaledInstance(image.getPreferredSize().width, image.getPreferredSize().height, 0)));
		}
		catch (IOException ex)
		{
			Logger.getLogger(FilmElement.class.getName()).log(Level.SEVERE, null, ex);
		}

		float p = fi.getPopularite();
		if (p < 0.2)
		{
			popularite.setText("Navet");
			popularite.setForeground(Color.LIGHT_GRAY);
		}
		else if (p < 0.4)
		{
			popularite.setText("Moyen");
			popularite.setForeground(Color.WHITE);
		}
		else if (p < 0.6)
		{
			popularite.setText("Pas si mal");
			popularite.setForeground(Color.CYAN);
		}
		else if (p < 0.8)
		{
			popularite.setText("Bon Film");
			popularite.setForeground(Color.ORANGE);
		}
		else
		{
			popularite.setText("Block Buster");
			popularite.setForeground(Color.GREEN);
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

        titre = new javax.swing.JLabel();
        acteur = new javax.swing.JLabel();
        popularite = new javax.swing.JLabel();
        image = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.cinema.view.user.CinemaApp.class).getContext().getResourceMap(FilmElement.class);
        setBackground(resourceMap.getColor("Form.background")); // NOI18N
        setMinimumSize(new java.awt.Dimension(300, 170));
        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(300, 170));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickFilmElement(evt);
            }
        });

        titre.setForeground(resourceMap.getColor("titre.foreground")); // NOI18N
        titre.setText(resourceMap.getString("titre.text")); // NOI18N
        titre.setName("titre"); // NOI18N
        titre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                titreMouseClicked(evt);
            }
        });

        acteur.setFont(resourceMap.getFont("acteur.font")); // NOI18N
        acteur.setForeground(resourceMap.getColor("acteur.foreground")); // NOI18N
        acteur.setText(resourceMap.getString("acteur.text")); // NOI18N
        acteur.setName("acteur"); // NOI18N
        acteur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                acteurMouseClicked(evt);
            }
        });

        popularite.setFont(resourceMap.getFont("popularite.font")); // NOI18N
        popularite.setForeground(resourceMap.getColor("popularite.foreground")); // NOI18N
        popularite.setText(resourceMap.getString("popularite.text")); // NOI18N
        popularite.setName("popularite"); // NOI18N
        popularite.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                populariteMouseClicked(evt);
            }
        });

        image.setBackground(resourceMap.getColor("image.background")); // NOI18N
        image.setText(resourceMap.getString("image.text")); // NOI18N
        image.setName("image"); // NOI18N
        image.setPreferredSize(new java.awt.Dimension(120, 146));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(popularite, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(acteur, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(titre, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(acteur, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(popularite)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void clickFilmElement(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clickFilmElement
	{//GEN-HEADEREND:event_clickFilmElement
		montrerFilm.run();
	}//GEN-LAST:event_clickFilmElement

	private void titreMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_titreMouseClicked
	{//GEN-HEADEREND:event_titreMouseClicked
		// TODO add your handling code here:
        clickFilmElement(evt);

	}//GEN-LAST:event_titreMouseClicked

	private void acteurMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_acteurMouseClicked
	{//GEN-HEADEREND:event_acteurMouseClicked
		// TODO add your handling code here:
        clickFilmElement(evt);
	}//GEN-LAST:event_acteurMouseClicked

	private void populariteMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_populariteMouseClicked
	{//GEN-HEADEREND:event_populariteMouseClicked
		// TODO add your handling code here:
		clickFilmElement(evt);

	}//GEN-LAST:event_populariteMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel acteur;
    private javax.swing.JLabel image;
    private javax.swing.JLabel popularite;
    private javax.swing.JLabel titre;
    // End of variables declaration//GEN-END:variables

	void setClick(Runnable runnable)
	{
		montrerFilm = runnable;
	}
}

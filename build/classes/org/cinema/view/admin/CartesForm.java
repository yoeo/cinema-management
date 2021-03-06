/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ListeCartes.java
 *
 * Created on 1 févr. 2011, 02:03:43
 */

package org.cinema.view.admin;

import java.util.List;
import javax.swing.JOptionPane;
import org.cinema.dao.typecarte.TypeCartesDao;
import org.cinema.dao.typecarte.TypeCartesInfo;
import org.cinema.utils.Log;

/**
 *
 * @author lynda
 */
public class CartesForm extends javax.swing.JPanel {

     TypeCartesDao carteDao;
     TypeCartesInfo carte;
     List<TypeCartesInfo> listCarte;


    /** Creates new form ListeCartes */
    public CartesForm() {
        initComponents();
        /* initilaiser TypeCartes*/
        carte = new TypeCartesInfo(null,0);
        carteDao = new TypeCartesDao();
        listCarte = carteDao.allTypeCarte();
        Remplir();


    }
    private void Remplir (){
         listCarte = carteDao.allTypeCarte();

       jComboBox1.removeAllItems();
        for (TypeCartesInfo ci : listCarte ) {
           Log.log(ci.getNom());


            this.jComboBox1.addItem("" + ci.getId() + " " + ci.getNom());
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

        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "[choisir une carte]" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Les Cartes :");

        jLabel2.setText("Nom:");

        jLabel3.setText("Tarif:");

        jToggleButton1.setText("Supprimer");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setText("Modifier");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField2)
                    .addComponent(jTextField1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(161, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /* modifier un tarif ou bien un nom pour une carte donnée*/
    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
       UpdateCard();
    }//GEN-LAST:event_jToggleButton2ActionPerformed
private void UpdateCard(){
    carte.setNom(jTextField1.getText());
    carte.setTarif(Float.valueOf(jTextField2.getText().trim()).floatValue());
    if (carteDao.updateTypeCarte(carte)){
        Remplir();
        JOptionPane.showMessageDialog(null, "la carte est bien modifié");

    }else  {
        JOptionPane.showMessageDialog(null, "erreur, la carte n'est pas modifié");

    }


   }






    /*  afficher la list des cartes dans un combobox,
 séléctionner une carte à modifier et à supprimer*/
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (jComboBox1.getSelectedItem() != null)
         {
        int id = Integer.parseInt(((String) jComboBox1.getSelectedItem()).split(" ")[0]);
        carte = carteDao.getTypeCarte(id);

          Log.log("c'est l'id n°"+ carte.getId());
          Log.log("le nom de la salle"+ carte.getNom());
          jTextField1.setText(carte.getNom());
          float tarif = carte.getTarif();
          jTextField2.setText(String.valueOf(tarif)+" €");

        }
         
           
    }//GEN-LAST:event_jComboBox1ActionPerformed
/*   Supprimer une carte*/
    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        delete();
    }//GEN-LAST:event_jToggleButton1ActionPerformed

private void delete (){
    if ( jComboBox1.getSelectedItem()== null)
    {
        JOptionPane.showMessageDialog(null,
                    "Il ya aucune salle.");
    }
     String nom_salle = jTextField1.getText();
        nom_salle = nom_salle.toUpperCase();
        if (nom_salle.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Selectionnez un type de carte"
                    + "  à supprimer.");
            
        } else {
            //verifier le suppression d'une carte
            boolean ok = carteDao.removeTypeCarte(carte.getId());

            if (ok) {
                JOptionPane.showMessageDialog(null, "la carte est supprimé.");
                refresh();
            } else {
                JOptionPane.showMessageDialog(null, " la salle n'est pas supprimer.");

            }


        }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    // End of variables declaration//GEN-END:variables

    /*  cette fonction permet de rafraichir le combobox en cas de mise à jour*/
    private void refresh() {
        listCarte = carteDao.allTypeCarte();
        jComboBox1.removeAllItems();
        for (TypeCartesInfo ci:listCarte){
            Log.log(ci.getNom());
            this.jComboBox1.addItem("" + ci.getId() + " " + ci.getNom());
        }

    }

}

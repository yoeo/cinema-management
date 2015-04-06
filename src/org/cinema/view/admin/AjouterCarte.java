/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AjouterCarte.java
 *
 * Created on 1 févr. 2011, 00:11:54
 */

package org.cinema.view.admin;

import java.util.List;
import javax.swing.JOptionPane;
import org.cinema.dao.typecarte.TypeCartesDao;
import org.cinema.dao.typecarte.TypeCartesInfo;

/**
 * 31 fevrier 2011
 * @author lynda
 */
public class AjouterCarte extends javax.swing.JFrame {
    TypeCartesDao carteDao;
    TypeCartesInfo carte;
    List<TypeCartesInfo> carteList;
    /** Creates new form AjouterCarte */
    public AjouterCarte() {
        initComponents();
        /* initialiser le type carte*/
        carteDao = new TypeCartesDao();
        carte = new TypeCartesInfo( null,0);
        carteList= carteDao.allTypeCarte();


    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Nom de la Carte:");

        jLabel2.setText("Tarif de la Carte:");

        jButton1.setText("Ajouter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Actualiser");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("€");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /* rafraichir les champs en cas d'erreur*/
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       actualiser();
    }//GEN-LAST:event_jButton2ActionPerformed
   /* ajouter une carte ou un tarif non existant*/

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

         //verifierChamps();
         existeCarte();
         carteList= carteDao.allTypeCarte();
         carte= new TypeCartesInfo(null, 0);
         carteDao= new TypeCartesDao();
         
         // existeCarte();
          ajouterCarte();
  
    }//GEN-LAST:event_jButton1ActionPerformed
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AjouterCarte().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

    private void actualiser() {
         this.jTextField1.setText("");
        this.jTextField2.setText("");
    }

    private void verifierChamps() {
        
        if(this.jTextField1.getText().isEmpty() && this.jTextField2.getText().isEmpty())
       {
           JOptionPane.showMessageDialog(null,
                    "les champs ne doivent pas être vide.");
       }
       

    }

    private void ajouterCarte() {
       if(this.jTextField1.getText().isEmpty() && this.jTextField2.getText().isEmpty())
       {
           JOptionPane.showMessageDialog(null,
                    "les champs ne doivent pas être vide.");
       }else{
     
             carte.setNom(jTextField1.getText());
             /* convertir le type String to Float avant d'insérer dans la base*/
          try {
              Float prix = Float.valueOf(jTextField2.getText().trim()).floatValue();
             carte.setTarif(prix);
             System.out.println("iiii"+prix);

        } catch(Exception e ) {

   System.out.println(e.getMessage()+"erreur");

        }
           /*  sauvegarder la nouvelle carte*/
             if(carteDao.saveTypeCarte(carte)){
              JOptionPane.showMessageDialog(null,
                    "la carte est bien enregistrée dans la base.");


            }

             else {
                JOptionPane.showMessageDialog(null, "erreur,la carte n'est pas enregistrée");
               }
        
        }
        
    }

 private void existeCarte() {

         for (TypeCartesInfo ci:carteList){

       if(ci.getNom().equals(jTextField1.getText())){
              JOptionPane.showMessageDialog(null,
                    "La carte existe déja.");
          actualiser();
        }

    }
  }

}
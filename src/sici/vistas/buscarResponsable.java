/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sici.vistas;

import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import sici.modelo.Responsable;
import src.HibernateUtil;

/**
 *
 * @author londe
 */
public class buscarResponsable extends javax.swing.JDialog {

    /**
     * Creates new form buscarResponsable
     */
    public buscarResponsable(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        crearTabla();
    }
    public void crearTabla() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        DefaultTableModel modelo = new DefaultTableModel();
        jtbTabla.setModel(modelo);
        modelo.addColumn("id");
        modelo.addColumn("Estado");
        modelo.addColumn("Nombre");
        modelo.addColumn("Direccion");
        modelo.addColumn("DNI");
        modelo.addColumn("E-mail");
        modelo.addColumn("Telefono");
    }
    private void buscar() {
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            
            DefaultTableModel modelo = new DefaultTableModel();
            jtbTabla.setModel(modelo);
            modelo.addColumn("id");
            modelo.addColumn("Estado");
            modelo.addColumn("Nombre");
            modelo.addColumn("Direccion");
            modelo.addColumn("DNI");
            modelo.addColumn("E-mail");
            modelo.addColumn("Telefono");
            List<Responsable>listaResponsables = null;

            sesion.beginTransaction();
            listaResponsables = sesion.createQuery("from Responsable").list();
            //Buscar Código////////////////////////////////////////////////////
            //SELECT name FROM table_example WHERE name LIKE "%search%"
            if(rdbCodigo.isSelected()){
                String id = txtBuscar.getText();
                listaResponsables = sesion.createQuery("from Responsable where idResponsable like '%" + id +"%'" ).list();
                for  (Responsable x : listaResponsables){
                    Responsable Responsable = x;
                    Object fila[] = new Object[7];
                        fila[0] = Responsable.getIdResponsable();
                        int estado = Responsable.getEstado();
                        if ( estado == 0){
                            fila[1] = "Inactivo";
                        }else{
                            fila[1] = "Activo";
                        }
                            fila[2] = Responsable.getNombre();
                            fila[3] = Responsable.getDireccion();
                            fila[4] = Responsable.getDni();
                            fila[5] = Responsable.getEmail();
                            fila[6] = Responsable.getTelefono();
                            modelo.addRow(fila);
                }
                sesion.getTransaction().commit();
                sesion.close();
            }
            //Buscar DNI////////////////////////////////////////////////////
            if(rdbDNI.isSelected()){
                String dni = txtBuscar.getText();
                listaResponsables = sesion.createQuery("from Responsable where dni like '%" + dni +"%'" ).list();
                for  (Responsable x : listaResponsables){
                    Responsable Responsable = x;
                    Object fila[] = new Object[7];
                        fila[0] = Responsable.getIdResponsable();
                        int estado = Responsable.getEstado();
                        if ( estado == 0){
                            fila[1] = "Inactivo";
                        }else{
                            fila[1] = "Activo";
                        }
                            fila[2] = Responsable.getNombre();
                            fila[3] = Responsable.getDireccion();
                            fila[4] = Responsable.getDni();
                            fila[5] = Responsable.getEmail();
                            fila[6] = Responsable.getTelefono();
                            modelo.addRow(fila);
                }
                sesion.getTransaction().commit();
                sesion.close();
            }
            
            //Buscar Nombre////////////////////////////////////////////////////
            if(rdbNombre.isSelected()){
                String nombre = txtBuscar.getText();
                listaResponsables = sesion.createQuery("from Responsable where nombre like '%" + nombre +"%'" ).list();
                for  (Responsable x : listaResponsables){
                    Responsable Responsable = x;
                    Object fila[] = new Object[7];
                        fila[0] = Responsable.getIdResponsable();
                        int estado = Responsable.getEstado();
                        if ( estado == 0){
                            fila[1] = "Inactivo";
                        }else{
                            fila[1] = "Activo";
                        }
                            fila[2] = Responsable.getNombre();
                            fila[3] = Responsable.getDireccion();
                            fila[4] = Responsable.getDni();
                            fila[5] = Responsable.getEmail();
                            fila[6] = Responsable.getTelefono();
                            modelo.addRow(fila);
                }
                sesion.getTransaction().commit();
                sesion.close();
            }
                
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtbTabla = new javax.swing.JTable();
        btnAceptar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        rdbNombre = new javax.swing.JRadioButton();
        rdbCodigo = new javax.swing.JRadioButton();
        rdbDNI = new javax.swing.JRadioButton();
        btnBuscar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSeleccionarTodo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jtbTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtbTabla);

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setText("Busqueda de Responsable:");

        txtBuscar.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtBuscar.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtBuscar.setText("Nombre, DNI, Código");
        txtBuscar.setEnabled(false);
        txtBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarMouseClicked(evt);
            }
        });
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        rdbNombre.setText("Nombre");
        rdbNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbNombreActionPerformed(evt);
            }
        });

        rdbCodigo.setText("Código");
        rdbCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbCodigoActionPerformed(evt);
            }
        });

        rdbDNI.setText("DNI");
        rdbDNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbDNIActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rdbNombre)
                        .addGap(18, 18, 18)
                        .addComponent(rdbCodigo)
                        .addGap(18, 18, 18)
                        .addComponent(rdbDNI)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbNombre)
                    .addComponent(rdbCodigo)
                    .addComponent(rdbDNI))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSeleccionarTodo.setText("Seliccionr Todo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(332, 332, 332)
                                .addComponent(btnSeleccionarTodo)
                                .addGap(18, 18, 18)
                                .addComponent(btnAceptar)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancelar)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar)
                    .addComponent(btnSeleccionarTodo))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdbNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbNombreActionPerformed
        // TODO add your handling code here:
        rdbNombre.setSelected(true);
        rdbDNI.setSelected(false);
        rdbCodigo.setSelected(false);
        txtBuscar.setEnabled(true);
        txtBuscar.setText("");
    }//GEN-LAST:event_rdbNombreActionPerformed

    private void rdbCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbCodigoActionPerformed
        // TODO add your handling code here:
        rdbNombre.setSelected(false);
        rdbDNI.setSelected(false);
        rdbCodigo.setSelected(true);
        txtBuscar.setEnabled(true);
        txtBuscar.setText("");
    }//GEN-LAST:event_rdbCodigoActionPerformed

    private void rdbDNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbDNIActionPerformed
        // TODO add your handling code here:
        rdbNombre.setSelected(false);
        rdbDNI.setSelected(true);
        rdbCodigo.setSelected(false);
        txtBuscar.setEnabled(true);
        txtBuscar.setText("");
    }//GEN-LAST:event_rdbDNIActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarMouseClicked
        // TODO add your handling code here:
        if(rdbCodigo.isSelected() || rdbDNI.isSelected() || rdbNombre.isSelected()){
            txtBuscar.setText("");
            txtBuscar.setEnabled(true);
            
        }else{
            JOptionPane.showMessageDialog(this, "Primero seleccione un item a buscar.");
        }
        
    }//GEN-LAST:event_txtBuscarMouseClicked

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        // TODO add your handling code here:
        int c = evt.getKeyChar();
        if(rdbCodigo.isSelected() || rdbDNI.isSelected()){
            
            if(c == evt.VK_ENTER){
                buscar();
                return;
            }
            buscar();
            if (!Character.isDigit(c) && c != evt.VK_DELETE && c != evt.VK_BACK_SPACE){
                evt.consume();
                JOptionPane.showMessageDialog(this, "Solo nuemeros");
            }
        }
        if(rdbNombre.isSelected()){
            if(c == evt.VK_ENTER){
                buscar();
                return;
            }
            buscar();
        }
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        TableModel model = jtbTabla.getModel();
        int indice = jtbTabla.getSelectedRow();
        Object[] fila = new Object[7];
        fila[0]=model.getValueAt(indice, 0);
        fila[1]=model.getValueAt(indice, 1);
        fila[2]=model.getValueAt(indice, 2);
        fila[3]=model.getValueAt(indice, 3);
        fila[4]=model.getValueAt(indice, 4);
        fila[5]=model.getValueAt(indice, 5);
        fila[6]=model.getValueAt(indice, 6);
        
        System.out.println(Arrays.toString(fila));
        
        this.setVisible(false);
        
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtBuscarKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(buscarResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(buscarResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(buscarResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(buscarResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                buscarResponsable dialog = new buscarResponsable(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSeleccionarTodo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jtbTabla;
    private javax.swing.JRadioButton rdbCodigo;
    private javax.swing.JRadioButton rdbDNI;
    private javax.swing.JRadioButton rdbNombre;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}

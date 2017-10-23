/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sici.vistas;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import sici.modelo.Responsable;
import src.HibernateUtil;

/**
 *
 * @author londe
 */
public class crudResponsable extends javax.swing.JDialog {

    /**
     * Creates new form crudResponsable
     */
    
    
    public crudResponsable(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        cargarTabla();
        limpiar();
        
    }
    
    
    public void eliminar(){
        String nombre, dni; //Crea las variables que se van imprimir
        nombre = txtNombre.getText(); //Tomamos los valores que estan en los txt
        dni = txtDNI.getText();
        int elimina = JOptionPane.showConfirmDialog(this, "¿Está seguro de Eliminar: "+nombre+" "+dni+"?", "ATENCIÓN", JOptionPane.YES_NO_OPTION);
        if(elimina == 0){// SI=0 y NO=1
            try {
                    Session sesion = HibernateUtil.getSessionFactory().openSession();
                    sesion.beginTransaction();

                    Responsable res = new Responsable();
                    res.setIdResponsable(Integer.parseInt(txtid.getText()));
                    res.setNombre(txtNombre.getText());
                    res.setDireccion(txtDireccion.getText());
                    res.setDni(txtDNI.getText());
                    res.setEmail(txtEmail.getText());
                    res.setTelefono(txtTelefono.getText());
                    res.setEstado(0);
                    sesion.update(res);
                    sesion.getTransaction().commit();
                    sesion.close();
            } catch (HibernateException he) {
                    he.printStackTrace();
            }
        }
        cargarTabla();
    }
    
    public void limpiar(){
        txtNombre.setText("");
        txtNombre.setEnabled(false);
        
        txtDNI.setText("");
        txtDNI.setEnabled(false);
        
        txtDireccion.setText("");
        txtDireccion.setEnabled(false);
        
        txtEmail.setText("");
        txtEmail.setEnabled(false);
        
        txtTelefono.setText("");
        txtTelefono.setEnabled(false);
        
        txtid.setText("");
        
        btnGuardar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
        
        chkEstado.setEnabled(false);
        
        btnGuardar.setText("Guardar");
    }
    
    public void cargarTabla(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        DefaultTableModel modelo = new DefaultTableModel();
        tblResponsable.setModel(modelo);
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
    
    public void actualizar() {
        try {
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.clear();
            sesion.beginTransaction();
                
                Responsable res = new Responsable();
                res.setIdResponsable(Integer.parseInt(txtid.getText()));
                res.setNombre(txtNombre.getText());
                res.setDireccion(txtDireccion.getText());
                res.setDni(txtDNI.getText());
                res.setEmail(txtEmail.getText());
                res.setTelefono(txtTelefono.getText());
                if(chkEstado.isSelected()){
                    res.setEstado(1);
                }else{
                    res.setEstado(0);
                }
                
                sesion.update(res);
                sesion.getTransaction().commit();
                sesion.close();
                
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Hay un error en los campos", "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        cargarTabla();
        limpiar();
    }
    
    public void guardar(){
        try {
            
            Session sesion = HibernateUtil.getSessionFactory().openSession();
            sesion.beginTransaction();
            
                Responsable res = new Responsable();
                res.setNombre(txtNombre.getText());
                res.setDireccion(txtDireccion.getText());
                res.setDni(txtDNI.getText());
                res.setEmail(txtEmail.getText());
                res.setTelefono(txtTelefono.getText());
                if(chkEstado.isSelected()){
                    res.setEstado(1);
                }else{
                    res.setEstado(0);
                }
                
                sesion.save(res);
                sesion.getTransaction().commit();
                sesion.close();
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Hay un error en los campos", "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        cargarTabla();
        limpiar();
    }
    
    public void activar(){
        btnGuardar.setEnabled(true);
        btnGuardar.setText("Actualizar");
        txtNombre.setEnabled(true);
        txtDNI.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtEmail.setEnabled(true);
        txtTelefono.setEnabled(true);
    }
    
    public boolean validarEmail(String email){
        //pattern es una clase de java
        Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");

        Matcher mather = pattern.matcher(email);
 
        if (mather.find()) {
            return true;
        } else {
            return false;
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
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lblMantenimientoUsuario = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        lblDNI = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtDNI = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtid = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        lblEmail1 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblEmail2 = new javax.swing.JLabel();
        chkEstado = new javax.swing.JCheckBox();
        txtTelefono = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblResponsable = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mantenimiento Responsable");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(554, 336, -1, -1));

        lblMantenimientoUsuario.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblMantenimientoUsuario.setText("Mantenimiento de Responsable");
        getContentPane().add(lblMantenimientoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 12, -1, -1));

        lblNombre.setText("Nombre");

        lblDNI.setText("DNI");

        lblDireccion.setText("Dirección");

        lblEmail.setText("E-mail");

        txtNombre.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtNombre.setText("Nombre");
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        txtDNI.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtDNI.setText("DNI");
        txtDNI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDNIKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDNIKeyTyped(evt);
            }
        });

        txtDireccion.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtDireccion.setText("Dirección");

        txtid.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtid.setText("Código");
        txtid.setEnabled(false);

        btnAgregar.setBackground(new java.awt.Color(51, 255, 0));
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(51, 51, 255));
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregar)
                .addGap(18, 18, 18)
                .addComponent(btnBuscar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblEmail1.setText("Código");

        txtEmail.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtEmail.setText("E-mail");
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        lblEmail2.setText("Estado");

        chkEstado.setSelected(true);

        txtTelefono.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtTelefono.setText("Telefono");

        lblTelefono.setText("Telefono");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblEmail1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre)
                            .addComponent(lblDNI)
                            .addComponent(lblDireccion)
                            .addComponent(lblEmail)
                            .addComponent(lblEmail2)
                            .addComponent(lblTelefono))
                        .addGap(101, 101, 101)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(chkEstado))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                        .addComponent(txtDNI)
                                        .addComponent(txtNombre)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtTelefono))))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombre)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDNI)
                            .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDireccion)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTelefono)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail2)
                    .addComponent(chkEstado))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, 330));

        btnGuardar.setBackground(new java.awt.Color(153, 153, 153));
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(153, 153, 153));
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(255, 51, 51));
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(255, 204, 51));
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        tblResponsable.setModel(new javax.swing.table.DefaultTableModel(
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
        tblResponsable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblResponsableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblResponsable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGuardar)
                .addGap(26, 26, 26)
                .addComponent(btnEliminar)
                .addGap(18, 18, 18)
                .addComponent(btnModificar)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar)
                .addContainerGap(176, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar)
                    .addComponent(btnEliminar)
                    .addComponent(btnModificar))
                .addGap(15, 15, 15))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 421, 560, 270));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if(txtNombre.getText().equals("") && txtDNI.getText().equals("") && txtDireccion.getText().equals("") &&
                txtTelefono.getText().equals("") && txtEmail.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Los campos estan vacios");
            return;
        }
        if(txtNombre.getText().equals("")){
            JOptionPane.showMessageDialog(this, "El Campo Nombre no puede estar vacío");
            return;
        }
        
        if(txtDNI.getText().equals("")){
            JOptionPane.showMessageDialog(this, "El Campo DNI no puede estar vacío");
            return;
        }
        
        if(txtDireccion.getText().equals("")){
            JOptionPane.showMessageDialog(this, "El Campo Direccion no puede estar vacío");
            return;
        }
        
        if(txtTelefono.getText().equals("")){
            JOptionPane.showMessageDialog(this, "El Campo Telefono no puede estar vacío");
            return;
        }
        
        if(txtEmail.getText().equals("")){
            JOptionPane.showMessageDialog(this, "El Campo E-mail no puede estar vacío");
            return;
        }
        
        if(!validarEmail(txtEmail.getText())){
            JOptionPane.showMessageDialog(this, "El E-mail no es correcto");
            return;
        }
        
        if(btnGuardar.getText().equals("Guardar")){
            guardar();
        }else if(btnGuardar.getText().equals("Actualizar")){
            actualizar();
        }
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        limpiar();
        activar();
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnGuardar.setText("Guardar");
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblResponsableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblResponsableMouseClicked
        // TODO add your handling code here:
        limpiar();
        
        int row = tblResponsable.getSelectedRow();
        txtid.setText(tblResponsable.getModel().getValueAt(row, 0).toString());
        String estado = tblResponsable.getModel().getValueAt(row, 1).toString();
        if("Activo".equals(estado)){
            chkEstado.setSelected(true);
            btnEliminar.setEnabled(true);
            btnModificar.setEnabled(true);
        }else{
            chkEstado.setSelected(false);
            btnEliminar.setEnabled(false);
            btnModificar.setEnabled(false);
        }
        txtNombre.setText(tblResponsable.getModel().getValueAt(row, 2).toString());
        txtDNI.setText(tblResponsable.getModel().getValueAt(row, 4).toString());
        txtDireccion.setText(tblResponsable.getModel().getValueAt(row, 3).toString());
        txtEmail.setText(tblResponsable.getModel().getValueAt(row, 5).toString());
        txtTelefono.setText(tblResponsable.getModel().getValueAt(row, 6).toString());

    }//GEN-LAST:event_tblResponsableMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
         int row = tblResponsable.getSelectedRow();
        if(tblResponsable.getModel().getValueAt(row, 1).toString().equals("Activo")){
            activar();
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        }else{
            JOptionPane.showMessageDialog(this, "Solo se pueden modificar usuarios Activos.");
        }
        
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        buscarResponsable buscar = new buscarResponsable(null, true);
        buscar.setVisible(true);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtDNIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDNIKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtDNIKeyPressed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtDNIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDNIKeyTyped
        // TODO add your handling code here:
        int c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != evt.VK_DELETE && c != evt.VK_BACK_SPACE){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo nuemeros");
        }
    }//GEN-LAST:event_txtDNIKeyTyped

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailFocusLost

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
            java.util.logging.Logger.getLogger(crudResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(crudResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(crudResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(crudResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                crudResponsable dialog = new crudResponsable(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JCheckBox chkEstado;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblDNI;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEmail1;
    private javax.swing.JLabel lblEmail2;
    private javax.swing.JLabel lblMantenimientoUsuario;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JTable tblResponsable;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtid;
    // End of variables declaration//GEN-END:variables
}

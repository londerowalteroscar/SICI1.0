
package sici.vistas;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import src.HibernateUtil;
import sici.modelo.Empresa;


public class crudEmpresa extends javax.swing.JDialog {

    
    public crudEmpresa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        cargarTabla();
        limpiar();
    }

    private crudEmpresa() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void eliminar(){
        String nombre, cuit; //Crea las variables que se van imprimir
        nombre = txtNombre.getText(); //Tomamos los valores que estan en los txt
        cuit = txtCUIT.getText();
        int elimina = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar: "+nombre+"?", "ATENCIÓN", JOptionPane.YES_NO_OPTION);
        if(elimina == 0){// SI=0 y NO=1
            try {
                    Session sesion = HibernateUtil.getSessionFactory().openSession();
                    sesion.beginTransaction();

                    Empresa emp = new Empresa();
                    emp.setIdEmpresa(Integer.parseInt(txtid.getText()));
                    emp.setNombre(txtNombre.getText());                  
                    emp.setCuit(txtCUIT.getText());                   
                    emp.setTelefono(txtTelefono.getText());
                    emp.setEstado(0);
                    sesion.update(emp);
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
        
        txtCUIT.setText("");
        txtCUIT.setEnabled(false);
        
        
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
        tblEmpresa.setModel(modelo);
        modelo.addColumn("id");
        modelo.addColumn("Nombre");
        modelo.addColumn("CUIT");
        modelo.addColumn("Telefono");
        modelo.addColumn("Estado");
        
        
        List<Empresa>listaEmpresas = null;
        
        sesion.beginTransaction();
        listaEmpresas = sesion.createQuery("from Empresa").list();
        
        for  (Empresa x : listaEmpresas){
            Empresa Empresa = x;
            
            Object fila[] = new Object[5];
            
            fila[0] = Empresa.getIdEmpresa();
            fila[1] = Empresa.getNombre();
            fila[2] = Empresa.getCuit();
            fila[3] = Empresa.getTelefono();
            int estado = Empresa.getEstado();
            if ( estado == 0){
                fila[4] = "Inactivo";
            }else{
                fila[4] = "Activo";
            }
            
           
            
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
                
                Empresa emp = new Empresa();
                emp.setIdEmpresa(Integer.parseInt(txtid.getText()));
                emp.setNombre(txtNombre.getText());
                emp.setCuit(txtCUIT.getText());            
                emp.setTelefono(txtTelefono.getText());
                if(chkEstado.isSelected()){
                    emp.setEstado(1);
                }else{
                    emp.setEstado(0);
                }
                
                sesion.update(emp);
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
            
                Empresa emp = new Empresa();
                emp.setNombre(txtNombre.getText());              
                emp.setCuit(txtCUIT.getText());
                emp.setTelefono(txtTelefono.getText());
                if(chkEstado.isSelected()){
                    emp.setEstado(1);
                }else{
                    emp.setEstado(0);
                }
                
                sesion.save(emp);
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
        txtCUIT.setEnabled(true);           
        txtTelefono.setEnabled(true);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblMantenimientoUsuario = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        lblDNI = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtCUIT = new javax.swing.JTextField();
        txtid = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        lblEmail1 = new javax.swing.JLabel();
        lblEmail2 = new javax.swing.JLabel();
        chkEstado = new javax.swing.JCheckBox();
        txtTelefono = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        lblMensaje = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmpresa = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblMantenimientoUsuario.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblMantenimientoUsuario.setText("Mantenimiento de Empresa");
        getContentPane().add(lblMantenimientoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 0, -1, -1));

        lblNombre.setText("Nombre");

        lblDNI.setText("CUIT");

        txtNombre.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtNombre.setText("Nombre");
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
        });

        txtCUIT.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtCUIT.setText("CUIT");
        txtCUIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCUITActionPerformed(evt);
            }
        });
        txtCUIT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCUITKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCUITKeyTyped(evt);
            }
        });

        txtid.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtid.setText("Código");
        txtid.setEnabled(false);

        btnAgregar.setBackground(new java.awt.Color(51, 255, 0));
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(51, 51, 255));
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
                .addGap(75, 75, 75)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDNI, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTelefono, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblEmail2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblEmail1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCUIT, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkEstado)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombre))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDNI)
                            .addComponent(txtCUIT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTelefono)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmail2)
                            .addComponent(chkEstado))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmail1)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 250));

        btnGuardar.setBackground(new java.awt.Color(153, 153, 153));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(153, 153, 153));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(255, 51, 51));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(255, 204, 51));
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

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
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar)
                    .addComponent(btnEliminar)
                    .addComponent(btnModificar))
                .addGap(15, 15, 15))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 440, 380, -1));

        tblEmpresa.setModel(new javax.swing.table.DefaultTableModel(
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
        tblEmpresa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmpresaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblEmpresa);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 540, 123));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtCUITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCUITActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCUITActionPerformed

    private void txtCUITKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCUITKeyPressed
        String cuit = txtCUIT.getText();
        
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("id");
        modelo.addColumn("Nombre");
        modelo.addColumn("CUIT");
        modelo.addColumn("Telefono");
        modelo.addColumn("Estado");
        tblEmpresa.setModel(modelo);
        
        List<Empresa>listaEmpresas = null;
        
        sesion.beginTransaction();
        listaEmpresas = sesion.createQuery("from Empresa where cuit like '" + cuit+"%'").list();
        
        if (listaEmpresas.size()==0){
            JOptionPane.showMessageDialog(this, "No se han encontrado coincidencias", "ERROR", JOptionPane.ERROR);
        }else{
        for  (Empresa x : listaEmpresas){
            Empresa Empresa = x;
            
            Object fila[] = new Object[5];
            
            fila[0] = Empresa.getIdEmpresa();
            fila[1] = Empresa.getNombre();
            fila[2] = Empresa.getCuit();
            fila[3] = Empresa.getTelefono();
            int estado = Empresa.getEstado();
            if ( estado == 0){
                fila[4] = "Inactivo";
            }else{
                fila[4] = "Activo";
            }
            
           
            
            modelo.addRow(fila);
        }
        }
        sesion.getTransaction().commit();
        sesion.close();  
        }
    lblMensaje.setText("");
                               

    }//GEN-LAST:event_txtCUITKeyPressed

    private void txtCUITKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCUITKeyTyped
        // TODO add your handling code here:
        int c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != evt.VK_DELETE && c != evt.VK_BACK_SPACE && c!= evt.VK_ENTER){
            evt.consume();
            JOptionPane.showMessageDialog(this, "Solo números");
        }
    }//GEN-LAST:event_txtCUITKeyTyped

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        limpiar();
        activar();
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnGuardar.setText("Guardar");
        lblMensaje.setText("");
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        limpiar();
        txtNombre.setEnabled(true);
        txtCUIT.setEnabled(true);
        lblMensaje.setText("Ingrese el nombre o CUIT a buscar y presione ENTER");
        
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if(txtNombre.getText().equals("") && txtCUIT.getText().equals("") &&
            txtTelefono.getText().equals("") ){
            JOptionPane.showMessageDialog(this, "Los campos estan vacios");
            return;
        }
        if(txtNombre.getText().equals("")){
            JOptionPane.showMessageDialog(this, "El Campo Nombre no puede estar vacío");
            return;
        }

        if(txtCUIT.getText().equals("")){
            JOptionPane.showMessageDialog(this, "El Campo DNI no puede estar vacío");
            return;
        }

      

        if(txtTelefono.getText().equals("")){
            JOptionPane.showMessageDialog(this, "El Campo Telefono no puede estar vacío");
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

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        int row = tblEmpresa.getSelectedRow();
        if(tblEmpresa.getModel().getValueAt(row, 4).toString().equals("Activo")){
            activar();
            btnModificar.setEnabled(false);
            btnEliminar.setEnabled(false);
        }else{
            JOptionPane.showMessageDialog(this, "Solo se pueden modificar usuarios Activos.");
        }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void tblEmpresaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpresaMouseClicked
        // TODO add your handling code here:
        limpiar();

        int row = tblEmpresa.getSelectedRow();
        txtid.setText(tblEmpresa.getModel().getValueAt(row, 0).toString());
        String estado = tblEmpresa.getModel().getValueAt(row, 4).toString();
        if("Activo".equals(estado)){
            chkEstado.setSelected(true);
            btnEliminar.setEnabled(true);
            btnModificar.setEnabled(true);
        }else{
            chkEstado.setSelected(false);
            btnEliminar.setEnabled(false);
            btnModificar.setEnabled(false);
        }
        txtNombre.setText(tblEmpresa.getModel().getValueAt(row, 1).toString());
        txtCUIT.setText(tblEmpresa.getModel().getValueAt(row, 2).toString());
        txtTelefono.setText(tblEmpresa.getModel().getValueAt(row, 3).toString());
    }//GEN-LAST:event_tblEmpresaMouseClicked

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
        String nombre = txtNombre.getText();
        
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("id");
        modelo.addColumn("Nombre");
        modelo.addColumn("CUIT");
        modelo.addColumn("Telefono");
        modelo.addColumn("Estado");
        tblEmpresa.setModel(modelo);
        
        List<Empresa>listaEmpresas = null;
        
        sesion.beginTransaction();
        listaEmpresas = sesion.createQuery("from Empresa where nombre like '" + nombre+"%'").list();
        
        if (listaEmpresas.size()==0){
            JOptionPane.showMessageDialog(this, "No se han encontrado coincidencias", "ERROR", JOptionPane.ERROR);
        }else{
        for  (Empresa x : listaEmpresas){
            Empresa Empresa = x;
            
            Object fila[] = new Object[5];
            
            fila[0] = Empresa.getIdEmpresa();
            fila[1] = Empresa.getNombre();
            fila[2] = Empresa.getCuit();
            fila[3] = Empresa.getTelefono();
            int estado = Empresa.getEstado();
            if ( estado == 0){
                fila[4] = "Inactivo";
            }else{
                fila[4] = "Activo";
            }
            
           
            
            modelo.addRow(fila);
        }
        }
        sesion.getTransaction().commit();
        sesion.close();  
        }
    lblMensaje.setText("");
    }//GEN-LAST:event_txtNombreKeyPressed

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
            java.util.logging.Logger.getLogger(crudEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(crudEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(crudEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(crudEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new crudEmpresa().setVisible(true);
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDNI;
    private javax.swing.JLabel lblEmail1;
    private javax.swing.JLabel lblEmail2;
    private javax.swing.JLabel lblMantenimientoUsuario;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTelefono;
    public javax.swing.JTable tblEmpresa;
    private javax.swing.JTextField txtCUIT;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtid;
    // End of variables declaration//GEN-END:variables
}

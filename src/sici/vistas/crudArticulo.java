/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sici.vistas;

import java.awt.Frame;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.HibernateException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import sici.modelo.Articulo;
import sici.modelo.Empresa;
import sici.modelo.Marca;
import sici.modelo.Unidad;
import src.HibernateUtil;

/**
 *
 * @author londe
 */
public class crudArticulo extends javax.swing.JDialog {

    /**
     * Creates new form crudEmpresa
     */
    public crudArticulo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
//        cargarTabla();
        cargarCmbEmpresa();
        cargarTabla();
        cargarCmbUnidad();
        cargarCmbMarca();
        desactivarCampos();
        limpiarCampos();
    }
    
    public void cargarCmbEmpresa(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<Empresa> emp = null;
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        sesion.beginTransaction();
        emp = sesion.createQuery("from Empresa").list();
        for (Empresa empresa : emp) {
            modelo.addElement(empresa.getNombre());
        }
        cmbEmpresa.setModel(modelo);
        sesion.getTransaction().commit();
        sesion.close();
    }
    
    public void cargarCmbUnidad(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<Unidad> uni = null;
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        sesion.beginTransaction();
        uni = sesion.createQuery("from Unidad").list();
        for (Unidad unidad : uni) {
            modelo.addElement(unidad.getDescripcion());
        }
        cmbUnidad.setModel(modelo);
        sesion.getTransaction().commit();
        sesion.close();
    }
    
    public void cargarCmbMarca(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<Marca> marc = null;
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        sesion.beginTransaction();
        marc = sesion.createQuery("from Marca").list();
        for (Marca marca : marc) {
            modelo.addElement(marca.getNombre());
        }
        cmbMarca.setModel(modelo);
        sesion.getTransaction().commit();
        sesion.close();
    }
    public void cargarTabla(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        DefaultTableModel modelo = new DefaultTableModel();
        tblArticulo.setModel(modelo);
        modelo.addColumn("id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Codigo");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Unidad");
        modelo.addColumn("Empresa");
        modelo.addColumn("Marca");
        modelo.addColumn("Estock Minimo");
        modelo.addColumn("Precio");
        modelo.addColumn("Estado");
        
        List<Articulo>listaArticulos = null;
        
        sesion.beginTransaction();
        listaArticulos = sesion.createQuery("from Articulo").list();
        for  (Articulo x : listaArticulos){
            Articulo Articulo = x;
            
            Object fila[] = new Object[10];
            
            fila[0] = Articulo.getIdArticulo();
            fila[1] = Articulo.getNombre();
            fila[2] = Articulo.getNumSerie();
            fila[3] = Articulo.getExistencia();
            fila[4] = Articulo.getUnidad().getDescripcion();
            fila[5] = Articulo.getEmpresa().getNombre();
            fila[6] = Articulo.getMarca().getNombre();
            fila[7] = Articulo.getEstockMinimo();
            fila[8] = Articulo.getPrecio();
            int estado = Articulo.getEstado();
            if ( estado == 0){
                fila[9] = "Inactivo";
            }else{
                fila[9] = "Activo";
            }
            
            
            modelo.addRow(fila);
        }
        sesion.getTransaction().commit();
        sesion.close(); 
    }
    
    public void mostrarTabla(){
        int row = tblArticulo.getSelectedRow();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        int id=(int)tblArticulo.getModel().getValueAt(row, 0);
        sesion.beginTransaction();
        Articulo art = sesion.load(Articulo.class, id);
        txtId.setText(String.valueOf(art.getIdArticulo()));
        txtNombre.setText(art.getNombre());
        txtCodigo.setText(art.getNumSerie());
        txtCantidad.setText(art.getExistencia().toString());
        cmbUnidad.setSelectedItem(art.getUnidad().getDescripcion());
        cmbEmpresa.setSelectedItem(art.getEmpresa().getNombre());
        txtDescripcion.setText(art.getDescripcion());
        cmbMarca.setSelectedItem(art.getMarca().getNombre());
        txtStockMinimo.setText(art.getEstockMinimo().toString());
        txtPrecio.setText(art.getPrecio().toString());
        
        
        String estado = art.getEstado().toString();
        if("1".equals(estado)){
            chkEstado.setSelected(true);
            btnEliminar.setEnabled(true);
            btnModificar.setEnabled(true);
        }else{
            chkEstado.setSelected(false);
            btnEliminar.setEnabled(false);
            btnModificar.setEnabled(false);
        }
     
        sesion.getTransaction().commit();
        sesion.close(); 
        
        
    }
    
    public void limpiarCampos(){
        txtId.setText("");
        txtNombre.setText("");
        txtCodigo.setText("");
        txtCantidad.setText("");
        cmbUnidad.setSelectedIndex(-1);
        cmbEmpresa.setSelectedIndex(-1);
        txtDescripcion.setText("");
        cmbMarca.setSelectedIndex(-1);
        txtStockMinimo.setText("");
        txtPrecio.setText("");
        chkEstado.setSelected(true);
    }
    
    public void activarCampos(){
        txtNombre.setEnabled(true);
        txtCodigo.setEnabled(true);
        txtCantidad.setEnabled(true);
        cmbUnidad.setEnabled(true);
        cmbEmpresa.setEnabled(true);
        txtDescripcion.setEnabled(true);
        cmbMarca.setEnabled(true);
        txtStockMinimo.setEnabled(true);
        txtPrecio.setEnabled(true);
        btnGuardar.setEnabled(true);
    }
    
    public void desactivarCampos(){
        txtNombre.setEnabled(false);
        txtCodigo.setEnabled(false);
        txtCantidad.setEnabled(false);
        cmbUnidad.setEnabled(false);
        cmbEmpresa.setEnabled(false);
        txtDescripcion.setEnabled(false);
        cmbMarca.setEnabled(false);
        txtStockMinimo.setEnabled(false);
        txtPrecio.setEnabled(false);
        btnGuardar.setEnabled(false);
    }
    
    public void activarBotones(){
        btnAgregar.setEnabled(true);
        btnBuscar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnModificar.setEnabled(true);
    }
    
    public void eliminar(){
        String nombre; //Crea las variables que se van imprimir
        nombre = txtNombre.getText(); //Tomamos los valores que estan en los txt
        int elimina = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar articulo: "+nombre+"?", "ATENCIÓN", JOptionPane.YES_NO_OPTION);
        if(elimina == 0){// SI=0 y NO=1
            try {
                    Session sesion = HibernateUtil.getSessionFactory().openSession();
                    sesion.beginTransaction();
                    int id = (Integer.parseInt(txtId.getText()));
                    Articulo art = sesion.load(Articulo.class, id);
                    art.setNombre(art.getNombre());
                    art.setNumSerie(art.getNumSerie());
                    art.setExistencia(art.getExistencia());
                    art.setUnidad(art.getUnidad());
                    art.setEmpresa(art.getEmpresa());
                    art.setMarca(art.getMarca());
                    art.setEstockMinimo(art.getEstockMinimo());
                    art.setPrecio(art.getPrecio());
                    art.setDescripcion(art.getDescripcion());
                    art.setEstado(0);
                    sesion.update(art);
                    sesion.getTransaction().commit();
                    sesion.close();
            } catch (HibernateException he) {
                    he.printStackTrace();
            }
        }
        cargarTabla(); 
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
        lblCodigo = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtStockMinimo = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbEmpresa = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        txtNombre = new javax.swing.JTextField();
        lblNombre1 = new javax.swing.JLabel();
        cmbMarca = new javax.swing.JComboBox<>();
        cmbUnidad = new javax.swing.JComboBox<>();
        chkEstado = new javax.swing.JCheckBox();
        btnEmpresa = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArticulo = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        lblMantenimientoUsuario.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblMantenimientoUsuario.setText("Mantenimiento de Artículo");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar Artículos"));

        lblNombre.setText("id");

        lblCodigo.setText("Código");

        lblDireccion.setText("Cantidad");

        lblEmail.setText("Unidad");

        txtId.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtId.setText("id");
        txtId.setEnabled(false);
        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        txtCodigo.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtCodigo.setText("Código");

        txtCantidad.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtCantidad.setText("Cantidad");

        jLabel1.setText("Empresa");

        jLabel3.setText("Marca");

        jLabel4.setText("Stock Mínimo");

        jLabel5.setText("Precio");

        txtStockMinimo.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtStockMinimo.setText("Stock Mínimo");

        txtPrecio.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtPrecio.setText("Precio");

        jLabel6.setText("Estado");

        cmbEmpresa.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        cmbEmpresa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Empresa" }));

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
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

        txtNombre.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        txtNombre.setText("Nombre");
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        lblNombre1.setText("Nombre");

        cmbMarca.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        cmbMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Marca" }));
        cmbMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMarcaActionPerformed(evt);
            }
        });

        cmbUnidad.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        cmbUnidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unidad" }));
        cmbUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbUnidadActionPerformed(evt);
            }
        });

        chkEstado.setSelected(true);
        chkEstado.setEnabled(false);

        btnEmpresa.setText("Anadir empresa");
        btnEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpresaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCodigo)
                    .addComponent(lblDireccion)
                    .addComponent(lblEmail)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(lblNombre)
                    .addComponent(lblNombre1))
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCantidad)
                                    .addComponent(txtCodigo)
                                    .addComponent(txtStockMinimo, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                    .addComponent(txtPrecio)
                                    .addComponent(cmbEmpresa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbMarca, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbUnidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 3, Short.MAX_VALUE))
                            .addComponent(txtId, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEmpresa)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkEstado)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombre)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombre1)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDireccion)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEmail)
                    .addComponent(cmbUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEmpresa))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtStockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(chkEstado))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        tblArticulo.setModel(new javax.swing.table.DefaultTableModel(
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
        tblArticulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblArticuloMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblArticulo);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGuardar)
                .addGap(18, 18, 18)
                .addComponent(btnModificar)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Descripcion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane3.setViewportView(txtDescripcion);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(lblMantenimientoUsuario))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblMantenimientoUsuario)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(38, 38, 38)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
        activarCampos();
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnAgregar.setEnabled(false);
        btnBuscar.setEnabled(false);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void cmbMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMarcaActionPerformed

    private void cmbUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUnidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbUnidadActionPerformed

    private void tblArticuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblArticuloMouseClicked
       mostrarTabla();    
       desactivarCampos();
       
    }//GEN-LAST:event_tblArticuloMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
        desactivarCampos();
        btnGuardar.setText("Guardar");
        activarBotones();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
//         TODO add your handling code here:
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
            if(txtNombre.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Debe agregar un Nombre", "Nombre Vacío", JOptionPane.ERROR_MESSAGE);
            }else if(txtCodigo.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Debe agregar un Código", "Código Vacío", JOptionPane.ERROR_MESSAGE);
            }else if(txtCantidad.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Debe agregar una Cantidad", "Cantidad Vacía", JOptionPane.ERROR_MESSAGE);
            }else if(cmbUnidad.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(this, "Debe seleccionar una Unidad", "Unidad Vacía", JOptionPane.ERROR_MESSAGE);
            }else if(cmbEmpresa.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(this, "Debe seleccionar una Empresa", "Empresa Vacía", JOptionPane.ERROR_MESSAGE);
            }else if(cmbMarca.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(this, "Debe seleccionar una Marca", "Marca Vacía", JOptionPane.ERROR_MESSAGE);
            }else if(txtStockMinimo.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Debe agregar un Stock Mínimo", "Stock Mínimo Vacío", JOptionPane.ERROR_MESSAGE);
            }else if(txtPrecio.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Debe agregar un Precio", "Precio Vacío", JOptionPane.ERROR_MESSAGE);
            }else if(btnGuardar.getText().equals("Guardar")){
                String nombre = txtNombre.getText();
                String codigo = txtCodigo.getText();
                String descripcion = txtDescripcion.getText();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                String unid = cmbUnidad.getSelectedItem().toString();
                    Query query = sesion.createQuery("from Unidad u where u.descripcion = :des");
                    query.setParameter("des", unid);
                    Unidad uni = (Unidad)query.uniqueResult();
                String empr = cmbEmpresa.getSelectedItem().toString();
                    Query query2 = sesion.createQuery("from Empresa e where e.nombre = :nom");
                    query2.setParameter("nom", empr);
                    Empresa emp = (Empresa)query2.uniqueResult();
                String marc = cmbMarca.getSelectedItem().toString();
                    Query query3 = sesion.createQuery("from Marca m where m.nombre = :nom");
                    query3.setParameter("nom", marc);
                    Marca mar = (Marca)query3.uniqueResult();
                int stock = Integer.parseInt(txtStockMinimo.getText());
                Double precio = Double.parseDouble(txtPrecio.getText());
                Articulo art = new Articulo();
                    art.setNombre(nombre);
                    art.setNumSerie(codigo);
                    art.setExistencia(cantidad);
                    art.setUnidad(uni);
                    art.setEmpresa(emp);
                    art.setMarca(mar);
                    art.setEstockMinimo(stock);
                    art.setPrecio(precio);
                    art.setEstado(1);
                    art.setDescripcion(descripcion);
                sesion.save(art);
                sesion.getTransaction().commit();
                sesion.close();
                cargarTabla();
                limpiarCampos();
                desactivarCampos();
                JOptionPane.showMessageDialog(this, "Artículo guardado Correctamente");
                activarBotones();
            }
            
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpresaActionPerformed
        Frame Principal = null;
        crudEmpresa crud = new crudEmpresa(Principal, true);
        crud.setVisible(true);
    }//GEN-LAST:event_btnEmpresaActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // TODO add your handling code here:
        cargarCmbEmpresa();
        cargarCmbMarca();
        cargarCmbUnidad();
        
    }//GEN-LAST:event_formWindowGainedFocus

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

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
            java.util.logging.Logger.getLogger(crudArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(crudArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(crudArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(crudArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                crudArticulo dialog = new crudArticulo(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnEmpresa;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JCheckBox chkEstado;
    private javax.swing.JComboBox<String> cmbEmpresa;
    private javax.swing.JComboBox<String> cmbMarca;
    private javax.swing.JComboBox<String> cmbUnidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblMantenimientoUsuario;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombre1;
    private javax.swing.JTable tblArticulo;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtStockMinimo;
    // End of variables declaration//GEN-END:variables
}

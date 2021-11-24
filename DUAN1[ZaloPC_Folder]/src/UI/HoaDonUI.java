/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import DAO.CTHoaDonDAO;
import DAO.CTSachDAO;
import DAO.GiamGiaDAO;
import DAO.HoaDonDAO;
import DAO.KhachHangDAO;
import DAO.SachDAO;
import DAO.UserDAO;
import HELP.JdbcHelper;
import MODEL.CTHoaDonModel;
import MODEL.CTSachModel;
import MODEL.GiamGiaModel;
import MODEL.HoaDonModel;
import MODEL.KhachHangModel;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class HoaDonUI extends javax.swing.JPanel {

    private Webcam webcam;
    private WebcamPanel panel;
    private HoaDonDAO hDao = new HoaDonDAO();
    private UserDAO uDao = new UserDAO();
    private KhachHangDAO khDao = new KhachHangDAO();
    private CTSachDAO ctsdao = new CTSachDAO();
    private SachDAO sDAO = new SachDAO();
    private CTHoaDonDAO cthdDao = new CTHoaDonDAO();
    private List<CTHoaDonModel> lstCTHD = new ArrayList<>();
    private List<HoaDonModel> lstHD;
    private GiamGiaDAO ggDAO = new GiamGiaDAO();
    private List<Vector> lstLuuTru = new ArrayList<>();

    ;
    /**
     * Creates new form HoaDonUI
     */
    public HoaDonUI() {
        initComponents();

        camera();
        new Thread(new Runnable() {
            @Override
            public void run() {
                fillTableHDC();
            }

        }).start();
    }

    public void camera() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                init();
                while (true) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Result result = null;
                    BufferedImage image = null;
                    if (webcam.isOpen()) {
                        if ((image = webcam.getImage()) == null) {
                            continue;
                        }
                    }
                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    try {
                        result = new MultiFormatReader().decode(bitmap);
                    } catch (NotFoundException e) {
                        lbTB.setText("Không có kết quả");
                        continue;
                    }
                    if (result != null) {
                        lbTB.setText(result.getText());
                        try {
                            setSanPham(ctsdao.findById(result.getText()));
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }).start();
    }

    public void insert() {
        if (tbSP0.getRowCount() < 1) {
            return;
        }
        try {
            Double.parseDouble(txtTienDua.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Hãy nhập sốt tiền khách đưa!");
            return;
        }
        try {

            HoaDonModel hd = new HoaDonModel();
            hd.setSoTienKhachDua(Double.parseDouble(txtTienDua.getText()));
            hd.setThanhTien(Double.valueOf(lbTong.getText()));
            hd.setTenKH(txtTenKH.getText());
            int a = hDao.insert(hd);
            if (a > 0) {
                for (CTHoaDonModel x : lstCTHD) {
                    cthdDao.insert(x);
                }
                fillTableHDC();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        try {
            Dimension size = WebcamResolution.QVGA.getSize();
            webcam = Webcam.getWebcams().get(0);
            webcam.setViewSize(size);
            panel = new WebcamPanel(webcam);
            panel.setSize(pnWebCam.getSize());
            pnWebCam.add(panel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillTableHDC() {
        DefaultTableModel mod = (DefaultTableModel) tbHDC.getModel();
        mod.setRowCount(0);
        lstHD = hDao.select();
        if (lstHD == null) {
            return;
        }
        for (HoaDonModel x : lstHD) {
            mod.addRow(new Object[]{
                x.getTenKH(), x.getNgayMua(), x.getThanhTien(), x.getSoTienKhachDua(), uDao.findById(x.getUser()).getHoveten()
            });
        }
    }

    public CTHoaDonModel getHoaDonCT() {
        CTHoaDonModel cthdModel = new CTHoaDonModel();
        cthdModel.setGia(Double.valueOf(txtPrice.getText()));
        cthdModel.setId_CTsach(txtTenSP.getToolTipText());
        cthdModel.setGiamGia(Double.valueOf(txtGiamGia.getText()));
        cthdModel.setSlBan((int) snSL.getValue());
        return cthdModel;
    }

    public void setSanPham(CTSachModel model) {
        try {
            txtTenSP.setText(String.valueOf(sDAO.findById(model.getId_sach()).getTenSach()));
            txtPrice.setText(String.valueOf(model.getGia()));
            snSL.setValue(1);
            txtGiamGia.setText(ggDAO.findByIdCTSSach(model.getId()).getGiamGia() + "");
            txtTenSP.setToolTipText(model.getId());
        } catch (Exception e) {
            txtTenSP.setText("");
            txtPrice.setText("");
            snSL.setValue(1);
            txtGiamGia.setText("");
        }
    }

    public void fillTableSP(List<CTHoaDonModel> CTS, JTable tb) {
        DefaultTableModel mod = (DefaultTableModel) tb.getModel();
        mod.setRowCount(0);
        if (CTS == null) {
            return;
        }
        for (CTHoaDonModel x : CTS) {
            mod.addRow(new Object[]{
                sDAO.findById(ctsdao.findById(x.getId_CTsach()).getId_sach()).getTenSach(),
                x.getGia(),
                x.getSlBan(),
                x.getGiamGia(),
                x.getGia() * x.getSlBan() * (100 - x.getGiamGia()) / 100
            });
        }
    }

    public void clearForm() {
        txtGiamGia.setText("");
        txtPrice.setText("");
        txtTenKH.setText("");
        txtTenSP.setText("");
        txtTienDua.setText("");
        lbTL.setText("0");
        lbTong.setText("0");
    }

    public void fillTableCHT() {
        DefaultTableModel mod = (DefaultTableModel) tbCHT.getModel();
        mod.setRowCount(0);
        if (lstLuuTru == null) {
            return;
        }
        for (int i = 0; i < lstLuuTru.size(); i++) {
            Vector vt = lstLuuTru.get(i);
            mod.addRow(new Object[]{
                vt.get(0), vt.get(1)
            });
        }
    }

    
    public boolean check() {
        try {
            int sl = (int) snSL.getValue();
            if (sl < 1) {
                JOptionPane.showMessageDialog(this, "Nhập số lượng!");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        if (txtTenSP.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có dữ liệu!");
            return true;
        }
        if (lstCTHD == null) {
            return true;
        }
        for (CTHoaDonModel x : lstCTHD) {
            if (x.getId_CTsach().equalsIgnoreCase(getHoaDonCT().getId_CTsach())) {
                int a = JOptionPane.showConfirmDialog(this, "Update số lượng cho " + txtTenSP.getText() + "?", "", JOptionPane.YES_NO_OPTION);
                if (a == 0) {
                    x.setSlBan((int) snSL.getValue());
                    fillTableSP(lstCTHD, tbSP0);
                }
                tinhTienThanhToan();
                return true;
            }
        }
        return false;
    }
    private void tinhTienThanhToan(){
        double tong=0;
        for (int i = 0; i < tbSP0.getRowCount(); i++) {
            tong = tong + (double) tbSP0.getValueAt(i, 4);
        }
        lbTong.setText(tong + "");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        tab = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbSP0 = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        btTHD = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btDelete = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtTienDua = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbTong = new javax.swing.JLabel();
        lbTL = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbHDC = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbSP1 = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbCHT = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbSp2 = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        pnWebCam = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        lbTB = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        snSL = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtGiamGia = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new java.awt.CardLayout());

        tab.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel5.setLayout(new java.awt.BorderLayout());

        tbSP0.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên sản phẩm", "Giá(VND)", "Số lượng", "Giảm giá(%)", "Thành tiền(VND)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbSP0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSP0MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbSP0);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel21.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        btTHD.setText("Thanh toán và tạo hóa đơn");
        btTHD.setEnabled(false);
        btTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTHDActionPerformed(evt);
            }
        });

        jButton2.setText("Lưu trữ hóa đơn");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btDelete.setText("Bỏ sản phẩm");
        btDelete.setEnabled(false);
        btDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeleteActionPerformed(evt);
            }
        });

        jLabel1.setText("Số tiền khách đưa:");

        txtTienDua.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTienDuaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTienDuaFocusLost(evt);
            }
        });
        txtTienDua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienDuaActionPerformed(evt);
            }
        });

        jLabel12.setText("Tên khách:");

        jLabel5.setText("Tổng tiền cần thanh toán:");

        lbTong.setText("0");

        lbTL.setText("0");

        jButton7.setText("Thêm thẻ thành viên");

        jButton1.setText("Tính tiền trả lại");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(btTHD)
                .addContainerGap(363, Short.MAX_VALUE))
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(txtTienDua, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(lbTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(2, 2, 2))
                            .addComponent(lbTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel5)
                    .addComponent(lbTong))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTienDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTL)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btTHD)
                    .addComponent(jButton2)
                    .addComponent(btDelete)
                    .addComponent(jButton7)))
        );

        jPanel5.add(jPanel21, java.awt.BorderLayout.PAGE_END);

        tab.addTab("Sản phẩm", jPanel5);

        jPanel9.setLayout(new java.awt.GridLayout(2, 1));

        tbHDC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tên người mua", "Thời gian mua", "Tổng tiền thanh toán", "Số tiền khách đưa", "Người bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbHDC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHDCMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbHDC);

        jPanel9.add(jScrollPane2);

        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.gray, java.awt.Color.gray));
        jPanel19.setLayout(new java.awt.BorderLayout());

        tbSP1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tên sản phẩm", "Giá(VND)", "Số lượng", "Giảm giá(%)", "Thành tiền(VND)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tbSP1);
        if (tbSP1.getColumnModel().getColumnCount() > 0) {
            tbSP1.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel19.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Sản phẩm có trong hóa đơn");
        jPanel20.add(jLabel7);

        jPanel19.add(jPanel20, java.awt.BorderLayout.PAGE_START);

        jPanel9.add(jPanel19);

        tab.addTab("Hóa đơn", jPanel9);

        jPanel2.setLayout(new java.awt.GridLayout(2, 0));

        tbCHT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên người mua", "Tổng tiền "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbCHT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCHTMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbCHT);

        jPanel2.add(jScrollPane3);

        jPanel22.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.gray, java.awt.Color.gray));
        jPanel22.setLayout(new java.awt.BorderLayout());

        tbSp2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên sản phẩm", "Giá(VND)", "Số lượng", "Giảm giá(%)", "Thành tiền(VND)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tbSp2);

        jPanel22.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Sản phẩm có trong hóa đơn");
        jPanel23.add(jLabel11);

        jPanel22.add(jPanel23, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(jPanel22);

        tab.addTab("Hóa đơn chưa hoàn thiện", jPanel2);

        jPanel6.add(tab, "card2");

        add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.GridLayout(2, 1));

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        pnWebCam.setBackground(new java.awt.Color(51, 102, 0));

        javax.swing.GroupLayout pnWebCamLayout = new javax.swing.GroupLayout(pnWebCam);
        pnWebCam.setLayout(pnWebCamLayout);
        pnWebCamLayout.setHorizontalGroup(
            pnWebCamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );
        pnWebCamLayout.setVerticalGroup(
            pnWebCamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 174, Short.MAX_VALUE)
        );

        lbTB.setText("Không xác định");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTB, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnWebCam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 37, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnWebCam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4);

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Tên sản phẩm:");

        txtTenSP.setEditable(false);

        jLabel4.setText("Giá:");

        txtPrice.setEditable(false);
        txtPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPriceFocusGained(evt);
            }
        });

        jLabel6.setText("Số lượng:");

        jButton3.setText("Thêm vào hóa đơn");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Làm mới");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        snSL.setName(""); // NOI18N
        snSL.setValue(1);

        jLabel16.setText("Giảm giá:");

        jLabel10.setText("%");

        txtGiamGia.setEditable(false);
        txtGiamGia.setText("0");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                            .addComponent(txtPrice)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(snSL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel10)))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(snSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(86, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel7);

        add(jPanel3, java.awt.BorderLayout.LINE_END);

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel3.setText("Quản lý hóa đơn");
        jPanel1.add(jLabel3);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setText("Trạng thái");
        jPanel11.add(jLabel9);

        add(jPanel11, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPriceFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_txtPriceFocusGained

    private void tbHDCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHDCMouseClicked
        // TODO add your handling code here:
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int index = tbHDC.getSelectedRow();
                    fillTableSP(cthdDao.findByIdHD(lstHD.get(index).getId()), tbSP1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }//GEN-LAST:event_tbHDCMouseClicked

    private void txtTienDuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienDuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienDuaActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
            if (check()) {
                return;
            }
            if (!check()) {
                lstCTHD.add(getHoaDonCT());
            }
            fillTableSP(lstCTHD, tbSP0);
            tinhTienThanhToan();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tbSP0MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSP0MouseClicked
        btDelete.setEnabled(true);
        try {
            int index= tbSP0.getSelectedRow();
            setSanPham(ctsdao.findById(lstCTHD.get(index).getId_CTsach()));
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tbSP0MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this, "Xác nhận lưu trữ hóa đơn!", "", JOptionPane.YES_NO_OPTION) == 1) {
            return;
        }
        String tenKh = txtTenKH.getText();
        if (tenKh.isEmpty() || tbSP0.getRowCount() < 1) {
            JOptionPane.showMessageDialog(this, "Chưa có tên khách hàng hoặc chưa có sản phầm!");
            return;
        }
        try {
            Vector vt = new Vector();
            vt.add(tenKh);
            vt.add(lbTong.getText());
            vt.addElement(lstCTHD);
            lstLuuTru.add(vt);
            lstCTHD = null;
        } catch (Exception e) {
        }
        fillTableSP(lstCTHD, tbSP0);
        fillTableCHT();
        clearForm();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tbCHTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCHTMouseClicked
        // TODO add your handling code here:
        
        try {
            int index = tbCHT.getSelectedRow();
            if (evt.getClickCount() == 2 ) {
                if(JOptionPane.showConfirmDialog(this,"Xác nhập chuyển qua Sản phẩm để hoàn thiện và thanh toán?","",JOptionPane.YES_NO_OPTION)==1) return;
                lstCTHD = (List<CTHoaDonModel>) lstLuuTru.get(index).get(2);
                txtTenKH.setText((String) lstLuuTru.get(index).get(0));
                tab.setSelectedIndex(0);
                lstLuuTru.remove(index);
                fillTableCHT();
                fillTableSP(lstCTHD, tbSP0);
                tinhTienThanhToan();
                tbSp2.removeAll();
                return;
            }
            Vector vt = lstLuuTru.get(index);
            fillTableSP((List<CTHoaDonModel>) vt.get(2), tbSp2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_tbCHTMouseClicked

    private void btTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTHDActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this, "Xác nhận tạo hóa đơn!", "", JOptionPane.YES_NO_OPTION) != 0) {
            return;
        }
        try {
            insert();
            lstCTHD.clear();
            fillTableSP(lstCTHD, tbSP0);
            btTHD.setEnabled(false);
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btTHDActionPerformed

    private void txtTienDuaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTienDuaFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTienDuaFocusGained

    private void txtTienDuaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTienDuaFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTienDuaFocusLost

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        setSanPham(new CTSachModel());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeleteActionPerformed
        try {
            int index = tbSP0.getSelectedRow();
            lstCTHD.remove(index);
            fillTableSP(lstCTHD, tbSP0);
            btDelete.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Chọn sản phẩm trên bảng!");
        }
    }//GEN-LAST:event_btDeleteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            if (tbSP0.getRowCount() < 1) {
                return;
            }
            double TKD = Double.valueOf(txtTienDua.getText());
            double tong = Double.valueOf(lbTong.getText());
            if (TKD - tong < 0) {
                JOptionPane.showMessageDialog(this, "Thiếu tiền!");
                btTHD.setEnabled(false);
                return;
            }
            lbTL.setText((TKD - tong) + "");
            btTHD.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Nhập số!");

        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btTHD;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbTB;
    private javax.swing.JLabel lbTL;
    private javax.swing.JLabel lbTong;
    private javax.swing.JPanel pnWebCam;
    private javax.swing.JSpinner snSL;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTable tbCHT;
    private javax.swing.JTable tbHDC;
    private javax.swing.JTable tbSP0;
    private javax.swing.JTable tbSP1;
    private javax.swing.JTable tbSp2;
    private javax.swing.JTextField txtGiamGia;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTienDua;
    // End of variables declaration//GEN-END:variables
}

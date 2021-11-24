/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.CTSachModel;
import MODEL.HoaDonModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CTSachDAO implements QLDAO<CTSachModel, String>{
    
    
    String INSERT_SQL = "INSERT INTO CTSACH(ID,VITRI,GIA,soLuong) VALUES (?,?,?,?)";
    String UPDATE_SQL = "UPDATE dbo.CTSACH SET VITRI =?, GIA = ? , soLuong = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM dbo.HocVien WHERE MaHV = ?";///chưa chắc
    String SELECT_ALL_SQL = "SELECT * FROM dbo.CTSACH";
    String SELECT_BY_ID = "SELECT * FROM dbo.CTSACH WHERE [ID] = ?";
    
    @Override
    public int insert(CTSachModel e) {
       JdbcHelper.executeUpdate(INSERT_SQL,
                e.getId(), e.getVitri(), e.getGia(), e.getSoLuong());
        return 0;
      
    }

    @Override
    public int update(CTSachModel e) {
        JdbcHelper.executeUpdate(INSERT_SQL,
                 e.getVitri(), e.getGia(), e.getSoLuong());
        return 0;
    }

    @Override
    public void delete(String k) {
       
    }

    @Override
    public List<CTSachModel> select() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CTSachModel findById(String k) {
        String sql= "select * from CTsach where id=? ";
        return select(sql, k).get(0);
    }

    @Override
    public List<CTSachModel> select(String sql, Object... args) {
        List<CTSachModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    CTSachModel model=readFromResultSet(rs);
                    list.add(model);
                }
            }
            finally{
                rs.getStatement().getConnection().close();
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    private CTSachModel readFromResultSet(ResultSet rs) throws SQLException{
        CTSachModel model=new CTSachModel();
        model.setGia(rs.getDouble("gia"));
        model.setId(rs.getString("id"));
        model.setId_Bia(rs.getInt("id_bia")); 
        model.setId_NN(rs.getInt("id_nn"));
        model.setId_NXB(rs.getInt("id_nxb"));
        model.setId_sach(rs.getInt("id_sach"));
        model.setSoLuong(rs.getInt("soLuong"));
        model.setSoTrang(rs.getInt("soTrang"));
        model.setTap(rs.getInt("tap"));
        model.setVitri(rs.getString("vitri"));
        model.setTrangThai(rs.getString("TrangThai"));
        return model;
    }
    
}

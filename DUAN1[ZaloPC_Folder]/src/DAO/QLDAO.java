
package DAO;
import java.util.List;

public interface QLDAO<Entity, Ken>{
    abstract  int insert(Entity e);
    abstract  int update(Entity e);
    abstract  void delete(Ken k);
    abstract  List<Entity> select();
    abstract  Entity findById(Ken k);
    abstract  List<Entity> select(String sql, Object... args);
}

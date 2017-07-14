package la.kaike.ksearch.biz.dal.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import la.kaike.ksearch.model.dbo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 管理员表 Mapper 接口
 * </p>
 *
 * @author chenghuanhuan
 * @since 2017-06-20
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据条件查询用户列表
     * @param name
     * @param beginTime
     * @param endTime
     * @param deptid
     * @return
     */
    List<Map<String, Object>> selectUsers(@Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("deptid") Integer deptid);

}
package la.kaike.ksearch.biz.dal.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import la.kaike.ksearch.model.dbo.user.User;
import la.kaike.ksearch.model.vo.user.UserPageReqVO;

import java.util.List;

/**
 * <p>
  * 管理员表 Mapper 接口
 * </p>
 *
 * @author chenghuanhuan
 * @since 2017-06-20
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> selectListPage(UserPageReqVO userPageReqVO);
}
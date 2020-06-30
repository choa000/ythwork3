package cn.edu.nenu.repository.mybatis;

import cn.edu.nenu.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * UserMapper Class
 *
 * @author <b>Oxidyc</b>, Copyright &#169; 2003
 * @version 1.0, 2020-04-09 15:16
 */
//@Repository
//@Mapper
public interface UserMapper {

    @Select("select id,name,username,password from ssh_user where id=?1")
    List<User> getPage(int pageNumber, int pageSize, Map<String, Object> param);

    @Select("select id,name,username,password from ssh_user limit #{start},#{limit}")
    List<User> findAll(@Param("start") int start, @Param("limit") int limit);

}

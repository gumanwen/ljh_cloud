package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.BizSignPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BizSignPointRepository extends JpaRepository<BizSignPoint,Integer>, JpaSpecificationExecutor<BizSignPoint> {

    @Query("update BizSignPoint t set t.enabled = '0' where t.id = ?1")
    BizSignPoint deleteSignPoint(Integer id);

    @Query(value = "SELECT a.*,b.route_type,b.point_inspection_type from biz_sign_point a LEFT JOIN biz_route b on a.route_id = b.id where a.id = ?1 and a.enabled = 1",nativeQuery = true)
    //@Query(value = "SELECT new com.yaobanTech.springcloud.domain.RoutePoint(b,a) from BizSignPoint a,BizRoute b where a.routeId=b.id and a.id=?1")
    BizSignPoint findSignPointById(Integer id);

    @Query(value = "SELECT a.*,b.route_type,b.point_inspection_type from biz_sign_point a LEFT JOIN biz_route b on a.route_id = b.id where b.id = ?1 and a.enabled = 1",nativeQuery = true)
    List<BizSignPoint> findSignPointListByRouteId(Integer routeId);

    @Query(value = "SELECT a.*,b.route_type,b.point_inspection_type from biz_sign_point a LEFT JOIN biz_route b on a.route_id = b.id where b.id = ?1 and a.sign_point_status = '合格' and a.enabled = 1",nativeQuery = true)
    List<BizSignPoint> findSignedList(Integer routeId);

    @Query(value = "insert into biz_files (`name`,pid,url,upload_date,remark,`type`,mime_type,isvalid) select `name`,?1 as pid,url,upload_date,remark,'qddfj' as type,mime_type,isvalid from  biz_files where pid =?2 and type = 'yhdfj'",nativeQuery = true)
    void copyFiles(String pid,String code);

//    @Query(value = "update biz_sign_point t set t.sign_point_status = '合格' where t.sign_point_code = ?1",nativeQuery = true)
//    @Modifying
//    void syncStatus(Integer id);
}

package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.BizSignedPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BizSignedPointRepository extends JpaRepository<BizSignedPoint,Integer>, JpaSpecificationExecutor<BizSignedPoint> {

//    @Query("update BizSignPoint t set t.enabled = '0' where t.id = ?1")
//    BizSignPoint deleteSignPoint(Integer id);
//
//    @Query(value = "SELECT a.*,b.route_type,b.point_inspection_type from biz_sign_point a LEFT JOIN biz_route b on a.route_id = b.id where a.id = ?1 and a.enabled = 1",nativeQuery = true)
//    //@Query(value = "SELECT new com.yaobanTech.springcloud.domain.RoutePoint(b,a) from BizSignPoint a,BizRoute b where a.routeId=b.id and a.id=?1")
//    BizSignPoint findSignPointById(Integer id);
//
//    @Query(value = "SELECT a.*,b.route_type,b.point_inspection_type from biz_sign_point a LEFT JOIN biz_route b on a.route_id = b.id where b.id = ?1 and a.enabled = 1",nativeQuery = true)
//    List<BizSignPoint> findSignPointListByRouteId(Integer routeId);

    @Query(value = "SELECT a.*,b.route_type,b.point_inspection_type from biz_signed_point a LEFT JOIN biz_route b on a.route_id = b.id where b.id = ?1 and a.task_id = ?2 and a.enabled = 1",nativeQuery = true)
    List<BizSignedPoint> findSignedList(Integer routeId,String taskId);

}

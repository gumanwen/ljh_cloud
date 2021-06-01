package com.yaobanTech.springcloud.repository;

import com.yaobanTech.springcloud.domain.BizRoute;
import com.yaobanTech.springcloud.domain.BizSignedPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BizSignedPointRepository extends JpaRepository<BizSignedPoint,Integer>, JpaSpecificationExecutor<BizSignedPoint> {

    @Query(value = "SELECT a.*,b.route_type,b.point_inspection_type from biz_signed_point a LEFT JOIN biz_route b on a.route_id = b.id where b.id = ?1 and a.task_id = ?2 and a.enabled = 1 and a.sign_point_status = '合格'",nativeQuery = true)
    List<BizSignedPoint> findSignedList(Integer routeId,String taskId);

    @Query(value = "SELECT a.*,b.route_type,b.point_inspection_type from biz_signed_point a LEFT JOIN biz_route b on a.route_id = b.id where b.id = ?1 and a.task_id = ?2 and a.enabled = 1 ",nativeQuery = true)
    List<BizSignedPoint> findListByTaskId(Integer routeId,String taskId);

    @Query(value = "select * from biz_signed_point where enabled = 1 and id = ?1",nativeQuery = true)
    BizSignedPoint findbyId(Integer id);

    @Query(value = "from BizRoute t where t.routeCreator = ?1 and t.pointInspectionType = '0'")
    List<BizRoute> findEndPointList(String user);
}

package org.saikumo.pwams.repository;

import org.saikumo.pwams.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Integer> {
    public Activity findById(Long id);

    public List findByStatus(String status);

    public List findByMentorIdAndStatus(Long MentorId,String status);
}

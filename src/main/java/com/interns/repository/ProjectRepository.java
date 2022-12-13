package com.interns.repository;

import org.springframework.data.repository.CrudRepository;

import com.interns.entity.Project;

public interface ProjectRepository extends CrudRepository<Project, Integer>
{

    // add methods if required

}

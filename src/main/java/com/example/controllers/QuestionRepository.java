package com.example.controllers;

import com.example.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface QuestionRepository extends JpaRepository<Question,Integer> {
}

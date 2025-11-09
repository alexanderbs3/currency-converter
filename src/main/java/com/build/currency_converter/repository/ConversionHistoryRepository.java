package com.build.currency_converter.repository;

import com.build.currency_converter.entity.ConversionHistory;
import com.build.currency_converter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversionHistoryRepository extends JpaRepository<ConversionHistory,Long> {
    List<ConversionHistory> findTop10ByUserOrderByConversionDateDesc(User user);
}

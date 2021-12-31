package com.ibm.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibm.order.entity.ItemList;

@Repository
public interface ItemListRepository extends JpaRepository<ItemList, Integer> {

}

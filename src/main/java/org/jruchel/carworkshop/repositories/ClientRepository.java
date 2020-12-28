package org.jruchel.carworkshop.repositories;

import org.jruchel.carworkshop.entities.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Integer> {

    @Query(nativeQuery = true, value = "select * from client where phone_number = ?1 limit 1")
    Client findByPhone(String phone);

    @Query(nativeQuery = true, value = "select * from client where email = ?1 limit 1")
    Client findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * from client where client.id in (select distinct orders.client_id from orders where orders.responded = false)")
    List<Client> getUnrespondedClients(Pageable pageable);

    @Query(nativeQuery = true, value = "select * from client where client.id in (select distinct orders.client_id from orders where orders.responded = true and orders.complete = false)")
    List<Client> getAwaitingClients(Pageable pageable);

    @Query(nativeQuery = true, value = "select distinct client_id, date into clients_by_latest_order from orders where responded = false order by date")
    void createClientsByLatestOrderUnresponded();

    @Query(nativeQuery = true, value = "drop table clients_by_latest_order")
    void dropClientsByLatestOrder();

    @Query(nativeQuery = true, value = "select client_id from clients_by_latest_order")
    List<Integer> getClientsByLatestOrder(Pageable pageable);
}
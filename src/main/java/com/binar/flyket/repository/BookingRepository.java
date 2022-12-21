package com.binar.flyket.repository;

import com.binar.flyket.dto.model.BookingDTO;
import com.binar.flyket.dto.model.BookingDetailDTO;
import com.binar.flyket.dto.model.MyOrderDTO;
import com.binar.flyket.model.Booking;
import com.binar.flyket.model.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query(value = "SELECT NEW com.binar.flyket.dto.model.MyOrderDTO(fs.id, bk.id, " +
            " bk.totalPassenger, fs.arrivalTime, fs.departureTime, fs.flightRoute.fromAirport.IATACode , fs.flightRoute.toAirport.IATACode," +
            " bk.updatedAt, bk.bookingStatus, " +
            " fs.flightRoute.hours, fs.flightRoute.minutes, fs.aircraftDetail.price) " +
            "FROM Booking AS bk " +
            "JOIN bk.flightSchedule AS fs " +
            "WHERE bk.user.id = :user_id")
    Page<MyOrderDTO> getRecentOrderByUser(@Param("user_id") String userId, Pageable pageable);

    @Query(value = "SELECT NEW com.binar.flyket.dto.model.BookingDTO(usr.id, " +
            " usr.email, usr.phoneNumber, bk.id, bk.amount, bk.bookingStatus, bk.createdAt, bk.updatedAt) " +
            "FROM Booking AS bk " +
            "JOIN bk.flightSchedule AS fs " +
            "JOIN bk.user AS usr " +
            "WHERE bk.user.id = usr.id " +
            "AND bk.flightSchedule.id = fs.id ")
    Page<BookingDTO> findAllBooking(Pageable pageable);

    @Query(value = "SELECT NEW com.binar.flyket.dto.model.BookingDetailDTO(bk.id, fs.flightRoute.fromAirport.IATACode, " +
            "fs.flightRoute.toAirport.IATACode, fs.flightRoute.hours, fs.flightRoute.minutes, bk.amount) " +
            "FROM Booking AS bk " +
            "JOIN bk.flightSchedule AS fs " +
            "WHERE bk.id = :booking_id")
    Optional<BookingDetailDTO> getBookingDetail(@Param("booking_id") String bookingId);

    @Query(value = "SELECT bk FROM Booking bk WHERE bk.expiredTime < :current_time AND bk.bookingStatus =:status")
    List<Booking> checkStatusBooking(@Param("current_time") Long currentTime,
                                     @Param("status")BookingStatus bookingStatus);

    @Query(value = "SELECT NEW com.binar.flyket.dto.model.BookingDTO(usr.id, " +
            " usr.email, usr.phoneNumber, bk.id, bk.amount, bk.bookingStatus, bk.createdAt, bk.updatedAt) " +
            "FROM Booking AS bk " +
            "JOIN bk.flightSchedule AS fs " +
            "JOIN bk.user AS usr " +
            "WHERE bk.user.id = usr.id " +
            "AND bk.flightSchedule.id = fs.id " +
            "AND bk.bookingStatus = :booking_status")
    Page<BookingDTO> validateBookingList(
            @Param("booking_status") BookingStatus bookingStatus,
            Pageable pageable);
}
